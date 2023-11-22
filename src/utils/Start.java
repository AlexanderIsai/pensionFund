package utils;

import fund.PensionFund;
import fund.Phrase;
import generators.GeneratorOfPensionFunds;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Start {
    private final Utils UTILS = new Utils();
    private final HashMap<Phrase, String> STRINGS = UTILS.getString();
    private final List<PensionFund> pensionFunds = GeneratorOfPensionFunds.generatePensionFunds();

    public Start() throws IOException {
    }

    public void startApp() throws IOException {
        System.out.println(STRINGS.get(Phrase.STAT_PAGE_OPTION));
        Stat stat = new Stat();
        stat.startStat(pensionFunds);
    }


}
