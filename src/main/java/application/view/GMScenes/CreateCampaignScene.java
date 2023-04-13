package application.view.GMScenes;

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

public class CreateCampaignScene implements Scene {
    private ServiceContext serviceContext;
    private OptionMovementStrategy optionMovementStrategy;
    private final OptionGrid optionGrid;
    private boolean isTyping;
    private String currentInput;

    public CreateCampaignScene() {
        this.optionMovementStrategy = new MoveOverOptionMovementStrategy();
        this.optionGrid = createOptionGrid();
        this.isTyping = false;
        this.currentInput = "";
    }

    private OptionGrid createOptionGrid() {
        OptionGridBuilder builder = new OptionGridBuilder();
        builder.addOptionsToNewRow(
                new SimpleOption("Back", "0")
        );
        builder.addMutableOptionToCurrentRow(
                new SimpleOption("<Enter name>", "2"),
                new SimpleOption("<Placeholder>", "3")
        );
        builder.addOptionsToCurrentRow(
                new SimpleOption("Next", "2")
        );
        OptionGrid res = builder.build();
        System.out.println("Created grid");
        System.out.println(res.DEBUG());
        res.moveRight();
        return res;
    }

    @Override
    public void draw() {
        // TODO: Consider code duplication with CreateCharacterScene#draw()
        LayoutBuilder layoutBuilder = new LayoutBuilder(serviceContext);
        layoutBuilder.setCenter(true)
                .setDistanceBetweenOptions(6)
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
                return new GMSelectionScene();
            }
            case "1" -> {
                // TODO: Handle <next> button
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
            case TAB -> {
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
