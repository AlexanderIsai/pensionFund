package generators;

import people.Gender;
import people.Worker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class GeneratorOfWorkers {

    private GeneratorOfWorkers() {
    }

    public static List<Worker> generateWorkers() throws FileNotFoundException {
        Random random = new Random();
        final int MIN_AGE_OF_WORKER = 20;
        final int MAX_AGE_OF_WORKER = 65;

        File persons = new File("..//pensionFund/dataBase/persons.txt");
        FileReader fileReader = new FileReader(persons);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<Worker> workers = bufferedReader.lines()
                .filter(Objects::nonNull)
                .map(worker -> {
                    String[] temp = worker.split(" ");
                    String name = temp[0] + " " + temp[1];
                    int age = random.nextInt(MIN_AGE_OF_WORKER,MAX_AGE_OF_WORKER);
                    double minSalary = Integer.parseInt(temp[2]);
                    double maxSalary = Integer.parseInt(temp[3]);
                    Gender gender = (temp[4].equals("MALE")) ? Gender.MALE : Gender.FEMALE;
                    return new Worker(name, age, minSalary, maxSalary, gender);
                })
                .toList();
        return workers;

    }

}
