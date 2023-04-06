package application.view;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.storage.services.ServiceContext;
import application.view.GMViews.GMSelectionView;
import application.view.builders.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.characterViews.CharacterSelectionView;
import application.view.options.*;
import application.view.strategies.MoveOverOptionMovementStrategy;
import application.view.strategies.OptionMovementStrategy;

public class MainMenuView implements View {
    private final Controller controller;
    private final ServiceContext serviceContext;
    private final OptionMovementStrategy optionMovementStrategy;
    private final OptionGrid optionGrid;

    public MainMenuView(Controller controller, ServiceContext serviceContext) {
        this.controller = controller;
        this.serviceContext = serviceContext;
        this.optionMovementStrategy = new MoveOverOptionMovementStrategy();
        OptionGridBuilder builder = new OptionGridBuilder();
        builder.addMutableOptionAtPos(0, 0,
                new SimpleOption("<Player>", "0"),
                new SimpleOption("<GM>", "1"));
        this.optionGrid = builder.build();
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
            case "1" -> new GMSelectionView(controller, serviceContext);
            default -> null;
        };
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return optionMovementStrategy.handleMove(optionGrid, key);
    }
}
