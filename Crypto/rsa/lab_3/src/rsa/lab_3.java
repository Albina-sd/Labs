package rsa;

import java.math.BigInteger;
import java.util.Scanner;

import static java.lang.Math.sqrt;

public class lab_3 {
    //задаем алфавит для шифрования текста на русском языке
    static String [] alf = new String[] {"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И",
            "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С",
            "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ь", "Ы", "Ъ",
            "Э", "Ю", "Я", " "};

    //метод проверки простоты числа
    public static boolean IsNumSimple(long i){
        int k=0;
        if (i < 2)
            return false;

        for (int j = 2; j < sqrt(i); j++)
            if (i % j == 0)
                k++;

        /*for (int j = 2; j<i; j++){
            if (i % j == 0)
                k++;
        }*/

        if (k==0)
            return true;
        else
            return false;

    }

    //подбор открытого ключа d
    public static long Calculate_d(long m){

        long d = m;

        /*while((IsNumSimple(d))&&(m % d != 0)){
            d = (long)(m * Math.random())+1;
            if ((IsNumSimple(d))&&(m % d != 0))
                break;
        }*/

        for (d = (long)(m/2 * Math.random())+1; d <= m; d++){
            if ((IsNumSimple(d))&&(m % d != 0))
                break;
        }
        //return 37;
        return d;
    }

    //вычисление закрытого ключа e
    public static long Calculate_e(long d, long m){
        long k=1;

        while (true){
            if ((m*k+1)%d == 0)
                break;
            else
                k++;
        }
        return (m*k+1)/d;
    }

    public static int [] encode(long e, long n, String message) {
        int [] rez = new int[message.length()];
        String str = "";

        //создаем массив каждый элемент которого исходное сообщение
        String [] s = message.split("");
        BigInteger bi;
        int index=0;

        System.out.print("До шифрования:");

        //шифруем массив сообщения
        for(int i = 0; i < s.length; i++){
            for(int j = 0; j < alf.length; j++){
                //находим индекс каждого элемента массива в алфавите
               if (alf[j].compareToIgnoreCase(s[i])==1)
                   index = j;
            }

            System.out.print(index+" ");

            //затем подставляем этот индекс в формулу M^e mod n
            bi = BigInteger.valueOf(index);
            bi = bi.pow((int)e);
            BigInteger n_ = BigInteger.valueOf(n);
            bi = bi.mod(n_);
            str += bi+" ";
        }

        //записываем зашифрованный текст в массив типа Integer
        s = str.split(" ");
        for(int i = 0; i < s.length; i++){
            rez[i] = Integer.valueOf(s[i]);
        }
       return rez;
    }

    public static String decode(long d, long n, int inM[]) {
        String res = "";
        BigInteger bi;

        //расшифровываем сообщение C^d mod n
        for(int i = 0; i < inM.length; i++){
            bi = BigInteger.valueOf(inM[i]);
            bi = bi.pow((int)d);
            BigInteger n_ = BigInteger.valueOf(n);
            bi = bi.mod(n_);
            int index = bi.intValue();
            System.out.print(index+" ");
            res += alf[index-1];
        }

        return res;
    }

    public static void help() {
        System.out.println("Программа для шифрования методом RSA.\n" +
                "Для того чтобы узнать шифрованный и зашифрованный текст,\n" +
                "вам нужно будет ввести такие параметры,как:p,q,e и d.\n" +
                "Если d ввести раной 0, то d генерируется с учетом параметров.\n" +
                "P и Q - простые числа. После чего с помощью функции Эйлера они\n" +
                "перемножаются. Далее мы должны выбрать E(открытый ключ)\n" +
                "таким образом, чтобы, оно было меньше функции Эйлера и\n" +
                "взаимно просто с ней");
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

        //считываем сообщение с консоли
        System.out.println("Введите сообщение:");
        String message = scanner.nextLine();

        //считываем p с консоли
        System.out.println("Введите p:");
        long p = scanner.nextInt();

        //считываем q с консоли
        System.out.println("Введите q:");
        long q = scanner.nextInt();

        //если p и q простые продолжаем процесс шифрования
        if (IsNumSimple(p)&&IsNumSimple(q)){
            //находим и выводим n
            long n = p * q;
            System.out.println("n = p * q = "+n);

            //находим и выводим функцию Эйлера
            long m = (p - 1)*(q - 1);
            System.out.println("m = (p - 1)*(q - 1) = "+m);

            //подбираем и выводим d
            System.out.println("Введите d, если введете 0, то d будет сгенерировано");
            long d = scanner.nextInt();

            if (d == 0)
                d = Calculate_d(m);
            System.out.println("d = "+d);

            //высчитываем и выводим e
            long e = scanner.nextInt();

            if (e == 0)
                e = Calculate_e(d, m);
            System.out.println("e = "+e);

            //записываем в массив результат шифрования исходного сообщения
            System.out.println("Шифруем сообщение:");
            int [] EnM = encode(e,n,message);

            System.out.println();

            System.out.print("После шифрования:");
            for(int i = 0; i < EnM.length; i++){
                System.out.print(EnM[i]+" ");
            }

            System.out.println();
            //дешифруем и выводим зашифрованный текст
            System.out.print("Дешифруем сообщение:");
            String DeM = decode(d, n, EnM);
            System.out.println();
            System.out.println(DeM);

            //если дешифрованное сообщение совпадает с исходным выводим "Сообщение //восстановлено"
            if ((DeM).compareToIgnoreCase(message) == message.length())
                System.out.println("Сообщение восстановлено");
        }
        //если числа непростые, то выводим "числа не простые"
        else System.out.println("числа не простые");
    }

}
