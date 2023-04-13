package application.view.GMScenes;

import application.listeners.ListenerKey;
import application.storage.services.ServiceContext;
import application.view.Scene;
import application.view.builders.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.options.OptionGrid;
import application.view.options.SimpleOption;
import application.view.strategies.MoveOverOptionMovementStrategy;
import application.view.strategies.OptionMovementStrategy;

public class GMSelectionScene implements Scene {
    private final OptionGrid optionGrid;
    private final OptionMovementStrategy optionMovementStrategy;
    private ServiceContext serviceContext;

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
        LayoutBuilder builder = new LayoutBuilder(serviceContext);
        builder.setDistanceBetweenOptions(10)
                .setCenter(true)
                .addLine("Please select an option")
                .addOptionRow(optionGrid.getOptionRow(0))
                .addOptionRow(optionGrid.getOptionRow(1));
        System.out.println(builder.build());
    }

    @Override
    public Scene confirm() {
        return switch (optionGrid.getCurrentOption().getId()) {
            case "0" -> new CreateCampaignScene();
            case "1" -> new SelectCampaignScene();
            default -> null;
        };
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return optionMovementStrategy.handleMove(optionGrid, key);
    }

    @Override
    public void setServiceContext(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }
}
