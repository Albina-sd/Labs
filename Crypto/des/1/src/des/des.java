package des;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static java.nio.charset.StandardCharsets.UTF_8;

public class des {

    public static String StringToBinaryFormat(String input){
        String output = "";
        byte[] bytes = input.getBytes(UTF_8);
        int i = 0;

        System.out.println("Переведем сообщение в двоичный формат:");

        for (int val : input.toCharArray())
        {

            if ((val>=1040)&&(val<=1103))
                val = val - 1040 + 192;

            String m = Integer.toBinaryString(val);

            if (m.length()<8)
                while (m.length()!=8){
                    m = "0"+m;
                }

            output += m;
            System.out.println(input.charAt(i) + " " + m);
            i++;
        }

        return output;
    }

    public static void vivod(String input, int k){

        for (int i = 0; i < input.length();){
            System.out.print(input.substring(i,i+k)+" ");
            i+=k;
        }

    }

    public static String KeyWord(){
        int k;
        String key = "";

        System.out.println("Ключ: ");
        for (int i = 0; i < 6; i++){
            k = ThreadLocalRandom.current().nextInt(65, 90 + 1);
            String kk = Integer.toBinaryString(k);
            System.out.print((char) Integer.parseInt(kk, 2)+" ");

            if (kk.length()<8)
                for (int j = 0; j<(8-kk.length());j++)
                    kk = "0"+kk;

            System.out.println(kk+" ");
            key += kk;
        }

        return key;
    }

    public static String rashir(String str){
        String res = "";
        System.out.println("Расширяем подблоки до 6 битов:");
        String [] s = new String[8];
        String [] s1 = new String[8];
        String [] s2 = new String[8];
        int j = 0;
        int i;

        //записываем в массив 4 средних бита
        for (i = 0; i < str.length();){
            s[j] = str.substring(i,i+4);
            System.out.print(s[j]+" ");

            i += 4;
            j++;
        }

        System.out.println();

        //записываем в массив 2 крайних бита
        i=4;
        for (j = 1; j < 7; j++){
            int a = i-1, b = i + 4;

            //складываем 4 средних бита с крайними
            s1[j] = str.substring(a,a+1)+s[j]+str.substring(b,b+1);

            i+=4;
        }

        //складываем 4 средних бита с крайними для первого и последнего блока
        s1[0] = str.substring(str.length()-1, str.length())+str.substring(0,4)+str.substring(4,5);
        s1[7] = str.substring(str.length()-5,str.length()-4)+str.substring(str.length()-4, str.length())+str.substring(0,1);

        //записываем в строку
        for (j = 0; j < 8; j++){
            res += s1[j];
            System.out.print(s1[j]+" ");
        }

        return res;
    }

    public static String finR(String R){
        int S1 [][] = {
                {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
        };

        int S2 [][] = {
                {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9},
        };

        int S3 [][] = {
                {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12},
        };

        int S4 [][] = {
                {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14},
        };

        int S5 [][] = {
                {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3},
        };

        int S6 [][] = {
                {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13},
        };

        int S7 [][] = {
                {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12},
        };

        int S8 [][] = {
                {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11},
        };

        String R2 = "", s = "";
        int i, j = 0;
        int [] stolb = new int [8];
        int [] str = new int [8];

        System.out.println("Используя приведенную таблицу подстановки, получаем выходной R -блок:");

        for (i = 1; i < R.length();){
            s = R.substring(i,i+4);

            stolb[j] = Integer.parseInt(s, 2);
            System.out.print(stolb[j]+" ");

            i += 6;
            j++;
        }

        System.out.println();
        i = 0;

        for (j = 0; j < 8; j++){
            s = R.substring(i, i+1) + R.substring(i+5, i+6);

            str[j] = Integer.parseInt(s, 2);
            System.out.print(str[j] + " ");

            i += 6;
        }

        System.out.println();
        s = "";
        int p;

        for (j = 0; j < 8; j++){
            switch (j){
                case 0:
                    p = S1 [str[j]][stolb[j]];
                    break;
                case 1:
                    p = S2 [str[j]][stolb[j]];
                    break;
                case 2:
                    p = S3 [str[j]][stolb[j]];
                    break;
                case 3:
                    p = S4 [str[j]][stolb[j]];
                    break;
                case 4:
                    p = S5 [str[j]][stolb[j]];
                    break;
                case 5:
                    p = S6 [str[j]][stolb[j]];
                    break;
                case 6:
                    p = S7 [str[j]][stolb[j]];
                    break;
                case 7:
                    p = S8 [str[j]][stolb[j]];
                    break;

                    default:
                        p = -1;
            }

            s = Integer.toBinaryString(p);
            if (s.length()<4)
                while (s.length()!=4){
                    s = "0" + s;
                }

            System.out.print(p+" ");
                R2 += s;

        }

        return R2;
    }

    public static String encode(String key, String message) {
        String L = message.substring(0,(message.length()/2));
        String R = message.substring(message.length()/2, message.length()), R1, R2, R2L;

        //делим сообщение на два подблока
        System.out.println();
        System.out.println("L0 = " + L);
        System.out.println("R0 = " + R);
        System.out.println();

        //расширение подблока до 48 бит
        R1 = rashir(R);
        System.out.println("\n");

        System.out.println("Ключ:");
        vivod(key,6);

        //сложение по модулю 2
        System.out.println("\n");
        R1 = XOR(R1, key);
        System.out.println("R1 + key");
        vivod(R1,6);

        //перестановека с помощью S боксов
        System.out.println("\n");
        R2 = finR(R1);
        System.out.println("\n");
        vivod(R2, 4);

        //вторая перестановка
        System.out.println("\n");
        R2 = perest(R2,3);
        vivod(R2, 4);

        //сложение по модулю 2 левого подблока и функции от правого подблока
        System.out.println("\n");
        R2 = XOR(L,R2);
        System.out.println("L + f(R2)");
        vivod(R2, 4);

        //соединяем получившийся код с R0 для последующей перестановки
        System.out.println("\n");
        System.out.println("Соединяем блоки R и L:");
        R2L = R + R2;
        vivod(R2L, 8);

        //последняя перестановка
        System.out.println("\n");
        R2L = perest(R2L,2);
        System.out.print("");
        vivod(R2L, 8);

       return L;
    }

    public static String perest(String input, int n){
        String result = "";
        int p1[] = {58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62,
                   54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49,
                   41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45,
                   37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};

        int p2[] = {40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38,
                    6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4,
                    44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42,
                    10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25};
        int p3[] = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8,
                    24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};

        System.out.println("Выполняем перестановку:");

        if (n == 1){
            for (int i = 0; i < input.length(); i++)
                result += input.charAt(p1[i]-1);
        }
        if (n == 2)
        {
            for (int i = 0; i < input.length(); i++)
                result += input.charAt(p2[i]-1);
        }
        if (n == 3)
        {
            for (int i = 0; i < input.length(); i++)
                result += input.charAt(p3[i]-1);
        }

        return result;
    }

