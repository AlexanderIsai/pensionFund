package generators;

import fund.PensionFund;
import utils.Utils;

import java.io.*;
import java.util.*;

public class GeneratorOfPensionFunds {
    private static final File FUNDS = new File("./dataBase/pensionFunds.txt");

    private GeneratorOfPensionFunds() {
    }

    public static List<PensionFund> generatePensionFunds() throws IOException {
        Utils utils = new Utils();
        List<String> texts = utils.getTexts();
        FileReader fileReader = new FileReader(FUNDS);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<PensionFund> pensionFunds = bufferedReader.lines()
                .filter(Objects::nonNull)
                .map(fund -> {
                    try {
                        return new PensionFund(fund);
                    } catch (IOException e) {
                        System.out.println(texts.get(12));
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return pensionFunds;
    }
}
