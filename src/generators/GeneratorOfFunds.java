package generators;

import utils.Utils;

import java.io.*;
import java.util.List;

public class GeneratorOfFunds {

    private static final File FUNDS = new File("./dataBase/funds.txt");
    private static final File PENSION_FUNDS = new File("./dataBase/pensionFunds.txt");

    public static void main(String[] args) throws IOException {

        Utils utils = new Utils();

        List<String> texts = utils.getTexts();

        try (FileReader fileReader = new FileReader(FUNDS);
             FileWriter fileWriter = new FileWriter(PENSION_FUNDS);

             BufferedReader bufferedReader = new BufferedReader(fileReader);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {


            List<String> listOfFunds = bufferedReader.lines().toList();

            for (String listOfFund : listOfFunds) {
                boolean isState = listOfFund.contains("Государственный");
                String generatedString = listOfFund.substring(0, listOfFund.lastIndexOf(" ")) + " " + isState;
                bufferedWriter.write(generatedString);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();

            if (PENSION_FUNDS.exists() && PENSION_FUNDS.length() > 0) {
                System.out.println(texts.get(11));
            }
        } catch (IOException e) {
            System.out.println(texts.get(12));
            e.printStackTrace();
        }
    }
}
