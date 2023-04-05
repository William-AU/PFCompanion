package application.view.options;

import application.model.datastructures.CircularDoublyLinkedList;

import java.util.ArrayList;
import java.util.List;

public class MutableOption implements Option {
    private final String label;
    private final String id;
    private boolean canMoveOver;
    private boolean highlighted;
    private CircularDoublyLinkedList<SimpleOption> allOptions;

    /**
     * An alternative constructor allowing an option to mutate to another option using a list of options
     * @param label The label of the initial option
     * @param id The ID of the initial option
     * @param otherOptions The list of options (in order) that this option can mutate to
     */
    public MutableOption(String label, String id, List<SimpleOption> otherOptions) {
        this.label = label;
        this.id = id;
        this.canMoveOver = false;
        otherOptions.forEach(option -> option.setCanMoveOver(false));
        SimpleOption thisButSimple = new SimpleOption(label, id);
        List<SimpleOption> baseList;
        if (otherOptions.contains(thisButSimple)) {
            baseList = otherOptions;
        } else {
            baseList = new ArrayList<>() {{
                add(thisButSimple);
            }};
            baseList.addAll(otherOptions);
        }
        this.allOptions = new CircularDoublyLinkedList<>(baseList);
    }

    /**
     * Functions like {@link #MutableOption(String, String, List)} except takes only a list of {@link SimpleOption}, to make mutable options easier
     * @param multipleOptions List of {@link SimpleOption} instances that can be mutated to
     */
    public MutableOption(List<SimpleOption> multipleOptions) {
        this(multipleOptions.get(0).getLabel(), multipleOptions.get(0).getId(), multipleOptions);
    }

    /**
     * Sets the next {@link SimpleOption}
     */
    @Override
    public void setNextOption() {
        allOptions.traverseForwards();
    }

    /**
     * Sets the previous {@link SimpleOption} if this instance allows mutation
     */
    @Override
    public void setPreviousOption() {
        allOptions.traverseBackwards();
    }

    /**
     * Returns the current selected {@link SimpleOption}
     * @return The {@link SimpleOption} currently selected
     */
    @Override
    public Option getSelectedOption() {
        if (allOptions == null) return this;
        return allOptions.getCurrentElement();
    }

    @Override
    public void toggleHighlight() {
        this.highlighted = !this.highlighted;
    }

    @Override
    public boolean isHighlighted() {
        return this.highlighted;
    }

    @Override
    public String getLabel() {
        if (this.allOptions == null) {
            return this.label;
        }
        return getSelectedOption().getLabel();
    }

    @Override
    public String getId() {
        if (this.allOptions == null) {
            return this.id;
        }
        return getSelectedOption().getId();
    }

    @Override
    public boolean canMoveOver() {
        return this.canMoveOver;
    }

    /**
     * Sets if it should be possible to move over the current {@link Option}. If this is enabled, pressing arrow keys should move to the next option. If this is disabled, arrow keys should instead mutate the option
     * @param canMoveOver The value of the flag
     */
    @Override
    public void setCanMoveOver(boolean canMoveOver) {
        this.canMoveOver = canMoveOver;
    }
}
