package people;

import java.util.Objects;

public abstract class Person {

    private String name;
    private int age;
    private int growth;
    private double weight;
    private int money;
    private Gender gender;

    private double minSalary;
    private double maxSalary;

    public Person(String name, int age, int growth, double weight, int money) {
        this.name = name;
        this.age = age;
        this.growth = growth;
        this.weight = weight;
        this.money = money;
    }

    public Person(String name, double minSalary, double maxSalary) {
        this.name = name;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public Person(){

    }

    public Person(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;

    }

    public void getInfoAboutPerson() {
        System.out.println();
        System.out.println("Имя человека: " + name);
        System.out.println("Возраст человека: " + age + " лет");
        System.out.println("Рост человека: " + growth + " см");
        System.out.println("Вес человека: " + weight + " кг");
        System.out.println();
    }

    public void goToWork() {
        if (age < 18 || age > 70) {
            System.out.println("отдыхаю дома");
        } else System.out.println("работаю");
    }

    public void die() {
        System.out.println("Человек погиб");
    }

    public abstract void die(int years);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }

    @Override
    public String toString() {
        return "people.Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", growth=" + growth +
                ", weight=" + weight +
                ", money=" + money +
                ", gender=" + gender +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        if (growth != person.growth) return false;
        if (Double.compare(person.weight, weight) != 0) return false;
        if (money != person.money) return false;
        if (Double.compare(person.minSalary, minSalary) != 0) return false;
        if (Double.compare(person.maxSalary, maxSalary) != 0) return false;
        if (!Objects.equals(name, person.name)) return false;
        return gender == person.gender;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + growth;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + money;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        temp = Double.doubleToLongBits(minSalary);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxSalary);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}