package commands;

import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;


/**
 * Команда "remove_greater id". Удаляет эл-ты больше элемента с данным id
 */
public class RemoveGreaterCommand extends AbstractCommand implements Serializable {
    private Route route;
    private String message;

    public RemoveGreaterCommand(String fileName, Route route) {
        super("remove_greater", fileName);
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
        collectionManager.remove_greater(routeCollection, route);
        message = "Комманда успешно завершена";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
