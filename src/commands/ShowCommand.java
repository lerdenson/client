package commands;

import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Класс команды "show". Выводит коллекцию в строковом представлении
 */
public class ShowCommand extends AbstractCommand implements Serializable {
    private String message;

    public ShowCommand(String fileName) {
        super("show", fileName);
    }

    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        this.message = collectionManager.show(routeCollection);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
