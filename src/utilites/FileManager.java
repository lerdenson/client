package utilites;

import general.Route;

import java.io.*;
import java.util.LinkedHashSet;

/**
 * Класс для работы с файлами
 */
public class FileManager {
    private File file;
    private XmlParser parser = new XmlParser();
    String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
        try {
            this.file = new File(fileName);
        } catch (NullPointerException e) {
            System.out.println("\u001B[37m" + "\u001B[31m" + "Вам необходимо задать переменную окружения!!!" + "\u001B[31m" + "\u001B[37m");
        }
    }


    /**
     * Чтение коллекции из файла
     *
     * @return коллекция, которая была считана из файла
     */
    public LinkedHashSet<Route> readCollection() {
        if (fileName != null) {
            if (file.exists() && !file.canRead()) {
                System.out.println("\u001B[37m" + "\u001B[31m" + "Недостаточно прав для чтения данных из файла. Добавьте права на чтение и запустите программу вновь" + "\u001B[31m" + "\u001B[37m");
                System.exit(0);
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String data = "";
                String line;
                while ((line = reader.readLine()) != null) {
                    data += line + "\n";
                }
                return parser.parse(data);
            } catch (FileNotFoundException e) {
                System.err.println("Файл с таким именем не найден :(");

            } catch (IOException e) {
                System.err.println("Ошибка ввода-вывода");

            }
        }
        return new LinkedHashSet<Route>();

    }

    /**
     * Метод для записи коллекции в файл
     *
     * @param routeCollection routeCollection
     */
    public void writeCollection(LinkedHashSet<Route> routeCollection) {


        if (fileName != null) {
            if (!file.canWrite()) {
                System.out.println("\u001B[37m" + "\u001B[31m" + "Недостаточно прав для записи в файл. Добавьте права на запись " + "\u001B[31m" + "\u001B[37m");
                try (FileWriter fileWriter = new FileWriter(new File("file2.XML"))) {
                    fileWriter.write(parser.unparse(routeCollection));
                } catch (Exception e) {
                    System.out.println();
                }

            } else {
                try (FileWriter fileWriter = new FileWriter(file)) {

                    fileWriter.write(parser.unparse(routeCollection));

                } catch (Exception e) {
                    System.out.println("f");

                }
            }
        }


    }

}