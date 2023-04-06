package application.view.strategies;

import application.listeners.ListenerKey;
import application.view.options.Option;
import application.view.options.OptionGrid;
import application.view.options.SimpleOption;

public class SimpleOptionMovementStrategy implements OptionMovementStrategy{
    @Override
    public boolean handleMove(OptionGrid grid, ListenerKey key) {
        Option currentOption = grid.getCurrentOption();
        switch (key) {
            case UP -> grid.moveUp();
            case DOWN -> grid.moveDown();
            case LEFT -> grid.moveLeft();
            case RIGHT -> grid.moveRight();
            case TAB -> grid.tab();
        }
        return !currentOption.equals(grid.getCurrentOption());
    }
}
