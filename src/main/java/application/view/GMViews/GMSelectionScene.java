package application.view.GMViews;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.storage.services.ServiceContext;
import application.view.Scene;
import application.view.builders.OptionGridBuilder;
import application.view.options.OptionGrid;

public class GMSelectionScene implements Scene {
    private final OptionGrid optionGrid;

    public GMSelectionScene() {
        OptionGridBuilder builder = new OptionGridBuilder();

        this.optionGrid = builder.build();
    }

    @Override
    public void draw() {

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
