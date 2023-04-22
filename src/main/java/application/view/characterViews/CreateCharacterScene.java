package application.view.characterViews;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.sceneServices.SceneServiceContext;
import application.view.Scene;
import application.view.builders.SceneBuilders.GenericSelectionSceneBuilder;

public class CreateCharacterScene implements Scene {
    private final Scene delegate;

    public CreateCharacterScene() {
        GenericSelectionSceneBuilder builder = new GenericSelectionSceneBuilder(
                new CharacterSelectionScene()
        );
        builder.setDistanceBetweenOptions(6)
                .setNextScene(null) // TODO: Implement
                .setInputButtonLabel("Enter name");
        this.delegate = builder.build();
    }

    @Override
    public void draw() {
        delegate.draw();
    }

    /**
     * Indicated that the user confirmed an option, usually by pressing enter
     * @return Returns a new {@link Scene} if the action requires a scene change, otherwise returns null
     */
    @Override
    public Scene confirm() {
        return delegate.confirm();
    }

    /**
     * Updates the view with the given char input, return true if anything changed
     * @param ch The char pressed
     * @return True if the char changed anything about the view state, false otherwise. Defaults to false as to be consistent with {@link #shouldAcceptLetters()}
     */
    @Override
    public boolean inputChar(char ch) {
        return delegate.inputChar(ch);
    }

    @Override
    public boolean shouldAcceptLetters() {
        return delegate.shouldAcceptLetters();
    }

    /**
     * Tells the {@link Controller} if this {@link Scene} is using fast draw. Fast draw delegates the responsibility of drawing to the {@link Controller}, this allows for slightly more optimised CLS timing.
     *
     * @return True if the scene uses fast draw, false otherwise
     */
    @Override
    public boolean useFastDraw() {
        return delegate.useFastDraw();
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

    /**
     * Updates the view with the given key input, return true if anything changed
     *
     * @param key Key pressed
     * @return True if the key press changed anything about the view state, false otherwise
     */
    @Override
    public boolean inputKey(ListenerKey key) {
        return delegate.inputKey(key);
    }

    @Override
    public void setServiceContext(SceneServiceContext serviceContext) {
        delegate.setServiceContext(serviceContext);
    }
}
