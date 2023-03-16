package application.controller;

import application.view.TitleView;
import application.view.View;

public class Controller {
    private View currentView;

    public Controller() {
        this.currentView = new TitleView();
    }

    public void reDraw() {
        currentView.draw();
    }

    public void setViewAndDraw(View view) {
        currentView = view;
        currentView.draw();
    }
}
