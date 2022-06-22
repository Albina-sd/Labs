package filtered_signal;

import java.util.Random;

public class Filter {

    //находим весовые коффициенты
    public static double [] generate_random_weights (int r){
        Random randomGen = new Random(System.currentTimeMillis());
        double weights [] = new double[r];
        int m = (r - 1)/2;
        double k;

        //центральный элемент
        weights[m] = (Math.random())* 0.2 + 0.3;
        k = weights[m];

        for (int i = 1; i < m; i++) {
            weights[m + i] = (Math.random()) * 0.2;
            weights[m - i] = weights[m + i];
            k += weights[m + i] * 2;
        }

        //крайние элементы
        weights[r-1] = (1 - k) / 2;
        weights[0] =  (1 - k) / 2;

        return weights;
    }

    //фильтруем сигнал по формуле среднего гармонического
    public static double[] harmonic_mean(double weights[], double signal [], int m){
        double [] new_signal = new double[signal.length];

        new_signal[0] = signal[0];
        for (int i = m; i < signal.length - m; i++){
            int a = i - m;
            for (int j = 0; j < weights.length; j++){
                new_signal[i] += weights[j]/signal[a];
                a++;
            }
            new_signal[i] = 1/new_signal[i];
        }

        return new_signal;
    }

}
