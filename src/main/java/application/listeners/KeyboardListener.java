package application.listeners;

import application.controller.Controller;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyboardListener implements NativeKeyListener {
    private final Controller controller;

    public KeyboardListener(Controller controller) {
        this.controller = controller;
    }


    /**
     * Invoked when a key has been pressed.
     *
     * @param nativeEvent the native key event.
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        controller.handleKeyEvent(getKey(nativeEvent.getKeyCode()));
    }

    private ListenerKey getKey(int code) {
        return switch (code) {
            case 57419 -> ListenerKey.LEFT;
            case 57416 -> ListenerKey.UP;
            case 57421 -> ListenerKey.RIGHT;
            case 57424 -> ListenerKey.DOWN;
            case 28 -> ListenerKey.ENTER;
            case 15 -> ListenerKey.TAB;
            default -> ListenerKey.NONE;
        };
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
}
