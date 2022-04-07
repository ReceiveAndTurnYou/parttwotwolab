package interfacepackage;

public class Director_Gild implements Servant, Object
{
    private String name;
    private String appointment_date;
    private double salary;

    public Director_Gild()
    {
        name = "Неизвестно";
        appointment_date="0.1.0101";
        salary = 0;
    }

    public Director_Gild(String name, String appointment_date, double salary)
    {
        this.name = name;
        this.appointment_date = appointment_date;
        this.salary = salary;
    }


    @Override
    public void print()
    {
        System.out.println("Director{" +
                "name= '" + name + ", "+ "appointment_date= "+ appointment_date + ", " + "salary= " + salary + "}");
    }

    @Override
    public double taxReturn()
    {
        double cof = 0.4;
        int base = 35000;
        double result = base * cof - salary;
        return result;
    }
}
