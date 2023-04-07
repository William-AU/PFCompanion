package application.view.characterViews;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.ConsoleService;
import application.storage.services.ServiceContext;
import application.view.Scene;
import application.view.builders.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.options.OptionGrid;
import application.view.options.SimpleOption;
import application.view.strategies.MoveOverOptionMovementStrategy;
import application.view.strategies.NullMovementStrategy;
import application.view.strategies.OptionMovementStrategy;

public class CreateCharacterScene implements Scene {
    private ServiceContext serviceContext;
    private OptionMovementStrategy optionMovementStrategy;
    private final OptionGrid optionGrid;
    // Scene specific state
    private boolean acceptChars;
    private String currentNameInput;
    private boolean isEnteringName;

    public CreateCharacterScene() {
        this.optionGrid = createOptionGrid();
        this.optionMovementStrategy = new MoveOverOptionMovementStrategy();
        this.currentNameInput = "";
        this.isEnteringName = false;
        this.acceptChars = false;
    }

    private OptionGrid createOptionGrid() {
        OptionGridBuilder builder = new OptionGridBuilder();
        builder.addOptionsToNewRow(
                new SimpleOption("Back", "0"),
                new SimpleOption("Enter name", "1"),
                new SimpleOption("Next", "2"));


        OptionGrid res = builder.build();
        // Ensure middle button is the initial highlighted option
        res.moveRight();
        return res;
    }

    @Override
    public void draw() {
        LayoutBuilder layoutBuilder = new LayoutBuilder(serviceContext);
        layoutBuilder.setCenter(true)
                .setDistanceBetweenOptions(6)
                .addOptionRow(optionGrid.getOptionRow(0));
        if (isEnteringName) {
            layoutBuilder.addLine("[" + currentNameInput + "]");
        } else {
            layoutBuilder.addLine(currentNameInput);
        }
        System.out.println(layoutBuilder.build());
    }

    /**
     * Indicated that the user confirmed an option, usually by pressing enter
     * @return Returns a new {@link Scene} if the action requires a scene change, otherwise returns null
     */
    @Override
    public Scene confirm() {
        switch (optionGrid.getCurrentOption().getId()) {
            case "0" -> {
                return new CharacterSelectionScene();
            }
            case "1" -> {
                // Enter name option
                toggleTyping();
                return this;
            }
            case "2" -> {
                // Send user to the next screen with created character
                // TODO: Implement
                return null;
            }
        };
        return null;
    }

    /**
     * Updates the view with the given char input, return true if anything changed
     * @param ch The char pressed
     * @return True if the char changed anything about the view state, false otherwise. Defaults to false as to be consistent with {@link #shouldAcceptLetters()}
     */
    @Override
    public boolean inputChar(char ch) {
        if (!isEnteringName) return false;
        currentNameInput += ch;
        return true;
    }

    @Override
    public boolean shouldAcceptLetters() {
        return acceptChars;
    }

    private void toggleTyping() {
        if (isEnteringName) {
            isEnteringName = false;
            acceptChars = false;
            optionMovementStrategy = new MoveOverOptionMovementStrategy();
        } else {
            isEnteringName = true;
            acceptChars = true;
            optionMovementStrategy = new NullMovementStrategy();
        }
    }

    /**
     * Updates the view with the given key input, return true if anything changed
     *
     * @param key Key pressed
     * @return True if the key press changed anything about the view state, false otherwise
     */
    @Override
    public boolean inputKey(ListenerKey key) {
        // Special case for backspace
        switch (key) {
            case BACKSPACE -> {
                if (currentNameInput.isBlank()) return false;
                currentNameInput = currentNameInput.substring(0, currentNameInput.length() - 1);
                return true;
            }
            case ESCAPE -> {
                if (isEnteringName) {
                    toggleTyping();
                    return true;
                }
                return false;
            }
            case TAB -> {
                if (isEnteringName) {
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
    public void setServiceContext(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }
}
