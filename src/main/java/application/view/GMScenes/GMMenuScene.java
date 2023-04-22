package application.view.GMScenes;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.model.GM.Campaign;
import application.view.Scene;

public class GMMenuScene implements Scene {
   private Controller controller;
   private String campaignName;

   private void initAfterController(Campaign initialCampaign) {
       campaignName = initialCampaign.getName();

   }

    @Override
    public void draw() {

    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
        boolean noActiveCampaign = !controller.hasActiveCampaign();
        if (noActiveCampaign) {
            controller.createNewCampaign();
        }
        initAfterController(controller.getCurrentCampaign());
    }

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
}
