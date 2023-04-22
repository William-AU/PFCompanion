package application.services.sceneServices;

import lombok.Data;
import lombok.Getter;
@Getter
public class SceneServiceContext {
    private final ColorService colorService;
    private final ConsoleService consoleService;
    private final TerminalService terminalService;

    public SceneServiceContext(ColorService colorService, ConsoleService consoleService, TerminalService terminalService) {
        this.colorService = colorService;
        this.consoleService = consoleService;
        this.terminalService = terminalService;
    }

}