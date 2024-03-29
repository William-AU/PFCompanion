package application.services.sceneServices;

import application.common.ArtConstants;
import application.utils.ASCIIUtils;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConsoleService {
    private  BuildProperties buildProperties;
    private Terminal terminal;

    @Autowired
    public void setBuildProperties(BuildProperties buildProperties, Terminal terminal) {
        this.buildProperties = buildProperties;
        this.terminal = terminal;
    }

    public void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name");
            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
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

    public String clearConsoleString(String input) {
        int height = terminal.getHeight();
        int lines = input.split("\n").length;
        // 3 is the number of blank spaces between the logo and the start of the window
        int toRepeat = height - lines - 3;
        if (toRepeat > 0) {
            return "\n".repeat(height - lines - 3);
        }
        return "";
    }

    public String getTitleString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ASCIIUtils.getString("Companion", ArtConstants.ART_SIZE, ASCIIUtils.ASCIIArtFont.ART_FONT_DIALOG_INPUT, "█")).append(buildProperties.getVersion()).append("\n\n\n");
        return sb.toString();
    }

    public void drawTitle() {
        ASCIIUtils.print("Companion", ArtConstants.ART_SIZE, ASCIIUtils.ASCIIArtFont.ART_FONT_DIALOG_INPUT, "█");
        System.out.println(buildProperties.getVersion());
        System.out.println();
        System.out.println();
    }
}