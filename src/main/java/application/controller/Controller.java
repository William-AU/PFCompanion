package application.controller;

import application.listeners.ListenerKey;
import application.services.ConsoleService;
import application.storage.services.ServiceContext;
import application.view.Scene;
import org.springframework.beans.factory.annotation.Autowired;

public class Controller {
    private Scene currentScene;
    private Scene defaultScene;
    private final ConsoleService consoleService;
    private final ServiceContext serviceContext;
    private final ControllerContext controllerContext;

    public Controller(ServiceContext serviceContext) {
        this.consoleService = serviceContext.getConsoleService();
        this.serviceContext = serviceContext;
        this.controllerContext = new ControllerContext();
    }

    /**
     * Tells the controller to clear the console window and draw the current selected view
     */
    public void reDraw() {
        consoleService.clearConsole();
        if (currentScene.shouldDrawTitle()) {
            consoleService.drawTitle();
        }
        currentScene.draw();
    }

    /**
     * Selects a scene to display and then calls reDraw()
     * @param scene The scene to draw
     */
    public void setViewAndDraw(Scene scene) {
        currentScene = scene;
        currentScene.setController(this);
        currentScene.setServiceContext(serviceContext);
        reDraw();
    }

    /**
     * Called by {@link application.listeners.KeyboardListener} when user presses a keyboard button, tells the view to handle the given key and then redraws the current view, unless the key pressed is {@link ListenerKey#ENTER} in which case it asks the view to confirm. If the confirm actions returns a valid {@link Scene} this will become the new main scene, otherwise it will redraw as usual.
     * @param key The key pressed by the user
     */
    public void handleKeyEvent(ListenerKey key) {
        if (key == ListenerKey.ENTER) {
            Scene newScene = currentScene.confirm();
            if (newScene != null) {
                setViewAndDraw(newScene);
                return;
            }
        }
        boolean nothingChanged = !currentScene.inputKey(key);
        if (nothingChanged) return;
        reDraw();
    }

    public void handleCharEvent(char event) {
        if (!currentScene.shouldAcceptLetters()) return;
        boolean nothingChanged = !currentScene.inputChar(event);
        if (nothingChanged) return;
        reDraw();
    }

    public void setStringInput(String str) {
        this.controllerContext.lastStringInput = str;
    }

    /**
     * Initial scene to display on application startup as specified in SpringConfig.
     * The scene doubles as the default scene, the "root" of all possible sub views so to speak
     * @param scene The scene to display, autowired from bean in SpringConfig
     */
    @Autowired
    public void setInitialView(Scene scene) {
        this.currentScene = scene;
        this.defaultScene = scene;
        scene.setController(this);
        scene.setServiceContext(serviceContext);
    }

    private class ControllerContext{
        private String lastStringInput;
        protected ControllerContext() {
            this.lastStringInput = "";
        }
    };
}
