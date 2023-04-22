package application.view.characterViews;

import application.listeners.ListenerKey;
import application.services.sceneServices.SceneServiceContext;
import application.view.Scene;
import application.view.builders.LayoutBuilder;
import application.view.options.Option;
import application.view.options.OptionGrid;
import application.view.options.Position;
import application.view.options.SimpleOption;
import application.view.strategies.OptionMovementStrategy;
import application.view.strategies.SimpleOptionMovementStrategy;

import java.util.HashMap;
import java.util.Map;

public class CharacterSelectionScene implements Scene {
    private SceneServiceContext serviceContext;
    private final OptionMovementStrategy optionMovementStrategy;
    private final OptionGrid optionGrid;

    public CharacterSelectionScene() {
        this.optionMovementStrategy = new SimpleOptionMovementStrategy();
        /*
        OPTION1
        OPTION2
         */
        Map<Position, Option> optionMap = new HashMap<>() {{
            put(new Position(0, 0), new SimpleOption("Create Character", "0"));
            put(new Position(0, 1), new SimpleOption("Load Character", "1"));
        }};
        this.optionGrid = new OptionGrid(optionMap);
    }

    @Override
    public void draw() {
        drawOptions();
    }

    /**
     * Indicated that the user confirmed an option, usually by pressing enter
     */
    @Override
    public Scene confirm() {
        return switch (optionGrid.getCurrentOption().getId()) {
            case "0" -> new CreateCharacterScene();
            case "1" -> new LoadCharacterScene();
            default -> null;
        };
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return optionMovementStrategy.handleMove(optionGrid, key);
    }


    private void drawOptions() {
        LayoutBuilder builder = new LayoutBuilder(serviceContext);
        builder.setDistanceBetweenOptions(10)
                .setCenter(true)
                .addLine("Please select an option")
                .addOptionRow(optionGrid.getOptionRow(0))
                .addOptionRow(optionGrid.getOptionRow(1));
        System.out.println(builder.build());
    }

    @Override
    public void setServiceContext(SceneServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }
}
