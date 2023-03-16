package application.model;

import java.util.HashMap;
import java.util.Map;

public class Character {
    private final Map<Attribute, Integer> attributes;
    private String name;

    public Character(String name, Map<Attribute, Integer> attributes) {
        this.attributes = attributes;
        this.name = name;
    }

    public Character(String name) {
        this.name = name;
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

    /**
     * Calculates the modifier for a given attribute, uses the formula (value / 2) - 5
     * @see <a href="https://www.d20pfsrd.com/basics-ability-scores/ability-scores/"\a>
     * @param attribute The attribute to calculate the modifier for
     * @return The calculated modifier
     */
    public int getModifier(Attribute attribute) {
        int value = attributes.get(attribute);
        // Note that the commonly cited (val - 10) / 2 is actually incorrect and doesn't handle negative values correctly
        return (value / 2) - 5;
    }

    public String getName() {
        return name;
    }
}
