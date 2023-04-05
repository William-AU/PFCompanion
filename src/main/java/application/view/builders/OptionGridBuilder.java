package application.view.builders;

import application.view.options.*;

import java.util.*;

/**
 * Simple builder for {@link OptionGrid} creation, abstracts away a lot of map constructions
 */
public class OptionGridBuilder {
    private final Map<Position, Option> optionMap;

    public OptionGridBuilder() {
        optionMap = new HashMap<>();
    }

    /**
     * Adds an option to the grid at a given position
     * @param position The position of the {@link Option} in the {@link OptionGrid}
     * @param option The {@link Option} to add
     * @return The updated instance of the {@link OptionGridBuilder}
     */
    public OptionGridBuilder addOption(Position position, Option option) {
        optionMap.put(position, option);
        return this;
    }

    /**
     * Adds an {@link Option} to the {@link OptionGrid} at a given position without manually creating a {@link Position} object
     * @param x The x-coordinate of the {@link Position}
     * @param y The y-coordinate of the {@link Position}
     * @param option The {@link Option} to display at the given position
     * @return The updated instance of the {@link OptionGridBuilder}
     */
    public OptionGridBuilder addOption(int x, int y, Option option) {
        return addOption(new Position(x, y), option);
    }

    /**
     * Creates a {@link MutableOption} and adds it as {@link OptionGridBuilder#addOption(Position, Option)}
     * @param position The {@link Position} of the {@link MutableOption}
     * @param options The {@link SimpleOption} objects that make up the {@link MutableOption}
     * @return The updated instance of the {@link OptionGridBuilder}
     */
    public OptionGridBuilder addMutableOption(Position position, SimpleOption... options) {
        List<SimpleOption> simpleOptions = new ArrayList<>() {{
            this.addAll(Arrays.asList(options));
        }};
        MutableOption toAdd = new MutableOption(simpleOptions);
        addOption(position, toAdd);
        return this;
    }

    /**
     * As {@link OptionGridBuilder#addMutableOption(Position, SimpleOption...)} except abstracts away the {@link Position} object
     */
    public OptionGridBuilder addMutableOption(int x, int y, SimpleOption... options) {
        return addMutableOption(new Position(x, y), options);
    }

    /**
     * Adds a collection of {@link Option} objects to the next free row in the internal pseudo {@link OptionGrid}
     * @param options The collection of {@link Option} objects to add
     * @return The updated instance of the {@link OptionGridBuilder}
     */
    public OptionGridBuilder addOptionRow(Option... options) {
        int currentLargestRow = getLargestRow();
        for (int i = 0; i < options.length; i++) {
            optionMap.put(new Position(currentLargestRow + 1, i), options[i]);
        }
        return this;
    }

    /**
     * Finds the largest occupied row in the {@link OptionGrid}, this is equivalent to finding the row of the largest {@link Position} in the optionMap
     * @return The largest row of the internal <{@link Position}, {@link Option}> map
     */
    private int getLargestRow() {
        // Stream magic
        return optionMap.keySet()
                .stream()
                .mapToInt(Position::getY)
                .max().orElseThrow(NoSuchElementException::new);
    }

    public OptionGrid build() {
        return new OptionGrid(optionMap);
    }
}
