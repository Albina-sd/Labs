package predict_time_range;

public class Neuron {

    private double [] prediction (double [] weights, int len, double data []){
        double predict [] = new double[len];
        int p = weights.length - 1;

        for (int i = 0; i < len; i++){
            if (i < p)
                predict[i] = data[i];
            else{
                predict[i] = net(weights,data);
                data = windowSet(predict,i+1,p);
            }
        }

        return predict;
    }

    private double[] SetWeights (int len){
        double new_weights [] = new double[len];

        for (int i = 0; i < len; i++)
            new_weights[i] = Math.random()*(0.8-0.3)+0.3;

        return new_weights;
    }

    private double [] windowSet(double [] data, int time, int p){
        double mass [] = new double[p];

        for (int i = 0; i < p; i++)
            mass[i] = data[time - p + i];

        return mass;
    }

    private double net (double [] weights, double [] data){
        double net = weights[0];

        for (int i = 1; i < weights.length; i++)
            net += weights[i] * data[i - 1];

        return net;
    }

    private double [] correction_weights(double[] data, double er, double norm, double [] weights){
        double w [] = new double[weights.length];

        w[0] = weights[0] + norm * er;

        for (int i = 1; i < weights.length; i++)
            w[i] = weights[i] + data[i - 1] * norm * er;

        return w;
    }

    public double [] Neuron(double [] data, int p, int num_predict){
        double norma = 0.1; // норма обучения
        double weights [] = SetWeights(p+1); // задаем весам случайное значение
        int slide = 1; // величина сдвига
        double Error; // количество ошибок в каждой эпохе
        double rms; // среднеквадратическая ошибка
        double eps = 0.0001; // точность
        double [] new_data;
        int era = 0;

        System.out.println("\n    k                           w                           E       RMS");
        do {
            Error = 0;
            rms = 0;
            for (int i = p; i < data.length;){
                new_data = windowSet(data,i,p);
                double x = net(weights, new_data);
                double er = data[i] - x; //высчитываем ошибку

                if (Math.abs(er) > eps) {
                    // коррекция весов
                    weights = correction_weights(new_data,er,norma,weights);
                    rms += Math.pow(er, 2);
                    Error++;
                }
                i += slide;
            }

            rms = Math.sqrt((rms));
            Error = Error/(data.length - p);
            print(era, weights, Error, rms);
            era++;
        } while ((Error > 0.9)||(rms > 0.002));//while (era < 4000);

        print(era, weights, Error, rms);
        new_data = windowSet(data,data.length,p);

        double [] predict = prediction(weights,num_predict+p,new_data);
        return predict;
    }

    public void print (int k, double [] weights, double SumError, double rms){
        System.out.format("%5d ",k);
        for(int i = 0; i < weights.length; i++) System.out.format("%6.3f ",weights[i]);
        System.out.format("  %6.4f ",SumError);
        System.out.format("  %6.4f ",rms);
        System.out.println();
    }
}