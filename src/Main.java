import fund.PensionFund;
import fund.Phrase;
import fund.Request;
import generators.GeneratorOfPensionFunds;
import people.Worker;
import utils.Utils;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Utils utils = new Utils();
        HashMap<Phrase, String> strings = utils.getString();
        Scanner scanner = new Scanner(System.in);
        List<PensionFund> pensionFunds = GeneratorOfPensionFunds.generatePensionFunds();
        System.out.println(strings.get(Phrase.PENSION_FUNDS));
        System.out.println(pensionFunds);
        int members = 0; // считаем количество членов пенсионных фондов
        for (PensionFund fund : pensionFunds) {
            members += fund.getMembers().size();
        }
        System.out.println(members);
        System.out.println(strings.get(Phrase.STAT_PAGE_OPTION));
        HashMap<Integer, Request> requests = utils.createRequestsMap();
        String answer = scanner.nextLine();
        while (answer.equals("y")) {
            utils.printStringWithNewLine(strings.get(Phrase.STAT_DESCRIPTION));
            int chose = scanner.nextInt();
            Request request = (chose <= requests.size() && chose > 0) ? requests.get(chose) : null;
            scanner.nextLine();
            try {
                switch (Objects.requireNonNull(request)) {
                    case MOST_POPULAR_FUND ->
                        //        Найдите фонд, где больше всего вложенцев
                            System.out.println(utils.findMaxPopularPensionFund(pensionFunds));
                    case WORKER_WITH_BIGGEST_PENSION -> {
                        //      Найдите имя человека, у которого самая большая пенсия (БЕРЕМ РАСЧЕТНУЮ ПЕНСИЮ без ВНИМАНИЯ К ФОНДУ)
                        Worker biggerAll = utils.findWorkerWithBiggestPensionAll(pensionFunds);
                        System.out.println(biggerAll.getName() + strings.get(Phrase.WORKERS_PENSION) + biggerAll.calculatePension());
                        //        альтернативный вариант (ДЛЯ ТЕХ, ЧЬИ ДЕНЬГИ не УКРАЛИ)
                        //        итерация)
                        Worker biggerState = utils.findWorkerWithBiggestPensionState(pensionFunds);
                        System.out.println(biggerState + strings.get(Phrase.STATE_PENSION) + biggerState.calculatePension());
                    }
                    case LOSERS ->
                        //        Найдите людей, которые стали жертвами "не государственных фондов"
                            System.out.println(utils.findLosers(pensionFunds).size());
                    case AVERAGE_PENSION_OF_FUNDS -> {
                        //        Найдите среднюю пенсию по фондам
                        System.out.println(strings.get(Phrase.MEDIAN_PENSION_OF_FUNDS));
                        pensionFunds.forEach(pensionFund -> {
                            System.out.println(pensionFund.getName() + " = " + pensionFund.calculateMedianPension(pensionFund.getMembers()));
                        });
                        //       альтернативный способ со стримами
                        utils.findMedianPensionForEach(pensionFunds);
                    }
                    //      System.out.println("======ЭТОТ КОД ВСЕ ТОРМОЗИТ И ДОЛГО ВЫПОЛНЯЕТСЯ======");
                    //        pensionFunds.forEach(pensionFund -> {
                    //            System.out.println("Средняя пенсия для фонда " + pensionFund.getName() + ": " + utils.findMedianPension(pensionFund));
                    //        });
                    case AVERAGE_PENSION_OF_WORKERS -> {
                        //        Найдите среднюю пенсию по людям
                        System.out.println(strings.get(Phrase.AVERAGE_PENSION_WORKER));
                        List<Double> pensions = utils.getAllPensions(pensionFunds);
                        System.out.printf("%.1f%n", pensions.stream().reduce(Double::sum).get() / pensions.size());
                    }
                    case BIGGEST_PENSION_DEPENDS_ON_AGE -> {
                        // Найдите наибольшую пенсию среди людей до 25
                        // находим наибольшую пенсию...буквально только пенсию
                        System.out.println(utils.getBiggestPensionWithAge(pensionFunds));
                        // находим человека с такой пенсией
                        System.out.println(utils.findWorkerWithBiggestPensionWithAge(pensionFunds));
                    }
                    case YOUNGEST_WORKER_OF_STATE_FUNDS ->
                        // ё) Найдите самого молодого человека среди вложенцев гос. фондов
                            System.out.println(utils.findYoungestMembersOfFunds(pensionFunds));
                    default -> System.out.println(strings.get(Phrase.TRY_AGAIN));
                }
                System.out.println(strings.get(Phrase.CONTINUE_OPTION));
                answer = scanner.nextLine();
            } catch (NullPointerException e) {
                System.out.println(strings.get(Phrase.ATTENTION));
                throw new NullPointerException();
            }
        }
        System.out.println(strings.get(Phrase.BYE));
    }
}
