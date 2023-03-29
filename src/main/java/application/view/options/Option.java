package application.view.options;

public class Option {
    private final String label;
    private final String id;
    private boolean canMoveOver;
    private boolean highlighted;

    public Option(String label, String id) {
        this.label = label;
        this.id = id;
        this.highlighted = false;
    }

    public Option(String label, String id, boolean canMoveOver) {
        this.label = label;
        this.id = id;
        this.canMoveOver = canMoveOver;
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
}
