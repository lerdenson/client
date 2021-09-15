package commands;


import general.Route;
import utilites.CollectionManager;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Класс команды "save". Сохраняет коллекцию в файл
 */
public class SaveCommand extends AbstractCommand implements Serializable {
    private String message;

    public SaveCommand(String fileName) {
        super("save", fileName);
    }

    /**
     * Выполнение команды
     *
     * @param routeCollection аргумент
     */
    public void execute(LinkedHashSet<Route> routeCollection) {
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.save(this.getFileName(), routeCollection);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
