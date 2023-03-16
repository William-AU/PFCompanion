package application.storage.repositories;

import application.storage.entities.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<CharacterEntity, String> {
    CharacterEntity getCharacterEntityByName(String name);
}
