import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class QAtest {

    public static void main(String[] args) throws Exception {

        File myFile = new File("C:/Users/Владислав/Desktop/project/QAtest/QAtest.txt");
        //File myFile = new File("C:/Users/Владислав/Desktop/text.txt");

        long startTime = System.currentTimeMillis();
        System.out.println("Количество строк в файле QAtest: " + lineReader(myFile));
        long endTime = System.currentTimeMillis();
        System.out.println("Время на выполнение подсчета с помощью LineNumberReader: " + (endTime - startTime) + "ms");

        System.out.println("-----------------------------------------------------------------");

        long startTime2 = System.currentTimeMillis();
        System.out.println("Количество строк в файле QAtest: " + fileScanner(myFile));
        long endTime2 = System.currentTimeMillis();
        System.out.println("Время на выполнение подсчета с помощью FileScanner: " + (endTime2 - startTime2) + "ms");

        System.out.println("-----------------------------------------------------------------");

        long startTime3 = System.currentTimeMillis();
        System.out.println("Количество строк в файле QAtest: " + countLinesNew(myFile));
        long endTime3 = System.currentTimeMillis();
        System.out.println("Время на выполнение подсчета с помощью countLinesNew: " + (endTime3 - startTime3) + "ms");

        System.out.println("-----------------------------------------------------------------");

        long startTime4 = System.currentTimeMillis();
        System.out.println("Количество строк в файле QAtest: " + getLineCount(myFile));
        long endTime4 = System.currentTimeMillis();
        System.out.println("Время на выполнение подсчета с помощью getlineCount: " + (endTime4 - startTime4) + "ms");

        System.out.println("-----------------------------------------------------------------");

    }

/*
    public static int lineReader(File myFile) throws IOException {
        LineNumberReader reader  = new LineNumberReader(new FileReader(myFile));
        int cnt = 0;
        String lineRead = "";
        while ((lineRead = reader.readLine()) != null) {

        }

        cnt = reader.getLineNumber();
        reader.close();
        return cnt;
    }

*/

    public static int lineReader(File myFile) throws IOException {
        //FileInputStream inputStream = new FileInputStream("C:/Users/Владислав/Desktop/text.txt");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(myFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        assert fileReader != null;
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);

        int lineNumber = 0;

        while (lineNumberReader.readLine() != null) {
            lineNumber++;
        }
        //System.out.println(lineNumber);
        lineNumberReader.close();
        return lineNumber;
    }

    public static int fileScanner(File myFile) throws IOException {

        Scanner fileScanner = null;
        int lineCount = 0;
        int countChar = 0;

        Pattern lineEndPattern = Pattern.compile("(?m)$");
        try {
            fileScanner = new Scanner(new File(String.valueOf(myFile))).useDelimiter(lineEndPattern);
            while (fileScanner.hasNext()) {

                String name = fileScanner.next();
                char[] chArray = name.toCharArray();
                for (char c : chArray) {
                    //находим символ
                    if (c == 'a') {
                        countChar++;
                    }
                }

                lineCount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return lineCount;
        }
        fileScanner.close();
        System.out.println("Количество букв 'a' в файле QAtest: " + countChar);
        return lineCount;

    }

    public static int countLinesNew(File myFile) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(myFile));
        try {
            byte[] c = new byte[1024];

            int readChars = is.read(c);
            if (readChars == -1) {
                // bail out if nothing to read
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0;
            int countT = 0;
            long countChar = 0;

            while (readChars == 1024) {
                for (int i = 0; i < 1024; i++) {
                    if (c[i] == '\n') {
                        ++count;
                    }

                    if (c[i] == 97) {
                        ++countChar;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                //System.out.println(readChars);
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                    if (c[i] == 97) {
                        ++countChar;
                    }

                    if (c[i] == (' ')) {
                        ++count;
                    }
                }
                readChars = is.read(c);

            }

            if (countT != 0) {
                count = count + countT;
            }

            System.out.println("Количество букв 'a' в файле QAtest: " + countChar);
            return count == 0 ? 1 : count;
        } finally {
            is.close();
        }
    }
    
    public static int getLineCount(File file) throws IOException {
        int result = 0;

        try
                (
                        FileReader       input = new FileReader(file);
                        LineNumberReader count = new LineNumberReader(input);
                )
        {
            while (count.skip(Long.MAX_VALUE) > 0)
            {
                // Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read the entire file
            }

            result = count.getLineNumber() + 1;                                    // +1 because line index starts at 0
        }
        return result;
    }
}