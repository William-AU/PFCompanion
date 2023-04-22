package application.view.GMScenes;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.sceneServices.SceneServiceContext;
import application.view.Scene;
import application.view.builders.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.options.OptionGrid;
import application.view.options.SimpleOption;
import application.view.strategies.OptionMovementStrategy;
import application.view.strategies.SimpleOptionMovementStrategy;

import java.util.List;

public class LoadCampaignScene implements Scene {
    private OptionGrid optionGrid;
    private Controller controller;
    private SceneServiceContext sceneServiceContext;
    private final OptionMovementStrategy optionMovementStrategy;

    public LoadCampaignScene() {
        this.optionMovementStrategy = new SimpleOptionMovementStrategy();
    }

    private void lateInit() {
        this.optionGrid = createOptionGrid();
    }

    private OptionGrid createOptionGrid() {
        OptionGridBuilder builder = new OptionGridBuilder();
        List<String> campaignNames = controller.getCampaignNames();
        SimpleOption backOption = new SimpleOption("Back", "0");
        builder.addOptionsToNewRow(backOption);
        for (int i = 0; i < campaignNames.size(); i++) {
            builder.addOptionsToNewRow(new SimpleOption(campaignNames.get(i), "" + (i + 1)));
        }
        return builder.build();
    }

    @Override
    public void draw() {
        LayoutBuilder layoutBuilder = new LayoutBuilder(sceneServiceContext);
        layoutBuilder.setCenter(true)
                .setDistanceBetweenOptions(6);
        optionGrid.getAllOptionRows().forEach(layoutBuilder::addOptionRow);
        System.out.println(layoutBuilder.build());
    }

    @Override
    public void setServiceContext(SceneServiceContext serviceContext) {
        this.sceneServiceContext = serviceContext;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
        lateInit();
    }

    @Override
    public Scene confirm() {
        if (optionGrid.getCurrentOption().getId().equals("0")) {
            return new GMSelectionScene();
        }

        List<String> campaignNames = controller.getCampaignNames();
        int index = Integer.parseInt(optionGrid.getCurrentOption().getId());
        String name = campaignNames.get(index - 1);
        controller.setCampaign(name);
        return new GMMenuScene();
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return optionMovementStrategy.handleMove(optionGrid, key);
    }
}
