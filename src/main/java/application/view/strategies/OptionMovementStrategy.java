package application.view.strategies;

import application.listeners.ListenerKey;
import application.view.options.OptionGrid;

public interface OptionMovementStrategy {
    /**
     * Moves the current grid based on some logic. Returns true if a move was successful
     * @param grid The {@link OptionGrid} to perform the movement on
     * @param key The {@link ListenerKey} pressed by the user
     * @return True if the option changed, false otherwise
     */
    boolean handleMove(OptionGrid grid, ListenerKey key);
}
