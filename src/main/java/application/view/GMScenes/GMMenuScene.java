package application.view.GMScenes;

import application.controller.Controller;
import application.listeners.ListenerKey;
import application.model.GM.Campaign;
import application.services.sceneServices.SceneServiceContext;
import application.view.FastDrawScene;
import application.view.Scene;
import application.view.builders.LayoutBuilder;
import application.view.builders.OptionGridBuilder;
import application.view.options.OptionGrid;
import application.view.options.SimpleOption;
import application.view.strategies.OptionMovementStrategy;
import application.view.strategies.SimpleOptionMovementStrategy;

public class GMMenuScene implements FastDrawScene {
   private Controller controller;
   private String campaignName;
   private OptionGrid optionGrid;
   private SceneServiceContext serviceContext;
   private OptionMovementStrategy optionMovementStrategy;

   private void initAfterController(Campaign initialCampaign) {
       campaignName = initialCampaign.getName();
       this.optionGrid = createOptionGrid();
       this.optionMovementStrategy = new SimpleOptionMovementStrategy();
   }

   private OptionGrid createOptionGrid() {
       OptionGridBuilder builder = new OptionGridBuilder();
       SimpleOption players = new SimpleOption("Players", "0");
       SimpleOption monsters = new SimpleOption("Monsters", "1");
       SimpleOption combat = new SimpleOption("Combat", "2");
       SimpleOption notes = new SimpleOption("Notes", "3");
       builder.addOptionsToNewRow(players, monsters)
               .addOptionsToNewRow(combat, notes);
       return builder.build();
   }


    @Override
    public void setController(Controller controller) {
        this.controller = controller;
        boolean noActiveCampaign = !controller.hasActiveCampaign();
        if (noActiveCampaign) {
            controller.createNewCampaign();
        }
        initAfterController(controller.getCurrentCampaign());
    }

    /**
     * Indicates that the user confirmed an option, usually by pressing enter
     *
     * @return Returns a new {@link Scene} if the action requires a scene change, otherwise returns null
     */
    @Override
    public Scene confirm() {
        switch (optionGrid.getCurrentOption().getId()) {
            case "0" -> {
                return new GMPlayersScene();
            }
            case "1" -> {
                return new GMMonstersScene();
            }
            case "2" -> {
                return new GMCombatScene();
            }
            case "3" -> {
                return new GMNotesScene();
            }
        }
        return null;
    }

    /**
     * Updates the view with the given key input, return true if anything changed
     *
     * @param key Key pressed
     * @return True if the key press changed anything about the view state, false otherwise
     */
    @Override
    public boolean inputKey(ListenerKey key) {
        return optionMovementStrategy.handleMove(optionGrid, key);
    }

    @Override
    public void setServiceContext(SceneServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }

    /**
     * Only called if this {@link Scene} returns true on {@link Scene#useFastDraw()}. Instead of using print statements, instead returns a formatted {@link String} to be printed.
     * {@link Scene#fastDraw()} is called immediately after the console is cleared by the controller
     *
     * @return The string to be drawn
     */
    @Override
    public String fastDraw() {
        LayoutBuilder builder = new LayoutBuilder(serviceContext);
        builder.setCenter(true)
                .setDistanceBetweenOptions(10)
                .addLine("Configuration options")
                .addOptionRow(optionGrid.getOptionRow(0))
                .setDistanceBetweenRows(2)
                .addLine("Misc. options")
                .setDistanceBetweenRows(0)
                .addOptionRow(optionGrid.getOptionRow(1));
        return builder.build();
    }
}
