package oneLayer;

import javax.swing.*;
import java.util.ArrayList;

public class Neuron {
    //сигмоидальная функция активации
    static int activation_sigm (double net){
        double out = 1/(1+Math.exp(-net));
        if (out >= 0.5)
            return 1;
        else return 0;
    }

    //пороговая функция активации
    static int activation_threshold (double net){
        if (net >= 0)
            return 1;
        else return 0;
    }

    static double [] fill_array (double weight0, double weight1, double weight2, double weight3, double weight4){
        double [] arr = new double[5];
        arr[0] = weight0;
        arr[1] = weight1;
        arr[2] = weight2;
        arr[3] = weight3;
        arr[4] = weight4;
        return arr;
    }

    static public void print (int k, double [] weights, int SumError, int [] prediction){
        System.out.format("%2d ",k);
        for(int i = 0; i < weights.length; i++) System.out.format("%4.1f ",weights[i]);
        System.out.format("%2d  ",SumError);
        for(int i = 0; i < prediction.length; i++) System.out.print(prediction[i] + " ");
        System.out.println();
    }

    static public int prediction (int [][] set, double [] weights,int j){
        int res = 0;
        System.out.println("Prediction:");
        for (int i = 0; i < set.length; i++){
            double net = weights[1] * set[i][0] + weights[2] * set[i][1]
                    + weights[3] * set[i][2] + weights[4] * set[i][3] + weights[0];
            if (j == 0) {
                System.out.print(activation_threshold(net)+" ");
                if (activation_threshold(net) == set[i][4]) res++;
            }
            else {
                System.out.print(activation_sigm(net) + " ");
                if (activation_sigm(net) == set[i][4]) res++;
            }
        }
        System.out.println();

        if (res != set.length) res = 0;
        else res = 1;
        return res;
    }

    static public void learn (int [][] origSet, int [][] set, int  size){
        for (int j = 0; j < 1; j++) {
            int SumError; // суммарная ошибка
            // задаем веса случайным образом
            double weight1 = Math.random()*(0.8-0.3)+0.3, weight2 = Math.random()*(0.8-0.3)+0.3,
                    weight3 = Math.random()*(0.8-0.3)+0.3, weight4 = Math.random()*(0.8-0.3)+0.3,
                    weight0 = Math.random()*(0.8-0.3)+0.3;
            //double weight0 = 0.609, weight1 = 0.584, weight2 = 0.634, weight3 = 0.578, weight4 = 0.405;
            double nLearn = 0.3; // норма обучения
            double net = 0; // сетевой вход
            int out  = 0, error = 0, k = 0;
            double [] weights = new double[5];
            int [] resPredict = new int[set.length];

            ArrayList <Integer> SE = new ArrayList<>();

            if (j == 0) System.out.println("\nFunction activation threshold:");
            else System.out.println("\nFunction activation sigmoid:");
            System.out.println(" k   w0   w1   w2   w3   w4   E  prediction");

            do {
                SumError = 0;

                weights = fill_array(weight0,weight1,weight2,weight3,weight4);

                for (int i = 0; i < size; i++) {
                    net = weight1 * set[i][0] + weight2 * set[i][1]
                            + weight3 * set[i][2] + weight4 * set[i][3] + weight0;

                    if (j == 0) out = activation_threshold(net);
                    else out = activation_sigm(net);

                    resPredict[i] = out;

                    if (out != set[i][4]) {
                        error = set[i][4] - out;
                        weight1 = weight1 + nLearn * error * set[i][0];
                        weight2 = weight2 + nLearn * error * set[i][1];
                        weight3 = weight3 + nLearn * error * set[i][2];
                        weight4 = weight4 + nLearn * error * set[i][3];
                        weight0 = weight0 + nLearn * error;
                        SumError++;
                    }
                }

                print(k + 1, weights, SumError, resPredict);

                SE.add(SumError);
                k++;
            } while (SumError != 0);

            int SErr[] = new int[SE.size()];
            k = 0;
            for (int i : SE) {
                SErr[k] = i;
                k++;
            }

            prediction(origSet, weights, j);

            String func;
            if (j == 0) func = "activation threshold";
            else func = "activation sigmoid";

            SwingUtilities.invokeLater(() -> {
                ScatterPlot example = new ScatterPlot("Safina A.D. | lab 1", SErr, func);
                example.setSize(800, 400);
                example.setLocationRelativeTo(null);
                example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                example.setVisible(true);
            });
        }
    }
}