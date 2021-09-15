package utilites;

import general.Route;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Класс для сравнения route. Сравнивает разницы между прямым расстоянием и длинной пути
 * Чем больше разница, тем меньше route
 */
public class RouteComparator implements Comparator<Route>, Serializable {
    @Override
    public int compare(Route o1, Route o2) {
        return Double.compare((o1.getCoordinates().getX() * o1.getCoordinates().getX() + o1.getCoordinates().getY()*o1.getCoordinates().getY()), o2.getCoordinates().getX() * o2.getCoordinates().getX() + o2.getCoordinates().getY()*o2.getCoordinates().getY());
    }
}