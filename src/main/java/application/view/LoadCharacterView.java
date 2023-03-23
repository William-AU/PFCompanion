package application.view;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.ColorService;
import application.services.ConsoleService;

public class LoadCharacterView implements View {
    private final Controller controller;
    private final ColorService colorService;
    private final ConsoleService consoleService;

    public LoadCharacterView(Controller controller, ColorService colorService) {
        this.controller = controller;
        this.colorService = colorService;
        consoleService = controller.getConsoleService();
    }

    @Override
    public void draw() {

    }

    /**
     * Indicated that the user confirmed an option, usually by pressing enter
     */
    @Override
    public View confirm() {
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
