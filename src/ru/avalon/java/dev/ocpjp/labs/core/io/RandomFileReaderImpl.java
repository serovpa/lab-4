package ru.avalon.java.dev.ocpjp.labs.core.io;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static java.util.Objects.nonNull;

public final class RandomFileReaderImpl implements RandomFileReader {

    private FileReader reader;

    private Random random = new Random();

    RandomFileReaderImpl(File file) throws IOException {
        reader = FileReader.getInstance(file);
    }

    @Override
    public String readLine() {
        try {
            int linesCount = reader.getLinesCount();
            int line = random.nextInt(linesCount);
            return reader.readLine(line);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        assert nonNull(reader);
        reader.close();
        reader = null;
    }
}
