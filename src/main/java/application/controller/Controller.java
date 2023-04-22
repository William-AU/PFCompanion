package application.controller;

import application.listeners.ListenerKey;
import application.model.GM.Campaign;
import application.model.character.Character;
import application.services.controllerServices.ControllerServiceContext;
import application.services.sceneServices.ConsoleService;
import application.services.sceneServices.SceneServiceContext;
import application.view.Scene;
import org.springframework.beans.factory.annotation.Autowired;

public class Controller {
    private Scene currentScene;
    private Scene defaultScene;
    private final ConsoleService consoleService;
    private final SceneServiceContext sceneServiceContext;
    private final ControllerContext controllerContext;
    private final ControllerServiceContext controllerServiceContext;

    public Controller(SceneServiceContext sceneServiceContext, ControllerServiceContext controllerServiceContext) {
        this.consoleService = sceneServiceContext.getConsoleService();
        this.sceneServiceContext = sceneServiceContext;
        this.controllerServiceContext = controllerServiceContext;
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
        currentScene.setServiceContext(sceneServiceContext);
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

    public void setCampaign(String name) {
        Campaign campaign = controllerServiceContext.getCampaignService().getCampaignByName(name);
        controllerContext.activeCampaign = campaign;
    }

    public void createNewCampaign() throws IllegalStateException {
        if (controllerContext.lastStringInput.equals("")) throw new IllegalStateException("No campaign name was given by the Scene, cannot create a new campaign");
        String name = controllerContext.lastStringInput;
        controllerServiceContext.getCampaignService().createAndSaveCampaign(name);
    }

    public boolean hasActiveCampaign() {
        return controllerContext.activeCampaign != null;
    }

    public boolean hasActiveCharacter() {
        return controllerContext.activeCharacter != null;
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
        scene.setServiceContext(sceneServiceContext);
    }

    private class ControllerContext{
        private String lastStringInput;
        private Character activeCharacter;
        private Campaign activeCampaign;
        protected ControllerContext() {
            this.lastStringInput = "";
            this.activeCharacter = null;
            this.activeCampaign = null;
        }
    };
}
