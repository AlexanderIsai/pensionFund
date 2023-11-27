package service;
import fund.PensionFund;
import fund.Phrase;
import fund.Request;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SupportService {

    private final File FILE = new File("./dataBase/texts.txt");

    public SupportService() {
    }
    /**
     * Сервисный метод, для получения коллекции строк
     * @return Возвращает коллекцию пар ключ-значение, где ключ - енам, а значение - строка,
     * используемая при выводе информации в консоль
     */
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
    /**
     * @param string - принимает строку
     * Разбивает строку по специальному символу, выводит каждый отдельный элемент с новой строки
     */
    public void printStringWithNewLine(String string) {
        String[] strings = string.split("\\|");
        for (String s : strings) {
            System.out.println(s);
        }
    }
    /**
     * Сервисный метод для получения коллекции запросов
     * @return Возвращает коллекцию типа ключ-значение, где ключ - порядковый номер, а значение - енам.
     */
    public HashMap<Integer, Request> createRequestsMap() {
        HashMap<Integer, Request> requestsMap = new HashMap<>();
        List<Request> requests = new ArrayList<>(Arrays.asList(Request.values()));
        for (int i = 0; i < requests.size(); i++) {
            requestsMap.put(i + 1, requests.get(i));
        }
        return requestsMap;
    }
    /**
     * Сервисный метод для получения массива строк из фала
     * @return массив строк из файла (устаревший))
     */
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
    /** Вспомогательный метод
     * @param pensionFunds - принимает пенсионный фонд
     * @return список количества участников каждого пенсионного фонда
     */
    public int findMaxQuantityMembers(List<PensionFund> pensionFunds) {
        List<Integer> countMembers = pensionFunds.stream()
                .filter(Objects::nonNull)
                .map(pensionFund -> pensionFund.getMembers().size())
                .toList();
        return Collections.max(countMembers);
    }
}
