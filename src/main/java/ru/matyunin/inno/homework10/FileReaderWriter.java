package ru.matyunin.inno.homework10;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/***
 * @author Артём Матюнин
 * Работает с потоками ввода/вывода
 * Используется для изменения строк кода в файле MyClass.java
 */

public class FileReaderWriter {

    /**
     * Построчно, пока есть что читать, сохраняет считанные строки в LinkedList и возвращает его.
     *
     * @param fileName имя файла, из которого нужно считать строки. Использует BufferedReader.
     */
    public List<String> readFile(String fileName) {
        List<String> readResult = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            while (bufferedReader.ready()) {
                readResult.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readResult;
    }

    /**
     * Каждый элемент writeList записывает в отдельную строку файла fileName
     *
     * @param writeList лист, содержащий строки, которые нужно записать в файл
     * @param fileName  имя файла для записи
     */
    public void writeResult(List<String> writeList, String fileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String s : writeList
            ) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
