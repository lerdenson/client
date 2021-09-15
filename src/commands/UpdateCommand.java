package commands;

import general.Coordinates;
import general.Location;
import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;

/**
 * Класс команды "update". Заменяет элемент по id
 */
public class UpdateCommand extends AbstractCommand implements Serializable {
    private String message;
    Integer id;
    String name;
    Coordinates coordinates;
    LocalDate creationDate;
    Location from;
    Location to;
    Float distance;

    public UpdateCommand(String fileName, Integer id, String name, Coordinates coordinates, LocalDate creationDate, Location from, Location to, Float distance) {
        super("update id", fileName);
        this.id = id;
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
     * @param routeCollection аргумент
     */
    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.update(routeCollection, id, name, creationDate, coordinates, from, to, distance);
        message = "Команда успешно завершена";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
