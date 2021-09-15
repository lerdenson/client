package commands;

import general.Coordinates;
import general.Location;
import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;

/**
 * Команда "add". Добавляет новый элемент в коллекцию
 */
public class AddCommand extends AbstractCommand implements Serializable {
    private final String name;
    private final Coordinates coordinates;
    private final Location from;
    private final Location to;
    private final LocalDate creationDate;
    private final Float distance;
    private String message;


    public AddCommand(String fileName, String name, Coordinates coordinates, LocalDate creationDate, Location from, Location to, Float distance) {
        super("add", fileName);
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.creationDate = creationDate;
        this.distance = distance;
    }

    /**
     * Выполнение команды
     *
     * @return состояние выполнения команды
     */
    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.add(routeCollection, name, coordinates, creationDate, from, to, distance);
        message = "\u001B[37m" + "\u001B[33m" + "Элемент успешно добавлен" + "\u001B[33m" + "\u001B[37m";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
