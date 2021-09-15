package commands;

import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Команда "remove_key". Удаляет эл-т по ключу
 */
public class RemoveByIdCommand extends AbstractCommand implements Serializable {
    private Integer id;
    private String message;

    public RemoveByIdCommand(String fileName, Integer id) {
        super("remove_by_id", fileName);
        this.id = id;

    }


    @Override
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.remove_by_id(routeCollection, id);
        message = "Комманда успешно завершена";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
