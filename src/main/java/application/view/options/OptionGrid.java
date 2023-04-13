package application.view.options;

import lombok.SneakyThrows;

import java.util.*;

public class OptionGrid {
    private final Map<Position, Option> optionMap;
    private Position currentPosition;

    public OptionGrid(Map<Position, Option> optionMap) {
        this.optionMap = optionMap;
        Position pos = new Position(0, 0);
        if (optionMap.containsKey(pos)) {
            currentPosition = pos;
            if (!optionMap.get(pos).isHighlighted()) {
                optionMap.get(pos).toggleHighlight();
            }
        } else {
            throw new IllegalArgumentException("Option map must have a zero element (Position(0, 0) could not be found)");
        }
        validate();
    }

    @SneakyThrows // SneakyThrows is redundant because IllegalArgumentException is sneaky by default, but this way we can use at normal while also showing that we actually intend to throw an exception... it's a bit hacky
    private List<List<Position>> getAllRows() throws IllegalArgumentException{
        List<Integer> knownRows = new ArrayList<>();
        Map<Integer, List<Position>> rowMap = new HashMap<>();
        for (Position pos : optionMap.keySet()) {
            int row = pos.getY();
            if (!knownRows.contains(row)) {
                knownRows.add(row);
                rowMap.put(row, new ArrayList<>());
            }
            rowMap.get(row).add(pos);
        }
        // This assignment reverses all the rows, we have to undo that
        for (List<Position> row : rowMap.values()) {
            Collections.reverse(row);
        }
        int max = Collections.max(knownRows);
        if (max != knownRows.size() - 1) throw new IllegalArgumentException("ROW GAP");
        return new ArrayList<>(rowMap.values());
    }

    private void validate() {
        List<List<Position>> rows;
        try {
            // Assignment to allRows will throw exception if there is a gap between the rows
            rows = getAllRows();
        } catch (IllegalArgumentException e) {
            throw e;
        }
        // Check that there is no gap in the same row
        for (List<Position> row : rows) {
            // For some reason it is possible for the layout builder to mess up the position order, this ensures we can trust the order
            Collections.sort(row);
            for (int i = 0; i < row.size(); i++) {
                // Extract the first position
                Position pos = row.remove(0);
                // If we are empty, then this means we have a fully cohesive row
                if (row.isEmpty()) break;
                // If we are not empty, then the next element must be next to the current one
                Position newPos = row.get(0);
                if (!pos.moveRight().equals(newPos)) {
                    throw new IllegalArgumentException("COLUMN GAP");
                }
            }
        }
    }

    private List<Position> getPositionRow(int row) {
        List<Position> result = new ArrayList<>();
        for (Position p : optionMap.keySet()) {
            if (p.getY() == row) result.add(p);
        }
        Collections.sort(result);
        return result;
    }

    public List<Option> getOptionRow(int row) {
        Position currentPos = new Position(0, row);
        if (!optionMap.containsKey(currentPos)) return null;
        List<Option> result = new ArrayList<>();
        Option nextOption = optionMap.get(currentPos);
        while (nextOption != null) {
            result.add(nextOption);
            nextOption = getNextOptionOnRow(currentPos, row);
            currentPos = currentPos.moveRight();
        }
        return result;
    }

    private Option getNextOptionOnRow(Position currentPos, int row) {
        Position newPosition = currentPos.moveRight();
        if (!optionMap.containsKey(newPosition)) return null;
        return optionMap.get(newPosition);
    }

    public Option getCurrentOption() {
        return optionMap.get(currentPosition);
    }

    /**
     * Moves the current selected grid option to the right, if no option exists this method does nothing
     * @return The instance of the {@link OptionGrid} with an updated position
     */
    public OptionGrid moveRight() {
        Position newPos = currentPosition.moveRight();
        if (optionMap.containsKey(newPos)) {
            optionMap.get(currentPosition).toggleHighlight();
            optionMap.get(newPos).toggleHighlight();
            currentPosition = newPos;
        }
        return this;
    }

