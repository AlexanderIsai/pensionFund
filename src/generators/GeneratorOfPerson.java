package generators;

import fund.Phrase;
import people.*;
import utils.Utils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GeneratorOfPerson {

    private final static int MIN_BORDER_OF_MIN_SALARY = 300;
    private final static int MAX_BORDER_OF_MIN_SALARY = 1001;
    private final static int MIN_BORDER_OF_MAX_SALARY = 600;
    private final static int MAX_BORDER_OF_MAX_SALARY = 6001;
    private final static File NAMES = new File("./dataBase/names.txt");
    private final static File PERSONS = new File("./dataBase/persons.txt");

    public static void generatePersons(String[] args) throws IOException {
        Utils utils = new Utils();

        HashMap<Phrase, String> strings = utils.getString();

        Random random = new Random();
        try (FileReader fileReader = new FileReader(NAMES);
             FileWriter fileWriter = new FileWriter(PERSONS);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            List<String> people = bufferedReader.lines().toList();

            for (String person : people) {
                int minSalary = random.nextInt(MIN_BORDER_OF_MIN_SALARY, MAX_BORDER_OF_MIN_SALARY);
                int maxSalary = random.nextInt(MIN_BORDER_OF_MAX_SALARY, MAX_BORDER_OF_MAX_SALARY);
                String[] temp = person.split(" ");
                Gender gender = (temp[0].endsWith("а") || temp[0].endsWith("я")) ? Gender.FEMALE : Gender.MALE;
                String generatedString = person + " " + minSalary + " " + maxSalary + " " + gender;
                bufferedWriter.write(generatedString);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            if (PERSONS.exists() && PERSONS.length() > 0) {
                System.out.println(strings.get(Phrase.SUCCESS_INFO));
            }
        } catch (IOException e) {
            System.out.println(strings.get(Phrase.FILE_NOT_FOUND));
            e.printStackTrace();
        }
    }
}
