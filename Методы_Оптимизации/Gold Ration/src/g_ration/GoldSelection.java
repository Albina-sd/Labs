package g_ration;

public class GoldSelection {
    //задаем функцию, в которой надо найти минимум
    double f(double x){
        return Math.exp(-0.2*x) * Math.sin(x);
    }

    //значение фи, с помощью которого находим отрезки по золотому сечению
    final double PHI = (1 + Math.sqrt(5)) / 2;

    /*
    функция нахождения минимума алгоритмом золотое сечение
    a, b - начало и конец интервала поиска
    e - точность, с которой производится поиск
    х1 и х2 - осуществляют золотое сечение данного интервала
    n - считает количество итераций
     */
    double findMin(double a, double b, double e){
        System.out.println("   N  "+"   a   "+"     b   "+"     x1   "+"     x2   "+"  f(x1) "+"  f(x2) "+"  b - a");
        double x1, x2;
        int n = 1;

        while (true){
            x1 = b - (b - a) / PHI;  //нахождение первой внутренней точки
            x2 = a + (b - a) / PHI;  //нахождение второй внутренней точки

            System.out.format("%4d",n);
            System.out.format("%9.5f",a);
            System.out.format("%9.5f",b);
            System.out.format("%9.5f",x1);
            System.out.format("%9.5f",x2);
            System.out.format("%9.5f",f(x1));
            System.out.format("%9.5f",f(x2));
            System.out.format("%9.5f%n",(b-a));

            //если интервал неопределенности меньше погрешности, то выходим из цикла
            if (Math.abs(b - a) < e)
                break;

            //сравнение значений функции в найденных точках и процедура исключения отрезка
            if (f(x1) >= f(x2))
                a = x1;
            else
                b = x2;

            n++;
        }
        return (a + b) / 2;
    }
}
