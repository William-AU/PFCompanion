package application.view.strategies;

import application.listeners.ListenerKey;
import application.view.options.OptionGrid;

public class NullMovementStrategy implements OptionMovementStrategy {
    @Override
    public boolean handleMove(OptionGrid grid, ListenerKey key) {
        return false;
    }
}
