package general;

import java.io.Serializable;
import java.time.LocalDate;

public class Route implements Comparable<Route>, Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле не может быть null
    private Location to; //Поле может быть null
    private Float distance; //Поле не может быть null, Значение поля должно быть больше 1


    public Route(Integer id, String name, Coordinates coordinates, LocalDate creationDate, Location from, Location to, Float distance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Route(String name, Coordinates coordinates, LocalDate creationDate, Location from, Location to, Float distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    /**
     * Перевод маршрута в вид строки
     *
     * @return string
     */
    @Override
    public String toString() {
        return "\nId: " + id.toString() + "\nНазвание: " + name + "\nКоординаты: x=" + coordinates.getX() +
                "; y=" + coordinates.getY() + "\nВремя создания: " + creationDate + "\nОткуда " + from.toString()
                + "\nКуда " + to.toString() + "\n" + "Расстояние: " + distance + "\n";
    }

    @Override
    public int compareTo(Route route) {
        return this.distance.compareTo(route.getDistance());
    }
}
