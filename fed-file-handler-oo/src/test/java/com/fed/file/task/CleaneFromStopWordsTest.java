package com.fed.file.task;


import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.junit.Test;

import com.fed.file.exception.FileHandlingException;

public class CleaneFromStopWordsTest {
    private static final String IN_STRING = "Create a java program which reads in a text file.\nTake a nap first.";
    private static final String STOP_STRING = "Create nap";

    @Test
    public void cleanStopWords() {
        List<String> inputList = createList(IN_STRING);
        List<String> stopList = createList(STOP_STRING);
        CleaneFromStopWords cleaner = new CleaneFromStopWords(inputList, stopList);
        List<String> cleaned = cleaner.clean();

        assertEquals(12, cleaned.size());
        assertEquals("a", cleaned.get(0));
        assertEquals("first.", cleaned.get(11));
    }

    private List<String> createList(String text) {
        File file = createFileFromString(text);
        FileWords fileWords = new FileWords(file);
        return fileWords.words();
    }
    
    private File createFileFromString(String inputString) {
        InputStream inStream = new ByteArrayInputStream(inputString.getBytes());
        return openFile(inStream);
    }

    private File openFile(InputStream inputStream) {
        OutputStream outStream = null;
        try {
            File temporaryFile = File.createTempFile("PREFIX", "SUFFIX");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            outStream = new FileOutputStream(temporaryFile);
            outStream.write(buffer);
            return temporaryFile;
        } catch (IOException e) {
            throw new FileHandlingException(e);
        } finally {
            closeOutputStream(outStream);
        }
    }

    private void closeOutputStream(OutputStream outStream) {
        try {
            if (outStream != null) {
                outStream.close();
            }
        } catch (IOException e) {
            throw new FileHandlingException(e);
        }
    }
    
}
