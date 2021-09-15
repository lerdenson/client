package commands;

import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * команда "clear". Удаляет все элементы коллекции
 */
public class ClearCommand extends AbstractCommand implements Serializable {
    private String message;
    public ClearCommand(String filename) {
        super("clear", filename);
    }


    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.clear(routeCollection);
        this.message = "Коллекция успешно отчищена";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
