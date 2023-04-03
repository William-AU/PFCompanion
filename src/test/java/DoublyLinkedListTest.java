import application.model.datastructures.CircularDoublyLinkedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DoublyLinkedListTest {
    private CircularDoublyLinkedList<Integer> simpleList;

    @BeforeEach
    private void init() {
        /*
        Represents the following list
        1 <-> 2 <-> 3 <->
         */
        List<Integer> baseList = new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }};
        this.simpleList = new CircularDoublyLinkedList<>(baseList);
    }

    @Test
    public void shouldNotBeEmpty() {
        List<Integer> emptyList = new ArrayList<>();
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () ->
                new CircularDoublyLinkedList<Integer>(emptyList));
        assertThat(thrown.getMessage(), is("Cannot instantiate empty CircularDoublyLinkedList"));
    }

    @Test
    public void shouldAllowSingleOption() {
        List<Integer> singletonList = new ArrayList<>() {{
            add(1);
        }};
        CircularDoublyLinkedList<Integer> list = new CircularDoublyLinkedList<>(singletonList);
        assertThat(list.getCurrentElement(), is(1));
        assertThat(list.traverseForwards(), is(1));
        assertThat(list.traverseBackwards(), is(1));
    }

    @Test
    public void shouldAllowForwardTraversal() {
        assertThat(simpleList.getCurrentElement(), is(1));
        int forward = simpleList.traverseForwards();
        // Make sure we get the correct forward traversal
        assertThat(forward, is(2));
        // Check that the list opdated correctly
        assertThat(simpleList.getCurrentElement(), is(2));
    }

    @Test
    public void shouldAllowBackwardsTraversal() {
        assertThat(simpleList.getCurrentElement(), is(1));
        int backward = simpleList.traverseBackwards();
        // Make sure we get the correct backward traversal
        assertThat(backward, is(3));
        // Check that the list opdated correctly
        assertThat(simpleList.getCurrentElement(), is(3));
    }

    @Test
    public void shouldBeCircularForwards() {
        assertThat(simpleList.getCurrentElement(), is(1));
        assertThat(simpleList.traverseForwards(), is(2));
        assertThat(simpleList.traverseForwards(), is(3));
        assertThat(simpleList.traverseForwards(), is(1));
    }

    @Test
    public void shouldBeCircularBackwards() {
        assertThat(simpleList.getCurrentElement(), is(1));
        assertThat(simpleList.traverseBackwards(), is(3));
        assertThat(simpleList.traverseBackwards(), is(2));
        assertThat(simpleList.traverseBackwards(), is(1));
    }
}
