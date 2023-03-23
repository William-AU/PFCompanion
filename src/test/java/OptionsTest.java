import application.view.options.Option;
import application.view.options.OptionGrid;
import application.view.options.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OptionsTest {
    private OptionGrid grid;

    @BeforeEach
    private void init() {
        /*
        Represent the following option grid
        OPTION1     OPTION2
        OPTION3     OPTION4     OPTION5
        OPTION6     OPTION7
        OPTION8     OPTION9     OPTION10    OPTION11
         */
        Map<Position, Option> gridMap = new HashMap<>() {{
            put(new Position(0, 0), new Option("Option 1", "1"));
            put(new Position(1, 0), new Option("Option 2", "2"));
            put(new Position(0, 1), new Option("Option 3", "3"));
            put(new Position(1, 1), new Option("Option 4", "4"));
            put(new Position(2, 1), new Option("Option 5", "5"));
            put(new Position(0, 2), new Option("Option 6", "6"));
            put(new Position(1, 2), new Option("Option 7", "7"));
            put(new Position(0, 3), new Option("Option 8", "8"));
            put(new Position(1, 3), new Option("Option 9", "9"));
            put(new Position(2, 3), new Option("Option 10", "10"));
            put(new Position(3, 3), new Option("Option 11", "11"));
        }};
        grid = new OptionGrid(gridMap);
    }

    @Test
    public void shouldHandleEvenGrid() {
        grid.moveRight();
        assertThat(grid.getCurrentOption().getId(), is("2"));
        grid.moveDown();
        assertThat(grid.getCurrentOption().getId(), is("4"));
        grid.moveLeft();
        assertThat(grid.getCurrentOption().getId(), is("3"));
        grid.moveUp();
        assertThat(grid.getCurrentOption().getId(), is("1"));
    }

    @Test
    public void shouldGetOptionRow() {
        List<Option> row1 = grid.getOptionRow(0);
        assertThat(row1.get(0).getId(), is("1"));
        assertThat(row1.get(1).getId(), is("2"));
        List<Option> row4 = grid.getOptionRow(3);
        assertThat(row4.get(0).getId(), is("8"));
        assertThat(row4.get(1).getId(), is("9"));
        assertThat(row4.get(2).getId(), is("10"));
        assertThat(row4.get(3).getId(), is("11"));
    }
}
