package application.model;

import java.util.HashMap;
import java.util.Map;

public class Character {
    private final Map<Attribute, Integer> attributes;

    public Character(Map<Attribute, Integer> attributes) {
        this.attributes = attributes;
    }

    public Character() {
        this.attributes = new HashMap<>() {{
            put(Attribute.STR, 10);
            put(Attribute.DEX, 10);
            put(Attribute.CON, 10);
            put(Attribute.INT, 10);
            put(Attribute.WIS, 10);
            put(Attribute.CHA, 10);
        }};
    }

    public void setAttribute(Attribute attribute, int value) {
        attributes.put(attribute, value);
    }

    public int getAttribute(Attribute attribute) {
        return attributes.get(attribute);
    }

    public int getModifier(Attribute attribute) {
        int value = attributes.get(attribute);
        return (value - 10) / 2;
    }
}
