package application.view.GMViews;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.storage.services.ServiceContext;
import application.view.View;
import application.view.builders.OptionGridBuilder;
import application.view.options.Option;
import application.view.options.OptionGrid;
import application.view.options.Position;

import java.util.HashMap;
import java.util.Map;

public class GMSelectionView implements View {
    private final Controller controller;
    private final ServiceContext serviceContext;
    private final OptionGrid optionGrid;

    public GMSelectionView(Controller controller, ServiceContext serviceContext) {
        this.controller = controller;
        this.serviceContext = serviceContext;
        OptionGridBuilder builder = new OptionGridBuilder();

        this.optionGrid = builder.build();
    }

    @Override
    public void draw() {

    }

    @Override
    public View confirm() {
        return null;
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return false;
    }
}
