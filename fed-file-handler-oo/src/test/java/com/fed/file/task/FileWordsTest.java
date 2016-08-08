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

public class FileWordsTest {
    private static final String IN_STRING = "Create a java program which reads in a text file.\nTake a nap first.";

    @Test
    public void test() {
        File file = createFileFromString(IN_STRING);
        FileWords fileWords = new FileWords(file);
        List<String> words = fileWords.words();
        assertEquals(14, words.size());
        assertEquals("Create", words.get(0));
        assertEquals("first.", words.get(13));
        
    }

    private File createFileFromString(String inputString) {
        InputStream inStream = new ByteArrayInputStream(IN_STRING.getBytes());
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
