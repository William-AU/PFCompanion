package application.listeners;

import application.controller.Controller;
import application.listeners.keyboardLayouts.KeyboardLayout;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class KeyboardListener implements NativeKeyListener {
    private final Controller controller;
    private final KeyboardLayout keyboardLayout;
    private final Set<ListenerModifier> activeModifiers;
    // Not entirely sure if this is needed, but better safe than sorry
    // Mutex for the activeModifiers set
    private final Semaphore mutex;


    public KeyboardListener(Controller controller, KeyboardLayout keyboardLayout) {
        this.controller = controller;
        this.keyboardLayout = keyboardLayout;
        this.activeModifiers = new HashSet<>();
        this.mutex = new Semaphore(1);
    }


    /**
     * Invoked when a key has been pressed.
     *
     * @param nativeEvent the native key event.
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        //System.out.println(nativeEvent.getKeyCode());
        ListenerModifier modifier = getActiveModifier(nativeEvent.getKeyCode());
        ListenerKey keyCode;
        if (modifier == ListenerModifier.NONE) {
            keyCode = keyboardLayout.getKey(nativeEvent.getKeyCode());
        } else {
            keyCode = keyboardLayout.getKeyWithModifier(nativeEvent.getKeyCode(), modifier);
        }
        if (keyCode != ListenerKey.NONE) {
            controller.handleKeyEvent(keyCode);
            return;
        }
        char ch = keyboardLayout.getChar(nativeEvent.getKeyCode(), modifier);
        if (ch == '\0') return;
        controller.handleCharEvent(keyboardLayout.getChar(nativeEvent.getKeyCode(), modifier));

    }

    private ListenerModifier getActiveModifier(int code) {
        try {
            mutex.acquire();
            ListenerModifier modifier = keyboardLayout.getModifier(code);
            if (modifier == ListenerModifier.NONE && activeModifiers.isEmpty()) return ListenerModifier.NONE;
            if (modifier != ListenerModifier.NONE) {
                activeModifiers.add(modifier);
                return modifier;
            }
            modifier = activeModifiers.stream().findFirst().get();
            return modifier;

        } catch (InterruptedException ignored) {
        } finally {
            mutex.release();
        }
        return ListenerModifier.NONE;
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        ListenerModifier modifier = keyboardLayout.getModifier(nativeKeyEvent.getKeyCode());
        if (modifier != ListenerModifier.NONE) {
            removeModifier(modifier);
        }
    }

    private void removeModifier(ListenerModifier modifier) {
        try {
            mutex.acquire();
            activeModifiers.remove(modifier);
        } catch (InterruptedException ignored) {
        } finally {
            mutex.release();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
}
