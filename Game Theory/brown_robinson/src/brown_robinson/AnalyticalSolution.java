package brown_robinson;

public class AnalyticalSolution {

    static public double [] strategyX (double [][] matrix){
        double c = 0;
        double [][] inv_matrix = inverts_matrix(matrix);
        double X [] = UC(inv_matrix);

        System.out.println("Обратная матрица:");
        printMass(inv_matrix);

        for (int i = 0; i < matrix.length; i++) c += X[i];

        for (int i = 0; i < matrix.length; i++) X[i] = X[i] / c;

        return X;
    }

    static public double [] strategyY (double [][] matrix){
        double c = 0;
        double [][] inv_matrix = inverts_matrix(matrix);
        double Y [] = UtC(inv_matrix);
        double X [] = UC(inv_matrix);

        for (int i = 0; i < matrix.length; i++) c += X[i];

        for (int i = 0; i < matrix.length; i++) Y[i] = Y[i] / c;

        return Y;
    }

    static public double price (double [][] matrix){
        double v = 0;
        double [][] inv_matrix = inverts_matrix(matrix);
        double V [] = UC(inv_matrix);

        for (int i = 0; i < matrix.length; i++) v += V[i];

        return (1/v);
    }

    static private double [] UC (double [][] matrix){
        double [] res = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                res[i] += matrix[j][i];
            }
        }

        return res;
    }

    static private double [] UtC (double [][] matrix){
        double [] res = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                res[i] += matrix[i][j];
            }
        }

        return res;
    }

    static private double [][] inverts_matrix(double [][] matrix){
        double [][] res_matrix = new double[matrix.length][matrix.length];
        double [][] ex_matrix = new double[matrix.length][matrix.length];
         double det = 0;

         for (int i = 0; i < matrix.length; i++) {
             double [][] minor = matrix_minor(matrix,i,0);
             det += sign(i,0) * determinant(minor) * matrix[i][0];

             for (int j = 0; j < matrix.length; j++){
                 minor = matrix_minor(matrix,i,j);

                 //рекурсивный метод возвращающий детерминант проверяющий
                 //если размер минора больше 2, возвращает себя, если равен 1, то d=minor
                 //на выходе текущий определитель (d)

                 ex_matrix[i][j] = sign(i,j) * determinant(minor); // составляем матрицу дополнений
             }
         }

        //System.out.println("det = "+det);
        //printMass(ex_matrix);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++)
                //транспонируем и умножаем на определитель
                res_matrix[j][i] = ex_matrix[i][j] * (1 / det);
        }

        //printMass(res_matrix);
         return res_matrix;
    }

    static private double [][] matrix_minor (double [][] matrix, int ii, int jj){
        double [][] minor = new double[matrix.length-1][];
        int m = 0, n = 0;

        //System.out.println(ii+" "+jj);
        for (int i = 0; i < matrix.length; i++) {

            if (i != ii) {

                double arr [] = new double[matrix.length-1];
                for (int j = 0; j < matrix.length; j++) {

                    if (j != jj) {
                        arr[n] = matrix[i][j];
                        n++;
                    }
                }

                minor[m] = arr;
                m++;
                n = 0;
            }
        }

        //printMass(minor);
        return minor;
    }

   static private double sign (int i, int j){
        double s = 0;
        if ((i+1)%2 == 0){
            if ((j+1)%2 == 0)
                s = 1;
            else s = -1;
        }
        else if ((i+1)%2 != 0){
            if ((j+1)%2 == 0)
                s = -1;
            else s = 1;
        }
        return s;
    }

    static private double determinant (double [][] matrix){
        //System.out.println(matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
        return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
    }

    static private void printMass (double [][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length;j++){
                System.out.format("%10.3f ",matrix[i][j]);
            }
            System.out.println();
        }
    }
}