    public static String XOR(String s1, String s2) {
        String res = "";
        boolean a, b;

        System.out.println("Производим операцию поразрядного суммирования:");

        for (int i = 0; i < s1.length(); i++){
            String m = s1.substring(i,i+1);
            String n = s2.substring(i,i+1);

            if (Integer.valueOf(m) == 1)
                a = true;
            else a = false;

            if (Integer.valueOf(n) == 1)
                b = true;
            else b = false;

            if (a ^ b)
                res += "1";
            else
                res += "0";
        }

        return res;
    }

    public static void help(){
        System.out.println("Программа для шифрования методом DES.");
        System.out.println("Для начала работы программы, нужно ввести исходное сообщение, которое должно состоять из 8 символов, ");
        System.out.println("затем нужно ввести ключ состоящий из 6 символов(уже в двоичном формате), если мы введем 1, то ключ ");
        System.out.println("будет сгенерирован случайным образом. После чего программа покажет нам наш исходный текст из 0 и 1, ");
        System.out.println( "которые соответствуют значениям из таблицы, взятой из приложения. После чего происходит перестановка," );
        System.out.println("и показывается исходный текст уже с переставленными символами. Далее мы разбиваем исходный текст после" );
        System.out.println("перестановки пополам, отсюда нам становится известно L1 и R1. Далее мы расширяем блок R1 и производим " );
        System.out.println("поразрядное суммирование по модулю 2. После чего выполняем 8 операций подстановки, где для каждой " );
        System.out.println("операции используется одна из 8 таблиц подстановки размером 4х16(Пример представлен в Приложении), ");
        System.out.println("используя одну из таблиц подстановки, находим число, стоящее на пересечении строки и столбца с ");
        System.out.println("соответствующими номерами. Это число будет являться выходном блоком, которое записывается в двоичном ");
        System.out.println("виде. После этого мы записываем полученные подблоки в один блок и соединяем полученную последовательность");
        System.out.println("с левым блоком, поосле выполняем перестановку по таблице, которая указана в приложении");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Нажмите 1, если хотите вызвать help");

        switch (scanner.nextInt()){
            case 1:
                help();
                break;
            case 2:
                break;
                default:
                    break;
        }

        /*if (h == 1)
            help();
        else*/
        String k = scanner.nextLine();

        System.out.println("Введите сообщение:");
        //String message = "Сафина А";
        //System.out.println("Сафина А");
        String message = scanner.nextLine();

        System.out.println();

        //перевод числа в двоичный формаат
        String CodeMes = StringToBinaryFormat(message);

        System.out.println("Введите ключ:");
        String key = scanner.nextLine();

        if (key.length() == 1) {
            //формирование случайного ключа
            key = KeyWord();
        }
        //String key = "101011101101110101001100110011000110111101110101";
        //System.out.println("Ключ: 10101110 11011101 01001100 11001100 01101111 01110101");
        //String key = "110011001110111011110001111010101110001011100000";
        //System.out.println("Ключ: 11001100 11101110 11110001 11101010 11100010 11100000");

        System.out.println();

        //первая перестановка
        message = perest(CodeMes,1);

        vivod(message,8);

        //первый цикл DES
        System.out.println();
        encode(key,message);

    }

}