    /**
     * Handles a tab event, moves to the next available right option in the row, if current option is the right most, loop back and select left most option
     * @return The updated instance of the {@link OptionGrid}
     */
    public OptionGrid tab() {
        Position rightPos = currentPosition.moveRight();
        if (optionMap.containsKey(rightPos)) {
            return moveRight();
        }
        Position leftMost = new Position(0, currentPosition.getY());
        if (currentPosition.equals(leftMost)) return this;
        optionMap.get(currentPosition).toggleHighlight();
        optionMap.get(leftMost).toggleHighlight();
        currentPosition = leftMost;
        return this;
    }

    /**
     * As {@link OptionGrid#tab()} except moves left instead of right
     * @return The updated instance of the {@link OptionGrid}
     */
    public OptionGrid shiftTab() {
        Position leftPos = currentPosition.moveLeft();
        if (optionMap.containsKey(leftPos)) {
            return moveLeft();
        }
        int rightMostCol = getRightMostPositionCol(currentPosition.getY());
        Position rightMost = new Position(rightMostCol, currentPosition.getY());
        if (currentPosition.equals(rightMost)) return this;
        optionMap.get(currentPosition).toggleHighlight();
        optionMap.get(rightMost).toggleHighlight();
        currentPosition = rightMost;
        return this;
    }

    private int getRightMostPositionCol(int row) {
        return optionMap.keySet()
                .stream()
                .filter(key -> key.getY() == row)
                .mapToInt(Position::getX)
                .max().orElse(0);
    }

    /**
     * Moves the current selected grid option to the right, if no option exists this method does nothing
     * @return The instance of the {@link OptionGrid} with an updated position
     */
    public OptionGrid moveLeft() {
        Position newPos = currentPosition.moveLeft();
        if (optionMap.containsKey(newPos)) {
            optionMap.get(currentPosition).toggleHighlight();
            optionMap.get(newPos).toggleHighlight();
            currentPosition = newPos;
        }
        return this;
    }

    /**
     * Moves the current selected grid option up, if the current option is further to the right than in the row above, will select the right-most option on the row above.
     * If the current option is already on the top row, this method does nothing
     * @return The instance of the {@link OptionGrid} with an updated position
     */
    public OptionGrid moveUp() {
        Position newPos = currentPosition.moveUp();
        if (currentPosition.getY() == 0) return this;
        if (!optionMap.containsKey(newPos)) {
            List<Position> row = getPositionRow(currentPosition.getY() - 1);
            newPos = row.get(row.size() - 1);
            optionMap.get(currentPosition).toggleHighlight();
            optionMap.get(newPos).toggleHighlight();
            currentPosition = newPos;
            return this;
        };
        optionMap.get(currentPosition).toggleHighlight();
        optionMap.get(newPos).toggleHighlight();
        currentPosition = newPos;
        return this;
    }

    private int getNumberOfRows() {
        return getAllRows().size();
    }

    /**
     * Moves the current selected grid option down, if the current option is further to the right than in the row below, will select the right-most option in the row below.
     * If the current option is already on the bottom row, this method does nothing
     * @return The instance of the {@link OptionGrid} with an updated position
     */
    public OptionGrid moveDown() {
        Position newPos = currentPosition.moveDown();
        if (currentPosition.getY() == getNumberOfRows() - 1) return this;
        if (!optionMap.containsKey(newPos)) {
            List<Position> row = getPositionRow(currentPosition.getY() + 1);
            newPos = row.get(row.size() - 1);
            optionMap.get(currentPosition).toggleHighlight();
            optionMap.get(newPos).toggleHighlight();
            currentPosition = newPos;
            return this;
        }
        optionMap.get(currentPosition).toggleHighlight();
        optionMap.get(newPos).toggleHighlight();
        currentPosition = newPos;
        return this;
    }

    public Map<Position, Option> DEBUG() {
        return optionMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionGrid that = (OptionGrid) o;
        //return optionMap.keySet().equals(that.optionMap.keySet()) && currentPosition.equals(that.currentPosition);
        return optionMap.equals(that.optionMap) && currentPosition.equals(that.currentPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionMap, currentPosition);
    }
}
