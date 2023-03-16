package application.controller;

import application.utils.ConsoleUtils;
import application.view.TitleView;
import application.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

@Component
public class Controller {
    private View currentView;
    private View defaultView;

    /**
     * Tells the controller to clear the console window and draw the current selected view
     */
    public void reDraw() {
        ConsoleUtils.clearConsole();
        currentView.draw();
    }

    /**
     * Selects a view to display and then calls reDraw()
     * @param view The view to draw
     */
    public void setViewAndDraw(View view) {
        currentView = view;
        currentView.draw();
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
