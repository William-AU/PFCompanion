package application.view.builders.layout;

import application.common.ArtConstants;
import application.services.sceneServices.ColorService;
import application.services.sceneServices.SceneServiceContext;
import application.services.sceneServices.TerminalService;
import application.view.options.Option;

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
        this.layoutContext = new LayoutContext();
    }

    public LayoutBuilder setDistanceBetweenRows(int distance) {
        layoutContext.setDistanceBetweenRows(distance);
        return this;
    }

    public LayoutBuilder setDistanceBetweenOptions(int distance) {
        layoutContext.setDistanceBetweenOptions(distance);
        return this;
    }

    public LayoutBuilder setCenter(boolean value) {
        layoutContext.setCenter(value);
        return this;
    }

    /**
     * Adds a line, functions as {@link StringBuilder#append(String)} but also appends \n
     * @param line The {@link String} to add
     * @return The updated instance of the {@link LayoutBuilder}
     */
    public LayoutBuilder addLine(String line) {
        stringBuilder.append("\n".repeat(Math.max(0, layoutContext.getDistanceBetweenRows())));
        if (layoutContext.isCenter()) {
            stringBuilder.append(center(line)).append("\n");
            return this;
        }
        stringBuilder.append(line).append("\n");
        return this;
    }

    private int getTerminalCols() {
        // We do not allow views wider than fits with the art, TODO: should be a setting somewhere
        int maxSize = ArtConstants.getColumns();
        int actualSize = terminalService.getSize().getRows();
        return Math.min(maxSize, actualSize);
    }

    private int getTerminalRows() {
        int artOffset = ArtConstants.getArtHeight();
        // Where is any of this coming from? NOBODY KNOWS
        // Does it work without it? ABSOLUTELY NOT
        // Is it operating system dependent? PROBABLY
        int extraOffset = 3 + 6;
       return terminalService.getSize().getRows() - artOffset - extraOffset;
    }

    /**
     * Adds a line, functions as {@link #addLine(String)} )} except formats using a given {@link String} format. Ignores distance set by {@link LayoutContext}
     * @param lines The lines to add
     * @param format The format to use
     * @return The updated instance of the {@link LayoutBuilder}
     */
    public LayoutBuilder addLine(List<String> lines, String format) {
        String content = String.format(format, lines.toArray());
        if (layoutContext.isCenter()) {
            stringBuilder.append(center(content)).append("\n");
            return this;
        }
        stringBuilder.append(content).append("\n");
        return this;
    }

    /**
     * Adds a line to the builder from a list of options. Each option is formatted by the {@link LayoutContext} and {@link application.config.ColorConfig.ColorContext}
     * If {@link LayoutContext#getDistanceBetweenRows()} is greater than 0, it will append these extra rows before printing the options
     * @param row The row of {@link Option} to be formatted
     * @return The updated instance of the {@link LayoutBuilder}
     */
    public LayoutBuilder addOptionRow(List<Option> row) {
        if (!layoutContext.getFormat().isBlank()) {
            return addOptionRow(row, layoutContext.getFormat());
        }
        // Append the distance between rows, default 0.
        stringBuilder.append("\n".repeat(Math.max(0, layoutContext.getDistanceBetweenRows())));

        StringBuilder tempStringBuilder = new StringBuilder();
        String prefix = "";
        for (Option option : row) {
            tempStringBuilder.append(prefix);
            String optionStr = colorService.formatOptionString(option.getLabel(), option.isHighlighted());
            tempStringBuilder.append(optionStr);
            prefix = " ".repeat(layoutContext.getDistanceBetweenOptions());
        }
        if (layoutContext.isCenter()) {
            stringBuilder.append(center(tempStringBuilder.toString()));
        } else {
            stringBuilder.append(tempStringBuilder);
        }
        stringBuilder.append("\n");
        return this;
    }

    public LayoutBuilder setCommandText(String commandText) {
        layoutContext.setCommandText(commandText);
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

        stringBuilder.append("\n".repeat(Math.max(0, layoutContext.getDistanceBetweenRows())));

        String content = String.format(format, row.toArray()) + "\n";
        if (layoutContext.isCenter()) {
            stringBuilder.append(center(content.toString()));
        } else {
            stringBuilder.append(content);
        }
        stringBuilder.append("\n");
        return this;
    }

    private String center(String toCenter) {
        //int columns = terminalService.getSize().getColumns();
        int columns = getTerminalCols();
        int columnsUsed = countStringColumns(toCenter);
        int columnsWhitespace = columns - columnsUsed;
        int leftPadding = columnsWhitespace / 2;
        return " ".repeat(leftPadding) + toCenter;
    }

    /**
     * Simple method for stripping away ANSI encoding from a string, also uses {@link String#strip()} for good measure
     */
    private String stripANSIString(String str) {
        String strippedString = str.replaceAll("\u001B\\[[\\d;]*[^\\d;]","");
        return strippedString.strip();
    }

    private int countStringColumns(String str) {
        return stripANSIString(str).length();
    }

    private int countStringRows(String str) {
        String stripped = stripANSIString(str);
        int newLines = stripped.length() - stripped.replace("\n", "").length();
        return newLines; // We *should* always append \n even to single line builders
    }

    public LayoutBuilder addOption(Option opt) {
        return addOptionRow(new ArrayList<>() {{add(opt);}});
    }

    public LayoutBuilder setFormat(String format) {
        this.layoutContext.setFormat(format);
        return this;
    }

    private void appendCommand(StringBuilder sb, String commandText, int builderRows) {
        int rows = getTerminalRows();
        int toAppend = rows - builderRows - 1; // -1 because we always append \n to lines
        sb.append("\n".repeat(toAppend));
        sb.append(commandText);
    }

    public String build() {
        StringBuilder resBuilder = new StringBuilder();
        resBuilder.append(stringBuilder);
        int builderRows = countStringRows(resBuilder.toString());
        if ((builderRows < getTerminalRows()) && (!layoutContext.getCommandText().equals(""))) {
            appendCommand(resBuilder, layoutContext.getCommandText(), builderRows);
        }
        return resBuilder.toString();
    }

    @Override
    public String toString() {
        return build();
    }
}
