package commands;


import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Command 'help'.
 */
public class HelpCommand extends AbstractCommand implements Serializable {
    private String message;
    public HelpCommand(String fileName) {
        super("help", fileName);
    }

    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        message = collectionManager.help();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

