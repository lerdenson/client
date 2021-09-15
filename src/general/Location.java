package general;

import java.io.Serializable;

public class Location implements Serializable {
    private float x;
    private Integer y; //Поле не может быть null
    private String name; //Длина строки не должна быть больше 515, Поле не может быть null

    public Location(float x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }


    public float getX() {
        return x;
    }


    public Integer getY() {
        return y;
    }


    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Название: " + name + ", x=" + x + "; y=" + y;
    }
}
