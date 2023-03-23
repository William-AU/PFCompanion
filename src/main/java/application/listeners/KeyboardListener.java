package application.listeners;

import application.controller.Controller;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.IOException;

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
        System.out.println("Found key: " + nativeEvent.getKeyCode() + " (" + nativeEvent.getKeyChar() + ")");
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
}
