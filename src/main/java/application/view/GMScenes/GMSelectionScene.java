package application.view.GMScenes;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.sceneServices.SceneServiceContext;
import application.view.Scene;
import application.view.builders.layout.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.options.OptionGrid;
import application.view.options.SimpleOption;
import application.view.strategies.MoveOverOptionMovementStrategy;
import application.view.strategies.OptionMovementStrategy;

public class GMSelectionScene implements Scene {
    private final OptionGrid optionGrid;
    private final OptionMovementStrategy optionMovementStrategy;
    private SceneServiceContext serviceContext;

    public GMSelectionScene() {
        /*
        OPTION1
        OPTION2
         */
        OptionGridBuilder builder = new OptionGridBuilder();
        builder.addOptionsToNewRow(
                // Double space is on purpose for spacing issues
                new SimpleOption("New  Campaign", "0")
        );
        builder.addOptionsToNewRow(
                new SimpleOption("Load Campaign", "1")
        );
        this.optionGrid = builder.build();
        this.optionMovementStrategy = new MoveOverOptionMovementStrategy();
    }

    @Override
    public void draw() {
        System.out.println(fastDraw());
    }

    /**
     * Tells the {@link application.controller.Controller} if this {@link Scene} is using fast draw. Fast draw delegates the responsibility of drawing to the {@link Controller}, this allows for slightly more optimised CLS timing.
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
        builder.setDistanceBetweenOptions(10)
                .setCenter(true)
                .addLine("Please select an option")
                .addOptionRow(optionGrid.getOptionRow(0))
                .addOptionRow(optionGrid.getOptionRow(1));
        return builder.build();
    }

    @Override
    public Scene confirm() {
        return switch (optionGrid.getCurrentOption().getId()) {
            case "0" -> new CreateCampaignScene();
            case "1" -> new LoadCampaignScene();
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
