package application.storage.services;

import application.services.ColorService;

public class ServiceContext {
    private final CharacterService characterService;
    private final CredentialsService credentialsService;
    private final ColorService colorService;

    public ServiceContext(CharacterService characterService, CredentialsService credentialsService, ColorService colorService) {
        this.characterService = characterService;
        this.credentialsService = credentialsService;
        this.colorService = colorService;
    }
}
