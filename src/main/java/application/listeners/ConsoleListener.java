package application.listeners;

import application.controller.Controller;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleListener {
    private Controller controller;

    public ConsoleListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Method will continuously scan the console input and react to any message typed
     * Makes use of {@link org.springframework.scheduling.annotation.Scheduled} to constantly listen without hogging the thread
     */
    //@Scheduled(fixedDelay = 1)
    public void listen() {
        /*
        LineReader reader = LineReaderBuilder.builder().build();
        String line = null;
        try {
            line = reader.readLine();
            System.out.println(line);
        } catch (UserInterruptException ignored) {

        } catch (EndOfFileException e) {
            System.out.println("Lost connection to terminal");
        }

         */
    }
}
