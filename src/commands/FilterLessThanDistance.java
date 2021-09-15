package commands;

import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Команда, выводящая элементы, значение поля distance которых меньше заданного
 */
public class FilterLessThanDistance extends AbstractCommand implements Serializable {
    private String message;
    Float distance;

    public FilterLessThanDistance(String fileName, Float distance) {
        super("filter_less_than_distance", fileName);
        this.distance = distance;
    }

    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        message = collectionManager.filterLessThanDistance(routeCollection, distance);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
