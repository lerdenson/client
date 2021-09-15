package commands;

import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Класс для команды "count_greater_than_distance"
 */
public class CountGreaterThanDistanceCommand extends AbstractCommand implements Serializable {
    private final Float distance;
    private String message;

    public CountGreaterThanDistanceCommand(String fileName, Float distance) {
        super("count_greater_than_distance", fileName);
        this.distance = distance;
    }

    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        message = "Коллекция содержит элементов с расстоянием больше заданного: " + collectionManager.countGreaterThanDistance(routeCollection, distance);

    }

    @Override
    public String getMessage() {
        return message;
    }
}