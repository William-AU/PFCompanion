package application.storage.entities;

import application.model.GM.Campaign;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class CampaignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    /**
     * A no-args constructor is required for a persistence entity to work
     */
    public CampaignEntity() {}

    /**
     * Sets entity data from a given {@link Campaign}, works as a manual constructor except also handles correctly incrementing ID
     * @param campaign The {@link Campaign} to get data from
     */
    public void setCampaign(Campaign campaign) {
        this.name = campaign.getName();
    }
}
