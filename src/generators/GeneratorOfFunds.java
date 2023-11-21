package generators;

import fund.Phrase;
import utils.Utils;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class GeneratorOfFunds {

    private static final File FUNDS = new File("./dataBase/funds.txt");
    private static final File PENSION_FUNDS = new File("./dataBase/pensionFunds.txt");

    private static final String STATE_STATUS = "Государственный";

    public static void main(String[] args) throws IOException {

        Utils utils = new Utils();

        HashMap<Phrase, String> strings = utils.getString();
        try (FileReader fileReader = new FileReader(FUNDS);
             FileWriter fileWriter = new FileWriter(PENSION_FUNDS);

             BufferedReader bufferedReader = new BufferedReader(fileReader);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {


            List<String> listOfFunds = bufferedReader.lines().toList();

            for (String listOfFund : listOfFunds) {
                boolean isState = listOfFund.contains(STATE_STATUS);
                String generatedString = listOfFund.substring(0, listOfFund.lastIndexOf(" ")) + " " + isState;
                bufferedWriter.write(generatedString);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();

            if (PENSION_FUNDS.exists() && PENSION_FUNDS.length() > 0) {
                System.out.println(strings.get(Phrase.SUCCESS_INFO));
            }
        } catch (IOException e) {
            System.out.println(strings.get(Phrase.FILE_NOT_FOUND));
            e.printStackTrace();
        }
    }
}
