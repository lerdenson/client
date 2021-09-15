package commands;

import general.Route;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Абстрактный класс для команд, содержит методы объекта,имя и описание
 */
public abstract class AbstractCommand implements Serializable {
    private String name;
    private String fileName;
    public AbstractCommand(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    /**
     * @return имя команды
     */
    public String getName(){
        return name;
    }
    public abstract void execute(LinkedHashSet<Route> routeCollection);
    public abstract String getMessage();
    public  String getFileName() {
        return this.fileName;
    }
}
