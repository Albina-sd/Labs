import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioSecrets {

    public static void main (String [] args) throws IOException
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Что Вы хотели бы сделать? (введите число)");
        int command = 0;

        do
        {
            System.out.println("1 - Скрыть сообщение в аудио-файле");
            System.out.println("2 - Извлечь сообщение из аудио-файла");
            System.out.print("> ");
            command = scan.nextInt();
            System.out.println();

            if (command == 1)
                encode();
            if (command == 2)
                decode();

        } while (command != 1 && command != 2);

        System.out.println("Завершено.");
        scan.close();
    }


    public static void decode() throws IOException
    {
        Scanner scan = new Scanner(System.in);

        System.out.print("Введите путь к файлу, из которого требуется извлечь сообщение (wav)\n> ");
        String audioToDecode = scan.nextLine();
        if (!audioToDecode.endsWith(".wav")) // Проверка окончания имени файла (добавляет расширение, если не указано)
            audioToDecode += ".wav";

        byte[] audioEncoded = getAudioData(audioToDecode);

        byte[] decryptedData = LSBdecode(audioEncoded);

        String message = new String( byteArrayToCharArray(decryptedData) );

        System.out.print("Введите путь к файлу, куда записать результаты извлечения\n> ");
        String outputName = scan.nextLine();
        if (!outputName.endsWith(".txt"))
            outputName += ".txt";

// пишет сообщение в файл
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(outputName));
        writer.write(message);
        writer.close();
        scan.close();
    }

    public static void encode()
    {
        Scanner scan = new Scanner(System.in);

        byte[] message = new byte[1];
        System.out.print("Введите путь к файлу, который требуется скрыть\n> ");
        String textFileName = scan.next(); // получение имени файла
        System.out.println();
        if (!textFileName.endsWith(".txt")) // Проверка окончания имени файла (добавляет расширение, если не указано)
            textFileName += ".txt";

        File file = new File(textFileName);
        try {
// \\Z в комбинации с .next() будут читать до тех пор, пока есть, что читать
            message = charArrayToByteArray( new Scanner(file).useDelimiter("\\Z").next().toCharArray() );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        System.out.println("Введите путь к файлу-контейнеру (wav)");
        System.out.print("или, если вы хотите сгенерировать его автоматически, введите \"1\".\n> ");
        String audioFileName = scan.next();
        System.out.println();

        byte[] audioData;
        if (audioFileName.equals("1"))
        {
            audioData = makeAudio(message.length); // Генерация аудио
            System.out.print("Генерация. Введите конечный путь для файла.\n> ");
            audioFileName = scan.next();
            System.out.println();
            saveByteArrayAsAudioFile(audioData, audioFileName);
        }
        else
            audioData = getAudioData(audioFileName);


//Сначала кодируется общее количество байтов, которые будут закодированы в первые 32 бита аудиоданных
//Это позволяет определить длину сообщения прямо при чтении аудиофайла.

        byte[] messageLength = new byte[4]; // 4 байта в int
        int messageLengthInt = message.length;
        for (int i = 3; i >= 0; i--)
        {
            messageLength[i] = (byte)messageLengthInt; // хранение 1 байта в байтовом массиве
            messageLengthInt = messageLengthInt >> 8; // и затем сдвиг вправо на 8 бит, чтобы получить следующий байт в позиции
        }

        LSBencode(messageLength, audioData, 0);
        LSBencode(message, audioData, 32);

        System.out.print("Введите имя файла со скрытым сообщением\n> ");
        String outputFile = scan.next();
        System.out.println();
        if (!outputFile.endsWith(".wav")) // Проверка окончания имени файла (добавляет расширение, если не указано)
            outputFile += ".wav";
        saveByteArrayAsAudioFile(audioData, outputFile);
        scan.close();

    }

    // Обратим внимание, что начальный индекс находится в битах (поскольку один бит хранится в каждом аудиобайте),
// поэтому, если мы кодируете int в 0, то следующий начальный индекс будет 32.
    public static void LSBencode(byte[] message, byte[] audioData, int startIndex)
    {
        for (int audioDataIndex = startIndex, messageIndex = 0; audioDataIndex < startIndex+message.length * 8; audioDataIndex+=8, messageIndex++)
            for (int j = 0; j < 8; j++)
            {
// System.err.println((audioData[audioDataIndex+j] & ~1) ^ (message[messageIndex] & 1));
// (message[messageIndex] & 1) gets the least significant bit of the message
// (audioData[audioDataIndex+j] & ~1) gets all of the non-least significant bits of the audioData
// Then they are XORed together to combine them
                audioData[audioDataIndex+j] = (byte) ( (audioData[audioDataIndex+j] & ~1) ^ (message[messageIndex] & 1) );
                message[messageIndex] >>= 1;
            }
    }

    public static byte[] LSBdecode(byte[] encodedData)
    {
// чтение длины сообщения, которая является целым числом, хранящимся в первых 32 байтах audioData
// messageLength сообщает количество байтов, которые зашифрованы в аудиоданных (не включая себя)
        int messageLength = 0;

// encodedData[] & 1 приводит к наименее значимым битам.
// добавление этого бита к декодированным данным и затем сдвиг влево, чтобы подготовиться к следующему биту
        messageLength += encodedData[0] & 1;
        for (int i = 1; i < 32; i++)
        {
            messageLength <<= 1;
            messageLength += encodedData[i] & 1;
        }

        byte[] data = new byte[messageLength];
        for (int audioIndex = 32, dataIndex = 0; audioIndex < messageLength + 32; audioIndex+=8, dataIndex++)
        {
            data[dataIndex] += encodedData[audioIndex] & 1;
            for (int i = 7; i >= 0; i--)
            {
                data[dataIndex] <<= 1;
                data[dataIndex] += encodedData[audioIndex + i] & 1;
            }

            System.err.println("Байт " + dataIndex + " : " + data[dataIndex]);
        }

        return data;
    }

    // Делает файл из статики. 44100 измерений в секунду.
    public static byte[] makeAudio (int messageLength) {
// messageLength - это количество байтов, которые необходимо кодировать. Так что 8 * messageLength - байтов в аудио необходимо
        byte[] audio = new byte[ Math.max((8*messageLength)+44100, 5*44100)];
        Random rand = new Random();
        rand.nextBytes(audio);
        return audio;
    }

    //Возвращаем аудиоданные с указанием имени файла
    public static byte[] getAudioData(String filename) {
        byte[] audioData = null;
        AudioInputStream ais = null;

// Проверка окончания имени файла (добавляет расширение, если не указано)
        if (!filename.endsWith(".wav"))
        {
            filename += ".wav";
        }

        try {
// чтение из файла
            File file = new File(filename);
            if (file.exists()) {
                ais = AudioSystem.getAudioInputStream(file);
                audioData = new byte[ais.available()];
                ais.read(audioData);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Не удается прочитать файл " + filename);
        }

        return audioData;
    }

    public static void saveByteArrayAsAudioFile(byte[] data, String filename)
    {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            AudioInputStream ais = new AudioInputStream(bais, format, data.length);
            if (filename.toLowerCase().endsWith(".wav")) {
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
            }
            else if (filename.toLowerCase().endsWith(".au")) {
                AudioSystem.write(ais, AudioFileFormat.Type.AU, new File(filename));
            }
            else {
                throw new RuntimeException("Формат файла не поддерживается: " + filename);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public static int getAudioDuration(byte[] data)
    {
    // 44100 байт данных в секунду, поэтому просто разделим, чтобы получить общую длину в секундах
        return data.length / 44100;
    }


    // Char в Byte Array и обратно - безопаснее, чем использование встроенных методов String,
    // потому что они всегда будут использовать UTF-16
    public static byte[] charArrayToByteArray(char[] chars)
    {
        byte[] bytes = new byte[chars.length*2];
        for(int i=0;i<chars.length;i++) {
            bytes[i*2] = (byte) (chars[i] >> 8);
            bytes[i*2+1] = (byte) chars[i];
        }
        return bytes;
    }
    public static char[] byteArrayToCharArray(byte[] bytes)
    {
        char[] chars = new char[bytes.length / 2];
        for(int i=0;i<chars.length;i++)
            chars[i] = (char) ((bytes[i*2] << 8) + bytes[i*2+1]);

        return chars;
    }
}