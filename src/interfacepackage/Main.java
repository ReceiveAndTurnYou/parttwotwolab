package interfacepackage;

import java.util.Scanner;

public class Main
{
    public static int menu()
    {
        int choice = 0;
        System.out.println(".......Меню.......");
        System.out.println("1. Вывести данные о рабочих");
        System.out.println("2. Вывести данные о начальниках цеха");
        System.out.println("3. Вывести данные о директоре");
        System.out.println("4. Определить выплачиваемые налоги рабочим");
        System.out.println("5. Определить выплачивыемые налоги начальникам цеха");
        System.out.println("6. Определить выплачивыемые налоги директору");
        System.out.println("7. Выход");
        System.out.print(">> ");
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static void main(String[] args)
    {
        switch(menu())
        {
            case 7: return;

        }
    }
}
