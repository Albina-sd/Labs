package brown_robinson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.math.RoundingMode;

public class BrownRobinson {
    private double [][] price_m;
    private double [] A_win; // вектор выйгрышей
    private double [] B_lose; // вектор проигрышей

    public void BrownRobinson (double [][] matrix){
        ArrayList<Double> vHList = new ArrayList<>();
        ArrayList<Double> vLList = new ArrayList<>();
        ArrayList<Double> al = new ArrayList<>();
        price_m = matrix;
        int x = 0, y = 0; // выбранные стратегии игрока А и B
        A_win = NullArr(matrix.length);
        B_lose = NullArr(matrix[0].length);
        int k = 0;
        int [] strategyA = new int[matrix.length];
        int [] strategyB = new int[matrix[0].length];

        double V_low, V_high; //верхняя и нижняя цены игры
        double eps = 0.1, e; //точность

        System.out.println("\n\nБраун-Робинсон:\n");
        System.out.printf("%-5s%-5s%-7s%-7s%-7s%-7s%-7s%-7s%-8s%-8s%-8s%-5s%n",
                "k", "X", "Y", "   ", " A ", "  ", "   ", " B ", "  ", "V_high", "V_low", "eps");
        System.out.println("-----------------------------------------------------------" +
                "---------------------");

        do {
            k++;
            System.out.printf("%-5s%-5s%-5s",k,x+1,y+1);
            A_win = getCol(x);
            B_lose = getStr(y);
            strategyB[x] += 1;
            strategyA[y] += 1;

            for (int i = 0; i < A_win.length; i++) {
                al.add(A_win[i]);
                System.out.printf("%-7s",A_win[i]);
            }
            System.out.print(" ");

            V_high = Collections.max(al);
            y = al.indexOf(V_high);
            V_high = V_high / k;
            al.removeAll(al);

            for (int i = 0; i < B_lose.length; i++) {
                al.add(B_lose[i]);
                System.out.printf("%-7s",B_lose[i]);
            }
            System.out.print("   ");
            V_low = Collections.min(al);
            x = al.indexOf(Collections.min(al));
            V_low = V_low/k;
            al.removeAll(al);

            vHList.add(V_high);
            vLList.add(V_low);
            e = (Collections.min(vHList) - Collections.max(vLList));
            System.out.printf("%-7s%-7s%-7s%n", roundD(V_high), roundD(V_low), roundD(e));
        } while (e >= eps);

        System.out.println("\nЦена игры: "+roundD((V_high + V_low)/2));

        System.out.print("\nX:");
        for (int i = 0; i < strategyA.length; i++) {
            double j = strategyA[i];
            al.add(j/k);
        }
        System.out.println(al);
        System.out.format("Значит оптимальная стратегия: %d",(al.indexOf(Collections.max(al))+1));
        al.removeAll(al);

        System.out.print("\n\nY:");
        for (int i = 0; i < strategyB.length; i++) {
            double j = strategyB[i];
            al.add(j/k);
        }
        System.out.println(al);
        System.out.format("Значит оптимальная стратегия: %d",(al.indexOf(Collections.max(al))+1));
    }

    private double [] getCol (int k){
        double col [] = new double[price_m[0].length];

        for (int i = 0; i < price_m.length; i++) {
            col[i] = price_m[i][k] + A_win[i];
        }

        return col;
    }

    private double [] getStr (int k){
        double str [] = new double[price_m.length];

        for (int i = 0; i < price_m.length; i++) {
            str[i] = price_m[k][i] + B_lose[i];
        }

        return str;
    }

    public double roundD(double num){

        double newDouble = new BigDecimal(num).setScale(3, RoundingMode.UP).doubleValue();

        return newDouble;
    }

    private double[] NullArr (int len){
        double [] arr = new double[len];
        for (int i = 0; i < len; i++) arr[i] = 0;
        return arr;
    }
}
