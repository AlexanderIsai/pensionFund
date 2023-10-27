package generators;
import people.*;
import java.io.*;
import java.util.List;
import java.util.Random;

public class GeneratorOfPerson {

    public static void main(String[] args) throws IOException {

        final int QUANTITY_OF_PERSONS = 1000;

        Random random = new Random();
        File names = new File("../pensionFund/dataBase/names.txt");
        File persons = new File("../pensionFund/dataBase/persons.txt");

        FileReader fileReader = new FileReader(names);
        FileWriter fileWriter = new FileWriter(persons);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        List<String> people = bufferedReader.lines().toList();

        for (String person : people) {
            int minSalary = random.nextInt(300, 1001);
            int maxSalary = random.nextInt(600, 6001);
            String[] temp = person.split(" ");
            Gender gender = (temp[0].endsWith("а") || temp[0].endsWith("я")) ? Gender.FEMALE : Gender.MALE;
            String generatedString = person + " " + minSalary + " " + maxSalary + " " + gender;
            bufferedWriter.write(generatedString);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
    }
}
