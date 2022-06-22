package social_net;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class Influence {
    public int [] agent1;
    public int [] agent2;

    public double [] init (int size, int [] agent1, int [] agent2, double op1, double op2){
        double [] x = new double[size];
        int a = 1, b = 20; // диапазон случайных чисел

        System.out.format("x[%d] = [", 0);
        for (int i = 0; i < size; i++){
            if (Arrays.binarySearch(agent1,i)>-1) x[i] = op1;
            else if (Arrays.binarySearch(agent2,i)>-1) x[i] = op2;
            else x[i]= a + (int)(Math.random()*b);

            System.out.format("%2.0f ",x[i]);
        }
        System.out.println("]");

        return x;
    }

    private int [] gen_rand (int a, int b, int size){
        SortedSet <Integer> randAgents = new TreeSet<>();

        do {
            randAgents.clear();
            for (int i = 0; i < size; i++)
                randAgents.add(a + (int) (Math.random() * b));
        }while (randAgents.size()<2);

        //System.out.println(randAgents);
        int [] arr = new int[randAgents.size()];
        Object[] arr1 = randAgents.toArray();

        for (int j = 0; j < randAgents.size(); j++)
            arr[j] = (int) arr1[j];

        return arr;
    }

    public void  gen_agent (int size){
        int b1 = size - size/5;  // максимальное количество агентов
        int count = 2 + (int)(Math.random()*b1); // количество агентов для 1 и 2 игрока
        int a = 0, b = size-1; // // диапазон агентов

        int arr[] = gen_rand(a,b,count);
        int size1 = 1 + (int)(Math.random()*(arr.length-1)); // количество агентов для 1 игрока
        //System.out.println("количество агентов для 1 игрока: "+size1);

        agent1 = Arrays.copyOfRange(arr,0,size1);
        System.out.println("Agents of the first player: "+Arrays.toString(agent1));
        agent2 = Arrays.copyOfRange(arr,size1,arr.length);
        System.out.println("Agents of the second player: "+Arrays.toString(agent2));
    }

}
