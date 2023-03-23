package application.utils;

import java.io.IOException;

public class ConsoleUtils {
    public static void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name");
            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                //Process startProcess = pb.inheritIO().start();
                //startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
            System.out.println();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}