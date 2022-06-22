package coop_game;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class Combinations {

    public static int[] generateCombinations(int M, int N, int[] arr) {
        if (arr == null)
        {
            arr = new int[M];
            for (int i = 0; i < M; i++)
                arr[i] = i + 1;
            return arr;
        }
        for (int i = M - 1; i >= 0; i--)
            if (arr[i] < N - M + i + 1)
            {
                arr[i]++;
                for (int j = i; j < M - 1; j++)
                    arr[j + 1] = arr[j] + 1;
                return arr;
            }
        return null;
    }

    public int value_comb(int [] arr){
        String str = "";
        for (int j = 0; j < arr.length; j++)
            str += arr[j];

        int num = Integer.parseInt(str);
        return num;
    }

    public int count_coalition(int [] arr, int arr1[], LinkedHashMap <Integer,Integer>game){
        int sum = 0;
        String str = "", str2 = "", n = "";

        for (int i = 0; i < arr1.length; i++)
            arr1[i] = arr[arr1[i]-1];

        for (int i = 0; i < arr.length; i++){
            if(Arrays.binarySearch(arr1,arr[i]) > -1){
                str += arr[i];
                if (str.length()<arr.length) str += ",";
                n += arr[i];
            }else str2 += arr[i];
        }
        str = "[" + str + "]";

        System.out.print(str+"  =  "+game.get(Integer.parseInt(n))+"   +   ["+str2+"] = "+Integer.parseInt(str2));
        return sum = game.get(Integer.parseInt(n)) + Integer.parseInt(str2);
    }

    public boolean find (int arr[], int arr1 []){
        int k = 0;
        for (int i = 0; i < arr1.length; i++)
            if (Arrays.binarySearch(arr,arr1[i])>-1) k++;


        if(k == 0) return false;
        else return true;
    }

    private int [] sorting (String str){
        int mas [] = new int[str.length()];
        String [] m = str.split("");
        Arrays.sort(m);

        for (int i = 0; i < m.length; i++)
            mas[i] = Integer.parseInt(m[i]);

        return mas;
    }

    public int convex_coal(int arr [], int arr1 [],LinkedHashMap <Integer,Integer>game){
        String str1 = "", str2 = "";
        int n1 = 0, n2 = 0;

        for (int i = 0; i < arr.length; i++){
            if (Arrays.binarySearch(arr1,arr[i]) > -1){
                str1 += arr[i];
                str2 += arr[i];
            } else {
                if (str1.length() < arr1.length+1) {
                    if ((str1.length() + 1 - str2.length()) > 1) str2 += arr[i];
                    else str1 += arr[i];
                }
                else str2 += arr[i];
            }
        }

        int [] m1 = sorting(str1);
        int [] m2 = sorting(str2);
        n1 =  game.get(value_comb(m1));
        n2 =  game.get(value_comb(m2));

        //System.out.print(str1+" = "+n1+"  +  "+str2+" = "+n2);
        System.out.print(Arrays.toString(m1)+" = "+n1+"  +  "+Arrays.toString(m2)+" = "+n2);
        return (n1+n2);
    }

    public int factorialUsingRecursion(int n) {
        if (n == 0) return 1;
        if (n <= 2) return n;
        return n * factorialUsingRecursion(n - 1);
    }
}
