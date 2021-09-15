package utilites;


import general.Coordinates;
import general.Location;
import general.Route;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для чтения xml файла.
 */

public class XmlParser implements Serializable {
    /**
     * Метод для считывания коллекции из XML
     *
     * @param data data
     * @return routeCollection
     */
    public LinkedHashSet<Route> parse(String data) {
        LinkedHashSet<Route> routes = new LinkedHashSet<>();
        if (!data.equals("")) {
            String[] lines = data.split("\n");

            ArrayList<String> routeString = correctStringRoutes(lines);


            Pattern pattern = Pattern.compile(">.+?<");
            for (String stringRoute : routeString) {
                routes.add(new Route(getId(stringRoute, pattern), getName(stringRoute, pattern), getCoordinates(stringRoute, pattern), getCreationDate(stringRoute, pattern), getLocation(stringRoute, pattern, "from"), getLocation(stringRoute, pattern, "to"), getDistance(stringRoute, pattern)));
            }

        }
        return routes;
    }


    public String unparse(LinkedHashSet<Route> routeCollection) {
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        result += "<Routes>\n";
        for (Route route : routeCollection) {
            result += "\t<Route>\n" +
                    "\t\t<id>" + route.getId() + "</id>\n" +
                    "\t\t<name>" + route.getName() + "</name>\n" +
                    "\t\t<coordinates>\n" +
                    "\t\t\t<x>" + route.getCoordinates().getX() + "</x>\n" +
                    "\t\t\t<y>" + route.getCoordinates().getY() + "</y>\n" +
                    "\t\t</coordinates>\n" +
                    "\t\t<creationDate>\n" +
                    "\t\t\t<year>" + route.getCreationDate().getYear() + "</year>\n" +
                    "\t\t\t<month>" + route.getCreationDate().getMonthValue() + "</month>\n" +
                    "\t\t\t<dayOfMonth>" + route.getCreationDate().getDayOfMonth() + "</dayOfMonth>\n" +
                    "\t\t</creationDate>\n" +
                    "\t\t<from>\n" +
                    "\t\t\t<name>" + route.getFrom().getName() + "</name>\n" +
                    "\t\t\t<x>" + route.getFrom().getX() + "</x>\n" +
                    "\t\t\t<y>" + route.getFrom().getY() + "</y>\n" +
                    "\t\t</from>\n" +
                    "\t\t<to>\n" +
                    "\t\t\t<name>" + route.getTo().getName() + "</name>\n" +
                    "\t\t\t<x>" + route.getTo().getX() + "</x>\n" +
                    "\t\t\t<y>" + route.getTo().getY() + "</y>\n" +
                    "\t\t</to>\n" +
                    "\t\t<distance>" + route.getDistance() + "</distance>\n" +
                    "\t</Route>\n";
        }
        result += "</Routes>";
        return result;
    }


    private ArrayList<String> splitStringToRoutes(String[] lines) {
        ArrayList<String> routes = new ArrayList<>();
        String routeLine = "";
        int i = 0;
        while (i < lines.length) {
            if (lines[i].contains("<Route>")) {
                while (!lines[i].contains("</Route>")) {
                    routeLine += lines[i] + "\n";
                    i++;
                }
                routeLine += lines[i];
                routes.add(routeLine);
                routeLine = "";
            }
            i++;
        }
        return routes;
    }

    private ArrayList<String> correctStringRoutes(String[] lines) {
        ArrayList<String> incorrectList = splitStringToRoutes(lines);
        ArrayList<String> correctList = new ArrayList<>();
        boolean ifCorrect;
        for (String a : incorrectList) {
            ifCorrect = a.matches("\\s*<Route>\n+" +
                    "\\s*<id>\\d+</id>\n+" +
                    "\\s*<name>.+?</name>\n+" +
                    "\\s*<coordinates>\n+" +
                    "\\s*<x>-?\\d+\\.?\\d*</x>\n+" +
                    "\\s*<y>-?\\d+</y>\n+" +
                    "\\s*</coordinates>\n+" +
                    "\\s*<creationDate>\n+" +
                    "\\s*<year>\\d+</year>\n+" +
                    "\\s*<month>\\d+</month>\n+" +
                    "\\s*<dayOfMonth>\\d+</dayOfMonth>\n+" +
                    "\\s*</creationDate>\n+" +
                    "\\s*<from>\n+" +
                    "\\s*<name>.{1,514}?</name>\n+" +
                    "\\s*<x>-?\\d+\\.?\\d*</x>\n+" +
                    "\\s*<y>-?\\d+</y>\n+" +
                    "\\s*</from>\n+" +
                    "\\s*<to>\n+" +
                    "\\s*<name>.{1,514}?</name>\n+" +
                    "\\s*<x>-?\\d+\\.?\\d*</x>\n+" +
                    "\\s*<y>-?\\d+</y>\n+" +
                    "\\s*</to>\n+" +
                    "\\s*<distance>\\d+\\.?\\d*</distance>\n+" +
                    "\\s*</Route>\n*");
            if (ifCorrect) correctList.add(a);
        }
        return correctList;
    }

