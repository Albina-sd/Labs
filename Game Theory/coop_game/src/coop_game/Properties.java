package coop_game;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class Properties {
    public boolean check_superaditive(LinkedHashMap<Integer,Integer> game){
        Combinations comb = new Combinations();
        int superAd = 0;

        for (int i = 2; i < 5; i++) {
            int n = 4;  // мощность
            int m = i;  // количество элементов в сочетании
            int arr[] = null;

            while ((arr = comb.generateCombinations(m, n, arr)) != null) {
                int sum = 0;
                int val = game.get(comb.value_comb(arr));
                System.out.print(Arrays.toString(arr)+" = "+val+"    >   ");

                for (int j = 0; j < arr.length; j++){
                    System.out.print("["+arr[j]+"] = "+game.get(arr[j]));
                    sum += game.get(arr[j]);
                    if (j < arr.length-1) System.out.print("  +  ");
                }

                System.out.print("   sum = "+sum);

                if (val < sum) {
                    superAd++;
                    System.out.print("  *");
                }
                System.out.println();
            }
        }

        for (int i = 3; i < 5; i++) {
            int n = 4;  // мощность
            int m = i;  // количество элементов в сочетании
            int arr[] = null;
            int arr1[] = null;

            while ((arr = comb.generateCombinations(m, n, arr)) != null) {
                int n1 = i;  // мощность
                int m1 = i-1;  // количество элементов в сочетании

                while ((arr1 = comb.generateCombinations(m1, n1, arr1)) != null) {
                    int val = game.get(comb.value_comb(arr));
                    System.out.print(Arrays.toString(arr) + " = " + val + "    >   ");

                    int sum = comb.count_coalition(arr, arr1, game);
                    System.out.print("   sum = "+sum);

                    if (val < sum) {
                        superAd++;
                        System.out.print("  *");
                    }
                    System.out.println();
                }
                if (i == 4){
                    int [] mass = new int[6];
                    int k = 0;

                    while ((arr1 = comb.generateCombinations(2, n1, arr1)) != null) {
                        mass[k] = comb.value_comb(arr1);
                        k++;
                    }

                    for (k = 0; k < mass.length/2; k++){
                        int sum = game.get(mass[mass.length-k-1]) + +game.get(mass[k]);
                        int val = game.get(comb.value_comb(arr));
                        System.out.print(Arrays.toString(arr) + " = " + val + "    >   ");

                        System.out.print("["+mass[k]+"] = "+game.get(mass[k])+"  +  ["+mass[mass.length-k-1]+"] = "+game.get(mass[mass.length-k-1]));
                        System.out.print("    sum = "+sum);
                        if (val < sum) {
                            superAd++;
                            System.out.print("  *");
                        }
                        System.out.println();
                    }
                }
            }
        }

        if (superAd != 0) {
            System.out.println("Not superaditive");
            return false;
        }
        else {
            System.out.println("This game is superaditive");
            return true;
        }
    }

    public boolean check_convex(LinkedHashMap<Integer,Integer> game){
        Combinations comb = new Combinations();

        int convex = 0;
        for (int i = 3; i < 5; i++) {
            int arr[] = null;
            int arr1[] = null;
            int n = 4;  // мощность
            int m = i;  // количество элементов в сочетании

            while ((arr = comb.generateCombinations(m, n, arr)) != null) {

                for (int j = 1; j <= 2; j++){
                    int m1 = j;  // количество элементов в сочетании

                    while ((arr1 = comb.generateCombinations(m1, n, arr1)) != null) {
                        if(comb.find(arr,arr1)){
                            int val = game.get(comb.value_comb(arr));
                            int val1 = game.get(comb.value_comb(arr1));

                            System.out.print(Arrays.toString(arr) + " = " + val + "   +   ");
                            System.out.print(Arrays.toString(arr1)+" = "+val1+"  sum = "+(val+val1)+"   >=   ");

                            int sum = comb.convex_coal(arr,arr1,game);
                            System.out.print("  sum = "+sum);
                            if ((val+val1)<sum) {
                                convex++;
                                System.out.print("  *");
                            }
                            System.out.println();
                        }
                    }
                }
            }
        }
        if (convex != 0){
            System.out.println("Not convex");
            return false;
        }
        else {
            System.out.println("This game is convex");
            return true;
        }
    }
}
