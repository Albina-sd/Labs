package antagonistic_game;

import java.util.ArrayList;
import java.util.Collections;

public class BrownRobinson {
    private double [][] price_m;
    private double [] A_win; // вектор выигрышей
    private double [] B_lose; // вектор проигрышей
    private double [] X; //стратегия Х
    private double [] Y; //стратегия Y
    private double price;

    public void BrownRobinson (double [][] matrix){
        ArrayList<Double> vHList = new ArrayList<>();
        ArrayList<Double> vLList = new ArrayList<>();
        ArrayList<Double> al = new ArrayList<>();
        price_m = matrix;
        int x = 0, y = 0; // выбранные стратегии игрока А и B
        A_win = NullArr(matrix.length);
        B_lose = NullArr(matrix[0].length);
        X = NullArr(matrix.length);
        Y = NullArr(matrix[0].length);
        int k = 0;
        int [] strategyA = new int[matrix.length];
        int [] strategyB = new int[matrix[0].length];

        double V_low, V_high; //верхняя и нижняя цены игры
        double eps = 0.1, e; //точность

        /*System.out.printf("%-5s%-5s%-7s%-7s%-7s%-7s%-7s%-7s%-11s%-8s%-8s%-5s%n",
                "k", "X", "Y", "   ", "   A ", "  ", "   ", "     B ", "  ", "    V_high", "  V_low", "  eps");
        System.out.println("-------------------------------------------------------------------" +
                "--------------------");*/

        do {
            k++;
            //System.out.printf("%-5s%-5s%-5s",k,x+1,y+1);
            A_win = getCol(x);
            B_lose = getStr(y);
            strategyB[x] += 1;
            strategyA[y] += 1;

            for (int i = 0; i < A_win.length; i++) {
                al.add(A_win[i]);
                //System.out.printf("%7.3f ",A_win[i]);
            }
            //System.out.print(" ");

            V_high = Collections.max(al);
            y = al.indexOf(V_high);
            V_high = V_high / k;
            al.removeAll(al);

            for (int i = 0; i < B_lose.length; i++) {
                al.add(B_lose[i]);
                //System.out.printf("%7.3f ",B_lose[i]);
            }
            //System.out.print("   ");
            V_low = Collections.min(al);
            x = al.indexOf(Collections.min(al));
            V_low = V_low/k;
            al.removeAll(al);

            vHList.add(V_high);
            vLList.add(V_low);
            e = (Collections.min(vHList) - Collections.max(vLList));
            //System.out.printf("%6.3f %6.3f %6.3f \n", V_high, V_low, e);
        } while (e >= eps);

        price = (V_high + V_low)/2;

        for (int i = 0; i < strategyA.length; i++) {
            double j = strategyA[i];
            X[i] = j/k;
        }

        for (int i = 0; i < strategyB.length; i++) {
            double j = strategyB[i];
            Y[i] = j/k;
        }
    }

    public double [] getX(){ return (this.X); }
    public double [] getY(){ return (this.Y); }
    public double getH() {return (this.price);}

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

    private double[] NullArr (int len){
        double [] arr = new double[len];
        for (int i = 0; i < len; i++) arr[i] = 0;
        return arr;
    }
}