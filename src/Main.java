import fund.PensionFund;
import generators.GeneratorOfPensionFunds;
import people.Worker;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        List<PensionFund> pensionFunds = GeneratorOfPensionFunds.generatePensionFunds();
        int members = 0; // считаем количество членов пенсионных фондов
        for (PensionFund fund : pensionFunds) {
            System.out.println(fund.getMembers().size());
            members += fund.getMembers().size();
        }
        System.out.println(members);
//        а) Найдите фонд, где больше всего вложенцев

        List<Integer> countMembers = pensionFunds.stream()
                .filter(Objects::nonNull)
                .map(pensionFund -> pensionFund.getMembers().size())
                .toList();
        System.out.println(Collections.max(countMembers)); //максимальное количество членов фонда

        PensionFund mostPopularFund = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .max(Comparator.comparingInt(fund -> fund.getMembers().size()))
                .orElse(null);
        assert mostPopularFund != null;
        System.out.println(mostPopularFund.getName() + " has " + Collections.max(countMembers) + " members");

//      Найдите имя человека, у которого самая большая пенсия (БЕРЕМ РАСЧЕТНУЮ ПЕНСИЮ без ВНИМАНИЯ К ФОНДУ)

        List<Double> pensionSizes = pensionFunds.stream()// список всех расчетных пенсий
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .map(PensionFund::getMembers)
                .flatMap(Collection::stream)
                .map(Worker::calculatePension)
                .toList();


        Worker worker = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .map(PensionFund::getMembers)
                .flatMap(Collection::stream)
                .max(Comparator.comparingDouble(Worker::calculatePension))
                .orElse(null);

        System.out.println(worker + " max pension - " + Collections.max(pensionSizes));

//        альтернативный вариант (ДЛЯ ТЕХ, ЧЬИ ДЕНЬГИ не УКРАЛИ)
//        итерация)
        double maxP = 0;
        Worker happy = null;
        for (PensionFund pensionFund : pensionFunds) {
            List<Worker> workerList = pensionFund.getMembers();
            for (Worker value : workerList) {
                if (maxP < pensionFund.calculatePensionFor(value)) {
                    maxP = pensionFund.calculatePensionFor(value);
                    happy = value;
                }
            }
        }
        System.out.println(happy);
        System.out.println(maxP);

//        оно же в стриме

        AbstractMap.SimpleEntry<Worker, Double> winner = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .flatMap(pensionFund -> pensionFund.getMembers().stream()
                        .map(member -> new AbstractMap.SimpleEntry<>(member, pensionFund.calculatePensionFor(member))))
                .max(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue))
                .orElse(null);

        System.out.println(winner);

//        Найдите людей, которые стали жертвами "не государственных фондов"

        List<Worker> losers = pensionFunds.stream() //лузеры
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .filter(pensionFund -> !pensionFund.isState())
                .flatMap(pensionFund -> pensionFund.getMembers().stream())
                .toList();
        System.out.println(losers.size());

//        Найдите среднюю пенсию по фондам

        pensionFunds.forEach(pensionFund -> {
            System.out.println(pensionFund.getName() + " = " + pensionFund.calculateMedianPension(pensionFund.getMembers()));
        });

//       альтернативный способ со стримами
        pensionFunds.forEach(pensionFund -> {
            double averagePension = pensionFund.getMembers().stream()
                    .mapToDouble(member -> pensionFund.calculateMedianPension(List.of(member)))
                    .average()
                    .orElse(0.0);
            System.out.println("Средняя пенсия для фонда " + pensionFund.getName() + ": " + averagePension);
        });

//        Найдите среднюю пенсию по людям
        System.out.printf("%.1f%n", pensionSizes.stream().reduce(Double::sum).get() / pensionSizes.size());

// Найдите наибольшую пенсию среди людей до 25
        // находим наибольшую пенсию...буквально только пенсию
        List<Double> pensionSizesLess25 = pensionFunds.stream()// список всех расчетных пенсий
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .map(PensionFund::getMembers)
                .flatMap(Collection::stream)
                .filter(member -> member.getAge() < 25)
                .map(Worker::calculatePension)
                .toList();


        // находим человека с такой пенсией
        Worker workerLess25 = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .map(PensionFund::getMembers)
                .flatMap(Collection::stream)
                .filter(member -> member.getAge() < 25)
                .max(Comparator.comparingDouble(Worker::calculatePension))
                .orElse(null);

        System.out.println(workerLess25 + " max pension - " + Collections.max(pensionSizesLess25));

        // ё) Найдите самого молодого человека среди вложенцев гос. фондов

        AbstractMap.SimpleEntry<Worker, Integer> young = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .filter(PensionFund::isState)
                .flatMap(pensionFund -> pensionFund.getMembers().stream()
                        .map(member -> new AbstractMap.SimpleEntry<>(member, member.getAge())))
                .min(Comparator.comparingInt(AbstractMap.SimpleEntry::getValue))
                .orElse(null);
        System.out.println(young);
    }
}
