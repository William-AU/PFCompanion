package application.services.controllerServices;

import application.model.character.Character;
import application.storage.entities.CharacterEntity;
import application.storage.repositories.CharacterRepository;
import org.springframework.stereotype.Service;

/**
 * Only class allowed to interact directly with the {@link application.storage.repositories.CharacterRepository} and {@link application.storage.entities.CharacterEntity} classes.
 * Any character extracted using this service should be of the class {@link Character}
 */
@Service
public class CharacterService {
    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds the first character by a given name and converts it to {@link Character}
     * @param name Name of the character to look for in the database
     * @return The converted {@link Character}
     */
    public Character getFirstCharacterByName(String name) {
        CharacterEntity entity = repository.getCharacterEntityByName(name);
        return entity.getCharacter();
    }
}
