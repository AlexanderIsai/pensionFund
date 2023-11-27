package service;

import fund.PensionFund;
import fund.Phrase;
import generators.GeneratorOfPensionFunds;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class StartService {
    private final SupportService SUPPORT_SERVICE = new SupportService();
    private final HashMap<Phrase, String> STRINGS = SUPPORT_SERVICE.getString();
    private final List<PensionFund> pensionFunds = GeneratorOfPensionFunds.generatePensionFunds();

    public StartService() throws IOException {
    }

    public void startApp() throws IOException {
        System.out.println(STRINGS.get(Phrase.STAT_PAGE_OPTION));
        StatisticService statisticService = new StatisticService();
        statisticService.startStat(pensionFunds);
    }


}
