package interfacepackage;

public class Master_Gild implements Servant, Object
{
    private String name;
    private int work_experience;
    private double salary;


    Master_Gild()
    {
        name = "Неизвестно";
        work_experience =0;
        salary = 0;
    }

    Master_Gild(String name, int work_experience, double salary)
    {
        this.name = name;
        this.work_experience = work_experience;
        this.salary = salary;
    }


    @Override
    public void print()
    {
        System.out.println("Master{ name = " + name + ", " + "work_experience= " + work_experience +
            ", " + "salary= "+ salary + "}");
    }

    @Override
    public double taxReturn()
    {
        double cof = 0.56;
        int base = 10000;
        double result = base * cof - salary;
        return result;
    }
}
