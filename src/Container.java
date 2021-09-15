import java.io.Serializable;
import java.net.DatagramPacket;

public class Container implements Serializable {
    private final Object object;
    private final DatagramPacket packet;

    public Container(Object object, DatagramPacket packet) {
        this.object = object;
        this.packet = packet;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public Object getObject() {
        return object;
    }

}
