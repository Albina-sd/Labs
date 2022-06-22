package optimal_strategy;

public class Matrix {

    public double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    public double [][] generate_matrix(int size){
        double [][] result = new double[size][size];

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++)
                result[i][j] = getRandomIntegerBetweenRange(-50,50);
        }

        return result;
    }


}
