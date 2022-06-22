package predict_time_range;

import javax.swing.*;

public class Lab3 {

    // задаем функцию и получаем массив значений для обучения
    private static double [] f (double a, double b, int n){
        double [] func = new double[n];
        double m = (b - a) / n;
        double j = a;

        for (int i = 0; i < n; i++){
            func[i] = Math.tan(j);
            j += m;
        }

        return func;
    }

    private static double [] scale(double a, double b, int N){
        double mass [] = new double[N];
        double n = (b - a) / N;

        mass[0] = a;
        for (int i = 1; i < N; i++)
            mass[i] = mass[i - 1] + n;

        return mass;
    }

    private static double rms_accuracy (double [] prediction, double [] target){
        double acc = 0;

        System.out.println();
        for (int i = 0; i < prediction.length; i++){
            //System.out.println(target[i] - prediction[i]);
            acc += Math.pow((target[i] - prediction[i]),2);
        }

        return Math.sqrt(acc);
    }

    public static void main(String[] args) {
        int a = 2, b = 3; // начало и конец отрезка
        int N = 20; // количество точек
        int p = 13; // длина окна
        double train_set [] = f(a,b,N);
        // начало и конец отрезка проверки(должен следовать сразу после отрезка обучения)
        double n_a = b, n_b = 2*b-a;
        double f = (n_b - n_a)/N;
        double d = (n_b - n_a)/f;
        int num_predict = (int)d;

        // обучаем НС и получаем массив предсказаний
        Neuron neuron = new Neuron();
        double [] predict = neuron.Neuron(train_set,p,num_predict);
        double func [] = f(n_a-((n_b-n_a)/d)*p,n_b,N+p);
        System.out.format(" RMS accuracy: %6.4f ",rms_accuracy(predict,func));

        // строим график прогнозирования
        /*SwingUtilities.invokeLater(() -> {
            ScatterPlot example = new ScatterPlot("Safina A.D. | lab 2",
                    scale(n_a-((n_b-n_a)/N)*p,n_b,N+p), func, predict,
                    "Original function", "Predicted function");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });*/
    }
}
