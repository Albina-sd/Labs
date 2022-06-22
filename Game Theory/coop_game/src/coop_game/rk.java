package coop_game;

import java.util.*;

public class rk {

    public static void main(String[] args) {
        LinkedHashMap<Integer, Integer> game = new LinkedHashMap<Integer, Integer>();

        Integer coalition [] = {1,2,3,4,12,13,14,23,24,34,123,124,134,234,1234};
        Integer pay [] = {3,4,4,4,8,7,7,9,8,9,13,13,13,14,16};

        for (int i = 0; i < coalition.length; i++)
            game.put(coalition[i], pay[i]);

        System.out.println(game);

        Properties properties = new Properties();

        System.out.println("Superaditivity check:");
        properties.check_superaditive(game);

        System.out.println("Convex check:");
        properties.check_convex(game);

        System.out.println("Shapley Vector:");
        Shapley shapley = new Shapley();
        double sharing [] = shapley.ShapleyVector(game);
        double sum = 0;

        for (int i = 0; i < sharing.length; i++)
            sum += sharing[i];
        System.out.println("\nConditions:");
        System.out.format("1. Group rationalization:  sum x[i] = %5.3f  ==  v(I) = %d\n",sum,game.get(1234));
        if (Math.abs(Math.round(sum) - game.get(1234)) > 0.5)
            System.out.println("Condition not performed");
        else System.out.println("Condition performed");

        sum = 0;
        System.out.format("\n2. Individual rationalization:\n");
        for (int i = 0; i < sharing.length; i++){
            System.out.format("x[%d] = %5.3f  >=  v[%d] = %d\n",i+1,sharing[i],i+1,game.get(i+1));
            if (sharing[i] < game.get(i+1)) sum++;
        }
        if (sum > 0) System.out.println("Condition not performed");
        else System.out.println("Condition performed");
    }
}
