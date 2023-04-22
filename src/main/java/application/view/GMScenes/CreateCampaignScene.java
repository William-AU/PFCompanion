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

    /**
     * Tells the {@link Controller} if this {@link Scene} is using fast draw. Fast draw delegates the responsibility of drawing to the {@link Controller}, this allows for slightly more optimised CLS timing.
     *
     * @return True if the scene uses fast draw, false otherwise
     */
    @Override
    public boolean useFastDraw() {
        return true;
    }

    /**
     * Only called if this {@link Scene} returns true on {@link Scene#useFastDraw()}. Instead of using print statements, instead returns a formatted {@link String} to be printed.
     * {@link Scene#fastDraw()} is called immediately after the console is cleared by the controller
     *
     * @return The string to be drawn
     */
    @Override
    public String fastDraw() {
        return delegate.fastDraw();
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
