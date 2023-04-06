package application.controller;

import application.listeners.ListenerKey;
import application.services.ConsoleService;
import application.view.View;
import org.springframework.beans.factory.annotation.Autowired;

public class Controller {
    private View currentView;
    private View defaultView;
    private ConsoleService consoleService;

    public Controller(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    public ConsoleService getConsoleService() {
        return consoleService;
    }

    /**
     * Tells the controller to clear the console window and draw the current selected view
     */
    public void reDraw() {
        consoleService.clearConsole();
        if (currentView.shouldDrawTitle()) {
            consoleService.drawTitle();
        }
        currentView.draw();
    }

    /**
     * Selects a view to display and then calls reDraw()
     * @param view The view to draw
     */
    public void setViewAndDraw(View view) {
        currentView = view;
        reDraw();
    }

    /**
     * Called by {@link application.listeners.KeyboardListener} when user presses a keyboard button, tells the view to handle the given key and then redraws the current view, unless the key pressed is {@link ListenerKey#ENTER} in which case it asks the view to confirm. If the confirm actions returns a valid {@link View} this will become the new main scene, otherwise it will redraw as usual.
     * @param key The key pressed by the user
     */
    public void handleKeyEvent(ListenerKey key) {
        if (key == ListenerKey.ENTER) {
            View newView = currentView.confirm();
            if (newView != null) {
                setViewAndDraw(newView);
                return;
            }
        }
        boolean nothingChanged = !currentView.inputKey(key);
        if (nothingChanged) return;
        reDraw();
    }

    public void handleCharEvent(char event) {
        if (!currentView.shouldAcceptLetters()) return;
        boolean nothingChanged = !currentView.inputChar(event);
        if (nothingChanged) return;
        reDraw();
    }

    /**
     * Initial view to display on application startup as specified in SpringConfig.
     * The view doubles as the default view, the "root" of all possible sub views so to speak
     * @param view The view to display, autowired from bean in SpringConfig
     */
    @Autowired
    public void setInitialView(View view) {
        System.out.println("Autowiring view");
        this.currentView = view;
        this.defaultView = view;
    }
}
