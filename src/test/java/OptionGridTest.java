import application.view.options.Option;
import application.view.options.OptionGrid;
import application.view.options.Position;
import application.view.options.SimpleOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Simple test to ensure {@link OptionGrid#equals(Object)} is implemented reasonably
 */
public class OptionGridTest {
    private OptionGrid grid1, grid2, grid3, grid4;

    @BeforeEach
    private void init() {
        // '<>' denotes selected option
        /* Grid 1
        <OPTION1> OPTION2
         */
        Map<Position, Option> map1 = new HashMap<>() {{
            put(new Position(0, 0), new SimpleOption("OPTION1", "1"));
            put(new Position(1, 0), new SimpleOption("OPTION2", "2"));
        }};
        grid1 = new OptionGrid(map1);

        /* Grid 2 (equal to grid 1)
        <OPTION1> OPTION2
         */
        Map<Position, Option> map2 = new HashMap<>() {{
            put(new Position(0, 0), new SimpleOption("OPTION1", "1"));
            put(new Position(1, 0), new SimpleOption("OPTION2", "2"));
        }};
        grid2 = new OptionGrid(map2);

        /* Grid 3
        OPTION1 <OPTION2>
         */
        Map<Position, Option> map3 = new HashMap<>() {{
            put(new Position(0, 0), new SimpleOption("OPTION1", "1"));
            put(new Position(1, 0), new SimpleOption("OPTION2", "2"));
        }};
        grid3 = new OptionGrid(map3);
        grid3.moveRight();

        /* Grid 4
        <OPTION1>
        OPTION2
         */
        Map<Position, Option> map4 = new HashMap<>() {{
            put(new Position(0, 0), new SimpleOption("OPTION1", "1"));
            put(new Position(0, 1), new SimpleOption("OPTION2", "2"));
        }};
    }

    @Test
    public void shouldBeEqualIfIdentical() {
        assertThat(grid1, is(grid2));
    }

    @Test
    public void shouldNotBeEqualIfSelectionIsDifferent() {
        assertThat(grid1, not(grid3));
    }

    @Test
    public void shouldNotBeEqualIfDifferent() {
        assertThat(grid1, not(grid4));
    }
}
