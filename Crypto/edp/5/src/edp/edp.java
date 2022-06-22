package edp;

import java.math.BigInteger;
import java.util.Scanner;

public class edp {

 public static void help() {
  System.out.println("Программа реализации Электронной Цифровой Подписи. \n" +
          "Для того чтобы программу начала работать нужно будет ввести значения: p, q, хеш-сумму,\n" +
          "d и е для открытого и закрытого ключа, которые нужно взять из задания №3 и 4. До ввода\n" +
          "После этого будет произведен расчет и показана ЭЦП. Далее будет показана проверка эцп с\n" +
          "хэш-образом сообщения.");
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

     //считываем хэш-сумму с консоли
        System.out.println("Введите хэш-сумму:");
        int hash = scanner.nextInt();

     //считываем p с консоли
        System.out.println("Введите p:");
        int p = scanner.nextInt();

     //считываем q с консоли
        System.out.println("Введите q:");
        int q = scanner.nextInt();

     //рассчитываем и выводим n
        int n = p * q;
        System.out.println("n = p * q = "+n);

     //считываем d с консоли
        System.out.println("Введите d для открытого ключа:");
        int d = scanner.nextInt();

     //считываем e с консоли
        System.out.println("Введите e для закрытого ключа:");
        int e = scanner.nextInt();

     //высчитываем S = hash^e  mod n
        BigInteger S;
        S = BigInteger.valueOf(hash);
        S = S.pow(e);
        BigInteger n_ = BigInteger.valueOf(n);
        S = S.mod(n_);
        System.out.println("S = (hash ^ e) % n = "+"("+hash+" ^ "+d+") % "+ n +" = "+ S);

     //высчитываем H = S^d  mod n
        BigInteger H;
        H = S;
        H = H.pow(d);
        H = H.mod(n_);
        System.out.println("H = (S ^ d) % n"+"("+S+" ^ "+e+") % "+ n +" = "+ H);
    }

}
