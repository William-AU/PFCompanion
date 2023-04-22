package application.view.GMScenes;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.sceneServices.SceneServiceContext;
import application.view.Scene;
import application.view.builders.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.options.OptionGrid;
import application.view.options.SimpleOption;

import java.util.List;

public class SelectCampaignScene implements Scene {
    private OptionGrid optionGrid;
    private Controller controller;
    private SceneServiceContext sceneServiceContext;

    public SelectCampaignScene() {
        this.optionGrid = createOptionGrid();
    }

    private OptionGrid createOptionGrid() {
        OptionGridBuilder builder = new OptionGridBuilder();
        List<String> campaignNames = controller.getCampaignNames();
        SimpleOption backOption = new SimpleOption("Back", "0");
        SimpleOption nextOption = new SimpleOption("Next", "1");
        if (campaignNames.isEmpty()) {
            builder.addOptionsToCurrentRow(backOption, nextOption);
            return builder.build();
        }
        for (int i = 0; i < campaignNames.size(); i++) {
            SimpleOption nameOption = new SimpleOption(campaignNames.get(i), "" + (i + 2));
            if (i == 0) {
                builder.addOptionsToCurrentRow(backOption, nameOption, nextOption);
            } else {
                builder.addOptionsToNewRow(nameOption);
            }
        }
        return builder.build();
    }

    @Override
    public void draw() {
        LayoutBuilder layoutBuilder = new LayoutBuilder(sceneServiceContext);
        layoutBuilder.setCenter(true)
                .setDistanceBetweenOptions(6);

    }

    @Override
    public void setServiceContext(SceneServiceContext serviceContext) {
        this.sceneServiceContext = serviceContext;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Scene confirm() {
        return null;
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return false;
    }
}
