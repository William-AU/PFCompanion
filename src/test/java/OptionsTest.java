import application.view.options.Option;
import application.view.options.OptionGrid;
import application.view.options.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContextException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OptionsTest {
    private OptionGrid gridLeft;
    private Map<Position, Option> invalidPlacementMap;

    @BeforeEach
    private void init() {
        /*
        Represent the following option grid
        OPTION1     OPTION2
        OPTION3     OPTION4     OPTION5
        OPTION6     OPTION7
        OPTION8     OPTION9     OPTION10    OPTION11
         */
        Map<Position, Option> gridMapLeftAlligned = new HashMap<>() {{
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
        gridLeft = new OptionGrid(gridMapLeftAlligned);

        /*
        Invalid grid construction
        OPTION1     OPTION2     OPTION3
        OPTION4                 OPTION5
        OPTION6     OPTION7     OPTION8
         */
        invalidPlacementMap = new HashMap<>() {{
            put(new Position(0, 0), new Option("Option 1", "1"));
            put(new Position(1, 0), new Option("Option 2", "2"));
            put(new Position(2, 0), new Option("Option 3", "3"));
            put(new Position(0, 1), new Option("Option 4", "4"));
            put(new Position(2, 1), new Option("Option 5", "5"));
            put(new Position(0, 2), new Option("Option 6", "6"));
            put(new Position(1, 2), new Option("Option 7", "7"));
            put(new Position(2, 2), new Option("Option 8", "8"));
        }};
    }

    @Test
    public void shouldHighlight() {
        Option currentOption = gridLeft.getCurrentOption();
        assertThat(currentOption.isHighlighted(), is(true));
        gridLeft.moveRight();
        assertThat(currentOption.isHighlighted(), is(false));
        assertThat(gridLeft.getCurrentOption().isHighlighted(), is(true));
    }

    @Test
    public void shouldHandleNo0Index() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () ->
                new OptionGrid(new HashMap<>() {{
                    put(new Position(1, 1), new Option("INVALID", "NONE"));
                }}));
        assertThat(thrown.getMessage(), is("Option map must have a zero element (Position(0, 0) could not be found)"));
    }

    @Test
    public void shouldHandleInvalidOptionPlacement() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () ->
                new OptionGrid(invalidPlacementMap));
        assertThat(thrown.getMessage(), is("Invalid grid option placement"));
    }

    @Test
    public void shouldBeNoGapBetweenRows() {
        /*
        OPTION1

        OPTION2
         */
        Map<Position, Option> map = new HashMap<>() {{
            put(new Position(0, 0), new Option("Option 1", "1"));
            put(new Position(0, 2), new Option("Option 2", "2"));
        }};
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> new OptionGrid(map));
        assertThat(thrown.getMessage(), is("Invalid grid option placement"));
    }

    @Test
    public void shouldHandleEvenGrid() {
        gridLeft.moveRight();
        assertThat(gridLeft.getCurrentOption().getId(), is("2"));
        gridLeft.moveDown();
        assertThat(gridLeft.getCurrentOption().getId(), is("4"));
        gridLeft.moveLeft();
        assertThat(gridLeft.getCurrentOption().getId(), is("3"));
        gridLeft.moveUp();
        assertThat(gridLeft.getCurrentOption().getId(), is("1"));
    }

    @Test
    public void shouldHandleGoingOutOfBoundsDown() {
        gridLeft.moveDown().moveDown().moveDown().moveDown();
        assertThat(gridLeft.getCurrentOption().getId(), is("8"));
        OptionGrid res = gridLeft.moveDown();
        assertThat(gridLeft.getCurrentOption().getId(), is("8"));
    }

    @Test
    public void shouldHandleGoingOutOfBoundsUp() {
        gridLeft.moveUp();
        assertThat(gridLeft.getCurrentOption().getId(), is("1"));
    }

    @Test
    public void shouldHandleGoingOutOfBoundsLeft() {
        gridLeft.moveLeft();
        assertThat(gridLeft.getCurrentOption().getId(), is("1"));
    }

    @Test
    public void shouldHandleGoingOutOfBoundsRight() {
        gridLeft.moveRight();
        assertThat(gridLeft.getCurrentOption().getId(), is("2"));
        OptionGrid res = gridLeft.moveRight();
        assertThat(gridLeft.getCurrentOption().getId(), is("2"));
    }

    @Test
    public void shouldHandleUnevenGridDown() {
        gridLeft.moveRight().moveDown().moveRight();
        assertThat(gridLeft.getCurrentOption().getId(), is("5"));
        gridLeft.moveDown();
        assertThat(gridLeft.getCurrentOption().getId(), is("7"));
    }

    @Test
    public void shouldHandleUnevenGridUpSimple() {
        gridLeft.moveDown().moveRight().moveRight();
        assertThat(gridLeft.getCurrentOption().getId(), is("5"));
        gridLeft.moveUp();
        assertThat(gridLeft.getCurrentOption().getId(), is("2"));
    }

    @Test
    public void shouldHandleUnevenGridUpComplex() {
        gridLeft.moveDown().moveDown().moveDown();
        assertThat(gridLeft.getCurrentOption().getId(), is("8"));
        gridLeft.moveRight().moveRight().moveRight();
        assertThat(gridLeft.getCurrentOption().getId(), is("11"));
        gridLeft.moveUp();
        assertThat(gridLeft.getCurrentOption().getId(), is("7"));
    }

    @Test
    public void shouldGetOptionRow() {
        List<Option> row1 = gridLeft.getOptionRow(0);
        assertThat(row1.get(0).getId(), is("1"));
        assertThat(row1.get(1).getId(), is("2"));
        List<Option> row4 = gridLeft.getOptionRow(3);
        assertThat(row4.get(0).getId(), is("8"));
        assertThat(row4.get(1).getId(), is("9"));
        assertThat(row4.get(2).getId(), is("10"));
        assertThat(row4.get(3).getId(), is("11"));
    }
}
