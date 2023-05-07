package application.view.GMScenes;

import application.listeners.ListenerKey;
import application.services.sceneServices.SceneServiceContext;
import application.view.FastDrawScene;
import application.view.Scene;
import application.view.builders.LayoutBuilder;

import java.util.ArrayList;
import java.util.List;

public class GMInitiativeScene implements FastDrawScene {
    private SceneServiceContext sceneServiceContext;
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

    @Override
    public void setServiceContext(SceneServiceContext serviceContext) {
        this.sceneServiceContext = serviceContext;
    }

    /**
     * Only called if this {@link Scene} returns true on {@link Scene#useFastDraw()}. Instead of using print statements, instead returns a formatted {@link String} to be printed.
     * {@link Scene#fastDraw()} is called immediately after the console is cleared by the controller
     *
     * @return The string to be drawn
     */
    @Override
    public String fastDraw() {
        String format = "%-10s %-15s %-8s";
        LayoutBuilder layoutBuilder = new LayoutBuilder(sceneServiceContext);
        List<String> title = new ArrayList<>() {{
            add("Name");
            add("Init. Roll");
            add("???");
        }};
        layoutBuilder.setCenter(true)
                .addLine("Initiative")
                .addLine(title, format);
        return layoutBuilder.build();
    }
}
