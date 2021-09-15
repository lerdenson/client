package commands;

import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Команда "remove_all_by_distance". Удаляет эл-ты с определенным кол-ом комнат
 */
public class RemoveAllByDistanceCommand extends AbstractCommand implements Serializable {
    Float distance;
    private String message;

    public RemoveAllByDistanceCommand(String fileName, Float distance) {

        super("remove_all_by_distance", fileName);
        this.distance = distance;
    }


    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.remove_all_by_distance(routeCollection, distance);
        message = "все элементы с заданным расстоянием успешно удалены";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
