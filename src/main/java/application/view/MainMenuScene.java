package application.view;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.sceneServices.SceneServiceContext;
import application.view.GMScenes.GMSelectionScene;
import application.view.builders.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.characterViews.CharacterSelectionScene;
import application.view.options.*;
import application.view.strategies.MoveOverOptionMovementStrategy;
import application.view.strategies.OptionMovementStrategy;

public class MainMenuScene implements Scene {
    private SceneServiceContext serviceContext;
    private final OptionMovementStrategy optionMovementStrategy;
    private final OptionGrid optionGrid;

    public MainMenuScene() {
        this.optionMovementStrategy = new MoveOverOptionMovementStrategy();
        OptionGridBuilder builder = new OptionGridBuilder();
        builder.addMutableOptionAtPos(0, 0,
                new SimpleOption("<Player>", "0"),
                new SimpleOption("<GM>", "1"));
        this.optionGrid = builder.build();
    }

    @Override
    public void draw() {
        System.out.println(fastDraw());
    }

    /**
     * Tells the {@link Controller} if this {@link Scene} is using fast draw. Fast draw delegates the responsibility of drawing to the {@link Controller}, this allows for slightly more optimised CLS timing.
     *
     * @return True if the scene uses fast draw, false otherwise
     */
    @Override
    public boolean useFastDraw() {
        return true;
    }

    /**
     * Only called if this {@link Scene} returns true on {@link Scene#useFastDraw()}. Instead of using print statements, instead returns a formatted {@link String} to be printed.
     * {@link Scene#fastDraw()} is called immediately after the console is cleared by the controller
     *
     * @return The string to be drawn
     */
    @Override
    public String fastDraw() {
        LayoutBuilder builder = new LayoutBuilder(serviceContext);
        builder.setCenter(true)
                .addLine("Select mode and press enter")
                .addOption(optionGrid.getCurrentOption());
        return builder.build();
    }

    @Override
    public Scene confirm() {
        // ID 0 = Player
        // ID 1 = GM
        return switch (optionGrid.getCurrentOption().getId()) {
            case "0" -> new CharacterSelectionScene();
            case "1" -> new GMSelectionScene();
            default -> null;
        };
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return optionMovementStrategy.handleMove(optionGrid, key);
    }

    @Override
    public void setServiceContext(SceneServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }
}
