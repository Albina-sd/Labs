package m_carlo;
import java.util.Scanner;

public class Lab2 {

    public static void main(String[] args) {
        System.out.println("\nПрограмма нахожения минимума унимодальной и " +
                "мультимодальной функции при помощи метода Монте-Карло." +
                "\nДля ввода дробных чисел необходимо отделять дробную часть запятой.");

        /*
        a - начало отрезка, на котором осуществляется поиск минимума
        b - конец отрезка
        N - количество испытаний
         */
        double a, b;
        double N;
        Scanner scanner = new Scanner(System.in);

        // проверка на правильность ввода входных данных
        try {
            System.out.println("Введите начало отрезка (a):");
            a = scanner.nextDouble();
            System.out.println("Введите конец отрезка (b):");
            b = scanner.nextDouble();

            // если границы отрезков введены не в том порядке - меняем местами
            if (a > b) {
                double j = b;
                b = a;
                a = j;
            }

            System.out.println("\nЗависимость N от P и q" );
            System.out.println("+-------+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+" +
                    "\n|  q/P  | 0.9 | 0.91| 0.92| 0.93| 0.94| 0.95| 0.96| 0.97| 0.98| 0.99|" +
                    "\n+-------+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+");
            String str="";

            for (double q = 0.005; q <= 0.1;){
                System.out.print("|");
                System.out.format("%6.3f",q);
                System.out.print(" |");
                for (double P = 0.9; P < 1;){
                    N = Math.log(1-P)/Math.log(1-q);
                    N = Math.round(N);
                    int n;
                    n = (int) N;
                    if (n < 100)
                        str += "  "+n+" |";
                    else str += " "+n+" |";
                    P += 0.01;
                }
                System.out.println(str);
                q += 0.005;
                str = "";
            }
            System.out.println("+-------+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+");

            for (int i = 1; i < 3; i++) {
                if (i == 2)
                    System.out.println("\nРезультаты поиска экстремума f(x)*sin(x) в зависимости от P и q");
                else System.out.println("\nРезультаты поиска экстремума f(x) в зависимости от P и q");
                System.out.println("+-------+---------+---------+---------+---------+-------" +
                        "--+---------+---------+---------+---------+---------+" +
                        "\n|  q/P  |   0.9   |   0.91  |   0.92  |   0.93  |   0.94  |   0.95  |   0.96 " +
                        " |   0.97  |   0.98  |   0.99  |");
                System.out.println("+-------+---------+---------+---------+---------+-------" +
                        "--+---------+---------+---------+---------+---------+");
                str = "";
                String s = "";

                for (double q = 0.005; q <= 0.1; ) {
                    System.out.print("|");
                    System.out.format("%6.3f", q);
                    System.out.print(" |");

                    for (double P = 0.9; P < 1; ) {
                        N = Math.log(1 - P) / Math.log(1 - q);
                        int n = (int) N;
                        MonteCarlo MC = new MonteCarlo();
                        s += MC.findMin(a, b, n, i);
                        str += " " + s.substring(0, 7) + " |";
                        s = "";
                        P += 0.01;
                    }
                    System.out.println(str);
                    q += 0.005;
                    str = "";
                }
                System.out.println("+-------+---------+---------+---------+---------+-------" +
                        "--+---------+---------+---------+---------+---------+");
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
