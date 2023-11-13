package generators;
import fund.PensionFund;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class GeneratorOfPensionFunds {
    private static final File funds = new File("./dataBase/pensionFunds.txt");
    private GeneratorOfPensionFunds() {
    }
    public static List<PensionFund> generatePensionFunds() throws FileNotFoundException {
        FileReader fileReader = new FileReader(funds);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<PensionFund> pensionFunds = bufferedReader.lines()
                .filter(Objects::nonNull)
                .map(fund -> {
                    try {
                        return new PensionFund(fund);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return pensionFunds;
    }
}
