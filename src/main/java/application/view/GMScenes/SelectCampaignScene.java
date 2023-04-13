package application.view.GMScenes;

import application.listeners.ListenerKey;
import application.view.Scene;

public class SelectCampaignScene implements Scene {
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
