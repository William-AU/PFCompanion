package application.view.GMScenes;

import application.listeners.ListenerKey;
import application.view.FastDrawScene;
import application.view.Scene;

public class GMPlayersScene implements FastDrawScene {
    /**
     * Indicates that the user confirmed an option, usually by pressing enter
     *
     * @return Returns a new {@link Scene} if the action requires a scene change, otherwise returns null
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

    /**
     * Only called if this {@link Scene} returns true on {@link Scene#useFastDraw()}. Instead of using print statements, instead returns a formatted {@link String} to be printed.
     * {@link Scene#fastDraw()} is called immediately after the console is cleared by the controller
     *
     * @return The string to be drawn
     */
    @Override
    public String fastDraw() {
        return "PLACEHOLDER GM PLAYERS SCENE";
    }
}
