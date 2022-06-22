package oneLayer;

public class Lab1 {

    public static void main(String[] args) {
        System.out.println("\nИсследование однослойных нейронных сетей " +
                "на примере моделирования булевых выражений");

        int [][] set = SetOfFunc.fillSet(); // получаем полный обучающий набор

        Neuron.learn(set, set, set.length); // обучаем на полном наборе или его части (от его начала)

        int elements [] = {1,3,7,11,14}; // номера наборов (начиная с нуля)

        /*
        int r = 5; // количество элементов в сочетании
        int n = elements.length; // мощность
        SetOfFunc.printCombination(set,elements, n, r); //выводим комбинации эмементов по r
         // и результаты обучения на них
        */

        int[][] newSet = SetOfFunc.newSet(set, elements);

        System.out.println("\n\nNew set:");
        for (int i = 0; i < elements.length; i++) {
            for (int k = 0; k < 5; k++) {
                System.out.print(newSet[i][k] + " ");
            }
            System.out.println();
        }

        Neuron.learn(set, newSet, newSet.length); // обучаем на части набора
    }
}
