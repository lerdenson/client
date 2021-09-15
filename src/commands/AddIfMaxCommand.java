package commands;

import general.Coordinates;
import general.Location;
import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;

/**
 * Команда "add_if_max". Добавляет новый элемент в коллекцию
 */
public class AddIfMaxCommand extends AbstractCommand implements Serializable {
    private final String name;
    private final Coordinates coordinates;
    private final Location from;
    private final Location to;
    private final LocalDate creationDate;
    private final Float distance;
    private String message;

    public AddIfMaxCommand(String fileName, String name, Coordinates coordinates, LocalDate creationDate, Location from, Location to, Float distance) {
        super("add_if_max", fileName);
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.creationDate = creationDate;
        this.distance = distance;
    }

    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        message = collectionManager.ifMaxRoute(routeCollection, name, coordinates, creationDate, from, to, distance);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

