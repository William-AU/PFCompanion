package application.services.sceneServices;

import application.common.Constants;
import application.utils.ASCIIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConsoleService {
    private  BuildProperties buildProperties;

    @Autowired
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
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

    public void drawTitle() {
        ASCIIUtils.print("Companion", Constants.ART_SIZE, ASCIIUtils.ASCIIArtFont.ART_FONT_DIALOG_INPUT, "█");
        System.out.println(buildProperties.getVersion());
        System.out.println();
        System.out.println();
    }
}