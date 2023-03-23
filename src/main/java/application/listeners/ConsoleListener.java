package application.listeners;

import application.controller.Controller;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

@Component
public class ConsoleListener {
    private Controller controller;
    private Terminal terminal;
    private LineReader lineReader;

    public ConsoleListener(Controller controller) {
        this.controller = controller;
    }

    @Autowired
    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
        terminal.enterRawMode();
        lineReader = LineReaderBuilder.builder().terminal(terminal).build();
    }

    /**
     * Method will continuously scan the console input and react to any message typed
     * Makes use of {@link org.springframework.scheduling.annotation.Scheduled} to constantly listen without hogging the thread
     */
    //@Scheduled(fixedDelay = 1)
    public void listen() {
        String line = null;
        try {
            line = lineReader.readLine(">");
        } catch (UserInterruptException | EndOfFileException ignored) {

        }
    }
}
