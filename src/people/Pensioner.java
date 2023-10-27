package people;

public class Pensioner extends Person {

    private double pension;

    public Pensioner(String name, int age, int growth, double weight, int money, double pension) {
        super(name, age, growth, weight, money);
        this.pension = pension;
    }

    public double getPension() {
        return pension;
    }

    public void setPension(double pension) {
        this.pension = pension;
    }


    @Override
    public void die() {
        System.out.println("Этот пенсионер умер, он заработал: " + (getAge() - 50) * pension);
    }

    @Override
    public void die(int years) {
        System.out.println("Этот пенсионер умрет через " + years + " лет");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Pensioner pensioner = (Pensioner) o;

        return Double.compare(pensioner.pension, pension) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(pension);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "people.Pensioner{" +
                "pension=" + pension +
                '}';
    }
}
