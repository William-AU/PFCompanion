package application.view.strategies;

import application.listeners.ListenerKey;
import application.view.options.Option;
import application.view.options.OptionGrid;
import application.view.options.Position;

public class CharacterViewMomentStrategy implements OptionMovementStrategy {
    private final SimpleOptionMovementStrategy delegate;

    public CharacterViewMomentStrategy() {
        this.delegate = new SimpleOptionMovementStrategy();
    }

    @Override
    public boolean handleMove(OptionGrid grid, ListenerKey key) {
        Option currentOption = grid.getCurrentOption();
        if (!currentOption.canMoveOver()) return false;
        return delegate.handleMove(grid, key);
    }
}
