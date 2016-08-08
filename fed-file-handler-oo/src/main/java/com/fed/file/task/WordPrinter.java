package com.fed.file.task;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import com.fed.file.api.IPrinter;

public final class WordPrinter implements IPrinter {
    private final SortedSet<Map.Entry<String, Integer>> sorted;

    public WordPrinter(final SortedSet<Map.Entry<String, Integer>> sorted) {
        this.sorted = sorted;
    }

    @Override
    public int print(int howManyWords, String fileName) {
        int wordsPrinted = 0;
        System.out.println("*** Printing " + howManyWords + " words Descending frequency then Ascending chronological text from File " + fileName);
        for (Iterator<Entry<String, Integer>> entryIterator = sorted.iterator(); wordsPrinted < howManyWords && wordsPrinted < sorted.size() && entryIterator.hasNext(); wordsPrinted++) {
            Entry<String, Integer> entry = entryIterator.next();
            System.out.println(entry.getKey() + " occurs " + entry.getValue() + " times;");

        }
        System.out.println("");
        return wordsPrinted;
    }

}
