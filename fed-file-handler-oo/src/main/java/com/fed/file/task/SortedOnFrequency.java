package com.fed.file.task;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fed.file.api.ISortedOnFrequency;

public final class SortedOnFrequency implements ISortedOnFrequency {
    private final List<String> allWords;
    
    public SortedOnFrequency(final List<String> allWords) {
        this.allWords = allWords;
    }
    
    @Override
    public SortedSet<Entry<String, Integer>> sort() {
        Map<String, Integer> frequencyMap = createUniqueWordMap();

        SortedSet<Map.Entry<String, Integer>> sortedByFrequencyThenWordSet = createBlankSortedSet();

        sortedByFrequencyThenWordSet.addAll(frequencyMap.entrySet());

        return sortedByFrequencyThenWordSet;
    }

    private Map<String, Integer> createUniqueWordMap() {
        Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
        for (String word : allWords) {
            Integer frequency = frequencyMap.get(word);
            frequency = (frequency == null) ? 1 : ++frequency;
            frequencyMap.put(word, frequency);
        }
        return frequencyMap;
    }

    private SortedSet<Map.Entry<String, Integer>> createBlankSortedSet() {
        SortedSet<Map.Entry<String, Integer>> sortedByFrequencyThenWordSet = new TreeSet<Map.Entry<String, Integer>>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
                int greater = entry2.getValue() - entry1.getValue();
                return greater != 0 ? greater : entry1.getKey().compareTo(entry2.getKey());
            }
        });
        return sortedByFrequencyThenWordSet;
    }

}
