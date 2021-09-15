package commands;

import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;


/**
 * Команда "remove_lower id". Удаляет эл-ты меньше элемента с данным id
 */
public class RemoveLowerCommand extends AbstractCommand implements Serializable {
    private Route route;
    private String message;

    public RemoveLowerCommand(String fileName, Route route) {
        super("remove_lower", fileName);
        this.route = route;
    }

    /**
     * Выполнение команды
     *
     * @param routeCollection аргумент
     */
    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.remove_lower(routeCollection, route);
        message = "операция успешно завершена";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
