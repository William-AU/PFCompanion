package application.view;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.storage.services.ServiceContext;
import application.view.builders.LayoutBuilder;
import application.view.options.*;
import application.view.strategies.MoveOverOptionMovementStrategy;
import application.view.strategies.OptionMovementStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainMenuView implements View {
    private final Controller controller;
    private final ServiceContext serviceContext;
    private final OptionMovementStrategy optionMovementStrategy;
    private final OptionGrid optionGrid;

    public MainMenuView(Controller controller, ServiceContext serviceContext) {
        this.controller = controller;
        this.serviceContext = serviceContext;
        this.optionMovementStrategy = new MoveOverOptionMovementStrategy();
        Map<Position, Option> optionMap = new HashMap<>() {{
            put(new Position(0, 0), new MutableOption("<Player>", "0",
                    new ArrayList<>()));
        }};
        this.optionGrid = new OptionGrid(optionMap);
    }

    @Override
    public void draw() {
        LayoutBuilder builder = new LayoutBuilder(serviceContext);
        builder.setCenter(true)
                .addLine("Select mode and press enter")
                .addOption(optionGrid.getCurrentOption());
        System.out.println(builder.build());
    }

    @Override
    public View confirm() {
        // ID 0 = Player
        // ID 1 = GM
        return switch (optionGrid.getCurrentOption().getId()) {
            case "0" -> new CharacterSelectionView(controller, serviceContext);
            case "1" -> new GMView(controller, serviceContext);
            default -> null;
        };
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return false;
    }
}
