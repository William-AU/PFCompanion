package application.view.options;

public interface Option {
    /**
     * Sets the next {@link Option} if this instance allows mutation
     */
    void setNextOption();

    /**
     * Sets the previous {@link Option} if this instance allows mutation
     */
    void setPreviousOption();

    /**
     * If this {@link Option} is mutable, returns the current selected {@link Option}
     *
     * @return The {@link Option} currently selected if this option is mutable, otherwise simple return the option
     */
    Option getSelectedOption();

    void toggleHighlight();

    boolean isHighlighted();

    String getLabel();

    String getId();

    boolean canMoveOver();

    /**
     * Sets if it should be possible to move over the current {@link Option}. If this is enabled, pressing arrow keys should move to the next option. If this is disabled, arrow keys should instead mutate the option
     * @param canMoveOver The value of the flag
     */
    void setCanMoveOver(boolean canMoveOver);
}
