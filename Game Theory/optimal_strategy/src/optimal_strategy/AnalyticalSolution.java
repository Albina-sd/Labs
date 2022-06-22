package optimal_strategy;

import java.util.Arrays;

public class AnalyticalSolution {
    private double [] strA;
    private double [] strB;
    private double v1;
    private double v2;

    public AnalyticalSolution(double [][] A, double [][] B){
        strA = StrategyA(A);
        strB = StrategyB(B);
        v1 = price(A);
        v2 = price(B);
    }

    public double [] X (){ return this.strA;}
    public double [] Y (){ return this.strB;}
    public double V1 (){ return this.v1;}
    public double V2 (){ return this.v2;}

    private double [] StrategyA (double A [][]){
        double c = 0;
        double [][] invA = inverse_matrix(A);
        double X [] = UM(invA);

        for (int i = 0; i < A.length; i++) c += X[i];

        for (int i = 0; i < A.length; i++) X[i] = X[i] / c;

        return X;
    }

    private double [] StrategyB (double [][] B){
        double c = 0;
        double [][] inv_matrix = inverse_matrix(B);
        double Y [] = UtM(inv_matrix);
        double X [] = UM(inv_matrix);

        for (int i = 0; i < B.length; i++) c += X[i];

        for (int i = 0; i < B.length; i++) Y[i] = Y[i] / c;

        return Y;
    }

    private double price (double [][] matrix){
        double v = 0;
        double [][] inv_matrix = inverse_matrix(matrix);
        double V [] = UM(inv_matrix);

        for (int i = 0; i < matrix.length; i++) v += V[i];

        return (1/v);
    }

    private double [][] inverse_matrix (double [][] M){
        double [][] result = new double[M.length][M.length];
        double det = determinant(M);
        if (det == 0) {
            result = null;
            System.out.println("No inverse matrix");
        }
        else {
            for (int i = 0; i < M.length; i++){
                for (int j = 0; j < M.length; j++)
                    result[i][j] = additional(i,j,M) * Math.pow(-1,(i+j+2)) * (1/det);
                //System.out.println(Arrays.toString(result[i]));
            }
        }
        return result;
    }

    private double additional (int I, int J, double [][] M){
        if (J == 0) J = 1;
        else J = 0;

        if (I == 0) I = 1;
        else I = 0;

        return M[I][J];
    }

    private double determinant (double [][] matrix){
        //System.out.println("det = "+(matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]));
        return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
    }

    private double [] UM (double [][] matrix){
        double [] res = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++)
                res[i] += matrix[j][i];
        }

        return res;
    }

    private double [] UtM (double [][] matrix){
        double [] res = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++)
                res[i] += matrix[i][j];
        }

        return res;
    }
}