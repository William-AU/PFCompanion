package application.services.sceneServices;

public class SceneServiceContext {
    private final ColorService colorService;
    private final ConsoleService consoleService;
    private final TerminalService terminalService;

    public SceneServiceContext(ColorService colorService, ConsoleService consoleService, TerminalService terminalService) {
        this.colorService = colorService;
        this.consoleService = consoleService;
        this.terminalService = terminalService;
    }

    public ConsoleService getConsoleService() {
        return consoleService;
    }


    public ColorService getColorService() {
        return colorService;
    }

}