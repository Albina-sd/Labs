package hash;

import java.util.Scanner;

public class hash {

    public static void help() {
        System.out.println("Программа реализации функции Хэширования. \n" +
                "Для того чтобы программу начала работать нужно будет ввести значения: p, q, Н0,\n" +
                "которые нужно взять из задания №3. До ввода всех параметров, программа попросит \n" +
                "вас ввести текст, после которого будет произведен расчет и показан хэш-образ текста, \n" +
                "который был введен.");
    }

    public static void main(String[] args) {
        //задаем алфавит для шифрования текста на русском языке
        String [] M = new String[] {"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И",
                "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С",
                "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ь", "Ы", "Ъ",
                "Э", "Ю", "Я", " "};

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

        //считываем сообщение с консоли
        System.out.println("Введите сообщение:");
        String message = scanner.nextLine();

        int [] H = new int[message.length()+1];

        //считываем p с консоли
        System.out.println("Введите p:");
        int p = scanner.nextInt();

        //считываем q с консоли
        System.out.println("Введите q:");
        int q = scanner.nextInt();

        //высчитываем и выводим n
        int n = p * q;
        System.out.println("n = p * q = "+n);

        String inputMas[] = message.split("");


        System.out.println("\nЗамена каждой буквы соответствующим номером в алфавите:");
        int inMas[] = new int [inputMas.length];
        //замена каждой буквы соответствующим номером в алфавите
        for (int k = 0; k < inputMas.length; k++){

            for (int j = 1; j < M.length; j++) {
                if (inputMas[k].equals(M[j-1])){
                    inMas[k] = j;
                    System.out.print(inMas[k]+" ");
                    break;
                }
            }
        }

        System.out.println();

        //высчитывание хэша
        System.out.println("Введите Н0");
        H[0] = scanner.nextInt();
        System.out.println("Получим хеш-образ сообщения:");
        for (int i = 1; i < inputMas.length+1; ++i) {
            int h = H[i - 1] + inMas[i - 1];
            H[i] = (h * h) % n;
            System.out.println("H" + i + " = " + " = ((H[" + (i - 1) + "]+ inMas[" + (i - 1) + "]) ^ 2) % " + n + " = ((" + H[i - 1] + " + " + inMas[i - 1] + ")^2)%" + n + " = " + H[i]);
        }

        System.out.println("ХЕШ-образ сообщения:"+H[message.length()]);
    }

}
