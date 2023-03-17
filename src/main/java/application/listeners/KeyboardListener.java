package application.listeners;

import application.controller.Controller;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.IOException;

@Component
public class KeyboardListener {
    private final Controller controller;

    public KeyboardListener(Controller controller) {
        this.controller = controller;

    }

    @Scheduled(fixedDelay = 1)
    public void listen() throws IOException {
        // Look into https://github.com/kwhat/jnativehook
    }
}
