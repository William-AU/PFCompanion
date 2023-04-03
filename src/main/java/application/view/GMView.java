package application.view;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.storage.services.ServiceContext;

public class GMView implements View {
    private final Controller controller;
    private final ServiceContext serviceContext;

    public GMView(Controller controller, ServiceContext serviceContext) {
        this.controller = controller;
        this.serviceContext = serviceContext;
    }

    @Override
    public void draw() {

    }

    @Override
    public View confirm() {
        return null;
    }

    @Override
    public boolean inputKey(ListenerKey key) {
        return false;
    }
}
