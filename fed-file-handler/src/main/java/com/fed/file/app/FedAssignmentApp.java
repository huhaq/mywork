package com.fed.file.app;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import com.fed.file.api.IFedFileTask;
import com.fed.file.task.FedFileTask;
import com.fed.file.util.FileUtility;

public class FedAssignmentApp {

    private void applyAlgorithm() {
        List<String> stopWords = getStopWords();
        
        String[] fileNames = new String[] { "Text1.txt", "Text2.txt" };
        for (int index = 0; index < fileNames.length; index++) {
            applyAlgorithmToPrintTopWords(stopWords, fileNames[index]);
        }
    }
    
    private List<String> getStopWords() {
        File stopWordFile = FileUtility.openFile("stopwords.txt");
        List<String> stopWords = new FedFileTask().splitFileIntoWords(stopWordFile);
        return stopWords;
    }

    private void applyAlgorithmToPrintTopWords(List<String> stopWords, String fileName) {
        IFedFileTask task = new FedFileTask();
        List<String> allWords = task.splitFileIntoWords(FileUtility.openFile(fileName));
        task.removeStopWordsFromList(allWords, stopWords);
        task.removeNonAlphabeticCharactersFromWords(allWords);
        task.stemWordsToRootForm(allWords);
        SortedSet<Map.Entry<String, Integer>> sortedSet = task.sortOnDescendingFrequencyThenChronologicalText(allWords);
        task.printTopWords(sortedSet, 20, fileName);
    }

    public static void main(String[] args) {
        FedAssignmentApp app = new FedAssignmentApp();
        app.applyAlgorithm();
    }

}
