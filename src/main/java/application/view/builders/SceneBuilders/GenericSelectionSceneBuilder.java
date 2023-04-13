package application.view.builders.SceneBuilders;

import application.listeners.ListenerKey;
import application.storage.services.ServiceContext;
import application.view.Scene;
import application.view.builders.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.options.OptionGrid;
import application.view.options.SimpleOption;
import application.view.strategies.MoveOverOptionMovementStrategy;
import application.view.strategies.NullMovementStrategy;
import application.view.strategies.OptionMovementStrategy;

/**
 * Creates a generic scene with a back button, an input prompt, and a next button
 * After the input is confirmed, the builder will return a new view, this view must implement {@link FutureCapableScene} to receive the prompt input.
 * This builder requires a previous {@link Scene} object which the back button points to
 */
public class GenericSelectionSceneBuilder {
    private FutureCapableScene nextScene;
    private final Scene previousScene;
    private String inputButtonlabel;
    private int distanceBetweenOptions;

    public GenericSelectionSceneBuilder(Scene previousScene) {
        this.previousScene = previousScene;
        this.distanceBetweenOptions = 6;
    }

    public GenericSelectionSceneBuilder setNextScene(FutureCapableScene scene) {
        this.nextScene = scene;
        return this;
    }

    public GenericSelectionSceneBuilder setInputButtonLabel(String label) {
        this.inputButtonlabel = label;
        return this;
    }

    public GenericSelectionSceneBuilder setDistanceBetweenOptions(int distance) {
        this.distanceBetweenOptions = distance;
        return this;
    }


    public Scene build() {
        return new GenericScene(inputButtonlabel,
                distanceBetweenOptions,
                previousScene,
                nextScene);
    }

    /**
     *
     */
    public interface FutureCapableScene extends Scene {
        void setFutureString(String string);
    }
    private class GenericScene implements Scene {
        private ServiceContext serviceContext;
        private OptionMovementStrategy optionMovementStrategy;
        private final OptionGrid optionGrid;
        private String currentInput;
        private boolean isTyping;
        // SET FROM BUILDER
        private final String inputButtonLabel;
        private final int distanceBetweenOptions;
        private final Scene previousScene;
        private final GenericSelectionSceneBuilder.FutureCapableScene nextScene;

        public GenericScene(String inputButtonLabel, int distanceBetweenOptions, Scene previousScene, GenericSelectionSceneBuilder.FutureCapableScene nextScene) {
            this.inputButtonLabel = inputButtonLabel;
            this.distanceBetweenOptions = distanceBetweenOptions;
            this.previousScene = previousScene;
            this.nextScene = nextScene;
            this.optionGrid = createOptionGrid();
            this.optionMovementStrategy = new MoveOverOptionMovementStrategy();
            this.currentInput = "";
            this.isTyping = false;
        }

        private OptionGrid createOptionGrid() {
            OptionGridBuilder builder = new OptionGridBuilder();
            builder.addOptionsToNewRow(
                    new SimpleOption("Back", "0"),
                    new SimpleOption(inputButtonLabel, "2"),
                    new SimpleOption("Next", "1")
            );
            OptionGrid res = builder.build();
            res.moveRight();
            return res;
        }

        @Override
        public void draw() {
            LayoutBuilder layoutBuilder = new LayoutBuilder(serviceContext);
            layoutBuilder.setCenter(true)
                    .setDistanceBetweenOptions(distanceBetweenOptions)
                    .addOptionRow(optionGrid.getOptionRow(0));
            if (isTyping) {
                layoutBuilder.addLine("[" + currentInput + "]");
            } else {
                layoutBuilder.addLine(currentInput);
            }
            System.out.println(layoutBuilder.build());
        }

        @Override
        public Scene confirm() {
            switch (optionGrid.getCurrentOption().getId()) {
                case "0" -> {
                    return previousScene;
                }
                case "1" -> {
                    nextScene.setFutureString(currentInput);
                    return nextScene;
                }
                case "2" -> {
                    toggleTyping();
                    return this;
                }
            };
            return null;
        }

        private void toggleTyping() {
            if (isTyping) {
                isTyping = false;
                optionMovementStrategy = new MoveOverOptionMovementStrategy();
            } else {
                isTyping = true;
                optionMovementStrategy = new NullMovementStrategy();
            }
        }


        @Override
        public boolean inputKey(ListenerKey key) {
            switch (key) {
                case BACKSPACE -> {
                    if (currentInput.isBlank()) return false;
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    return true;
                }
                case ESCAPE -> {
                    if (isTyping) {
                        toggleTyping();
                        return true;
                    }
                    return false;
                }
                case TAB, SHIFT_TAB -> {
                    if (isTyping) {
                        toggleTyping();
                    }
                    return optionMovementStrategy.handleMove(optionGrid, key);
                }
                default -> {
                    return optionMovementStrategy.handleMove(optionGrid, key);
                }
            }
        }

        @Override
        public boolean inputChar(char ch) {
            if (!isTyping) return false;
            currentInput += ch;
            return true;
        }

        @Override
        public boolean shouldAcceptLetters() {
            return isTyping;
        }

        @Override
        public void setServiceContext(ServiceContext serviceContext) {
            this.serviceContext = serviceContext;
        }
    }
}
