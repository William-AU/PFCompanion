package application.services.controllerServices;

import lombok.Data;
import lombok.Getter;

@Getter
public class ControllerServiceContext {
    private final CampaignService campaignService;
    private final CharacterService characterService;

    public ControllerServiceContext(CampaignService campaignService, CharacterService characterService) {
        this.campaignService = campaignService;
        this.characterService = characterService;
    }
}
