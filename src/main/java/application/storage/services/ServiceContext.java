package application.storage.services;

import application.services.ColorService;

public class ServiceContext {
    private final CharacterService characterService;
    private final CredentialsService credentialsService;
    private final ColorService colorService;
    private final TerminalService terminalService;

    public ServiceContext(CharacterService characterService, CredentialsService credentialsService, ColorService colorService, TerminalService terminalService) {
        this.characterService = characterService;
        this.credentialsService = credentialsService;
        this.colorService = colorService;
        this.terminalService = terminalService;
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
