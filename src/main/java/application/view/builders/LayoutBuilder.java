package application.view.builders;

import application.common.Constants;
import application.services.ColorService;
import application.storage.services.ServiceContext;
import application.storage.services.TerminalService;
import application.view.options.Option;
import application.view.options.SimpleOption;

import java.util.ArrayList;
import java.util.List;

public class LayoutBuilder {
    private LayoutContext layoutContext;
    private final StringBuilder stringBuilder;
    private final ColorService colorService;
    private final TerminalService terminalService;

    public LayoutBuilder(ServiceContext serviceContext) {
        this.layoutContext = new LayoutContext();
        this.stringBuilder = new StringBuilder();
        this.colorService = serviceContext.getColorService();
        this.terminalService = serviceContext.getTerminalService();
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
        if (layoutContext.center) {
            stringBuilder.append(center(line)).append("\n");
            return this;
        }
        stringBuilder.append(line).append("\n");
        return this;
    }

    /**
     * Adds a line to the builder from a list of options. Each option is formatted by the {@link LayoutContext} and {@link application.config.ColorConfig.ColorContext}
     * @param row The row of {@link SimpleOption} to be formatted
     * @return The updated instance of the {@link LayoutBuilder}
     */
    public LayoutBuilder addOptionRow(List<Option> row) {
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



    public String build() {
        return stringBuilder.toString();
    }

    private static class LayoutContext {
        private int distanceBetweenOptions;
        private boolean center;

        public LayoutContext() {
            distanceBetweenOptions = 1;
            center = false;
        }

        public LayoutContext(int distanceBetweenOptions) {
            this.distanceBetweenOptions = distanceBetweenOptions;
            center = false;
        }
    }
}
