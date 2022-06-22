package optimization;

import org.apache.commons.math3.util.Precision;

public class Optimize {

    //функция выполняющая нормировку вектора весов
    static double [] normalize_vector (double [] v1){
        double v2 [] = new double[v1.length];
        int sum = 0;

         for (int i = 0; i < v1.length; i++) sum += v1[i];
         for (int i = 0; i < v1.length; i++) v2[i] = v1[i]/sum;

        return  v2;
    }

    public String criteria (int k){
        String str="";
        if(k == 0) str = "A";
        if(k == 1) str = "B";
        if(k == 2) str = "C";
        if(k == 3) str = "D";
        return str;
    }

    //Метод замены критериев ограничениями
    void limit_method (double A [][], int m_сrit, double lim1, double lim2, double lim3){
        System.out.println("Проведём нормировку матрицы:");

        double [] Amax = {0, 0, 0, 0};
        double [] Amin = {10, 10, 10, 10};
        int k = 0; //номер строки с лучшим решением

        //находим минимальные и максимальные значения во всех столбцах
        // кроме столбца главного критерия
        for (int j = 0; j < A.length; j++){
            for (int i = 0; i < A.length; i++){
                if (i != m_сrit){
                if (Amax[i] < A[j][i])
                    Amax[i] = A[j][i];
                if (Amin[i] > A[j][i])
                    Amin[i] = A[j][i];
                }
            }
        }

        //выполняем нормировку матрицы по столбцам кроме столбца главного критерия
        for (int i = 0; i < A.length; i++){
            for (int j = 0; j < A.length; j++) {
                if (j != m_сrit)
                    A[i][j] = (A[i][j] - Amin[j]) / (Amax[j] - Amin[j]);

                String str = String.valueOf(A[i][j]);
                if (str.endsWith("0")) str = " "+str.substring(0,1)+" ";
                else str = str.substring(0,3);
                System.out.print(str + " ");
            }
            System.out.println();
        }

        System.out.println("\nУстановим минимально допустимые уровни для остальных критериев:");
        System.out.println("Легкость обработки не менее " +lim1+ "*Amax2 (критерий 2)");
        System.out.println("Долговечность не менее " +lim2+ "*Amax3 (критерий 3)");
        System.out.println("Водостойкость не менее " + lim3+ "*Amax4 (критерий 4)");

        //находим лучшее решение
        String l = "";
        for (int i = 0; i < A.length; i++) {
            if ((A[i][1] > 0)&&(A[i][2] > 0)&&(A[i][3] > 0))
                l += i;
        }

        for (int i = 1; i < l.length(); i++) {
            int g =  Integer.parseInt(l.substring(i-1,i));
            int m = Integer.parseInt(l.substring(i,i+1));
            if (A[g][0] > A[m][0])
                k = g;
        }

        System.out.println("Лучший вариант: "+ criteria(k));
    }

    void Pareto(double [][] A){
        System.out.println("Точка утопии:"+"\n10 10\n");
        double dist1 = 100, dist2;
        int s = -1;

        for (int i = 0; i < A.length; i++){
            //находим Евклидово расстояние между текущим критерием и точкой утопии
            dist2 = Math.sqrt(Math.pow((10 - A[i][0]),2)+Math.pow((10 - A[i][1]),2));
            String str="";
            if(i == 0) str = "A";
            if(i == 1) str = "B";
            if(i == 2) str = "C";
            if(i == 3) str = "D";
            System.out.println("Расстояние для "+str+" варианта: "+Precision.round(dist2,3));

            //находим минимальное расстояние
            if (dist1 > dist2) {
                dist1 = dist2;
                s = i;
            }
        }

        System.out.println("Евклидово расстояние до точки утопии минимально для: "+ criteria(s));
    }

    void WeightingCombining_criteria(double [][] A, double vect[]){
        System.out.println("Нормализуем матрицу:");
        double sum = 0;
        double [][] new_A = new double[A.length][A.length];
        double comb_crit [] = new double[A.length]; //значения объединенного критерия
        int k = -1; //номер лучшего критерия

        //нормализация матрицы по столбцам
        for (int i = 0; i < A.length; i++){
            for (int j = 0; j < A.length; j++) sum += A[j][i];
            for (int j = 0; j < A.length; j++){
                new_A [j][i] = A[j][i]/sum;
            }
            sum = 0;
        }

        for (int i = 0; i < A.length; i++){
            for (int j = 0; j < A.length; j++){
                System.out.format("%6.2f ",new_A[i][j]);
                comb_crit[i] += new_A[i][j]*vect[j]; //формируем объединенный критерий
            }
            System.out.println();
        }

        System.out.println("\nЗначения объединенного критерия для всех альтернатив:");
        sum = 0;
        for (int i = 0; i < A.length; i++){
            System.out.format("%6.2f\n", comb_crit[i]);
            if (sum < comb_crit[i]){ //находим наилучший вариант
                sum = comb_crit[i];
                k = i;
            }
        }

        System.out.println("Наиболее приемлемой является альтернатива - "+criteria(k)+".");
    }

