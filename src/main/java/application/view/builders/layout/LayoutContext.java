package application.view.builders.layout;

public class LayoutContext {
    private int distanceBetweenOptions, distanceBetweenRows;
    private boolean center;
    private String format, commandText;

    public LayoutContext() {
        distanceBetweenOptions = 1;
        distanceBetweenRows = 0;
        center = false;
        format = "";
        commandText = "";
    }

    public String getCommandText() {
        return commandText;
    }

    public int getDistanceBetweenOptions() {
        return distanceBetweenOptions;
    }

    public void setDistanceBetweenOptions(int distanceBetweenOptions) {
        this.distanceBetweenOptions = distanceBetweenOptions;
    }

    public int getDistanceBetweenRows() {
        return distanceBetweenRows;
    }

    public void setDistanceBetweenRows(int distanceBetweenRows) {
        this.distanceBetweenRows = distanceBetweenRows;
    }

    public boolean isCenter() {
        return center;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public void setCenter(boolean center) {
        this.center = center;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public LayoutContext(int distanceBetweenOptions) {
        this.distanceBetweenOptions = distanceBetweenOptions;
        center = false;
    }
}
