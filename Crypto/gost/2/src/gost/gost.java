package gost;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static java.nio.charset.StandardCharsets.UTF_8;

public class gost {

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
        for (int i = 0; i < 4; i++){
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

    public static String finR(String R){
        int S [][] = {
                {1, 13, 4, 6, 7, 5, 14, 4},
                {15, 11, 11, 12, 13, 8, 11, 10},
                {13, 4, 10, 7, 10, 1, 4, 9},
                {0, 1, 0, 1, 1, 13, 12, 2},
                {5, 3, 7, 5, 0, 10, 6, 13},
                {7, 15, 2, 15, 8, 3, 13, 8},
                {10, 5, 1, 13, 9, 4, 15, 0},
                {4, 9, 13, 8, 15, 2, 10, 14},
                {9, 0, 3, 4, 14, 14, 2, 6},
                {2, 10, 6, 10, 4, 15, 3, 11},
                {3, 14, 8, 9, 6, 12, 8, 1},
                {14, 7, 5, 14, 12, 7, 1, 12},
                {6, 6, 9, 0, 11, 6, 0, 7},
                {11, 8, 12, 3, 2, 0, 7, 15},
                {8, 2, 15, 11, 5, 9, 5, 5},
                {12, 12, 14, 2, 3, 11, 9, 3},
        };
        String R2 = "", s = "";
        int i, j = 0;
        int [] stolb = new int [8];

        System.out.println("Используя приведенную таблицу подстановки, получаем выходной R -блок:");

        System.out.println("8 7 6 5 4 3 2 1");

        i = 0;
        for (j = 0; j < stolb.length; j++){
            s = R.substring(i,i+4);

            stolb[j] = Integer.parseInt(s, 2);
            System.out.print(stolb[j]+" ");

            i += 4;
        }

        System.out.println();
        s = "";

        for (j = 0; j < 8; j++){
            int p = S [stolb[j]][j];

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

    public static String sdvig(String str){
        String res = "";

        System.out.println("Производим сдвиг на 11 бит влево:");

        res += str.substring(11, str.length());
        res += str.substring(0,11);

        return res;
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

    public static String encode(String key, String message) {
        String L = message.substring(0,(message.length()/2));
        String R = message.substring(L.length()), f, f2L, R1;

        System.out.println();
        System.out.println("Разбаваем на два подблока:");
        System.out.print("L0 = ");
        vivod(L, 8);
        System.out.println();
        System.out.print("R0 = ");
        vivod(R, 8);
        System.out.println();

        System.out.println("Ключ:");
        vivod(key,8);

        System.out.println("\n");
        f = mod32(R, key);

        System.out.println("\n");
        f = finR(f);
        System.out.println();
        vivod(f,4);

        System.out.println("\n");
        f = sdvig(f);
        vivod(f,4);

        System.out.println("\n");
        R1 = XOR(f,L);
        vivod(R1,4);
       return L;
    }

    public static String mod32(String s1, String s2) {
        String res = "", res1 = "";
        int k=0, i = s1.length()-1, r;
        System.out.println("Производим операцию суммирования по mod 2^32:");
        System.out.print("R0: ");
        vivod(s1,8);
        System.out.println();
        System.out.print("Х0: ");
        vivod(s2,8);
        System.out.println();
        BigInteger bi;

        //bi = BigInteger.valueOf(Integer.valueOf(s1.substring(0,s1.length()), 2) + Integer.valueOf(s2.substring(0,s1.length()), 2));
        //System.out.println(bi);
        r = Integer.valueOf(s1.substring(s1.length()/2,s1.length()), 2) + Integer.valueOf(s2.substring(s1.length()/2,s1.length()), 2);
        //System.out.println(r);
        res += Integer.toBinaryString(r);

        if (res.length() > s1.length()/2){
            res1 += res.substring(1,res.length());
            k = 1;
        }
        else if (res.length() < s1.length()/2){
            res1 = "0" + res.substring(1,res.length());
            k = 1;
        }
        else res1 += res;

        res="";
        r = Integer.valueOf(s1.substring(0,s1.length()/2), 2) + Integer.valueOf(s2.substring(0,s1.length()/2), 2) + k;
        //System.out.println(r);
        res += Integer.toBinaryString(r);

        if (res.length() > s1.length()/2){
            res1 = res.substring(1,res.length()) + res1;
        }
        else res1 = res + res1;

        System.out.print("    ");
        vivod(res1,8);

        return res1;
    }

    public static void help(){
        System.out.println("Программа для шифрования методом ГОСТ 28147-89.");
        System.out.println("Для работы программы нужно ввести 8 букв из ФИО, затем нужно будет ввести первые четыре");
        System.out.println("символа ключа(в двоичном формате), состоящего из 32 символов, которые будут являются X0.");
        System.out.println("Разбиваем исходный текст на правую R0 и левую L0 части. Далее нам нужно вычислить сумму ");
        System.out.println("R0 и X0 по модулю 2 в степени 32. Далее в программе выводится результат суммирования и");
        System.out.println("происходит преобразование в блоке подстановки. То есть показываются соотвествующие номера");
        System.out.println("строк в таблице подстановки,заполнение и результат. Далее у нас происходит сдвиг результата ");
        System.out.println("на 11 бит влево. Таким образом мы нашли значение функции f, далее вычисляем R1,");
        System.out.println("путем сложения f и L0 по модулю 2");
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

        String m = scanner.nextLine();

        //считываем исходное сообщение с консоли
        System.out.println("Введите сообщение:");
        //String message = "Сафина А";
        //System.out.println("Сафина А");
        String message = scanner.nextLine();

        System.out.println();

        //переводим сообщение в двоичный формат
        String CodeMes = StringToBinaryFormat(message);

        System.out.println();

        System.out.println("Введите ключ:");
        String key = scanner.nextLine();

        if (key.length() == 1) {
            //формирование случайного ключа
            key = KeyWord();
        }

        //String key = "10011110001110001001100011101010";
        //System.out.println("Ключ: 10011110 00111000 10011000 11101010");
        //String key = "11001111111011101110110111100101";
        //System.out.println("Ключ: 11001111 11101110 11101101 11100101");

        System.out.println();

        //вывод сообщения в двоичном формате
        System.out.println("Сообщение, переведенное в двоичную последовательность:");
        vivod(CodeMes,8);

        System.out.println();
        //делаем один цикл шифрования ГОСТ
        encode(key,CodeMes);
    }

}
