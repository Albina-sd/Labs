package mixed_strategies;

import java.util.ArrayList;

public class Simplex {
    //source - симплекс таблица без базисных переменных
    double[][] table; //симплекс таблица

    int m, n;

    ArrayList <Integer> basis; //список базисных переменных

    public Simplex(float[][] source) {
        m = source.length;
        n = source[0].length;
        table = new double[m] [n + m - 1];
        basis = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (j < n)
                    table[i][j] = source[i][j];
                else table[i][j] = 0;
            }

            //выставляем коэффициент 1 перед базисной переменной в строке
            if ((n + i) < table[0].length) {
                table[i][n + i] = 1;
                basis.add(n + i);
            }
        }

        n = table[0].length;
    }

    //result - в этот массив будут записаны полученные значения X
    public double[] Calculate(int l) {
        double result [] = new double[l];
        int mainCol, mainRow; //ведущие столбец и строка

        while (!IsItEnd()) {
            mainCol = findMainCol();
            mainRow = findMainRow(mainCol);
            basis.set(mainRow,mainCol);

            double[][] new_table = new double[m][n];

            for (int j = 0; j < n; j++)
                new_table[mainRow][j] = table[mainRow][j] / table[mainRow][mainCol];

            for (int i = 0; i < m; i++) {
                if (i == mainRow)
                    continue;

                for (int j = 0; j < n; j++)
                    new_table[i][j] = table[i][j] - table[i][mainCol] * new_table[mainRow][j];
            }
            table = new_table;
        }

        for (int i = 0; i < table.length; i++){
            for (int j = 0; j < table[0].length; j++){
                System.out.format("%7.2f ",table[i][j]);
            }
            System.out.println();
        }
        System.out.println(basis);
        
        //заносим в result найденные значения X
        for (int i = 0; i < result.length; i++) {
            int k = basis.get(i);
            if (k != -1)
                result[i] = table[k-1][0];
            else result[i] = 0;
        }

        //result[result.length-1] = table[table.length-1][0];
        return result;
    }

    private boolean IsItEnd() {
        boolean flag = true;

        for (int j = 1; j < n; j++) {
            if (table[m - 1][j] < 0) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private int findMainCol() {
        int mainCol = 1;

        for (int j = 2; j < n; j++)
            if (table[m - 1][j] < table[m - 1][mainCol])
        mainCol = j;

        return mainCol;
    }

    private int findMainRow(int mainCol) {
        int mainRow = 0;

        for (int i = 0; i < m - 1; i++)
            if (table[i][mainCol] > 0) {
            mainRow = i;
            break;
        }

        for (int i = mainRow + 1; i < m - 1; i++)
            if ((table[i][mainCol] > 0) && ((table[i][0] / table[i][mainCol]) < (table[mainRow][0] / table[mainRow][mainCol])))
        mainRow = i;

        return mainRow;
    }
}