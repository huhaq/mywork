package com.fed.file.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fed.file.api.IFileWords;
import com.fed.file.exception.FileHandlingException;

public final class FileWords implements IFileWords {
    private final File file;
    
    public FileWords(final File file) {
        this.file = file;
    }
    
    @Override
    public final List<String> words() {
        List<String> words = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            String line;
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split(" ");
                words.addAll(Arrays.asList(lineWords));
            }
        } catch (Exception e) {
            throw new FileHandlingException(e);
        } finally {
            if (reader != null) {
                closeReader(reader);
            }
        }
        return words;
    }

    private void closeReader(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            throw new FileHandlingException(e);
        }
    }
}
