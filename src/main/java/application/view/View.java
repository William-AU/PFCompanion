package application.view;

import application.listeners.ListenerKey;

public interface View {
    void draw();

    /**
     * Indicates that the user confirmed an option, usually by pressing enter
     * @return Returns a new {@link View} if the action requires a scene change, otherwise returns null
     */
    View confirm();

    /**
     * Updates the view with the given key input, return true if anything changed
     * @param key Key pressed
     * @return True if the key press changed anything about the view state, false otherwise
     */
    boolean inputKey(ListenerKey key);

    /**
     * Tells the controller if this view requires the basic title be drawn. Usually only disabled if the view wants to draw a scene specific title instead
     * @return True if the controller should draw the main title, false otherwise
     */
    default boolean shouldDrawTitle() {
        return true;
    }
}
