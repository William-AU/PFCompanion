package application.storage.repositories;

import application.storage.entities.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<CampaignEntity, Integer> {
    CampaignEntity getCampaignEntityByName(String name);
    boolean existsCampaignEntityByName(String name);
}
