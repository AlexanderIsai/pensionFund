package utils;

import fund.PensionFund;
import fund.Phrase;
import fund.Request;
import people.Worker;

import java.io.*;
import java.util.*;


public class Utils {

    private final File FILE = new File("./dataBase/texts.txt");

    private HashMap<Phrase, String> strings = getString();


    public Utils() throws IOException {
    }

    public PensionFund findMaxPopularPensionFund(List<PensionFund> pensionFunds) {
        System.out.println(strings.get(Phrase.POPULAR_FUND));
        PensionFund mostPopularPensionFund = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .max(Comparator.comparingInt(fund -> fund.getMembers().size()))
                .orElse(null);
        return mostPopularPensionFund;
    }

    public int findMaxQuantityMembers(List<PensionFund> pensionFunds) {
        List<Integer> countMembers = pensionFunds.stream()
                .filter(Objects::nonNull)
                .map(pensionFund -> pensionFund.getMembers().size())
                .toList();
        return Collections.max(countMembers);
    }


    public Worker findWorkerWithBiggestPensionAll(List<PensionFund> pensionFunds) {
        System.out.println(strings.get(Phrase.HIGHEST_PENSION));
        Worker worker = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .map(PensionFund::getMembers)
                .flatMap(Collection::stream)
                .max(Comparator.comparingDouble(Worker::calculatePension))
                .orElse(null);
        return worker;
    }

    public Worker findWorkerWithBiggestPensionState(List<PensionFund> pensionFunds) {
        System.out.println(strings.get(Phrase.HIGHEST_STATE_PENSION));
        AbstractMap.SimpleEntry<Worker, Double> winner = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .flatMap(pensionFund -> pensionFund.getMembers().stream()
                        .map(member -> new AbstractMap.SimpleEntry<>(member, pensionFund.calculatePensionFor(member))))
                .max(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue))
                .orElse(null);
        assert winner != null;
        return winner.getKey();
    }

    public Double biggestPensionAll(List<PensionFund> pensionFunds) {
        double maxP = 0;

        for (int i = 0; i < pensionFunds.size(); i++) {
            List<Worker> members = pensionFunds.get(i).getMembers();
            for (int j = 0; j < members.size(); j++) {
                pensionFunds.get(i).calculatePensionFor(members.get(j));
                if (pensionFunds.get(i).calculatePensionFor(members.get(j)) > maxP) {
                    maxP = pensionFunds.get(i).calculatePensionFor(members.get(j));
                }
            }
        }
        return maxP;
    }

    public List<Worker> findLosers(List<PensionFund> pensionFunds) {
        System.out.println(strings.get(Phrase.LOSERS));
        List<Worker> losers = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .filter(pensionFund -> !pensionFund.isState())
                .flatMap(pensionFund -> pensionFund.getMembers().stream())
                .toList();
        return losers;
    }

    public List<Double> getAllPensions(List<PensionFund> pensionFunds) {
        List<Double> pensions = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .map(PensionFund::getMembers)
                .flatMap(Collection::stream)
                .map(Worker::calculatePension)
                .toList();
        return pensions;
    }

    public double findMedianPension(PensionFund pensionFund) {
        System.out.println(strings.get(Phrase.MEDIAN_PENSION_OF_FUNDS));
        double average = pensionFund.getMembers().stream()
                .filter(Objects::nonNull)
                .mapToDouble(pension -> pensionFund.calculateMedianPension(pensionFund.getMembers()))
                .average()
                .orElse(0.0);
        return average;
    }

    public double getBiggestPensionWithAge(List<PensionFund> pensionFunds) {
        System.out.println(strings.get(Phrase.HIGHEST_PENSION_AGE));
        Scanner scanner = new Scanner(System.in);
        System.out.println(strings.get(Phrase.INPUT_AGE));
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

    public Worker findWorkerWithBiggestPensionWithAge(List<PensionFund> pensionFunds) {
        System.out.println(strings.get(Phrase.WORKER_HIGHEST_PENSION_AGE));
        Scanner scanner = new Scanner(System.in);
        System.out.println(strings.get(Phrase.INPUT_AGE));
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

    public Worker findYoungestMembersOfFunds(List<PensionFund> pensionFunds) {
        System.out.println(strings.get(Phrase.YOUNGEST_WORKER_STATE_FUND));
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

    public void findMedianPensionForEach(List<PensionFund> pensionFunds) {
        System.out.println(strings.get(Phrase.MEDIAN_PENSION_OF_FUNDS));
        pensionFunds.forEach(pensionFund -> {
            double averagePension = pensionFund.getMembers().stream()
                    .mapToDouble(member -> pensionFund.calculateMedianPension(List.of(member)))
                    .average()
                    .orElse(0.0);
            System.out.println(strings.get(Phrase.AVERAGE_PENSION_FUNDS) + pensionFund.getName() + ": " + averagePension);
        });
    }

    public HashMap<Integer, Request> createRequestsMap() {
        HashMap<Integer, Request> requestsMap = new HashMap<>();
        List<Request> requests = new ArrayList<>(Arrays.asList(Request.values()));
        for (int i = 0; i < requests.size(); i++) {
            requestsMap.put(i + 1, requests.get(i));
        }
        return requestsMap;
    }

    public List<String> getTexts() throws IOException {
        List<String> texts;
        try (FileReader fileReader = new FileReader(FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            texts = bufferedReader.lines().toList();
        } catch (IOException e) {
            System.out.println("Файл не найден");
            throw new IOException();
        }
        return texts;
    }

    public HashMap<Phrase, String> getString() throws IOException {
        HashMap<Phrase, String> strings = new HashMap<>();
        List<Phrase> phrases = new ArrayList<>(Arrays.asList(Phrase.values()));
        try (FileReader fileReader = new FileReader(FILE);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            List<String> texts = bufferedReader.lines().toList();
            for (int i = 0; i < phrases.size(); i++) {
                strings.put(phrases.get(i), texts.get(i));
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
            throw new IOException();
        }
        return strings;
    }

    public void printStringWithNewLine(String string) {
        String[] strings = string.split("\\|");
        for (String s : strings) {
            System.out.println(s);
        }
    }
}
