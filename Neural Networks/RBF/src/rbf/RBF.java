package rbf;

public class RBF {
    private double norma = 0.3; // норма обучения
    private double weights []; // весовые коэффициенты
    private double eps = 0.001; // точность
    private int J = 10; // количество нейронов
    private double sigma = 0.9; // ширина RBF
    private double K = 4000; // количество эпох
    private double [] centers;
    private double [] new_data;
    private double rms;

    public RBF(double train_set [], double a, double b, double t []){
        algorithm(train_set, a, b, t);
        new_data = predict(t);
    }

    private void algorithm(double data [], double a, double b, double t []){
        centers = Set_c(a, b);
        weights = SetWeights();
        double Error;
        int era = 0;

        System.out.println("\n  k                                 w                                          E");

        do{
            Error = 0;
            this.rms = 0;

            for (int i = 0; i < data.length; i++){
                double y = net(centers,t[i]);
                double er = data[i] - y; //высчитываем ошибку

                //System.out.format("data = %6.4f  y = %6.4f\n",data[i],y);

                if (Math.abs(er) > eps){
                    // коррекция весов
                    //System.out.format("w01 = %6.4f",weights[0]);
                    weights = correction_weights(er,t[i],this.weights);
                    //System.out.format(" w02 = %6.4f\n",weights[0]);
                    Error++;
                    this.rms += Math.pow((data[i] - y), 2);
                }
            }

            //print(era, this.weights, Math.sqrt(this.rms));
            era++;

        } while ((Error/data.length) > eps);  // (era <= K)

        print(--era, this.weights, Math.sqrt(this.rms));
    }

    private double[] correction_weights (double er, double t, double weights []){
        double n_weights [] = weights;

        for (int i = 0; i < n_weights.length; i++)
            n_weights[i] += GaussRBF(this.centers[i],t) * this.norma * er;

        return (n_weights);
    }

    private double net (double centers [], double t){
        double net = 0;
        for (int j = 0; j < this.J; j++){
            net += this.weights[j] * GaussRBF(centers[j],t);
        }

        return (net);
    }

    private double [] Set_c(double a, double b){
        double c [] = new double[J];

        for (int j = 0; j < this.J; j++)
            c[j] = (a + j*(b-a))/(this.J + 1);

        return c;
    }

    private double GaussRBF (double c, double t){
        return Math.exp(-Math.pow((c - t),2)/(2*Math.pow(this.sigma,2)));
    }

    private double[] SetWeights (){
        double new_weights [] = new double[this.J];

        for (int i = 0; i < new_weights.length; i++)
            new_weights[i] = 0;
            //new_weights[i] = Math.random()*(0.8-0.3)+0.3;

        return new_weights;
    }

    private void print (int k, double [] weights, double SumError){
        System.out.format("%5d ",k);
        for(int i = 0; i < weights.length; i++) System.out.format("%6.3f ",weights[i]);
        System.out.format("  %6.4f ",SumError);
        System.out.println();
    }

    private double[] predict (double t []){
        double m [] = new double[t.length];
        for (int i = 0; i < t.length; i++){
            m[i] = net(this.centers,t[i]);
        }
        return m;
    }

    public double [] PredictRes() {return this.new_data;}

    public double RMS_value() {return Math.sqrt(this.rms);}
}
