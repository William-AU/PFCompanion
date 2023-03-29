package application.view;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.services.ColorService;
import application.view.options.Option;
import application.view.options.OptionGrid;
import application.view.options.Position;
import org.springframework.boot.info.BuildProperties;

import java.util.HashMap;
import java.util.Map;

public class MainMenuView implements View {
    // Option 0 is top option
    private int highlightedOption = 0;
    private int totalOptions = 2;
    private Controller controller;
    private final ColorService colorService;

    private final OptionGrid optionGrid;

    public MainMenuView(Controller controller, ColorService colorService) {
        this.controller = controller;
        this.colorService = colorService;

        Map<Position, Option> optionMap = new HashMap<>() {{
            put(new Position(0, 0), new Option("Create Character", "0"));
            put(new Position(0, 1), new Option("Load Character", "1"));
        }};
        this.optionGrid = new OptionGrid(optionMap);
    }

    @Override
    public void draw() {
        drawOptions();
    }

    /**
     * Indicated that the user confirmed an option, usually by pressing enter
     */
    @Override
    public View confirm() {
        return switch (highlightedOption) {
            case 0 -> new CreateCharacterView(controller, colorService);
            case 1 -> new LoadCharacterView(controller, colorService);
            default -> null;
        };
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return switch (key) {
            case UP -> moveUp();
            case DOWN -> moveDown();
            default -> false;
        };
    }

    /**
     * Moves the current highlighted option one up
     * @return True if it is possible to move up, false otherwise
     */
    private boolean moveUp() {
        if (highlightedOption == 0) return false;
        highlightedOption--;
        return true;
    }

    /**
     * Moves the current highlighted option one down
     * @return True if it is possible to move down, false otherwise
     */
    private boolean moveDown() {
        if (highlightedOption == totalOptions - 1) return false;
        highlightedOption++;
        return true;
    }


    private void drawOptions() {
        String option1 = "Create new character";
        String option2 = "Load character";

        colorService.printLn(option1, highlightedOption == 0);
        colorService.printLn(option2, highlightedOption == 1);
    }
}
