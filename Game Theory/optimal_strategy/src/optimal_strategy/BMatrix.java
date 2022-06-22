package optimal_strategy;

public class BMatrix {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    private int [][] color;

    public void BMatrix(double [][] A, double [][] B){
        color  = new int[A.length][A.length];

        System.out.println(ANSI_RED + "\nNash Optimality is red" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Pareto Optimality is green" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Both is purple\n" + ANSI_RESET);

        for (int i = 0; i < A.length; i++){
            for (int j = 0; j < A.length; j++){
                if(checkForNashOptimality(A,B,i,j)) color[i][j]++;
                if (checkForParetoEfficiency(A,B,i,j)) color[i][j] += 3;
            }
        }

        printMass(A,B);
    }

    private boolean checkForNashOptimality (double [][] A, double [][] B, int I,int J){
        boolean a = true, b = true;

        for (int i = 0; i < A.length; i++)
            if (A[i][J] > A[I][J]) a = false;

        for (int j = 0; j < A.length; j++)
            if (B[I][j] > B[I][J]) b = false;

        return (a && b);
    }

    private boolean checkForParetoEfficiency (double [][] A, double [][] B, int I,int J){
        boolean condition = false;

        for (int i = 0; i < A.length; i++){
            for (int j = 0; j < A.length; j++){
                if ((A[i][j] > A[I][J] && B[i][j] >= B[I][J]) || (A[i][j] >= A[I][J] && B[i][j] > B[I][J]))
                    condition = true;
            }
        }

        return !condition;
    }

    private void printMass (double [][] A, double[][] B){
        for (int i = 0; i < A.length; i++){
            for (int j = 0; j < A[0].length;j++) {
                if (this.color[i][j] == 1){
                    int a = (int)A[i][j];
                    int b = (int)B[i][j];
                    System.out.print(ANSI_RED + "[ "+ a+", "+b+" ]" + ANSI_RESET);
                }
                else if (this.color[i][j] == 3){
                    int a = (int)A[i][j];
                    int b = (int)B[i][j];
                    System.out.print(ANSI_GREEN + "[ "+ a+", "+b+" ]" + ANSI_RESET);
                }
                else if (this.color[i][j] > 3){
                    int a = (int)A[i][j];
                    int b = (int)B[i][j];
                    System.out.print(ANSI_PURPLE + "[ "+ a+", "+b+" ]" + ANSI_RESET);
                }
                else System.out.format("[%3.0f, %3.0f]", A[i][j], B[i][j]);
            }
            System.out.println();
        }
    }
}
