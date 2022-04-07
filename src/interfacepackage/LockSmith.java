package interfacepackage;

public class LockSmith extends Worker
{
    String uniform;
    String weapon;

    LockSmith()
    {
        uniform = "Неизвестно";
        weapon = "Неизвестно";
    }

    LockSmith(String fio, String sex, String birth_date, String uniform, String weapon)
    {
        super(fio, sex, birth_date);
        this.uniform = uniform;
        this.weapon = weapon;
    }


    @Override
    public void print()
    {
        System.out.println("Turner{ name = " + fio + ", " + "sex= " + sex +
                ", " + "birth_date= "+ birth_date + ", " + "uniform= "+
                "Синий комбинезон с подтяжечками" + ", " + "weapon= " + weapon + "}");
    }

    @Override
    public double taxReturn()
    {
        return 1112.12;
    }

    @Override
    public String get_uniform()
    {
        return "Красный комбинезон без подтяжечек";
    }

    public String get_weapon()
    {
        return "Топор с кобальтовыми вставками";
    }

}
