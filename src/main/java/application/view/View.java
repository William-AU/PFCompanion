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
     * Updates the view with the given char input, return true if anything changed
     * @param ch The char pressed
     * @return True if the char changed anything about the view state, false otherwise. Defaults to false as to be consistent with {@link #shouldAcceptLetters()}
     */
    default boolean inputChar(char ch) {
        return false;
    };

    /**
     * Tells the controller if this view requires the basic title be drawn. Usually only disabled if the view wants to draw a scene specific title instead
     * @return True if the controller should draw the main title, false otherwise. Defaults to true.
     */
    default boolean shouldDrawTitle() {
        return true;
    }

    /**
     * Tells the controller if this view is accepting letter input, usually for text input
     * @return True if the current view is accepting letters, false otherwise. Defaults to false.
     */
    default boolean shouldAcceptLetters() {
        return false;
    }
}
