package interfacepackage;

public class Turner extends Worker
{
    String uniform;

    Turner()
    {
        super();
        uniform = "Неизвестно";
    }

    Turner(String fio, String sex, String birth_date, String uniform)
    {
        super(fio, sex, birth_date);
        this.uniform = uniform;
    }

    @Override
    public void print()
    {
        System.out.println("Turner{ name = " + fio + ", " + "sex= " + sex +
                ", " + "birth_date= "+ birth_date + ", " + "uniform= "+ "Синий комбинезон с подтяжечками" + "}");
    }

    @Override
    public double taxReturn()
    {
        return 1312.21;
    }

    @Override
    public String get_uniform()
    {
        return "Синий комбинезон с подтяжечками";
    }
}
