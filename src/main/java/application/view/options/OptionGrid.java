package application.view.options;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionGrid {
    private final Map<Position, Option> optionMap;
    private Position currentPosition;

    public OptionGrid(Map<Position, Option> optionMap) {
        this.optionMap = optionMap;
        Position pos = new Position(0, 0);
        if (optionMap.containsKey(pos)) {
            currentPosition = pos;
        } else {
            throw new IllegalArgumentException("Option map must have a zero element (Position(0, 0) could not be found)");
        }
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

    // TODO: Consider edge cases where grid is not a perfect rectangle!
    public boolean moveRight() {
        Position newPos = currentPosition.moveRight();
        if (!optionMap.containsKey(newPos)) return false;
        currentPosition = newPos;
        return true;
    }

    public boolean moveLeft() {
        Position newPos = currentPosition.moveLeft();
        if (!optionMap.containsKey(newPos)) return false;
        currentPosition = newPos;
        return true;
    }

    public boolean moveUp() {
        Position newPos = currentPosition.moveUp();
        if (!optionMap.containsKey(newPos)) return false;
        currentPosition = newPos;
        return true;
    }

    public boolean moveDown() {
        Position newPos = currentPosition.moveDown();
        if (!optionMap.containsKey(newPos)) return false;
        currentPosition = newPos;
        return true;
    }
}
