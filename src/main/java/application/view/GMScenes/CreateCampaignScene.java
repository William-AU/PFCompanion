package application.view.GMScenes;

import application.listeners.ListenerKey;
import application.services.sceneServices.SceneServiceContext;
import application.view.Scene;
import application.view.builders.SceneBuilders.GenericSelectionSceneBuilder;

public class CreateCampaignScene implements Scene {
    private final Scene delegate, nextScene;

    public CreateCampaignScene() {
        this.nextScene = new GMMenuScene();
        GenericSelectionSceneBuilder builder = new GenericSelectionSceneBuilder(new GMSelectionScene());
        builder.setInputButtonLabel("Enter Campaign Name")
                .setDistanceBetweenOptions(6)
                .setNextScene(this.nextScene);
        this.delegate = builder.build();
    }

    @Override
    public void draw() {
        delegate.draw();
    }

    @Override
    public Scene confirm() {
        return delegate.confirm();
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return delegate.inputKey(key);
    }

    @Override
    public boolean inputChar(char ch) {
        return delegate.inputChar(ch);
    }

    @Override
    public boolean shouldAcceptLetters() {
        return delegate.shouldAcceptLetters();
    }

    @Override
    public void setServiceContext(SceneServiceContext serviceContext) {
        delegate.setServiceContext(serviceContext);
    }
}
