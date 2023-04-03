package application.view.options;

import application.model.datastructures.CircularDoublyLinkedList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Option {
    private final String label;
    private final String id;
    private boolean canMoveOver;
    private boolean highlighted;
    private CircularDoublyLinkedList<Option> otherOptions;

    /**
     * Creates an {@link Option} instance which represents a menu option and can be printed using {@link application.view.builders.LayoutBuilder}.
     * @param label The label to be shown in the GUI
     * @param id The ID that represents this option
     */
    public Option(String label, String id) {
        this.label = label;
        this.id = id;
        this.highlighted = false;
        otherOptions = null;
    }

    /**
     * An alternative constructor allowing an option to mutate to another option using a list of options
     * @param label The label of the initial option
     * @param id The ID of the initial option
     * @param otherOptions The list of options (in order) that this option can mutate to
     */
    public Option(String label, String id, List<Option> otherOptions) {
        this.label = label;
        this.id = id;
        this.canMoveOver = false;
        otherOptions.forEach(option -> option.setCanMoveOver(false));
        Option thisOption = this;   // Required because of how anonymous instanciation works in java
        List<Option> baseList;
        if (otherOptions.contains(thisOption)) {
            baseList = otherOptions;
        } else {
            baseList = new ArrayList<>() {{
                add(thisOption);
            }};
            baseList.addAll(otherOptions);
        }
        this.otherOptions = new CircularDoublyLinkedList<>(baseList);
    }

    /**
     * Functions like {@link #Option(String, String, List)} except takes only a list of {@link Option}, to make mutable options easier
     * @param multipleOptions List of {@link Option} instances that can be mutated to
     */
    public Option(List<Option> multipleOptions) {
        this(multipleOptions.get(0).label, multipleOptions.get(0).id, multipleOptions);
    }

    /**
     * Sets the next {@link Option} if this instance allows mutation
     */
    public void setNextOption() {
        otherOptions.traverseForwards();
    }

    /**
     * Sets the previous {@link Option} if this instance allows mutation
     */
    public void setPreviousOption() {
        otherOptions.traverseBackwards();
    }

    /**
     * If this {@link Option} is mutable, returns the current selected {@link Option}
     * @return The {@link Option} currently selected if this option is mutable, otherwise simple return the option
     */
    public Option getSelectedOption() {
        if (otherOptions == null) return this;
        return otherOptions.getCurrentElement();
    }

    public void toggleHighlight() {
        this.highlighted = !highlighted;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public String getLabel() {
        return label;
    }

    public String getId() {
        return id;
    }

    public boolean canMoveOver() {
        return canMoveOver;
    }

    public void setCanMoveOver(boolean canMoveOver) {
        this.canMoveOver = canMoveOver;
    }

    public void printThis() {
        System.out.println(this);
        System.out.println(this.otherOptions);
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
        Option option = (Option) o;
        return label.equals(option.label) && id.equals(option.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, id);
    }
}
