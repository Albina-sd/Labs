package g_ration;

public class OptimalPassive {
    //задаем функцию, в которой надо найти минимум
    double f(double x){
        return Math.exp(-0.2*x) * Math.sin(x);
    }

    //функция, осуществляющая форматированный вывод текущих значений
    void print (int i, double a, double xMin, double function, double functionMin){
        System.out.format("%4d",i);
        System.out.format("%10.5f",a);
        System.out.format("%10.5f",xMin);
        System.out.format("%10.5f",function);
        System.out.format("%10.5f",functionMin);
        System.out.format("%10.5f",(function - functionMin));
    }

    /*
     функция нахождения минимума алгоритмом оптимальный пассивный поиск
    a, b - начало и конец интервала поиска
    N - количество точек среди которых будет осуществляться поиск минимума
    xMin - переменная, в которую записывается точка локального минимума
    function - переменная, в которую записывается текущее значение функции
    functionMin - переменная, в которую записывается значение локального минимума
    e - точность, с которой производится поиск
     */

    double findMin(double a, double b, double e){
        double N = (2*(b - a)/e) - 1;

        System.out.println("   N  "+"     x   "+"    Xmin  "+"    f(x) "+"    Fmin "+" Fmin - f(x)");

        double functionMin = f(a), function;
        double xMin = a;

        print(1, a, xMin, f(a), functionMin);
        a += e;

        for (int i = 2; i <= N; i++){
            function = f(a);
            print(i, a, xMin,function,functionMin);

            if (function < functionMin) {
                functionMin = function;
                xMin = a;
            }
            a += e;
        }
        return xMin;
    }
}
