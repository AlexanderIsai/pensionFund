package generators;
import fund.PensionFund;
import fund.Phrase;
import service.SupportService;
import java.io.*;
import java.util.*;

public class GeneratorOfPensionFunds {
    private static final File FUNDS = new File("./dataBase/pensionFunds.txt");

    private GeneratorOfPensionFunds() {
    }

    public static List<PensionFund> generatePensionFunds() throws IOException {
        SupportService supportService = new SupportService();
        HashMap<Phrase, String> strings = supportService.getString();
        FileReader fileReader = new FileReader(FUNDS);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<PensionFund> pensionFunds = bufferedReader.lines()
                .filter(Objects::nonNull)
                .map(fund -> {
                    try {
                        return new PensionFund(fund);
                    } catch (IOException e) {
                        System.out.println(strings.get(Phrase.SUCCESS_INFO));
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return pensionFunds;
    }
}
