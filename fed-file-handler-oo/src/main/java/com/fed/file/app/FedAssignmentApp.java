package com.fed.file.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import com.fed.file.exception.FileHandlingException;
import com.fed.file.task.CleanFromNonAlphabeticCharacters;
import com.fed.file.task.CleaneFromStopWords;
import com.fed.file.task.FileWords;
import com.fed.file.task.SortedOnFrequency;
import com.fed.file.task.StemmedToRootForm;
import com.fed.file.task.WordPrinter;

public class FedAssignmentApp {

    private static final int WORD_TO_PRINT = 20;

    public static void main(String[] args) {
        FedAssignmentApp app = new FedAssignmentApp();
        app.applyAlgorithm();
    }

    private void applyAlgorithm() {
        List<String> stopWords = getStopWords();

        String[] fileNames = new String[] { "Text1.txt", "Text2.txt" };
        for (int index = 0; index < fileNames.length; index++) {
            List<String> allFileWords = splitFileIntoWords(fileNames[index]);
            List<String>  stopWordsRemoved = removeStopWords(allFileWords, stopWords);
            List<String>  nonAlphabeticCharsRemoved = cleanNonAlphabeticCharacters(stopWordsRemoved);
            List<String>  stemmed = stemWordsToRootForm(nonAlphabeticCharsRemoved);
            SortedSet<Map.Entry<String, Integer>> sortedSet = sortOnDescendingFrequencyThenChronologicalText(stemmed);
            printWords(sortedSet, fileNames[index]);
        }
    }

    private void printWords(SortedSet<Entry<String, Integer>> sortedSet, String fileName) {
        new WordPrinter(sortedSet).print(WORD_TO_PRINT, fileName);
    }

    private List<String> stemWordsToRootForm(List<String> nonAlphabeticCharsRemoved) {
        return new StemmedToRootForm(nonAlphabeticCharsRemoved).stem();
    }

    private SortedSet<Entry<String, Integer>> sortOnDescendingFrequencyThenChronologicalText(List<String> nonAlphabeticCharsRemoved) {
        return new SortedOnFrequency(nonAlphabeticCharsRemoved).sort();
    }

    private List<String> cleanNonAlphabeticCharacters(List<String> stopWordsRemoved) {
        return new CleanFromNonAlphabeticCharacters(stopWordsRemoved).clean();
    }

    private List<String> removeStopWords(List<String> allFileWords, List<String> stopWords) {
        return new CleaneFromStopWords(allFileWords, stopWords).clean();
    }

    private List<String>  splitFileIntoWords(String fileName) {
        return new FileWords(openFile(fileName)).words();
    }

    private List<String> getStopWords() {
        return new FileWords(openFile("stopwords.txt")).words();
    }

    private File openFile(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = getInputStreamFor(fileName);
            return openFile(inputStream);
        } finally {
            closeInputStream(inputStream);
        }
    }

    private final File openFile(InputStream inputStream) {
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

    private final void closeInputStream(InputStream inStream) {
        try {
            if (inStream != null) {
                inStream.close();
            }
        } catch (IOException e) {
            throw new FileHandlingException(e);
        }
    }

    private final void closeOutputStream(OutputStream outStream) {
        try {
            if (outStream != null) {
                outStream.close();
            }
        } catch (IOException e) {
            throw new FileHandlingException(e);
        }
    }

    private final InputStream getInputStreamFor(String fileName) {
        InputStream resourcePropStream = null;
        try {
            resourcePropStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        } catch (Exception e) {
        }
        return resourcePropStream;
    }

}
