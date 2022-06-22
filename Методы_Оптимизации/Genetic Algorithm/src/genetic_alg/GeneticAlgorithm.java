package genetic_alg;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;


public class GeneticAlgorithm{
    public static final int SIZE_POPULATION = 4;  //количество особей в популяции
    public static final double M_RATE = 0.25; //вероятность мутации
    private static final int MAX_ITERATIONS = 10; //количество итераций

    //инициализация начальной популяции
    public void init(List<Individual2> population) {
        double f = 0;

        Random randomGen = new Random(System.currentTimeMillis());

        System.out.println("\nStart population:");

        for (int i = 0; i < SIZE_POPULATION; i++) {
            double x1 = (randomGen.nextDouble()) * 2;
            double x2 = (randomGen.nextDouble()) * 4 - 2;

            Individual2 individual = new Individual2(x1,x2);
            population.add(individual);

            f += population.get(i).fitness_function;
        }
        for (int i = 0; i < population.size(); i++) {
            System.out.println(population.get(i));
        }

        Collections.sort(population);
        System.out.println("Средний результат: " + f/SIZE_POPULATION);
        System.out.println("Максимальный результат: " + population.get(0).fitness_function);
    }

    static double tenSS (String str){
        double p = 0, m = 1;
        if (str.startsWith("10")){
            str = str.substring(1);
            m = -1;
        }else str = str.substring(8);
        int k = 0;
        for (int i = 0; i < str.length(); i++){
            p += Double.valueOf(str.substring(i,i+1))* Math.pow(2,-k);
            k++;
        }
        p *= m;
        return p;
    }

    //мутация
    double mutation(double x1) {
        String buf=Long.toBinaryString(Double.doubleToLongBits(x1));
        int position = (int)(Math.random()*buf.length()); //номер бита  для мутации
        StringBuilder s = new StringBuilder(buf.subSequence(0, buf.length()));
        for(int i=0;i<s.length();i++){
            if(s.charAt(position)=='0')s.setCharAt(position,'1');
            else if(s.charAt(position)=='1') s.setCharAt(position,'0');
        }
        return tenSS(s.toString());
    }

    //скрещивание
    List<Individual2> crossing(List<Individual2> population) {
        List<Individual2> children = new ArrayList<>(); //список потомков
        double new_x1, new_x2;

        for (int i = 0; i < 4; i++){
            new_x1 = 0;
            new_x2 = 0;
            switch (i){
                case 0:
                    //гененируем первого потомка
                    new_x1 = population.get(1).x1;
                    new_x2 = population.get(0).x2;
                    break;

                case 1:
                    //гененируем второго потомка
                    new_x1 = population.get(2).x1;
                    new_x2 = population.get(0).x2;
                    break;

                case 2:
                    //гененируем треьего потомка
                    new_x1 = population.get(0).x1;
                    new_x2 = population.get(1).x2;
                    break;

                case 3:
                    //гененируем четвертого потомка
                    new_x1 = population.get(0).x1;
                    new_x2 = population.get(2).x2;
                    break;
            }

            if (Math.random() == M_RATE) {   //вычисляется вероятность мутации
                new_x1 = mutation(new_x1);
                new_x2 = mutation(new_x2);
            }

            Individual2 child = new Individual2(new_x1,new_x2);
            children.add(child);
        }

        return children;
    }
    private void Start(){
        List<Individual2> population = new ArrayList<>();//особи
        init(population); //инициализация начальной популяции


        for (int j = 0; j < MAX_ITERATIONS; j++) {
            double f = 0;
            System.out.println("\nGeneration " + (j+1));

            population = crossing(population); //скрещивание

            for (int i = 0; i < SIZE_POPULATION; i++){
                System.out.println(i + " > " + population.get(i));
                f += population.get(i).fitness_function;
            }

            Collections.sort(population);
            System.out.println("Средний результат: " + f/SIZE_POPULATION);
            System.out.println("Максимальный результат: " + population.get(0).fitness_function);
        }
    }

    public static void main(String[] args) {
        new GeneticAlgorithm().Start();
    }
}

