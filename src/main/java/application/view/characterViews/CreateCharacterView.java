package application.view.characterViews;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.ColorService;
import application.services.ConsoleService;
import application.storage.services.ServiceContext;
import application.view.View;
import application.view.builders.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.options.OptionGrid;
import application.view.options.SimpleOption;
import application.view.strategies.MoveOverOptionMovementStrategy;
import application.view.strategies.NullMovementStrategy;
import application.view.strategies.OptionMovementStrategy;

public class CreateCharacterView implements View {
    private final Controller controller;
    private final ServiceContext serviceContext;
    private final ConsoleService consoleService;
    private OptionMovementStrategy optionMovementStrategy;
    private final OptionGrid optionGrid;
    // View specific state
    private boolean acceptChars;
    private String currentNameInput;
    private boolean isEnteringName;

    public CreateCharacterView(Controller controller, ServiceContext serviceContext) {
        this.controller = controller;
        this.serviceContext = serviceContext;
        this.consoleService = controller.getConsoleService();
        this.optionGrid = createOptionGrid();
        this.optionMovementStrategy = new MoveOverOptionMovementStrategy();
        this.currentNameInput = "";
        this.isEnteringName = false;
        this.acceptChars = false;
    }

    private OptionGrid createOptionGrid() {
        OptionGridBuilder builder = new OptionGridBuilder();
        builder.addOptionsToNewRow(new SimpleOption("Enter name", "0"));


        return builder.build();
    }

    @Override
    public void draw() {
        LayoutBuilder layoutBuilder = new LayoutBuilder(serviceContext);
        layoutBuilder.setCenter(true)
                .addOptionRow(optionGrid.getOptionRow(0));
        if (isEnteringName) {
            layoutBuilder.addLine("[" + currentNameInput + "]");
        }
    }

    /**
     * Indicated that the user confirmed an option, usually by pressing enter
     * @return Returns a new {@link View} if the action requires a scene change, otherwise returns null
     */
    @Override
    public View confirm() {
        if (isEnteringName) {
            // Handle name
            isEnteringName = false;
            acceptChars = false;
            optionMovementStrategy = new MoveOverOptionMovementStrategy();
        }
        switch (optionGrid.getCurrentOption().getId()) {
            case "0" -> {
                // Enter name option
                optionMovementStrategy = new NullMovementStrategy();
                isEnteringName = true;
                acceptChars = true;
                return this;
            }
        };
        return null;
    }

    @Override
    public boolean inputChar(char ch) {
        return View.super.inputChar(ch);
    }

    @Override
    public boolean shouldAcceptLetters() {
        return acceptChars;
    }

    /**
     * Updates the view with the given key input, return true if anything changed
     *
     * @param key Key pressed
     * @return True if the key press changed anything about the view state, false otherwise
     */
    @Override
    public boolean inputKey(ListenerKey key) {
        return false;
    }
}
