package application.model.GM;

import application.storage.entities.CampaignEntity;

public class Campaign {
    private String name;

    public Campaign(String name) {
        this.name = name;
    }

    public Campaign(CampaignEntity entity) {
        this.name = entity.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
