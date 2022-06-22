package filtered_signal;

import java.util.Random;

public class Noise {

    //создаем зашумленный сигнал по равномерному закону ПСЧ
    public static double [] noise_signal(double [] signal){
        Random randomGen = new Random(System.currentTimeMillis());

        double n_signal [] = new double[signal.length];
        for (int i = 0; i < signal.length; i++){
            n_signal[i] = signal[i]+(randomGen.nextDouble())*0.5 - 0.2;
            System.out.format("%.3f ",n_signal[i]);
        }
        return n_signal;
    }

    //находим критерий зушумленности по формуле Чебышева
    public static double chebyshev_noisy (double [] filtered){
        double noisy = -100;

        for (int i = 1; i < filtered.length; i++){
            if (noisy < (filtered[i]- filtered[i-1]))
                noisy = filtered[i] - filtered[i-1];
        }

        return noisy;
    }

    //находим критерий отличия по формуле Чебышева
    public static double chebyshev_differences (double [] filtered, double [] noise){
        double dif = -100;

        for (int i = 0; i < filtered.length; i++){
            if (dif < (filtered[i] - noise[i]))
                dif = filtered[i] - noise[i];
        }

        return dif;
    }

    //Формула расстояния Чебышева
    public static double chebyshev_dist (double dif, double noisy){
        return Math.max(dif,noisy);
    }

    //значение линейной свертки критериев
    public static double conv (double dif, double noisy, double conv_weight){
        return (conv_weight*noisy+(1-conv_weight)*dif);
    }
}
