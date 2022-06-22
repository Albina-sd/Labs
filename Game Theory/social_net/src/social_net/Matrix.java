package social_net;

import java.util.Arrays;

public class Matrix {
    public double [][] generate_matrix (int size){
        double [][] mass = new double[size][size];
        double b = (double)1/size;  // максимальная степень доверия
        double a = (double)1/(size*5);  // минимальная степень доверия

        for (int i = 0; i < size; i++) {
            double sum = 0;

            for (int j = 0; j < size-1; j++) {
                mass[i][j] = a + (Math.random() * (b+a));
                sum += mass[i][j];
            }
            mass[i][size-1] = 1 - sum;
            //if (sum != 1) --i;
        }

        System.out.println("Trust matrix A:");
        print_mass(mass);
        return mass;
    }

    /*public boolean check_stochastic(double[][] mass){
        boolean cond = true;
        for (int i = 0; i < mass.length; i++) {
            double sum = 0;
            for (int j = 0; j < mass.length; j++)
                sum += mass[i][j];
            System.out.println(i+": "+Arrays.toString(mass)+" sum = "+sum);
        }

        if (cond) System.out.println("Matrix is stochastic");
        return cond;
    }*/

    public double [] agent_opinion(int size){
        int a = 1, b = 20; // диапазон случайных чисел

        System.out.println("\nVector of initial opinions of agents without influence:");
        System.out.print("x[0] = [");
        double [] arr = gen_rand(a,b,size);
        System.out.println("]");

        return arr;
    }

    private double [] gen_rand (int a, int b, int size){
        double [] arr = new double[size];

        for (int i = 0; i < size; i++){
            arr[i] = a + (int)(Math.random()*b);
            System.out.format("%2.0f ",arr[i]);
        }
        return arr;
    }

    public double [] final_opinion (double [][] matrix, double [] vector, boolean influence){
        double [] x = new double[vector.length];
        double x0[] = vector;
        double eps = 0.000001; // точность
        double delta = 20;
        int iter = 1;

        while(delta > eps) {  //(iter < 9){
            double max = 0, min = 100;
            System.out.format("x[%d] = [", iter);

            for (int i = 0; i < vector.length; i++) {
                double sum = 0;

                for (int j = 0; j < vector.length; j++)
                    sum += matrix[i][j] * x0[j];

                x[i] = sum;
                System.out.format("%6.3f ", x[i]);

               max = Math.max(max,x[i]);
               min = Math.min(min,x[i]);
            }
            System.out.println("]");
            delta = max - min;
            x0 = x;
             if (!influence) matrix = multmat(matrix);
            iter++;
        }

        if (!influence) {
            System.out.println("\nAfter interaction trust matrix:");
            print_mass(matrix);
        }
        return x;
    }

    private double [][] multmat (double [][] mass){
        double [][] n_mass = new double[mass.length][mass.length];

        for (int i = 0; i < mass.length; i++){
            for (int j = 0; j < mass.length; j++){
                n_mass[i][j] = 0;
                for (int k = 0; k < mass.length; k++)
                    n_mass[i][j] += mass[i][k] * mass[k][j];
            }
        }

        return n_mass;
    }

    public void print_mass(double[][] mass){
        for (int i = 0; i < mass.length; i++) {
            System.out.print("|");
            for (int j = 0; j < mass.length; j++)
                System.out.format("%5.3f ", mass[i][j]);
            System.out.println("|");
        }
    }
}
