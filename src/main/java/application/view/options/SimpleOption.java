package application.view.options;

import application.view.builders.layout.LayoutBuilder;

import java.util.Objects;

public class SimpleOption implements Option {
    private final String label;
    private final String id;
    private boolean highlighted;

    /**
     * Creates an {@link SimpleOption} instance which represents a menu option and can be printed using {@link LayoutBuilder}.
     * @param label The label to be shown in the GUI
     * @param id The ID that represents this option
     */
    public SimpleOption(String label, String id) {
        this.label = label;
        this.id = id;
        this.highlighted = false;
    }



    /**
     * Does nothing for {@link SimpleOption}
     */
    @Override
    public void setNextOption() {
    }

    /**
     * Does nothing for {@link SimpleOption}
     */
    @Override
    public void setPreviousOption() {
    }

    /**
     * {@link SimpleOption} does not support mutation so this method simply returns the current instance
     * @return Returns the instance of this {@link SimpleOption}
     */
    @Override
    public Option getSelectedOption() {
        return this;
    }

    @Override
    public void toggleHighlight() {
        this.highlighted = !highlighted;
    }

    @Override
    public boolean isHighlighted() {
        return highlighted;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * {@link SimpleOption} does not support mutation, so this always returns tru
     * @return True
     */
    @Override
    public boolean canMoveOver() {
        return true;
    }

    /**
     * {@link SimpleOption} does not support mutation, so this does nothing
     * @param canMoveOver The value of the flag
     */
    @Override
    public void setCanMoveOver(boolean canMoveOver) {
    }

    @Override
    public String toString() {
        return "Option{" + label  +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleOption option = (SimpleOption) o;
        return label.equals(option.label) && id.equals(option.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, id);
    }
}
