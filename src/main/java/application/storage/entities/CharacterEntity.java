package application.storage.entities;

import application.model.Attribute;
import application.model.Character;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class CharacterEntity {
    @Id
    private int id;
    private int STR, DEX, CON, INT, WIS, CHA;

    private String name;

    /**
     * No args constructor for JPA services
     */
    public CharacterEntity() {

    }

    /**
     * Constructs a CharacterEntity object from a Character object. This construction keeps all the information of the
     * underlying character model, but formats it in a way that is suitable for a JPA repository
     * @param character The underlying character to copy
     */
    public CharacterEntity(Character character) {
        name = character.getName();
        STR = character.getAttribute(Attribute.STR);
        DEX = character.getAttribute(Attribute.DEX);
        CON = character.getAttribute(Attribute.CON);
        INT = character.getAttribute(Attribute.INT);
        WIS = character.getAttribute(Attribute.WIS);
        CHA = character.getAttribute(Attribute.CHA);
    }

    /**
     * Recreates a Character object from the data within this entity
     * @return The generated Character object
     */
    public Character getCharacter() {
        Map<Attribute, Integer> attributes = new HashMap<>() {{
            put(Attribute.STR, STR);
            put(Attribute.DEX, DEX);
            put(Attribute.CON, CON);
            put(Attribute.INT, INT);
            put(Attribute.WIS, WIS);
            put(Attribute.CHA, CHA);
        }};
        return new Character(name, attributes);
    }
}
