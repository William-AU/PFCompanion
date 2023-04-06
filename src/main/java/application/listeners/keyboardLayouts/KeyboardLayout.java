package application.listeners.keyboardLayouts;

import application.listeners.ListenerKey;
import application.listeners.ListenerModifier;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Defines a keyboard layout based on {@link NativeKeyEvent#getKeyCode()} codes
 */
public interface KeyboardLayout {
    /**
     * Get the layout specific char given a {@link NativeKeyEvent#getKeyCode()} and a modifier key
     * @param code The {@link NativeKeyEvent#getKeyCode()}
     * @param modifier ALT, SHIFT, CTRL as defined in {@link ListenerModifier}
     * @return The keyboard specific char with the given modifier held
     */
    char getChar(int code, ListenerModifier modifier);

    /**
     * Get the {@link ListenerKey} given the layout specific {@link NativeKeyEvent#getKeyCode()};
     * @param code The {@link NativeKeyEvent#getKeyCode()}
     * @return The keyboard specific {@link ListenerKey}
     */
    ListenerKey getKey(int code);

    /**
     * Get the modifier (ALT, SHIFT, CTRL) from a specific {@link NativeKeyEvent#getKeyCode()}
     * @param code The {@link NativeKeyEvent#getKeyCode()}
     * @return The keyboard specific {@link ListenerModifier}
     */
    ListenerModifier getModifier(int code);
}
