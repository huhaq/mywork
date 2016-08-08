package com.fed.file.task;


import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.junit.Test;

import com.fed.file.task.SortedOnFrequency;
import com.fed.file.task.WordPrinter;

public class WordPrinterTest {

    @Test
    public void printWhenHaveMoreWordsThanRequiredToBePrinted() {
        String[] wordArray = new String[] { "after", "before", "After", "before", "world", "before", "before" };
        List<String> words = Arrays.asList(wordArray);
        SortedOnFrequency frequencySorter = new SortedOnFrequency(words);
        SortedSet<Map.Entry<String, Integer>> sortedSet = frequencySorter.sort();
        
        WordPrinter printer = new WordPrinter(sortedSet);
        assertEquals(sortedSet.size()-1, printer.print(sortedSet.size()-1, "ABC.txt"));
    }

    @Test
    public void printWhenHaveLessWordsThanRequiredToBePrinted() {
        String[] wordArray = new String[] { "after", "before", "After", "before", "world", "before", "before" };
        List<String> words = Arrays.asList(wordArray);
        SortedOnFrequency frequencySorter = new SortedOnFrequency(words);
        SortedSet<Map.Entry<String, Integer>> sortedSet = frequencySorter.sort();
        
        WordPrinter printer = new WordPrinter(sortedSet);
        assertEquals(sortedSet.size(), printer.print(sortedSet.size()+1, "ABC.txt"));
    }
}
