import application.model.Attribute;
import application.model.Character;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class CharacterTest {
    private static Character character;

    @BeforeAll
    private static void init() {
        character = new Character();
    }

    /**
     *  Stupid simple test to ensure the equation used for calculating modifiers actually match the expected result for
     *  common stat values
     */
    @Test
    public void shouldCalculateModifierCorrectly() {
        for (int i = 1; i < 21; i++) {
            int expected = switch (i) {
                case 1 -> -5;
                case 2,3 -> -4;
                case 4,5 -> -3;
                case 6,7 -> -2;
                case 8,9 -> -1;
                case 10,11 -> 0;
                case 12,13 -> 1;
                case 14,15 -> 2;
                case 16,17 -> 3;
                case 18,19 -> 4;
                case 20 -> 5;
                default -> throw new IllegalStateException("Unexpected value: " + i);
            };
            character.setAttribute(Attribute.STR, i);
            int actual = character.getModifier(Attribute.STR);
            assertThat(actual, is(expected));
        }
    }
}
