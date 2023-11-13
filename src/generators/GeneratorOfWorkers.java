package generators;
import people.Worker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class GeneratorOfWorkers {

    private static int minAgeOfWorker = 20;
    private static int maxAgeOfWorker = 65;
    private static final File persons = new File("./dataBase/persons.txt");

    private GeneratorOfWorkers() {
    }

    public static int getMinAgeOfWorker() {
        return minAgeOfWorker;
    }

    public static void setMinAgeOfWorker(int minAgeOfWorker) {
        GeneratorOfWorkers.minAgeOfWorker = minAgeOfWorker;
    }

    public static int getMaxAgeOfWorker() {
        return maxAgeOfWorker;
    }

    public static void setMaxAgeOfWorker(int maxAgeOfWorker) {
        GeneratorOfWorkers.maxAgeOfWorker = maxAgeOfWorker;
    }

    public static List<Worker> generateWorkers() throws FileNotFoundException {
        FileReader fileReader = new FileReader(persons);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<Worker> workers = bufferedReader.lines()
                .filter(Objects::nonNull)
                .map(Worker::new)
                .toList();
        return workers;
    }

}
