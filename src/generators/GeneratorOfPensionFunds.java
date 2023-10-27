package generators;

import fund.PensionFund;
import people.Worker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.DayOfWeek;
import java.util.*;

public class GeneratorOfPensionFunds {

    private GeneratorOfPensionFunds() {
    }

    public static List<PensionFund> generatePensionFunds() throws FileNotFoundException {
        Random random = new Random();
        final int MIN_QUANTITY = 100;
        final int MAX_QUANTITY = 5000;

        List<Worker> workers = GeneratorOfWorkers.generateWorkers();


        File funds = new File("..//pensionFund/dataBase/pensionFunds.txt");
        FileReader fileReader = new FileReader(funds);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<PensionFund> pensionFunds = bufferedReader.lines()
                .filter(Objects::nonNull)
                .map(fund -> {
                    String[] temp = fund.split(" ");
                    boolean isState = temp[temp.length - 1].equals("true");
                    String dateOfCreate = temp[temp.length - 2];
                    String name = String.join(" ", Arrays.copyOfRange(temp, 0, temp.length - 2));
                    List<Worker> members = new ArrayList<>();
                    int quantityOfMembers = (isState) ? random.nextInt(MIN_QUANTITY * 10, MAX_QUANTITY * 2) : random.nextInt(MIN_QUANTITY, MAX_QUANTITY / 2);
                    for (int i = 0; i < quantityOfMembers; i++) {
                        Worker worker = workers.get(random.nextInt(workers.size()));
                        if(!members.contains(worker)){
                            members.add(worker);
                        }
                    }
                    Map<DayOfWeek, Boolean> daysOfWeek = new HashMap<>();
                    daysOfWeek.put(DayOfWeek.MONDAY, true);
                    daysOfWeek.put(DayOfWeek.TUESDAY, true);
                    daysOfWeek.put(DayOfWeek.WEDNESDAY, true);
                    daysOfWeek.put(DayOfWeek.THURSDAY, true);
                    daysOfWeek.put(DayOfWeek.FRIDAY, true);
                    daysOfWeek.put(DayOfWeek.SATURDAY, true);
                    daysOfWeek.put(DayOfWeek.SUNDAY, false);
                    return new PensionFund(name, isState, dateOfCreate, members, daysOfWeek);
                })
                .toList();
        return pensionFunds;
    }


}
