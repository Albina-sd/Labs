package mixed_strategies;
import java.util.Scanner;

public class Lab_1 {

    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        float [][] matrix = {{11,11,14,16,11},{2,11,12,10,5},{5,3,16,19,5},{2,10,12,17,3}};

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.format("%3.0f ", matrix[i][j]);
            }
            System.out.println();
        }

        System.out.println();
        ProblemForMethod P = new ProblemForMethod();
        float [][] simplex_a = P.SimplexMatrixForA(matrix);

        System.out.println();
        Simplex S = new Simplex(simplex_a);
        double [] result = S.Calculate(4);

        for (int i = 0; i < result.length; i++){
            System.out.format("%5.3f ",result[i]);
        }

        System.out.println("\n");
        float [][] simplex_b = P.SimplexMatrixForB(matrix);

        System.out.println();

        /*float[][] table = { {25, -3,  5},
                {30, -2,  5},
                {10,  1,  0},
                { 6,  3, -8},
                { 0, -6, -5} }; */

        Simplex S2 = new Simplex(simplex_b);
        double[] result2 = S2.Calculate(2);

        for (int i = 0; i < result2.length; i++){
            System.out.format("%5.3f ",result2[i]);
        }
    }
}
