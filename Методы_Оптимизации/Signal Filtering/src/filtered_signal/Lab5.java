package filtered_signal;

import javax.swing.*;

public class Lab5 {

    public static double signal (double x){
        return Math.sin(x)+0.5;
    }

    public static String [][] Data (int num_tests, int r, double [] y_noised, double [] x_mean, double [] y_orig){
        String result [][] = new String [12][6+r];
        double best_weights [] = new double[r];
        Noise N = new Noise();
        int k = 0, num_dist = 0;
        double best_dist = 1000;

        Filter filter = new Filter();

        for (double la = 0.0; la < 1.1;){
            double save_res = 100000;
            double [] save = new double[r+4];

            for (int n = 0; n < num_tests; n++) {
                double weights[] = filter.generate_random_weights(r);

                //фильтруем сигнал
                double y_filtered[] = filter.harmonic_mean(weights, y_noised, (r - 1) / 2);

                double dif = N.chebyshev_differences(y_filtered, y_noised);
                double noisy = N.chebyshev_noisy(y_filtered);
                double dist = N.chebyshev_dist(dif, noisy);
                double conv = N.conv(dif, noisy, la);

                if (save_res > conv){
                    save_res = conv;

                    for (int m = 0; m < weights.length; m++){
                        save[m] = weights[m];
                    }

                    save[r] = noisy;
                    save[r+1] = dif;
                    save[r+2] = conv;
                    save[r+3] = dist;
                    //System.out.println("dif: "+dif+"  noisy: "+noisy+ " dist: " + dist);
                }
            }

            if (best_dist > save[r+3]){
                best_dist = save[r+3];
                num_dist = k;

                for (int m = 0; m < r; m++){
                    best_weights[m] = save[m];
                }
            }

            //System.out.println();
            String str = String.valueOf(la);
            result [k][0] = str.substring(0,3);
            for (int m = 0; m < (4+r); m++){
                str = String.valueOf(save[m]);
                //System.out.print(save[m]+" ");
                result[k][m+1] = str.substring(0,6);
            }

            //System.out.println();
            la += 0.1;
            k++;
        }

        double [] y_filter = filter.harmonic_mean(best_weights, y_noised, (r - 1) / 2);
        SwingUtilities.invokeLater(() -> {
            ScatterPlot example = new ScatterPlot("Safina A.D. | lab 6 |  window = 3", x_mean, y_orig, y_noised, y_filter);
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });

        result[num_dist][5+r] = "  + ";

        return result;
    }

    public static void main(String[] args) {
        System.out.println("\nПрограмма стохастической фильтрации сигнала.\n");

        int M = 100; //максимальное количество испытаний
        int step = M + 1; //количество шагов
        double min = 0, max = 3.14; //границы интервала
        double length_interval = max - min; // длина интервала
        int num_tests = 939; //Число испытаний

        double x_mean [] = new double[step];
        double y_orig [] = new double[step];

        //получаем значения функции (нашего исходного сигнала)
        System.out.print("X:       ");
        x_mean[0] = min;
        y_orig[0] = signal(x_mean[0]);
        System.out.format("%.3f ",x_mean[0]);

        for (int i = 1; i < step; i++){
            x_mean[i] = x_mean[i - 1] + length_interval/M;
            y_orig[i] = signal(x_mean[i]);
            System.out.format("%.3f ",x_mean[i]);
        }

        System.out.print("\nY_orig:  ");
        for (int i = 0; i < step; i++)
            System.out.format("%.3f ",y_orig[i]);

        //создаем зашумленный сигнал
        System.out.print("\nY_noise: ");
        Noise N = new Noise();
        Filter filter = new Filter();
        double y_noised [] = N.noise_signal(y_orig);

        int r = 3; // размер окна
        String data [][] = Data(num_tests,r,y_noised,x_mean,y_orig);

        System.out.println("\n\nПри окне равном 3:");
        System.out.println("+-----+--------------------------+--------+--------+--------+--------+------+");
        System.out.println("|convW|           alpha          |   dif  |  noisy |  conv  |  dist  |  min |");
        System.out.println("+-----+--------------------------+--------+--------+--------+--------+------+");

        for (int i = 0; i < 12; i++){
            for (int j = 0; j < (6+r); j++){
                System.out.print("| "+data[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
            System.out.println("+-----+--------------------------+--------+--------+--------+--------+------+");
        }

        r = 5; // размер окна
        String data2 [][] = Data(num_tests,r,y_noised,x_mean,y_orig);

        System.out.println("\n\nПри окне равном 5:");
        System.out.println("+-----+--------------------------------------------+--------+--------+--------+--------+------+");
        System.out.println("|convW|                    alpha                   |   dif  |  noisy |  conv  |  dist  |  min |");
        System.out.println("+-----+--------------------------------------------+--------+--------+--------+--------+------+");

        for (int i = 0; i < 12; i++){
            for (int j = 0; j < (6+r); j++){
                System.out.print("| " + data2[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
            System.out.println("+-----+--------------------------------------------+--------+--------+--------+--------+------+");
        }

    }
}