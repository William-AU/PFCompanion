package application.view.strategies;

import application.listeners.ListenerKey;
import application.view.options.Option;
import application.view.options.OptionGrid;
import application.view.options.Position;

public class MoveOverOptionMovementStrategy implements OptionMovementStrategy {
    private final SimpleOptionMovementStrategy delegate;

    public MoveOverOptionMovementStrategy() {
        this.delegate = new SimpleOptionMovementStrategy();
    }

    /**
     * Moves the current grid based on some logic. Returns true if a move was successful
     * @param grid The {@link OptionGrid} to perform the movement on
     * @param key The {@link ListenerKey} pressed by the user
     * @return True if the option changed, false otherwise
     */
    @Override
    public boolean handleMove(OptionGrid grid, ListenerKey key) {
        Option currentOption = grid.getCurrentOption();
        if (!currentOption.canMoveOver()) {
            return handleMutableOption(currentOption, key);
        };
        return delegate.handleMove(grid, key);
    }

    private boolean handleMutableOption(Option option, ListenerKey key) {
        switch (key) {
            case LEFT -> {
                option.setPreviousOption();
                return true;
            }
            case RIGHT -> {
                option.setNextOption();
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}
