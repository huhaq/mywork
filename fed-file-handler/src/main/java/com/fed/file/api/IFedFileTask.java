package com.fed.file.api;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface IFedFileTask {
    List<String> splitFileIntoWords(File file);
    
    void removeStopWordsFromList(List<String> inWords, List<String> stopWords);
    
    void removeNonAlphabeticCharactersFromWords(List<String> words);

    void stemWordsToRootForm(List<String> words);
    
    SortedSet<Map.Entry<String,Integer>> sortOnDescendingFrequencyThenChronologicalText(List<String> words);
    
    void printTopWords(SortedSet<Map.Entry<String,Integer>> sorted, int howMany, String fileName);
}
