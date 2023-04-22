package application.storage.entities;

import application.model.GM.Campaign;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class CampaignEntity {
    @Id
    private int id;

    private String name;

    /**
     * A no-args constructor is required for a persistence entity to work
     */
    public CampaignEntity() {}

    /**
     * When creating a {@link CampaignEntity} we often already have a {@link Campaign} object, which has all of the data we need. However, this constructor should handle formatting that data in a way that facilitates database storage
     * @param campaign The {@link Campaign} object to clone and save
     */
    public CampaignEntity(Campaign campaign) {
        this.name = campaign.getName();
    }
}
