package fund;

import generators.GeneratorOfWorkers;
import people.Worker;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PensionFundService {

    public static HashMap<DayOfWeek, Boolean> fillWorkDays() {
        HashMap<DayOfWeek, Boolean> workingDays = new HashMap<>();
        Random random = new Random();
        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        for (DayOfWeek dayOfWeek : daysOfWeek) {
            workingDays.put(dayOfWeek, random.nextDouble() > 0.1);
        }
        return workingDays;
    }
    public static List<Worker> fillPensionFundByMembers(int minQuantity, int maxQuantity, boolean isState) throws IOException {
        Random random = new Random();
        List<Worker> workers = GeneratorOfWorkers.generateWorkers();
        List<Worker> members = new ArrayList<>();
        int quantityOfMembers = (isState) ? random.nextInt(minQuantity * 10, maxQuantity * 2) : random.nextInt(minQuantity, maxQuantity / 2);
        for (int i = 0; i < quantityOfMembers; i++) {
            Worker worker = workers.get(random.nextInt(workers.size()));
            if (!members.contains(worker)) {
                members.add(worker);
            }
        }
        return members;
    }
}
