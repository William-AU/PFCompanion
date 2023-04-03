package application.view;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.ColorService;
import application.storage.services.ServiceContext;
import application.view.builders.LayoutBuilder;
import application.view.options.Option;
import application.view.options.OptionGrid;
import application.view.options.Position;
import application.view.strategies.OptionMovementStrategy;
import application.view.strategies.SimpleOptionMovementStrategy;
import org.springframework.boot.info.BuildProperties;

import java.util.HashMap;
import java.util.Map;

public class CharacterSelectionView implements View {
    private final Controller controller;
    private final ServiceContext serviceContext;
    private final OptionMovementStrategy optionMovementStrategy;
    private final OptionGrid optionGrid;

    public CharacterSelectionView(Controller controller, ServiceContext serviceContext) {
        this.controller = controller;
        this.serviceContext = serviceContext;
        this.optionMovementStrategy = new SimpleOptionMovementStrategy();

        Map<Position, Option> optionMap = new HashMap<>() {{
            put(new Position(0, 0), new Option("Create Character", "0"));
            put(new Position(0, 1), new Option("Load Character", "1"));
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
    public View confirm() {
        return switch (optionGrid.getCurrentOption().getId()) {
            case "0" -> new CreateCharacterView(controller, serviceContext.getColorService());
            case "1" -> new LoadCharacterView(controller, serviceContext.getColorService());
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
}
