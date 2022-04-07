package interfacepackage;

public abstract class Worker implements Servant, Object
{
    protected String fio;
    protected String sex;
    protected String birth_date;

    Worker()
    {
        fio = "Неизвестно";
        sex = "Неизвестно";
        birth_date = "01.01.0101";
    }

    Worker(String fio, String sex, String birth_date)
    {
        this.fio = fio;
        this.sex = sex;
        this.birth_date = birth_date;
    }

    public abstract String get_uniform();


}
