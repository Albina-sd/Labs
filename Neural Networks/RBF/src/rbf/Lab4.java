package rbf;

import javax.swing.*;

public class Lab4 {
    public static double t [];

    // задаем функцию и получаем массив значений для обучения
    private static double [] f (double a, double b, int n){
        double [] func = new double[n];
        double m = (b - a) / n;
        double j = a;
        double x [] = new double[n];

        for (int i = 0; i < n; i++){
            x[i] = j;
            //func[i] = Math.exp(i - 2) + Math.cos(2*i);
            func[i] = Math.tan(j);
            j += m;
        }

        t = x;
        return func;
    }

    public static void main(String[] args) {
        int a = 2, b = 3; // начало и конец отрезка
        //int a = -1, b = 2;
        int N = 20; // размер обучающей выборки

        double train_set [] = f(a,b,N); // обучающий набор данных

        RBF rbf = new RBF(train_set, a, b, t);
        double predicted_set [] = rbf.PredictRes();

        /*for (int i = 0; i < t.length; i++)
            System.out.format("%6.4f ",t[i]);
        System.out.println();
        for (int i = 0; i < t.length; i++)
            System.out.format("%6.4f ",train_set[i]);
        System.out.println();
        for (int i = 0; i < t.length; i++)
            System.out.format("%6.4f ",predicted_set[i]);*/

        System.out.format("\nRMS = %6.4f",rbf.RMS_value());

        // строим график прогнозирования
        SwingUtilities.invokeLater(() -> {
            ScatterPlot example = new ScatterPlot("Safina A.D. | lab 2",
                    t, train_set, predicted_set,
                    "Original function", "Predicted function");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
