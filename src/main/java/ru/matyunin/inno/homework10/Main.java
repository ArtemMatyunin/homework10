package ru.matyunin.inno.homework10;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.*;

/***
 * @author Артём Матюнин
 * Отредактируем файл MyClass.java, зарузим и вызовем метод doWork через MyIntarface
 * Заранее подготовлены файлы MyClass.java и MyInterface.java за пределами src
 *
 */

public class Main {

    /**
     * Компилирование java-файла
     *
     * @param filename - путь и название java-файла, который нужно скомпилировать
     */
    private static void compileJavaFile(String filename) {
        File file = new File(filename);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        System.out.println("Compiling " + file.getName());
        int result = compiler.run(null, null, null, file.getAbsolutePath());
        if (result != 0) {
            throw new RuntimeException();
        }
    }

    /***
     * Вызов кастомного загрузика MyClassLoader и загрузка класса
     * @param className - название файла .class, который будем загружать
     *
     */

    private static Class<?> loadMyClass(String className) {

        MyClassLoader myClassLoader = new MyClassLoader();

        Class<?> simpleClass = null;
        try {
            simpleClass = myClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Загружен " + simpleClass);

        return simpleClass;
    }

    public static void main(String[] args) {
        //зададим название файла
        final String fileClassName = "MyClass";
        final String extent = ".java";
        final String finalFileName = fileClassName + extent;

        //считаем файл в readList коллекцию для последующего редактирования
        FileReaderWriter fileReaderWriter = new FileReaderWriter();
        List<String> readList = fileReaderWriter.readFile(finalFileName);

        System.out.println("Исходный файл:");
        for (String s : readList
        ) {
            System.out.println(s);

        }
        System.out.println("Введите текст по-строчно. Если ничего не введено и нажат Enter, то ввод завершен");

        //построчно введем текст
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        int index = 5;
        while (!inputString.isEmpty()) {
            readList.add(index, inputString);
            inputString = scanner.nextLine();
            index++;
        }
        System.out.println("Для записи в файл нажмите Enter");
        scanner.nextLine();
        fileReaderWriter.writeResult(readList, finalFileName);


        System.out.println("Теперь файл выглядит так: ");
        for (String s : readList) {
            System.out.println(s);
        }


        System.out.println("Для компиляции файла нажмите Enter");
        scanner.nextLine();
        compileJavaFile(finalFileName);


        System.out.println("Для загрузки класса нажмите Enter");
        scanner.nextLine();
        final Class<?> myClass = loadMyClass(fileClassName);

        System.out.println("Сработал загрузчик: " + myClass.getClassLoader());

        System.out.println("Для вызова метода нажмите Enter");
        scanner.nextLine();
        try {
            MyInterface myDownloadClass = (MyInterface) myClass.newInstance();
            myDownloadClass.doWork();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