    void analysis_hierarchy (double A [][], double vector_crit []){
        double optional [] = new double[A.length]; //сумма нормированных критериев по каждой альтернативе
        double vect [] = new double[A.length];
        double sum_col [] = new double[A.length];
        double m_prior [] [] = new double[A.length][A.length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                double comp, delta;
                if (j == i) comp = 1;
                else {
                    delta = vector_crit[i] - vector_crit[j];
                    comp = delta;
                    if (delta == 1)  comp = 2 * delta;
                    if (delta < -1) comp = 1 / -delta;
                    if (delta == -1) comp = 1 /(2 * -delta);
                }

                m_prior[i][j] = comp;
                vect[i] += comp;
            }
        }
        double [] norm_vect = normalize_vector(vect); //нормализуем сумму по строке

        for (int k = 0; k < A.length; k++) {
            double new_A [][] = new double[A.length][A.length];
            double comp, delta;
            double vector [] = new double[A.length];
            double sum_c [] = new double[A.length];

            System.out.println("Для критерия " + criteria(k) +" составим" +
                    " и нормализуем матрицу попарного сравнения альтернатив:");

            //составим матрицу попарного сравнения
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A.length; j++) {
                    if (j == i) comp = 1;
                    else {
                        delta = A[i][k] - A[j][k];
                        comp = delta;
                        if (delta == 1)  comp = 2 * delta;
                        if (delta < -1) comp = 1 / -delta;
                        if (delta == -1) comp = 1 /(2 * -delta);
                    }

                    new_A[i][j] = comp;
                    vector[i] += comp;
                }
            }
            double [] norm_vector = normalize_vector(vector); //нормализуем сумму по строке

            System.out.println("     A      B      C      D    Сумма      Нормированная" +
                    "\n                               по строке  сумма по строке");
            for (int i = 0; i < A.length; i++) {
                if(i == 0) System.out.print("A ");
                if(i == 1) System.out.print("B ");
                if(i == 2) System.out.print("C ");
                if(i == 3) System.out.print("D ");
                for (int j = 0; j < A.length; j++) {
                    System.out.format("%6.3f ",new_A[i][j]);
                    sum_c[i] += new_A[j][i];
                }
                System.out.format("%7.3f    %6.3f", vector[i], norm_vector[i]);
                System.out.println();
            }

            double self_value = 0;
            for (int i = 0; i < A.length; i++) {
                self_value += sum_c[i] * norm_vector[i];
                optional[i] += norm_vector[i]*norm_vect[k];
            }
            System.out.format("self_value = %6.3f", self_value);
            //0.9 - Значение показателя случайной согласованности (ПСС)
            System.out.format("\nConsistency Relationship - %6.3f",((self_value-4)/3)/0.9);
            System.out.println("\n");
        }

        System.out.println("Оценка приоритетов критериев");

        System.out.println("     1      2      3      4    Сумма      Нормированная" +
                "\n                               по строке  сумма по строке");
        for (int i = 0; i < A.length; i++) {
            System.out.print((i+1)+" ");
            for (int j = 0; j < A.length; j++) {
                System.out.format("%6.3f ",m_prior[i][j]);
                sum_col[i] += m_prior[j][i];
            }
            System.out.format("%7.3f    %6.3f", vect[i],norm_vect[i]);
            System.out.println();
        }

        double self_value = 0;
        for (int i = 0; i < A.length; i++) {
            self_value += sum_col[i] * norm_vect[i];
        }
        System.out.format("self_value = %6.3f",self_value);
        System.out.format("\nConsistency Relationship - %6.3f",((self_value-4)/3)/0.9);

        System.out.println("\n\nПосле перемножения матрицы (i – альтернатива, j - критерий) " +
                "\nна столбец оценки приоритетов получим:");
        int k = -1;
        double m = 0;
        for (int i = 0; i < A.length; i++) {
            System.out.format("%6.3f\n", optional[i]);
            if (m < optional[i]){
                k = i;
                m = optional[i];
            }
        }

        System.out.println("Оптимальным вариантом является альтернатива "+criteria(k)+".");
    }
}