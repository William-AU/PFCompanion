package application.view.builders;

import application.config.ColorConfig;
import application.services.ColorService;
import application.view.options.Option;

import java.util.ArrayList;
import java.util.List;

public class LayoutBuilder {
    private LayoutContext layoutContext;
    private final StringBuilder stringBuilder;
    private final ColorService colorService;

    public LayoutBuilder(ColorService colorService) {
        this.layoutContext = new LayoutContext();
        this.stringBuilder = new StringBuilder();
        this.colorService = colorService;
    }

    public LayoutBuilder setDistanceBetweenOptions(int distance) {
        layoutContext.distanceBetweenOptions = distance;
        return this;
    }

    /**
     * Adds a line, functions as {@link StringBuilder#append(String)} but also appends \n
     * @param line The {@link String} to add
     * @return The updated instance of the {@link LayoutBuilder}
     */
    public LayoutBuilder addLine(String line) {
        stringBuilder.append(line).append("\n");
        return this;
    }

    /**
     * Adds a line to the builder from a list of options. Each option is formatted by the {@link LayoutContext} and {@link application.config.ColorConfig.ColorContext}
     * @param row The row of {@link Option} to be formatted
     * @return The updated instance of the {@link LayoutBuilder}
     */
    public LayoutBuilder addOptionRow(List<Option> row) {
        String prefix = "";
        for (Option option : row) {
            stringBuilder.append(prefix);
            String optionStr = colorService.formatOptionString(option.getLabel(), option.isHighlighted());
            stringBuilder.append(optionStr).append("\n");
            prefix = " ".repeat(layoutContext.distanceBetweenOptions);
        }
        return this;
    }

    public LayoutBuilder addOption(Option opt) {
        return addOptionRow(new ArrayList<>() {{add(opt);}});
    }



    public String build() {
        return stringBuilder.toString();
    }

    private static class LayoutContext {
        private int distanceBetweenOptions;

        public LayoutContext() {
            distanceBetweenOptions = 1;
        }

        public LayoutContext(int distanceBetweenOptions) {
            this.distanceBetweenOptions = distanceBetweenOptions;
        }
    }
}
