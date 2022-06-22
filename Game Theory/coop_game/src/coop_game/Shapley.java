package coop_game;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class Shapley {

    public double [] ShapleyVector(LinkedHashMap<Integer,Integer> game){
        Combinations comb = new Combinations();
        int N = 4; //количество игроков
        double x1 = 0;
        double [] result = new double[N];

        for (int j = 0; j < N; j++){
            x1 = comb.factorialUsingRecursion(N-1)*game.get(j+1);
            //System.out.print("x = "+x1 + " + ");

            for (int i = 2; i < 5; i++) {
                int n = N;  // мощность
                int m = i;  // количество элементов в сочетании
                int arr[] = null;

                while ((arr = comb.generateCombinations(m, n, arr)) != null) {
                    int arr1 [] = {j+1};
                    double x = 0;
                    if (comb.find(arr,arr1)){
                        //System.out.print(Arrays.toString(arr));
                        x = comb.factorialUsingRecursion(N-arr.length)*comb.factorialUsingRecursion(arr.length-1);

                        int k = Arrays.binarySearch(arr,j+1);
                        int num = comb.value_comb(arr);
                        String str = "";
                        str += num;
                        str = str.substring(0,k)+str.substring(k+1);
                        num = Integer.parseInt(str);
                        //System.out.print(x+" * "+(game.get(comb.value_comb(arr))-game.get(num)));
                        x *= (game.get(comb.value_comb(arr))-game.get(num));
                        //System.out.println(" = "+x);
                    }
                    x1 += x;
                }
            }

            x1 /= comb.factorialUsingRecursion(N);
            result[j] = x1;
            System.out.format("Player %d   x = %5.3f\n",j+1,x1);
        }
        return result;
    }
}
