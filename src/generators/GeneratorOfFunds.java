package generators;

import java.io.*;
import java.util.List;
import java.util.Random;

public class GeneratorOfFunds {

    public static void main(String[] args) throws IOException {

        Random random = new Random();

        File funds = new File("..//pensionFund/dataBase/funds.txt");
        File pensionFunds = new File("..//pensionFund/dataBase/pensionFunds.txt");

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
