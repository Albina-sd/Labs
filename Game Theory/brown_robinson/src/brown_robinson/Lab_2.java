package brown_robinson;

import java.util.ArrayList;
import java.util.Collections;

public class Lab_2 {

    public static void main(String[] args) {
        //задаем исходную матрицу игры
        double [][] matrix = {{9,10,13},
                            {1,18,11},
                            {17,4,0}};

        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length;j++){
                System.out.format("%3.0f ",matrix[i][j]);
            }
            System.out.println();
        }

        System.out.println();

        if (matrix.length == matrix[0].length){
            System.out.println("Аналитический расчет:\n");
            AnalyticalSolution AS = new AnalyticalSolution();
            ArrayList<Double> al = new ArrayList<>();

            double [] X = AS.strategyX(matrix);
            for (int i = 0; i < X.length; i++) al.add(X[i]);
            System.out.print("\nX: ");
            printMass(X);
            System.out.println("Стратегия игрока А: "+(al.indexOf(Collections.max(al))+1)+"\n");

            double [] Y = AS.strategyY(matrix);
            System.out.print("Y: ");
            printMass(Y);
            al.removeAll(al);
            for (int i = 0; i < X.length; i++) al.add(Y[i]);
            System.out.println("Стратегия игрока B: "+(al.indexOf(Collections.max(al))+1)+"\n");

            double V = AS.price(matrix);
            System.out.format("v = %5.3f",V);
        }

        BrownRobinson br = new BrownRobinson();
        br.BrownRobinson(matrix);
    }

    static private void printMass (double [] matrix){
        for (int i = 0; i < matrix.length; i++)
            System.out.format("%5.3f ",matrix[i]);
        System.out.println();
    }
}
