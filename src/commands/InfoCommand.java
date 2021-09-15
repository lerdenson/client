package commands;


import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Команда "info". Выводит информацию о коллекции
 */
public class InfoCommand extends AbstractCommand implements Serializable {
    private String message;

    public InfoCommand(String fileName) {
        super("info", fileName);
    }


    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        message = collectionManager.info(routeCollection);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
