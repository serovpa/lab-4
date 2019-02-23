package ru.avalon.java.dev.ocpjp.labs.core.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Абстрактное представление об объекте, позволяющем получать
 * из файла случайную строку.
 */
public interface RandomFileReader extends Closeable {

    /**
     * Возвращает случайную строку файла.
     *
     * @return экземпляр типа {@link String}
     */
    String readLine();

    /**
     * Фабричный метод, возвращающий реализацию интерфейса
     * {@link RandomFileReader}.
     *
     * @param file описатель файла
     * @return экземпляр типа {@link RandomFileReader}
     * @throws IOException в случае ошибки ввода-вывода
     */
    static RandomFileReader getInstance(File file) throws IOException {
        return new RandomFileReaderImpl(file);
    }

    /**
     * Фабричный метод, возвращающий реализацию интерфейса
     * {@link RandomFileReader}.
     *
     * @param pathname путь к файлу, хранящемуся в ресурсах
     *                 приложения
     * @return экземпляр типа {@link RandomFileReader}
     * @throws IOException в случае ошибки ввода-вывода
     */
    static RandomFileReader fromSystemResource(String pathname) throws IOException {
        try {
            URL url = ClassLoader.getSystemResource(pathname);
            File file = new File(url.toURI());
            return getInstance(file);
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }
}
