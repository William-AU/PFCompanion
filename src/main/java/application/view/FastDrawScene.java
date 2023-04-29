package application.view;

import application.controller.Controller;

/**
 * A simple interface which defaults to using FastDraw instead of regular Draw methods
 */
public interface FastDrawScene extends Scene {
    /**
     * Only called if this {@link Scene} returns true on {@link Scene#useFastDraw()}. Instead of using print statements, instead returns a formatted {@link String} to be printed.
     * {@link Scene#fastDraw()} is called immediately after the console is cleared by the controller
     *
     * @return The string to be drawn
     */
    @Override
    String fastDraw();

    /**
     * Tells the {@link Controller} if this {@link Scene} is using fast draw. Fast draw delegates the responsibility of drawing to the {@link Controller}, this allows for slightly more optimised CLS timing.
     *
     * @return True if the scene uses fast draw, false otherwise
     */
    @Override
    default boolean useFastDraw() {
        return true;
    }

    @Override
    default void draw() {
        System.out.println(fastDraw());
    };
}
