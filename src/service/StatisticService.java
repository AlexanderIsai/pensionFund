package service;
import fund.PensionFund;
import fund.Phrase;
import fund.Request;
import people.Worker;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class StatisticService {
    private final CalculationService CALCULATION_SERVICE = new CalculationService();
    private final SupportService SUPPORT_SERVICE = new SupportService();
    private final HashMap<Phrase, String> STRINGS = SUPPORT_SERVICE.getString();
    private final HashMap<Integer, Request> REQUESTS = SUPPORT_SERVICE.createRequestsMap();
    private final String CONTINUE_OPTION = "y";

    public StatisticService() throws IOException {
    }

    public  void startStat(List<PensionFund> pensionFunds){
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        while (answer.equals(CONTINUE_OPTION)) {
            SUPPORT_SERVICE.printStringWithNewLine(STRINGS.get(Phrase.STAT_DESCRIPTION));
            int chose = scanner.nextInt();
            Request request = (chose <= REQUESTS.size() && chose > 0) ? REQUESTS.get(chose) : null;
            scanner.nextLine();
            try {
                switch (Objects.requireNonNull(request)) {
                    case MOST_POPULAR_FUND ->
                        /**
                        * Найдите фонд, где больше всего вложенцев
                        */
                            System.out.println(CALCULATION_SERVICE.findMaxPopularPensionFund(pensionFunds));
                    case WORKER_WITH_BIGGEST_PENSION -> {
                        /**
                         * Найдите имя человека, у которого самая большая пенсия (БЕРЕМ РАСЧЕТНУЮ ПЕНСИЮ без ВНИМАНИЯ К ФОНДУ)
                         */
                        Worker biggerAll = CALCULATION_SERVICE.findWorkerWithBiggestPensionAll(pensionFunds);
                        System.out.println(biggerAll.getName() + STRINGS.get(Phrase.WORKERS_PENSION) + biggerAll.calculatePension());
                        /**
                         * альтернативный вариант (ДЛЯ ТЕХ, ЧЬИ ДЕНЬГИ не УКРАЛИ)
                         * итерация)
                         */
                        Worker biggerState = CALCULATION_SERVICE.findWorkerWithBiggestPensionState(pensionFunds);
                        System.out.println(biggerState + STRINGS.get(Phrase.STATE_PENSION) + biggerState.calculatePension());
                    }
                    case LOSERS ->
                        /**
                        * Найдите людей, которые стали жертвами "не государственных фондов"
                        */

                            System.out.println(CALCULATION_SERVICE.findLosers(pensionFunds).size());
                    case AVERAGE_PENSION_OF_FUNDS -> {
                        /**
                         * Найдите среднюю пенсию по фондам
                         */
                        System.out.println(STRINGS.get(Phrase.MEDIAN_PENSION_OF_FUNDS));
                        pensionFunds.forEach(pensionFund -> {
                            System.out.println(pensionFund.getName() + " = " + pensionFund.calculateMedianPension(pensionFund.getMembers()));
                        });
                        /**
                         * альтернативный способ со стримами
                         */

                        CALCULATION_SERVICE.findMedianPensionForEach(pensionFunds);
                    }
                    //      System.out.println("======ЭТОТ КОД ВСЕ ТОРМОЗИТ И ДОЛГО ВЫПОЛНЯЕТСЯ======");
                    //        pensionFunds.forEach(pensionFund -> {
                    //            System.out.println("Средняя пенсия для фонда " + pensionFund.getName() + ": " + utils.findMedianPension(pensionFund));
                    //        });
                    case AVERAGE_PENSION_OF_WORKERS -> {
                        /**
                         * Найдите среднюю пенсию по людям
                         */
                        System.out.println(STRINGS.get(Phrase.AVERAGE_PENSION_WORKER));
                        List<Double> pensions = CALCULATION_SERVICE.getAllPensions(pensionFunds);
                        System.out.printf("%.1f%n", pensions.stream().reduce(Double::sum).get() / pensions.size());
                    }
                    case BIGGEST_PENSION_DEPENDS_ON_AGE -> {
                        /**
                         * Найдите наибольшую пенсию среди людей до 25
                         * находим наибольшую пенсию...буквально только пенсию
                         */
                        System.out.println(CALCULATION_SERVICE.getBiggestPensionWithAge(pensionFunds));
                        /**
                         * находим человека с такой пенсией
                         */
                        System.out.println(CALCULATION_SERVICE.findWorkerWithBiggestPensionWithAge(pensionFunds));
                    }
                    case YOUNGEST_WORKER_OF_STATE_FUNDS ->
                        /**
                        * Найдите самого молодого человека среди вложенцев гос. фондов
                        */
                            System.out.println(CALCULATION_SERVICE.findYoungestMembersOfFunds(pensionFunds));
                    default -> System.out.println(STRINGS.get(Phrase.TRY_AGAIN));
                }
                System.out.println(STRINGS.get(Phrase.CONTINUE_OPTION));
                answer = scanner.nextLine();
            } catch (NullPointerException e) {
                System.out.println(STRINGS.get(Phrase.ATTENTION));
                throw new NullPointerException();
            }
        }
        System.out.println(STRINGS.get(Phrase.BYE));
    }
}
