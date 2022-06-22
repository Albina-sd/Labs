package antagonistic_game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class NumericalSolution {
    private double x;
    private double y;
    private double Amaxmin;
    private double Bminmax;
    private double [][] arr;

    public NumericalSolution(double a, double b, double c, double d, double e){
        int N = 30; // шаг сетки
        double H;
        ArrayList<Double> stop_numerical = new ArrayList<>();

        for (int i = 2; i < N; i++) {

            double[][] mass = net(i, a, b, c, d, e);
            MaxCol(mass);
            if (SPoint() == 0) {
                System.out.println("\nСедловая точка есть");
                System.out.format("x = %5.3f y = %5.3f\n", this.x / N, this.y / N);
                H = kernel(a, b, c, e, d, this.x / N, this.y / N);
                System.out.format("H = %5.3f", H);
                stop_numerical.add(H);
            } else {
                System.out.println("\nСедловой точки нет, решение методом Брауна-Робинсон:");
                BrownRobinson br = new BrownRobinson();
                br.BrownRobinson(this.arr);
                H = br.getH();
                finding_results(br.getX(), br.getY(), H);
                stop_numerical.add(H);
            }

            if(i > 7)
            {
                if((Math.abs(stop_numerical.get(i-4)-stop_numerical.get(i-2))<0.01)
                        &&(Math.abs(stop_numerical.get(i-4)-stop_numerical.get(i-3))<0.01)
                        &&(Math.abs(stop_numerical.get(i-3)-stop_numerical.get(i-2))<0.01))
                    break;
            }
        }
    }

    private void finding_results(double [] x, double [] y, double h){
        double x_val = 0, y_val = 0;

        for (int i = 0; i < x.length; i++){
            x_val += i*x[i]/x.length;
            y_val += i*y[i]/x.length;
        }
        System.out.format("x = %5.3f y = %5.3f H(x,y) = %5.3f",x_val,y_val,h);
    }

    private double [][] net(int N, double a, double b, double c, double d, double e){
        arr = new double[N+1][N+1];
        double [][] arr2 = new double[N+1][N+1];
        ArrayList<Double> list = new ArrayList<>();
        ArrayList<Double> MaxMin = new ArrayList<>();
        double x1, y1;

        System.out.println("\n\nN = "+N);
        for (int i = 0; i < arr.length; i++){

            x1 = (double) i/N;
            for (int j = 0; j < arr[i].length; j++){
                y1 = (double) j/N;
                arr[i][j] = kernel(a,b,c,e,d,x1,y1);
                arr2[j][i] = arr[i][j];
                //System.out.format("%6.3f ",arr[i][j]);
                list.add(arr[i][j]);
            }
            //System.out.println();
            MaxMin.add(Collections.min(list));
            list.clear();
        }

        Amaxmin = Collections.max(MaxMin);
        x = MaxMin.indexOf(Amaxmin);
        return arr2;
    }

    private int SPoint(){
        System.out.format("\nmaxmin = %5.3f minimax = %5.3f\n",this.Amaxmin,this.Bminmax);
        if (Math.abs(this.Amaxmin - this.Bminmax)<0.000001)
            return 0;
        else return -1;
    }

    private void MaxCol (double [][]arr){
        ArrayList<Double> col = new ArrayList<>();

        for (int j = 0; j < arr[0].length; j++){
            var num = arr[j];
            var max = Collections.max(Arrays.stream(num).boxed().collect(Collectors.toList()));
            col.add(max);
        }

        Bminmax = Collections.min(col);
        y = col.indexOf(Bminmax);
    }

    private double kernel(double a, double b, double c, double e, double d,double x,double y){
        return ((a*Math.pow(x,2)) + (b*Math.pow(y,2)) + (c*x*y) + (d*x) + (e*y));
    }
}
