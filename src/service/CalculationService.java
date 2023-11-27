package service;
import fund.PensionFund;
import fund.Phrase;
import people.Worker;
import java.io.*;
import java.util.*;


public class CalculationService {

    private final File FILE = new File("./dataBase/texts.txt");

    private final SupportService SUPPORT_SERVICE = new SupportService();

    private final HashMap<Phrase, String> STRINGS = SUPPORT_SERVICE.getString();


    public CalculationService() throws IOException {
    }

    /**
     * @param pensionFunds - принимает список пенсионных фондов
     * @return PensionFund - возвращает пенсионный фонд с самым большим количеством участников
     */

    public PensionFund findMaxPopularPensionFund(List<PensionFund> pensionFunds) {
        System.out.println(STRINGS.get(Phrase.POPULAR_FUND));
        PensionFund mostPopularPensionFund = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .max(Comparator.comparingInt(fund -> fund.getMembers().size()))
                .orElse(null);
        return mostPopularPensionFund;
    }

    /**
     * @param pensionFunds - принимает список пенсионных фондов
     * @return Worker - возвращает работника с самой большой пенсией (принимаются во внимание все фонды)
     */
    public Worker findWorkerWithBiggestPensionAll(List<PensionFund> pensionFunds) {
        System.out.println(STRINGS.get(Phrase.HIGHEST_PENSION));
        Worker worker = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .flatMap(pensionFund -> pensionFund.getMembers().stream())
                .max(Comparator.comparingDouble(Worker::calculatePension))
                .orElse(null);
        return worker;
    }
    /**
     * @param pensionFunds - принимает список пенсионных фондов
     * @return Worker - возвращает работника с самой большой пенсией (принимаются во внимание только
     * государственные фонды...в остальных фондах деньги украли)
     */
    public Worker findWorkerWithBiggestPensionState(List<PensionFund> pensionFunds) {
        System.out.println(STRINGS.get(Phrase.HIGHEST_STATE_PENSION));
        AbstractMap.SimpleEntry<Worker, Double> winner = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .flatMap(pensionFund -> pensionFund.getMembers().stream()
                        .map(member -> new AbstractMap.SimpleEntry<>(member, pensionFund.calculatePensionFor(member))))
                .max(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue))
                .orElse(null);
        assert winner != null;
        return winner.getKey();
    }
    /**
     * @param pensionFunds - принимает список пенсионных фондов
     * @return Double - возвращает самую большую пенсию (принимаются во внимание только государственные фонды)
     */
    public Double biggestPensionAll(List<PensionFund> pensionFunds) {
        double maxP = 0;
        for (PensionFund pensionFund : pensionFunds) {
            List<Worker> members = pensionFund.getMembers();
            for (Worker member : members) {
                pensionFund.calculatePensionFor(member);
                if (pensionFund.calculatePensionFor(member) > maxP) {
                    maxP = pensionFund.calculatePensionFor(member);
                }
            }
        }
        return maxP;
    }
    /**
     * @param pensionFunds - принимает список пенсионных фондов
     * @return losers - возвращает список участников негосударственных фондов
     */
    public List<Worker> findLosers(List<PensionFund> pensionFunds) {
        System.out.println(STRINGS.get(Phrase.LOSERS));
        List<Worker> losers = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .filter(pensionFund -> !pensionFund.isState())
                .flatMap(pensionFund -> pensionFund.getMembers().stream())
                .toList();
        return losers;
    }
    /**
     * @param pensionFunds - принимает список пенсионных фондов
     * @return pensions - возвращает список всех пенсий
     */
    public List<Double> getAllPensions(List<PensionFund> pensionFunds) {
        List<Double> pensions = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .map(PensionFund::getMembers)
                .flatMap(Collection::stream)
                .map(Worker::calculatePension)
                .toList();
        return pensions;
    }

    /**
     * @param pensionFund - принимает пенсионный фонд
     * @return double - возвращает среднюю пенсию по фонду
     */
    public double findMedianPension(PensionFund pensionFund) {
        System.out.println(STRINGS.get(Phrase.MEDIAN_PENSION_OF_FUNDS));
        double average = pensionFund.getMembers().stream()
                .filter(Objects::nonNull)
                .mapToDouble(pension -> pensionFund.calculateMedianPension(pensionFund.getMembers()))
                .average()
                .orElse(0.0);
        return average;
    }
    /**
     * @param pensionFunds - принимает пенсионный фонд
     * @return double - возвращает самую большую пенсию у участника ДО определенного возраста
     */
    public double getBiggestPensionWithAge(List<PensionFund> pensionFunds) {
        System.out.println(STRINGS.get(Phrase.HIGHEST_PENSION_AGE));
        Scanner scanner = new Scanner(System.in);
        System.out.println(STRINGS.get(Phrase.INPUT_AGE));
        int age = scanner.nextInt();
        List<Double> pensions = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .map(PensionFund::getMembers)
                .flatMap(Collection::stream)
                .filter(member -> member.getAge() < age)
                .map(Worker::calculatePension)
                .toList();
        return Collections.max(pensions);
    }
    /**
     * @param pensionFunds - принимает пенсионный фонд
     * @return double - возвращает участника ДО определенного возраста с самой большой пенсией
     */
    public Worker findWorkerWithBiggestPensionWithAge(List<PensionFund> pensionFunds) {
        System.out.println(STRINGS.get(Phrase.WORKER_HIGHEST_PENSION_AGE));
        Scanner scanner = new Scanner(System.in);
        System.out.println(STRINGS.get(Phrase.INPUT_AGE));
        int age = scanner.nextInt();
        Worker worker = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .map(PensionFund::getMembers)
                .flatMap(Collection::stream)
                .filter(member -> member.getAge() < age)
                .max(Comparator.comparingDouble(Worker::calculatePension))
                .orElse(null);
        return worker;
    }
    /**
     * @param pensionFunds - принимает пенсионный фонд
     * @return double - возвращает самого молодого участника пенсионных фондов
     */
    public Worker findYoungestMembersOfFunds(List<PensionFund> pensionFunds) {
        System.out.println(STRINGS.get(Phrase.YOUNGEST_WORKER_STATE_FUND));
        Worker worker = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null && pensionFund.isState())
                .flatMap(pensionFund -> pensionFund.getMembers().stream())
                .min(Comparator.comparingInt(Worker::getAge))
                .orElse(null);
        return worker;
//        AbstractMap.SimpleEntry<Worker, Integer> worker = pensionFunds.stream()
//                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
//                .filter(PensionFund::isState)
//                .flatMap(pensionFund -> pensionFund.getMembers().stream()
//                        .map(member -> new AbstractMap.SimpleEntry<>(member, member.getAge())))
//                .min(Comparator.comparingInt(AbstractMap.SimpleEntry::getValue))
//                .orElse(null);
//        return worker.getKey();
    }
    /**
     * @param pensionFunds - принимает пенсионный фонд
     * @return выводит на экран средние пенсии по фондам (реализация через форич)
     */
    public void findMedianPensionForEach(List<PensionFund> pensionFunds) {
        System.out.println(STRINGS.get(Phrase.MEDIAN_PENSION_OF_FUNDS));
        pensionFunds.forEach(pensionFund -> {
            double averagePension = pensionFund.getMembers().stream()
                    .mapToDouble(member -> pensionFund.calculateMedianPension(List.of(member)))
                    .average()
                    .orElse(0.0);
            System.out.println(STRINGS.get(Phrase.AVERAGE_PENSION_FUNDS) + pensionFund.getName() + ": " + averagePension);
        });
    }
}
