package service;

import fund.PensionFund;
import fund.Phrase;
import generators.GeneratorOfPensionFunds;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Start {
    private final SupportService SUPPORT_SERVICE = new SupportService();
    private final HashMap<Phrase, String> STRINGS = SUPPORT_SERVICE.getString();
    private final List<PensionFund> pensionFunds = GeneratorOfPensionFunds.generatePensionFunds();

    public Start() throws IOException {
    }

    public void startApp() throws IOException {
        System.out.println(STRINGS.get(Phrase.STAT_PAGE_OPTION));
        Statistic statistic = new Statistic();
        statistic.startStat(pensionFunds);
    }


}
