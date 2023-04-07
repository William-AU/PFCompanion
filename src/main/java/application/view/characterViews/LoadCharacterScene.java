package application.view.characterViews;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.ConsoleService;
import application.storage.services.ServiceContext;
import application.view.Scene;

public class LoadCharacterScene implements Scene {

    public LoadCharacterScene() {
    }

    @Override
    public void draw() {

    }

    /**
     * Indicated that the user confirmed an option, usually by pressing enter
     */
    @Override
    public Scene confirm() {
        return null;
    }

    /**
     * Updates the view with the given key input, return true if anything changed
     *
     * @param key Key pressed
     * @return True if the key press changed anything about the view state, false otherwise
     */
    @Override
    public boolean inputKey(ListenerKey key) {
        return false;
    }
}
