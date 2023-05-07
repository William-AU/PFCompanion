package application.view.builders;

import application.common.Constants;
import application.services.sceneServices.ColorService;
import application.services.sceneServices.SceneServiceContext;
import application.services.sceneServices.TerminalService;
import application.view.options.Option;
import application.view.options.SimpleOption;

import java.util.ArrayList;
import java.util.List;

public class LayoutBuilder {
    private LayoutContext layoutContext;
    private final StringBuilder stringBuilder;
    private final ColorService colorService;
    private final TerminalService terminalService;

    public LayoutBuilder(SceneServiceContext serviceContext) {
        this.layoutContext = new LayoutContext();
        this.stringBuilder = new StringBuilder();
        this.colorService = serviceContext.getColorService();
        this.terminalService = serviceContext.getTerminalService();
    }

    public LayoutBuilder setDistanceBetweenRows(int distance) {
        layoutContext.distanceBetweenRows = distance;
        return this;
    }

    public LayoutBuilder setDistanceBetweenOptions(int distance) {
        layoutContext.distanceBetweenOptions = distance;
        return this;
    }

    public LayoutBuilder setCenter(boolean value) {
        layoutContext.center = value;
        return this;
    }

    /**
     * Adds a line, functions as {@link StringBuilder#append(String)} but also appends \n
     * @param line The {@link String} to add
     * @return The updated instance of the {@link LayoutBuilder}
     */
    public LayoutBuilder addLine(String line) {
        stringBuilder.append("\n".repeat(Math.max(0, layoutContext.distanceBetweenRows)));
        if (layoutContext.center) {
            stringBuilder.append(center(line)).append("\n");
            return this;
        }
        stringBuilder.append(line).append("\n");
        return this;
    }

    /**
     * Adds a line, functions as {@link #addLine(String)} )} except formats using a given {@link String} format. Ignores distance set by {@link LayoutContext}
     * @param lines The lines to add
     * @param format The format to use
     * @return The updated instance of the {@link LayoutBuilder}
     */
    public LayoutBuilder addLine(List<String> lines, String format) {
        String content = String.format(format, lines.toArray());
        if (layoutContext.center) {
            stringBuilder.append(center(content)).append("\n");
            return this;
        }
        stringBuilder.append(content).append("\n");
        return this;
    }

    /**
     * Adds a line to the builder from a list of options. Each option is formatted by the {@link LayoutContext} and {@link application.config.ColorConfig.ColorContext}
     * If {@link LayoutContext#distanceBetweenRows} is greater than 0, it will append these extra rows before printing the options
     * @param row The row of {@link Option} to be formatted
     * @return The updated instance of the {@link LayoutBuilder}
     */
    public LayoutBuilder addOptionRow(List<Option> row) {
        if (!layoutContext.format.isBlank()) {
            return addOptionRow(row, layoutContext.format);
        }
        // Append the distance between rows, default 0.
        stringBuilder.append("\n".repeat(Math.max(0, layoutContext.distanceBetweenRows)));

        StringBuilder tempStringBuilder = new StringBuilder();
        String prefix = "";
        for (Option option : row) {
            tempStringBuilder.append(prefix);
            String optionStr = colorService.formatOptionString(option.getLabel(), option.isHighlighted());
            tempStringBuilder.append(optionStr);
            prefix = " ".repeat(layoutContext.distanceBetweenOptions);
        }
        if (layoutContext.center) {
            stringBuilder.append(center(tempStringBuilder.toString()));
        } else {
            stringBuilder.append(tempStringBuilder);
        }
        stringBuilder.append("\n");
        return this;
    }

    /**
     * Adds a line to the builder fro a list of options using a given formatting {@link String}. Otherwise functions as {@link #addOptionRow(List)}, however, distance between options as defined in {@link LayoutContext} is ignored.
     * @param row The row of {@link Option} to be formatted
     * @param format The format to use
     * @return The updated instance of the {@link LayoutBuilder}
     */
    public LayoutBuilder addOptionRow(List<Option> row, String format) {
        // Append the distance between rows, default 0.
        if (format.isBlank()) {
            return addOptionRow(row);
        }

        stringBuilder.append("\n".repeat(Math.max(0, layoutContext.distanceBetweenRows)));

        String content = String.format(format, row.toArray()) + "\n";
        if (layoutContext.center) {
            stringBuilder.append(center(content.toString()));
        } else {
            stringBuilder.append(content);
        }
        stringBuilder.append("\n");
        return this;
    }

    private String center(String toCenter) {
        //int columns = terminalService.getSize().getColumns();
        int columns = Constants.getColumns();
        int columnsUsed = countStringColumns(toCenter);
        int columnsWhitespace = columns - columnsUsed;
        int leftPadding = columnsWhitespace / 2;
        return " ".repeat(leftPadding) + toCenter;
    }

    /**
     * Simple method for stripping away ANSI encoding from a string, also uses {@link String#strip()} for good measure
     */
    private int countStringColumns(String str) {
        String strippedString = str.replaceAll("\u001B\\[[\\d;]*[^\\d;]","");
        return strippedString.strip().length();
    }

    public LayoutBuilder addOption(Option opt) {
        return addOptionRow(new ArrayList<>() {{add(opt);}});
    }

    public LayoutBuilder setFormat(String format) {
        this.layoutContext.format = format;
        return this;
    }

    public String build() {
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return build();
    }

    private static class LayoutContext {
        private int distanceBetweenOptions, distanceBetweenRows;
        private boolean center;
        private String format;

        public LayoutContext() {
            distanceBetweenOptions = 1;
            distanceBetweenRows = 0;
            center = false;
            format = "";
        }

        public LayoutContext(int distanceBetweenOptions) {
            this.distanceBetweenOptions = distanceBetweenOptions;
            center = false;
        }
    }
}