    private Integer getId(String lineWithRoute, Pattern pattern) {
        String[] lines = lineWithRoute.split("\n");
        for (String a : lines) {
            if (a.matches("\\s*<id>\\d+</id>")) {
                Matcher matcher = pattern.matcher(a);
                if (matcher.find()) {
                    return Integer.parseInt(a.substring(matcher.start() + 1, matcher.end() - 1));
                }
            }
        }
        return 0;
    }

    private String getName(String lineWithRoute, Pattern pattern) {
        String[] lines = lineWithRoute.split("\n");
        for (String a : lines) {
            if (a.matches("\\s*<name>\\w+</name>")) {
                Matcher matcher = pattern.matcher(a);
                if (matcher.find()) {
                    return a.substring(matcher.start() + 1, matcher.end() - 1);
                }
            }
        }
        return "";
    }

    private Float getX(String lineWithRoute, Pattern pattern) {
        String[] lines = lineWithRoute.split("\n");
        for (String a : lines) {
            if (a.matches("\\s*<x>-?\\d+\\.?\\d*</x>")) {
                Matcher matcher = pattern.matcher(a);
                if (matcher.find()) {
                    return Float.parseFloat(a.substring(matcher.start() + 1, matcher.end() - 1));
                }
            }
        }
        return 0f;
    }

    private String getStringY(String lineWithRoute, Pattern pattern) {
        String[] lines = lineWithRoute.split("\n");
        for (String a : lines) {
            if (a.matches("\\s*<y>-?\\d+\\.?\\d*</y>")) {
                Matcher matcher = pattern.matcher(a);
                if (matcher.find()) {
                    return a.substring(matcher.start() + 1, matcher.end() - 1);
                }
            }
        }
        return "0";
    }

    private Coordinates getCoordinates(String lineWithRoute, Pattern pattern) {
        String[] lines = lineWithRoute.split("\n");
        String stringCoordinates = "";
        int i = 0;
        while (i < lines.length) {
            if (lines[i].contains("<coordinates>")) {
                while (!lines[i].contains("</coordinates>")) {
                    stringCoordinates += lines[i] + "\n";
                    i++;
                }
            }
            i++;
        }
        return new Coordinates(getX(stringCoordinates, pattern), Long.parseLong(getStringY(stringCoordinates, pattern)));
    }

    private Location getLocation(String lineWithRoute, Pattern pattern, String whichOne) {
        String[] lines = lineWithRoute.split("\n");
        String stringLocation = "";
        int i = 0;
        while (i < lines.length) {
            if (lines[i].contains("<" + whichOne + ">")) {
                while (!(lines[i].contains("</" + whichOne + ">"))) {
                    stringLocation += lines[i] + "\n";
                    i++;
                }
            }
            i++;
        }
        return new Location(getX(stringLocation, pattern), Integer.parseInt(getStringY(stringLocation, pattern)), getName(stringLocation, pattern));
    }

    private Float getDistance(String lineWithRoute, Pattern pattern) {
        String[] lines = lineWithRoute.split("\n");
        for (String a : lines) {
            if (a.matches("\\s*<distance>\\d+\\.?\\d*</distance>")) {
                Matcher matcher = pattern.matcher(a);
                if (matcher.find()) {
                    return Float.parseFloat(a.substring(matcher.start() + 1, matcher.end() - 1));
                }
            }
        }
        return 0f;
    }

    private Integer getData(String whichOne, String lineWithRoute, Pattern pattern) {
        String[] lines = lineWithRoute.split("\n");
        for (String a : lines) {
            if (a.contains("<" + whichOne + ">")) {
                Matcher matcher = pattern.matcher(a);
                if (matcher.find()) {
                    return Integer.parseInt(a.substring(matcher.start() + 1, matcher.end() - 1));
                }
            }
        }
        return 1;
    }

    private LocalDate getCreationDate(String lineWithRoute, Pattern pattern) {
        String[] lines = lineWithRoute.split("\n");
        String stringCreationDate = "";
        int i = 0;
        while (i < lines.length) {
            if (lines[i].contains("<creationDate>")) {
                while (!lines[i].contains("</creationDate>")) {
                    stringCreationDate += lines[i] + "\n";
                    i++;
                }
            }
            i++;
        }
        return LocalDate.of(getData("year", stringCreationDate, pattern), getData("month", stringCreationDate, pattern), getData("dayOfMonth", stringCreationDate, pattern));
    }
}
