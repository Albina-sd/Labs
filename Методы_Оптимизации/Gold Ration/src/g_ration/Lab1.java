package g_ration;
import java.util.Scanner;


public class Lab1 {

    public static void main(String[] args) {
        System.out.println("\nПрограмма нахожения минимума унимодальной функции при помощи метода" +
                " золотого сечения и оптимального пассивного поиска." +
                "\nДля ввода дробных чисел необходимо отделять дробную часть запятой.");

        /*
        a - начало отрезка, на котором осуществляется поиск минпимума
        b - конец отрезка
        e - точность
         */
        double a, b, e;
        Scanner scanner = new Scanner(System.in);

        // проверка на правильность ввода входных данных
        try {
            System.out.println("Введите начало отрезка (a):");
            a = scanner.nextDouble();
            System.out.println("Введите конец отрезка (b):");
            b = scanner.nextDouble();
            System.out.println("Введите точность (е):");
            e = scanner.nextDouble();
            // если границы отрезков введены не в том порядке - меняем местами
            if (a > b) {
                double j = b;
                b = a;
                a = j;
            }

            System.out.println("Выберите алгоритм поиска:" +
                    "\n 1 - Золотое сечение" +
                    "\n 2 - Оптимальный пассивный поиск");
            int i = scanner.nextInt();
            switch (i) {
                case 1:
                    // вызов класса выполняющего метод золотого сечения
                    GoldSelection GS = new GoldSelection();
                    System.out.println("x* = " + GS.findMin(a, b, e)); // вывод минимума
                    break;
                case 2:
                    // вызов класса выполняющего метод пассивного поиска
                    OptimalPassive OP = new OptimalPassive();
                    System.out.println("x* = " + OP.findMin(a, b, e)); // вывод минимума
                    break;
                default:
                    main(args);
            }

            System.out.println("Начать заново? Y/N");
            String restart = scanner.next();
            if (restart.equals("Y"))
                main(args);
            else System.out.println("Конец.");
        }
        catch (Exception E) {
            System.out.println("Введены неверные данные." +
                    "\nВыход из программы...");
        }
    }
}
