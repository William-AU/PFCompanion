package application.storage.repositories;

import application.storage.entities.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Integer> {
    CharacterEntity getCharacterEntityByName(String name);
}
