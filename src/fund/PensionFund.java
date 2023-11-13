package fund;
import generators.GeneratorOfWorkers;
import people.Worker;
import calculator.AbleToCalculatePension;

import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class PensionFund {

    private static int minQuantity = 100;
    private static int maxQuantity = 5000;
    private String name;
    private boolean isState;
    private String dateCreation;
    private List<Worker> members;

    private static Map<DayOfWeek, Boolean> daysOfWeek = fillWorkDays();

    public PensionFund(String name, boolean isState, String dateCreation) {
        this.name = name;
        this.isState = isState;
        this.dateCreation = dateCreation;
    }

    public PensionFund(){}

    public PensionFund(String name, boolean isState, String dateCreation, List<Worker> members) {
        this.name = name;
        this.isState = isState;
        this.dateCreation = dateCreation;
        this.members = members;
    }
    public PensionFund (String string) throws FileNotFoundException {
        String[] temp = string.split(" ");
        this.isState = temp[temp.length - 1].equals("true");
        this.dateCreation = temp[temp.length - 2];
        this.name = String.join(" ", Arrays.copyOfRange(temp, 0, temp.length - 2));
        this.members = fillPensionFundByMembers(minQuantity, maxQuantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return isState;
    }

    public void setState(boolean state) {
        isState = state;
    }

    public List<Worker> getMembers() {
        return members;
    }

    public void setMembers(List<Worker> members) {
        this.members = members;
    }

    public Map<DayOfWeek, Boolean> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(Map<DayOfWeek, Boolean> daysOfWeek) {
        PensionFund.daysOfWeek = daysOfWeek;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public static int getMinQuantity() {
        return minQuantity;
    }

    public static void setMinQuantity(int minQuantity) {
        PensionFund.minQuantity = minQuantity;
    }

    public static int getMaxQuantity() {
        return maxQuantity;
    }

    public static void setMaxQuantity(int maxQuantity) {
        PensionFund.maxQuantity = maxQuantity;
    }

    public List<Worker> fillPensionFundByMembers(int minQuantity, int maxQuantity) throws FileNotFoundException {
        Random random = new Random();
        List<Worker> workers = GeneratorOfWorkers.generateWorkers();
        List<Worker> members = new ArrayList<>();
        int quantityOfMembers = (isState) ? random.nextInt(minQuantity * 10, maxQuantity * 2) : random.nextInt(minQuantity, maxQuantity / 2);
        for (int i = 0; i < quantityOfMembers; i++) {
            Worker worker = workers.get(random.nextInt(workers.size()));
            if(!members.contains(worker)){
                members.add(worker);
            }
        }
           return members;
    }

    public static HashMap<DayOfWeek, Boolean> fillWorkDays(){
        HashMap<DayOfWeek, Boolean> workingDays = new HashMap<>();
        Random random = new Random();
        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        for (DayOfWeek dayOfWeek : daysOfWeek) {
            workingDays.put(dayOfWeek, random.nextDouble() > 0.1);
        }
        return workingDays;
    }

    public boolean getInfo() {
        int count = (members != null) ? members.size() : 0;
        System.out.println(isState ? "Фонд государственный. Количество членов - " + count / 1000 + " тысяч" : "Фонд негосударственный. Количество членов - " + count);
        return false;
    }

    public boolean isWorkingDay() {
        boolean isWorking = false;
        LocalDate localDate = LocalDate.now();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (daysOfWeek.get(dayOfWeek)) {
            isWorking = true;
//            System.out.println("Мы работаем. Сегодня - " + dayOfWeek);
        } else {
            System.out.println("Мы не работаем. Сегодня - " + dayOfWeek);
        }

        return isWorking;
    }

    public double calculatePensionFor(AbleToCalculatePension obj) {

        if (obj == null || daysOfWeek == null || !isWorkingDay()) {
            return 0.0;
        }
        double pensionCalculate = 0.0;
        if (isState) {
            pensionCalculate = obj.calculatePension();
        }
        return pensionCalculate;
    }

    public double calculateMedianPension(List<Worker> members) {
        if (members == null || members.size() == 0) {
            return 0.0;
        }
        double sumOfPension = 0;
        for (Worker member : members) {
            sumOfPension += calculatePensionFor(member);
        }
        return sumOfPension / members.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PensionFund fund = (PensionFund) o;

        if (isState != fund.isState) return false;
        if (!Objects.equals(name, fund.name)) return false;
        if (!Objects.equals(dateCreation, fund.dateCreation)) return false;
        if (!Objects.equals(members, fund.members)) return false;
        return Objects.equals(daysOfWeek, daysOfWeek);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (isState ? 1 : 0);
        result = 31 * result + (dateCreation != null ? dateCreation.hashCode() : 0);
        result = 31 * result + (members != null ? members.hashCode() : 0);
        result = 31 * result + (daysOfWeek != null ? daysOfWeek.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "fund.PensionFund{" +
                "name='" + name + '\'' +
                ", isState=" + isState +
                ", dateCreation='" + dateCreation + '\'' +
                ", members=" + members.size() +
                ", daysOfWeek=" + daysOfWeek +
                '}';
    }
}
