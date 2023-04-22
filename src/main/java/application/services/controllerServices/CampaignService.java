package application.services.controllerServices;

import application.model.GM.Campaign;
import application.storage.entities.CampaignEntity;
import application.storage.repositories.CampaignRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {
    private final CampaignRepository repository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.repository = campaignRepository;
    }

    public List<String> getCampaignNames() {
        return new ArrayList<>() {{
            repository.getALL().forEach(campaign -> add(campaign.getName()));
        }};
    }

    /**
     * Find the campaign by the given name
     * @param name Name of the campaign given at creation
     * @return The {@link Campaign} representation of the campaign
     */
    public Campaign getCampaignByName(String name) {
        CampaignEntity entity = repository.getCampaignEntityByName(name);
        return new Campaign(entity);
    }

    /**
     * Create a blank {@link Campaign} object with the given name
     * @param name The name of the new {@link Campaign}
     * @throws IllegalStateException if the save fails because a {@link Campaign} with the given name already exists in the database
     */
    public void createAndSaveCampaign(String name) throws IllegalStateException {
        if (repository.existsCampaignEntityByName(name)) throw new IllegalStateException("A campaign by the name " + name + " already exists in the database");
        Campaign campaign = new Campaign(name);

    }
}
