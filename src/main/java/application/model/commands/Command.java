package application.model.commands;

import java.util.List;

public interface Command {
    /**
     * Get the description of the command, used for the command help command
     * @return String description of the command
     */
    String getDescription();

    /**
     * Get the aliases that can be used to call the command
     * @return A list of aliases unique to this command
     */
    List<String> getAliases();
}
