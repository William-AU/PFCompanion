package application.storage.repositories;

import application.storage.entities.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {
    CredentialsEntity getByUsername(String username);
    Boolean existsByUsername(String username);
}
