package generators;

import people.Worker;
import utils.Utils;

import java.io.*;
import java.util.*;

public class GeneratorOfWorkers {

    private final static int MIN_AGE_OF_WORKER = 20;
    private final static int MAX_AGE_OF_WORKER = 65;
    private static final File PERSONS = new File("./dataBase/persons.txt");

    private GeneratorOfWorkers() {
    }

    public static int getMinAgeOfWorker() {
        return MIN_AGE_OF_WORKER;
    }

    public static int getMaxAgeOfWorker() {
        return MAX_AGE_OF_WORKER;
    }

    public static List<Worker> generateWorkers() throws IOException {
        Utils utils = new Utils();

        List<String> texts = utils.getTexts();
        List<Worker> workers;
        try (FileReader fileReader = new FileReader(PERSONS);
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {
            workers = bufferedReader.lines()
                    .filter(Objects::nonNull)
                    .map(Worker::new)
                    .toList();
        } catch (IOException e) {
            System.out.println(texts.get(12));
            throw new RuntimeException(e);
        }
        return workers;
    }

}
