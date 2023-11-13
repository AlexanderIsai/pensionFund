import fund.PensionFund;
import generators.GeneratorOfPensionFunds;
import people.Worker;
import utils.Utils;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        Utils utils = new Utils();
        Scanner scanner = new Scanner(System.in);
        List<PensionFund> pensionFunds = GeneratorOfPensionFunds.generatePensionFunds();
        System.out.println("=== PENSION FUNDS ===");
        System.out.println(pensionFunds);
        int members = 0; // считаем количество членов пенсионных фондов
        for (PensionFund fund : pensionFunds) {

//            System.out.println(fund.getMembers().size());
            members += fund.getMembers().size();
        }
        System.out.println(members);
        System.out.println("Приветствую на нашей странице статистики. Хотите узнать больше? y/n");
        String answer = scanner.nextLine();
        while (answer.equals("y")){
        System.out.println("Выберите тип расчета: \n " +
                "1 - найти фонд, где больше всего членов \n " +
                "2 - найти имя человека, у которого самая большая пенсия \n " +
                "3 - найти людей, которые стали жертвами негосударственных фондов \n " +
                "4 - найти среднюю пенсию по фондам \n " +
                "5 - найти среднюю пенсию по людям \n " +
                "6 - найти наибольшую пенсию среди людей определенного возраста \n " +
                "7 - найти самого молодого человека из членов государственных фондов");
        int chose = scanner.nextInt();
        scanner.nextLine();
            switch (chose) {
                case 1 ->
                    //        а) Найдите фонд, где больше всего вложенцев
                        System.out.println(utils.findMaxPopularPensionFund(pensionFunds));
                case 2 -> {
                    //      Найдите имя человека, у которого самая большая пенсия (БЕРЕМ РАСЧЕТНУЮ ПЕНСИЮ без ВНИМАНИЯ К ФОНДУ)
                    Worker biggerAll = utils.findWorkerWithBiggestPensionAll(pensionFunds);
                    System.out.println(biggerAll.getName() + " workers pension = " + biggerAll.calculatePension());
                    //        альтернативный вариант (ДЛЯ ТЕХ, ЧЬИ ДЕНЬГИ не УКРАЛИ)
//        итерация)
                    Worker biggerState = utils.findWorkerWithBiggestPensionState(pensionFunds);
                    System.out.println(biggerState + " state pension = " + biggerState.calculatePension());
                }
                case 3 ->
                    //        Найдите людей, которые стали жертвами "не государственных фондов"
                        System.out.println(utils.findLosers(pensionFunds).size());
                case 4 -> {
                    //        Найдите среднюю пенсию по фондам
                    System.out.println("==== MEDIAN PENSION OF EVEN FUND ====");
                    pensionFunds.forEach(pensionFund -> {
                        System.out.println(pensionFund.getName() + " = " + pensionFund.calculateMedianPension(pensionFund.getMembers()));
                    });
//       альтернативный способ со стримами
                    utils.findMedianPensionForEach(pensionFunds);
                }
//        System.out.println("======ЭТОТ КОД ВСЕ ТОРМОЗИТ И ДОЛГО ВЫПОЛНЯЕТСЯ======");
//        pensionFunds.forEach(pensionFund -> {
//            System.out.println("Средняя пенсия для фонда " + pensionFund.getName() + ": " + utils.findMedianPension(pensionFund));
//        });
                case 5 -> {
                    //        Найдите среднюю пенсию по людям
                    System.out.println("===== AVERAGE PENSION AMONG WORKERS =====");
                    List<Double> pensions = utils.getAllPensions(pensionFunds);
                    System.out.printf("%.1f%n", pensions.stream().reduce(Double::sum).get() / pensions.size());
                }
                case 6 -> {
                    // Найдите наибольшую пенсию среди людей до 25
                    // находим наибольшую пенсию...буквально только пенсию
                    System.out.println(utils.getBiggestPensionWithAge(pensionFunds));
                    // находим человека с такой пенсией
                    System.out.println(utils.findWorkerWithBiggestPensionWithAge(pensionFunds));
                }
                case 7 ->
                    // ё) Найдите самого молодого человека среди вложенцев гос. фондов
                        System.out.println(utils.findYoungestMembersOfFunds(pensionFunds));
                default -> System.out.println("Try again");
            }
            System.out.println("Будете продолжать? y/n");
            answer = scanner.nextLine();
        }
        System.out.println("Auf Wiedersehen");
    }
}
