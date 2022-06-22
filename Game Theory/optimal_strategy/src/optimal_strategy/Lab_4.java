package optimal_strategy;

import java.util.Arrays;

public class Lab_4 {

    public static void main(String[] args) {
        double [][] matrixA = {{6, 7},
                            {0, 9}};
        double [][] matrixB = {{8, 4},
                            {1, 3}};

        int size = 10;  // размер массива
        Matrix matrix = new Matrix();
        // генерируем матрицы
        double [][] A = matrix.generate_matrix(size);
        double [][] B = matrix.generate_matrix(size);

        BMatrix bM = new BMatrix();
        bM.BMatrix(A,B);  // игра 10 на 10
        System.out.println(" (A,B): ");
        bM.BMatrix(matrixA,matrixB);

        AnalyticalSolution analyticalSolution = new AnalyticalSolution(matrixA,matrixB);
        System.out.println("\nX = "+Arrays.toString(analyticalSolution.X()));
        System.out.println("Y = "+ Arrays.toString(analyticalSolution.Y()));
        System.out.format("v1 = %5.3f\n",analyticalSolution.V1());
        System.out.format("v2 = %5.3f\n",analyticalSolution.V2());

        System.out.println("Family battle: ");
        double [][] matrixA1 = {{4, 0},{0, 1}};
        double [][] matrixB1 = {{1, 0},{0, 4}};
        bM.BMatrix(matrixA1,matrixB1);

        System.out.println("Crossroads: ");
        double [][] matrixA3 = {{1, 0.5},{2, 0}};
        double [][] matrixB3 = {{1, 2},{0.5, 0}};
        bM.BMatrix(matrixA3,matrixB3);

        System.out.println("Prisoner's dilemma: ");
        double [][] matrixA2 = {{-5, 0},{-10, -1}};
        double [][] matrixB2 = {{-5, -10},{0, -1}};
        bM.BMatrix(matrixA2,matrixB2);
    }
}
