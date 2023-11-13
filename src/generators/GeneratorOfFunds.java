package generators;
import java.io.*;
import java.util.List;

public class GeneratorOfFunds {

    private static final File funds = new File("./dataBase/funds.txt");
    private static final File pensionFunds = new File("./dataBase/pensionFunds.txt");

    public static void main(String[] args) throws IOException {

        FileReader fileReader = new FileReader(funds);
        FileWriter fileWriter = new FileWriter(pensionFunds);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        List<String> listOfFunds = bufferedReader.lines().toList();

        for (String listOfFund : listOfFunds) {
            boolean isState = listOfFund.contains("Государственный");
            String generatedString = listOfFund.substring(0, listOfFund.lastIndexOf(" ")) + " " + isState;
            bufferedWriter.write(generatedString);
            bufferedWriter.newLine();
        }
            bufferedWriter.flush();
    }
}
