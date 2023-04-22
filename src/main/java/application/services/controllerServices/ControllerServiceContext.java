package application.services.controllerServices;

public class ControllerServiceContext {
    private final CampaignService campaignService;
    private final CharacterService characterService;

    public ControllerServiceContext(CampaignService campaignService, CharacterService characterService) {
        this.campaignService = campaignService;
        this.characterService = characterService;
    }
}
