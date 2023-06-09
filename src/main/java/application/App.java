package application;

import application.controller.Controller;
import application.listeners.KeyboardListener;
import application.listeners.keyboardLayouts.KeyboardLayout;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableJpaRepositories
@SpringBootApplication
public class App implements CommandLineRunner {
    private Controller controller;
    private KeyboardLayout keyboardLayout;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new KeyboardListener(controller, keyboardLayout));
        controller.reDraw();
    }

    @Autowired
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Autowired
    public void setKeyboardLayout(KeyboardLayout layout) {
        this.keyboardLayout = layout;
    }
}
