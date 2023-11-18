package utils;

import fund.PensionFund;
import fund.Request;
import people.Worker;

import java.io.*;
import java.util.*;


public class Utils {
    List<String> texts = getTexts();

    public Utils() throws IOException {
    }

    public PensionFund findMaxPopularPensionFund(List<PensionFund> pensionFunds) {
        System.out.println(texts.get(13));
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
        System.out.println(texts.get(14));
        Worker worker = pensionFunds.stream()
                .filter(pensionFund -> pensionFund != null && pensionFund.getMembers() != null)
                .map(PensionFund::getMembers)
                .flatMap(Collection::stream)
                .max(Comparator.comparingDouble(Worker::calculatePension))
                .orElse(null);
        return worker;
    }

    public Worker findWorkerWithBiggestPensionState(List<PensionFund> pensionFunds) {
        System.out.println(texts.get(15));
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
        System.out.println(texts.get(16));
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
        System.out.println(texts.get(17));
        double average = pensionFund.getMembers().stream()
                .filter(Objects::nonNull)
                .mapToDouble(pension -> pensionFund.calculateMedianPension(pensionFund.getMembers()))
                .average()
                .orElse(0.0);
        return average;
    }

    public double getBiggestPensionWithAge(List<PensionFund> pensionFunds) {
        System.out.println(texts.get(18));
        Scanner scanner = new Scanner(System.in);
        System.out.println(texts.get(19));
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
        System.out.println(texts.get(20));
        Scanner scanner = new Scanner(System.in);
        System.out.println(texts.get(19));
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
        System.out.println(texts.get(21));
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
        System.out.println(texts.get(22));
        pensionFunds.forEach(pensionFund -> {
            double averagePension = pensionFund.getMembers().stream()
                    .mapToDouble(member -> pensionFund.calculateMedianPension(List.of(member)))
                    .average()
                    .orElse(0.0);
            System.out.println(texts.get(23) + pensionFund.getName() + ": " + averagePension);
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
        File file = new File("./dataBase/texts.txt");
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            texts = bufferedReader.lines().toList();
        } catch (IOException e) {
            System.out.println("Файл не найден");
            throw new IOException();
        }
        return texts;
    }

    public void printStringWithNewLine(String string) {
        String[] strings = string.split("\\|");
        for (String s : strings) {
            System.out.println(s);
        }

    }
}
