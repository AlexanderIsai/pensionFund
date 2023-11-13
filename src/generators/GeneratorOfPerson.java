package generators;

import people.*;

import java.io.*;
import java.util.List;
import java.util.Random;

public class GeneratorOfPerson {

    private static int minBorderOfMinSalary = 300;
    private static int maxBorderOfMinSalary = 1001;
    private static int minBorderOfMaxSalary = 600;
    private static int maxBorderOfMaxSalary = 6001;
    private static File names = new File("./dataBase/names.txt");
    private static File persons = new File("./dataBase/persons.txt");


    public static void generatePersons(String[] args) throws IOException {

        Random random = new Random();

        FileReader fileReader = new FileReader(names);
        FileWriter fileWriter = new FileWriter(persons);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        List<String> people = bufferedReader.lines().toList();

        for (String person : people) {
            int minSalary = random.nextInt(minBorderOfMinSalary, maxBorderOfMinSalary);
            int maxSalary = random.nextInt(minBorderOfMaxSalary, maxBorderOfMaxSalary);
            String[] temp = person.split(" ");
            Gender gender = (temp[0].endsWith("а") || temp[0].endsWith("я")) ? Gender.FEMALE : Gender.MALE;
            String generatedString = person + " " + minSalary + " " + maxSalary + " " + gender;
            bufferedWriter.write(generatedString);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
    }
}
