import application.view.builders.OptionGridBuilder;
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

public class OptionGridBuilderTest {
    private OptionGrid expectedGrid, expectedGrid2;

    @BeforeEach
    private void init() {
        /* Expect the following grid:
        OPTION1
        OPTION2 OPTION3
         */
        Map<Position, Option> manualOptions = new HashMap<>() {{
           put(new Position(0, 0), new SimpleOption("OPTION1", "1"));
           put(new Position(0, 1), new SimpleOption("OPTION2", "2"));
           put(new Position(1, 1), new SimpleOption("OPTION3", "3"));
        }};
        expectedGrid = new OptionGrid(manualOptions);

        /* Expect grid 2
        OPTION1 OPTION2
         */
        Map<Position, Option> manualOptions2 = new HashMap<>() {{
            put(new Position(0, 0), new SimpleOption("OPTION1", "1"));
            put(new Position(1, 0), new SimpleOption("OPTION2", "2"));
        }};
        expectedGrid2 = new OptionGrid(manualOptions2);
    }

    @Test
    public void shouldAddOptionAtPosition() {
        OptionGridBuilder builder = new OptionGridBuilder();
        builder.addOptionAtPosition(0, 0, new SimpleOption("OPTION1", "1"))
                .addOptionAtPosition(new Position(0, 1), new SimpleOption("OPTION2", "2"))
                .addOptionAtPosition(1, 1, new SimpleOption("OPTION3", "3"));
        OptionGrid actualGrid = builder.build();
        assertThat(actualGrid, is(expectedGrid));
    }

    @Test
    public void shouldAddOptionToNewRow() {
        OptionGridBuilder builder = new OptionGridBuilder();
        builder.addOptionsToNewRow(new SimpleOption("OPTION1", "1"))
                .addOptionsToNewRow(new SimpleOption("OPTION2", "2"), new SimpleOption("OPTION3", "3"));
        OptionGrid actualGrid = builder.build();
        assertThat(actualGrid, is(expectedGrid));
    }

    @Test
    public void shouldAddOptionToCurrentRow() {
        OptionGridBuilder builder = new OptionGridBuilder();
        builder.addOptionsToCurrentRow(new SimpleOption("OPTION1", "1"))
                .addOptionsToNewRow(new SimpleOption("OPTION2", "2"))
                .addOptionsToCurrentRow(new SimpleOption("OPTION3", "3"));
        OptionGrid actualGrid = builder.build();
        assertThat(actualGrid, is(expectedGrid));
    }

    @Test
    public void shouldHandleNewIntoCurrent() {
        OptionGridBuilder builder = new OptionGridBuilder();
        builder.addOptionsToNewRow(new SimpleOption("OPTION1", "1"))
                .addOptionsToCurrentRow(new SimpleOption("OPTION2", "2"));
        OptionGrid actualGrid = builder.build();
        assertThat(actualGrid, is(expectedGrid2));
    }

}
