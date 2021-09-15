package utilites;

import general.Coordinates;
import general.Location;
import general.Route;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Управляет коллекцией
 */
//работа с коллекцией: создание нового id, добавление элемента, удаление и тд
public class CollectionManager implements Serializable {

    public CollectionManager() {
    }

    public String help() {
        return "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "remove_all_by_distance distance : удалить из коллекции все элементы, значение поля distance которого эквивалентно заданному\n" +
                "count_greater_than_distance distance : вывести количество элементов, значение поля distance которых больше заданного\n" +
                "filter_less_than_distance distance : вывести элементы, значение поля distance которых меньше заданного";
    }

    public void save(String fileName, LinkedHashSet<Route> routeCollection) {
        FileManager fileManager = new FileManager(fileName);
        fileManager.writeCollection(routeCollection);
    }

    /**
     * Читает коллекцию из файла
     */
    public LinkedHashSet<Route> loadCollection(String fileName) {
        FileManager fileManager = new FileManager(fileName);
        LinkedHashSet<Route> routeCollection = fileManager.readCollection();
        ArrayList<Integer> listId = new ArrayList<>();
        boolean notUnique = false;
        for (Route route : routeCollection) {
            for (Integer id : listId) {
                if (route.getId().equals(id)) {
                    route.setId(newId(routeCollection));
                    notUnique = true;
                }
            }
            listId.add(route.getId());
        }
        if (notUnique) System.out.println("id были не уникальны. Но не волнуйтесь, мы все исправили:)");
        return routeCollection;
    }


    /**
     * Чистит коллекцию
     */

    public void clear(LinkedHashSet<Route> routeCollection) {
        routeCollection.clear();
    }

    public String info(LinkedHashSet<Route> routeCollection) {
        String message = ("Тип коллекции: LinkedHashSet\n" +
                "Колличество элементов: " + routeCollection.size() + "\n" +
                "Имена элементов: \n");
        message += routeCollection.stream().map(Route::getName).collect(Collectors.joining("\n"));
        return message;
    }

    public String show(LinkedHashSet<Route> routeCollection) {
        return "\n" + routeCollection.stream().map(Route::toString).collect(Collectors.joining("\n"));
    }


    /**
     * Генерирует ID
     *
     * @return ID
     */
    private Integer newId(LinkedHashSet<Route> routeCollection) {
        return (routeCollection.size() == 0) ? 1 : routeCollection.stream()
                .mapToInt(Route::getId)
                .max()
                .getAsInt() + 1;
    }

    /**
     * Заменяет элемент по ID
     *
     * @param id ID
     */
    public void update(LinkedHashSet<Route> routeCollection, Integer id, String name, LocalDate creationDate, Coordinates coordinates, Location from, Location to, Float distance) {
        routeCollection.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .ifPresent(s -> {
                    s.setName(name);
                    s.setCreationDate(creationDate);
                    s.setCoordinates(coordinates);
                    s.setFrom(from);
                    s.setTo(to);
                    s.setDistance(distance);
                });
    }

    /**
     * Добавляет новый элемент в коллекцию
     */
    public void add(LinkedHashSet<Route> routeCollection, String name, Coordinates coordinates, LocalDate creationDate, Location from, Location to, Float distance) {
        routeCollection.add(new Route(newId(routeCollection), name, coordinates, creationDate, from, to, distance));
        sort(routeCollection);
    }

    /**
     * Удаляет элемент по его ID
     *
     * @param id id
     */
    public void remove_by_id(LinkedHashSet<Route> collection, Integer id) {
        collection.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .ifPresent(collection::remove);
    }

    /**
     * подсчитывает количество маршрутов с длинной больше заданного
     *
     * @param distance distance
     * @return колличество маршрутов
     */
    public int countGreaterThanDistance(LinkedHashSet<Route> routeCollection, Float distance) {
        return (int) routeCollection.stream()
                .filter(s -> s.getDistance().compareTo(distance) > 0)
                .count();
    }

    public String filterLessThanDistance(LinkedHashSet<Route> routeCollection, Float distance) {
        return routeCollection.stream()
                .filter(s -> s.getDistance().compareTo(distance) < 0)
                .map(Route::toString).collect(Collectors.joining("\n"));
    }

    /**
     * Удаляет все элемнеты с такой длинной пути
     *
     * @param distance distance
     */
    public void remove_all_by_distance(LinkedHashSet<Route> routeCollection, Float distance) {
        routeCollection.stream().filter(s -> s.getDistance().compareTo(distance) == 0).forEach(routeCollection::remove);
        sort(routeCollection);
    }

    /**
     * Удаляет все элемнеты больше заданного
     *
     * @param route route
     */
    public void remove_greater(LinkedHashSet<Route> routeCollection, Route route) {
        routeCollection.stream().filter(s -> s.compareTo(route) < 0).forEach(routeCollection::remove);
        sort(routeCollection);
    }

    /**
     * Удаляет все элемнеты меньше заданного
     *
     * @param route route
     */
    public void remove_lower(LinkedHashSet<Route> routeCollection, Route route) {
        routeCollection.stream().filter(s -> s.compareTo(route) > 0).forEach(routeCollection::remove);
        sort(routeCollection);
    }

    /**
     * Добавляет элемент в коллекцию, если он больше всех в коллекции
     */
    public String ifMaxRoute(LinkedHashSet<Route> routeCollection, String name, Coordinates coordinates, LocalDate creationDate, Location from, Location to, Float distance) {
        Route route = new Route(0, name, coordinates, creationDate, from, to, distance);
        Route maxRoute = routeCollection.stream().max(Route::compareTo).get();
        if (route.compareTo(maxRoute) > 0) {
            route.setId(newId(routeCollection));
            routeCollection.add(route);
            return "\u001B[37m" + "\u001B[33m" + "Элемент успешно добавлен" + "\u001B[33m" + "\u001B[37m";
        } else return "\u001B[37m" + "\u001B[33m" + "Элемент не является максимальным" + "\u001B[33m" + "\u001B[37m";
    }

    /**
     * сортирует коллекцию
     */
    public void sort(LinkedHashSet<Route> routeCollection) {
        Route[] routes = routeCollection.toArray(new Route[0]);
        RouteComparator comparator = new RouteComparator();

        Arrays.sort(routes, comparator);
        routeCollection.clear();
        routeCollection.addAll(Arrays.asList(routes));
    }
}