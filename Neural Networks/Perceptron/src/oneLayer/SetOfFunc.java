package oneLayer;

public class SetOfFunc {

    //задаем булеву функцию
    static int f (int x1, int x2, int x3, int x4){
        return ((x1 | x2) & x3 & x4);
    }

    //combinations
    static void combinationUtil(int [][] set, int arr[], int data[], int start,
                                int end, int index, int r)
    {
        if (index == r)
        {
            int[][] newSet = SetOfFunc.newSet(set, data);
            Neuron.learn(set, newSet, newSet.length);

            for (int j=0; j<r; j++)
                System.out.print(data[j]+",");
            System.out.println();
            return;
        }

        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(set, arr, data, i+1, end, index+1, r);
        }
    }

    static void printCombination( int [][] set, int arr[], int n, int r)
    {
        int data[] = new int[r];

        combinationUtil(set, arr, data, 0, n-1, 0, r);
    }

    //создем новый тренировочный сет
    static int [][] newSet (int set[][], int [] elements){
        int nSet [][] = new int[elements.length][5];
        int k = 0, i = elements[0];

        do{
            if (i == elements[k]) {
                for (int j = 0; j < 5; j++)
                    nSet[k][j] = set[i][j];
                k++;
            }
            i++;
        }while ((k < elements.length)&&(i < set.length));

        return nSet;
    }

    //задаем полный набор данных
    static int [][] fillSet (){
        System.out.println("\nТаблица истинности БФ");
        System.out.println("x1 x2 x3 x4 f");

        int [][] set = new int[16][5];

        for (int i = 0; i < 16; i++){
            String str = Integer.toString(i, 2);
            int len = str.length();
            for (int j = 0; j < (4 - len); j++) str = 0 + str;

            set[i][0] = Integer.parseInt(str.substring(0,1));
            set[i][1] = Integer.parseInt(str.substring(1,2));
            set[i][2] = Integer.parseInt(str.substring(2,3));
            set[i][3] = Integer.parseInt(str.substring(3));
            set[i][4] = f(set[i][0],set[i][1],set[i][2],set[i][3]);

            System.out.println(set[i][0]+"  "+set[i][1]+"  "+set[i][2]+"  "+set[i][3]+"  "+set[i][4]);
        }

        return set;
    }
}
