package application.services.sceneServices;

import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling anything to do with the terminal linked to the JVM. Primarily used for resolution information
 */
@Service
public class TerminalService {
    Terminal terminal;

    public TerminalService(Terminal terminal) {
        this.terminal = terminal;
    }

    public Size getSize() {
        return terminal.getSize();
    }
}
