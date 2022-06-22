package m_carlo;

import java.awt.*;
import java.applet.*;
import java.util.Random;

public class MonteCarlo {
    //задаем функцию, в которой надо найти минимум
    double f1(double x){
        return Math.exp(-0.2*x) * Math.sin(x);
    }
    double f2(double x){
        return Math.exp(-0.2*x) * Math.sin(x)* Math.sin(5 * x);
    }
    /*
    функция нахождения минимума методом случайного поиска
    a, b - начало и конец интервала поиска
    N - количество испытаний, которое расчитывается в основном классе
    х - случайное число в интервале от a до b
    xMin - значение х, при котором значение функции минимально среди всех остальных найденных случайных значений
    n - считает количество итераций
    k - необходимо для выбора между унимодальной либо мультимодальной функций
     */
    double findMin(double a, double b, int N, int k){
        int n = 0;
        double x, xMin, min = 0;
        Random randomGen = new Random(System.currentTimeMillis());
        xMin = (randomGen.nextDouble()) * ((b - a) + 1) + a;

        while (true){
            x = (randomGen.nextDouble()) * ((b - a) + 1) + a;

            if (k == 1){
                if (f1(xMin) >= f1(x))
                    xMin = x;
                min = f1(xMin);
            }
            else if (k == 2){
                if (f2(xMin) >= f2(x))
                    xMin = x;
                min = f2(xMin);
            }

            if (n >= N - 1)
                break;
            n++;
        }
        return min;
    }
}
