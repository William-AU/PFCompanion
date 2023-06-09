package application.view.GMScenes;

import application.listeners.ListenerKey;
import application.services.sceneServices.SceneServiceContext;
import application.view.FastDrawScene;
import application.view.Scene;

public class GMCombatScene implements FastDrawScene {
    private final Scene initiativeScene;

    public GMCombatScene() {
        this.initiativeScene = new GMInitiativeScene();
    }

    /**
     * Indicates that the user confirmed an option, usually by pressing enter
     *
     * @return Returns a new {@link Scene} if the action requires a scene change, otherwise returns null
     */
    @Override
    public Scene confirm() {
        return initiativeScene.confirm();
    }

    /**
     * Updates the view with the given key input, return true if anything changed
     *
     * @param key Key pressed
     * @return True if the key press changed anything about the view state, false otherwise
     */
    @Override
    public boolean inputKey(ListenerKey key) {
        return initiativeScene.inputKey(key);
    }

    @Override
    public void setServiceContext(SceneServiceContext serviceContext) {
        initiativeScene.setServiceContext(serviceContext);
    }

    /**
     * Only called if this {@link Scene} returns true on {@link Scene#useFastDraw()}. Instead of using print statements, instead returns a formatted {@link String} to be printed.
     * {@link Scene#fastDraw()} is called immediately after the console is cleared by the controller
     *
     * @return The string to be drawn
     */
    @Override
    public String fastDraw() {
        return initiativeScene.fastDraw();
    }
}
