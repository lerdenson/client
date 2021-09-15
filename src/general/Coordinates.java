package general;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private float x;
    private Long y; //Поле не может быть null


    public Coordinates(float x, Long y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }


    public Long getY() {
        return y;
    }

}
