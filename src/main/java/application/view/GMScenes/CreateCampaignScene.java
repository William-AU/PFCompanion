package application.view.GMScenes;

import application.listeners.ListenerKey;
import application.storage.services.ServiceContext;
import application.view.Scene;
import application.view.builders.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.builders.SceneBuilders.GenericSelectionSceneBuilder;
import application.view.options.OptionGrid;
import application.view.options.SimpleOption;
import application.view.strategies.MoveOverOptionMovementStrategy;
import application.view.strategies.NullMovementStrategy;
import application.view.strategies.OptionMovementStrategy;
import application.view.strategies.SimpleOptionMovementStrategy;

public class CreateCampaignScene implements Scene {
    private final Scene delegate, nextScene;

    public CreateCampaignScene() {
        this.nextScene = null;  // TODO: Add scene
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
    public void setServiceContext(ServiceContext serviceContext) {
        delegate.setServiceContext(serviceContext);
    }
}
