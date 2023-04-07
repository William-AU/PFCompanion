package application.storage.services;

import application.services.ColorService;
import application.services.ConsoleService;

public class ServiceContext {
    private final CharacterService characterService;
    private final CredentialsService credentialsService;
    private final ColorService colorService;
    private final TerminalService terminalService;
    private final ConsoleService consoleService;

    public ServiceContext(CharacterService characterService, CredentialsService credentialsService,
                          ColorService colorService, TerminalService terminalService, ConsoleService consoleService) {
        this.characterService = characterService;
        this.credentialsService = credentialsService;
        this.colorService = colorService;
        this.terminalService = terminalService;
        this.consoleService = consoleService;
    }

    public ConsoleService getConsoleService() {
        return consoleService;
    }

    public CharacterService getCharacterService() {
        return characterService;
    }

    public CredentialsService getCredentialsService() {
        return credentialsService;
    }

    public ColorService getColorService() {
        return colorService;
    }

    public TerminalService getTerminalService() {
        return terminalService;
    }
}
