package mixed_strategies;

public class ProblemForMethod {
    public static float [][] SimplexMatrixForA(float matrix [][]){
        float [][] simplex_a = new float[matrix.length+2][matrix[0].length];
        float [][] matrix1 = new float[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix[0].length; i++){
            for (int j = 0; j < matrix.length; j++) {
                matrix1[i][j] = matrix[j][i];
            }
        }

        for (int i = 0; i < simplex_a.length-1; i++){
            for (int j = 0; j < simplex_a[0].length; j++) {
                if (j == 0) simplex_a[i][j] = 1;
                else {
                    simplex_a[i][j] = matrix1[i][j-1];
                }
            }
        }

        for (int j = 0; j < simplex_a[0].length; j++){
            if (j == 0) simplex_a[simplex_a[0].length][j] = 0;
            else simplex_a[simplex_a.length-1][j] = -1;
        }

        for (int i = 0; i < simplex_a.length; i++){
            for (int j = 0; j < simplex_a[0].length; j++) {
                System.out.format("%3.0f ",simplex_a[i][j]);
            }
            System.out.println();
        }

        return simplex_a;
    }

    public static float [][] SimplexMatrixForB(float matrix [][]){
        float [][] simplex_b = new float[matrix.length+1][matrix[0].length+1];

        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++) {
                simplex_b[i][j+1] = matrix[i][j];
            }
        }

        for (int j = 0; j < matrix.length; j++) {
            simplex_b[j][0] = 1;
        }

        for (int j = 1; j < simplex_b[0].length; j++) {
            simplex_b[matrix.length][j] = -1;
        }

        simplex_b[matrix.length][0] = 0;

        for (int i = 0; i < simplex_b.length; i++){
            for (int j = 0; j < simplex_b[0].length; j++) {
                System.out.format("%3.0f ",simplex_b[i][j]);
            }
            System.out.println();
        }

        return simplex_b;
    }
}
