package hopfild;

import java.util.ArrayList;
import java.util.Random;

public class Lab_5 {

    public static void print (int [][] mass, ArrayList<String> names){
        String m [][] = print1(mass);

        System.out.println();
        for (int i = 0; i < mass.length; i++){
            System.out.println("Образ "+names.get(i));
            for (int j = 0; j < mass[0].length;) {
                System.out.println(m[i][j] + " " + m[i][j+1] + " " + m[i][j+2]+" "+m[i][j+3]);
                j += 4;
            }
            System.out.println();
        }
    }

    public static String [][] print1 (int [][] mass){
        String s [][] = new String[mass.length][mass[0].length];

        for (int i = 0; i < mass.length; i++){
            for (int j = 0; j < mass[0].length; j++){
                if (mass[i][j] < 0) s[i][j] = "-";
                else if (mass[i][j] > 0) s[i][j] = "1";
                //System.out.print(mass[i][j]);
            }
        }
        return s;
    }

    public static int [][] new_image (int image [][]){
        int n_img [][] = image;
        Random rnd = new Random(System.currentTimeMillis());
        int N = 3; // количество изменений

        for (int i = 0; i < image.length; i++){
            for (int j = 0; j < N; j++) {
                int k = rnd.nextInt(image[0].length);
                n_img[i][k] *= -1;
            }
        }

        return n_img;
    }

    public static void main(String[] args) {
        // обучающий набор S  T  U
        //int [][] image = {{-1,1,1,1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,1,1,1,-1},
                          //{1,1,1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,1,-1,-1},
                          //{1,-1,-1,1,1,-1,-1,1,1,-1,-1,1,1,-1,-1,1,1,-1,-1,1,-1,1,1,-1}};
        // обучающий набор 1 2 3
        int [][] image = {{-1,-1,1,-1,-1,1,1,-1,1,-1,1,-1,-1,-1,1,-1,-1,-1,1,-1,1,1,1,1},
                          {-1,1,1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1-1,-1,1,1,1,1},
                          {1,1,1,1,-1,-1,-1,1,-1,1,1,1,-1,-1,-1,1,-1,-1,-1,1,1,1,1,1}};

        ArrayList<String> names = new ArrayList<>();
        names.add("1");
        names.add("2");
        names.add("3");

        System.out.println("Исходный образ");
        print(image,names);

        int [][] test = new_image(image);
        System.out.println("Искаженный образ");
        print(test,names);

        RNN rnn = new RNN(image,test);
        System.out.println("Результат");
        test = rnn.getY();

        print(test,names);
    }
}
