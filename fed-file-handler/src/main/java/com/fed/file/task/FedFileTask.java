package com.fed.file.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import libraries.Stemmer;

import com.fed.file.api.IFedFileTask;
import com.fed.file.exception.FileHandlingException;

public class FedFileTask implements IFedFileTask {

    @Override
    public List<String> splitFileIntoWords(File file) {
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

    @Override
    public void removeStopWordsFromList(List<String> wordList, List<String> stopWords) {
        for (Iterator<String> iterator = wordList.iterator(); iterator.hasNext();) {
            String word = iterator.next();
            if (stopWords.contains(word)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void removeNonAlphabeticCharactersFromWords(List<String> wordList) {
        for (ListIterator<String> iterator = wordList.listIterator(); iterator.hasNext();) {
            String word = iterator.next();
            String cleaned = word.replaceAll("[^A-Za-z]", "");
            removeEmptyCleanedWord(iterator, cleaned);
        }
    }

    @Override
    public void stemWordsToRootForm(List<String> words) {
        Stemmer stemmer = new Stemmer();
        for (ListIterator<String> iterator = words.listIterator(); iterator.hasNext();) {
            String lowerCasedWord = iterator.next().toLowerCase();
            stemmer.add(lowerCasedWord.toCharArray(), lowerCasedWord.length());
            stemmer.stem();
            String stemmedWord = new String(stemmer.getResultBuffer(), 0, stemmer.getResultLength());
            iterator.set(stemmedWord);
        }
    }

    @Override
    public SortedSet<Map.Entry<String, Integer>> sortOnDescendingFrequencyThenChronologicalText(List<String> words) {
        Map<String, Integer> frequencyMap = createUniqueWordMap(words);

        SortedSet<Map.Entry<String, Integer>> sortedByFrequencyThenWordSet = createBlankSortedSet();

        sortedByFrequencyThenWordSet.addAll(frequencyMap.entrySet());

        return sortedByFrequencyThenWordSet;
    }

    @Override
    public void printTopWords(SortedSet<Entry<String, Integer>> sorted, int howMany, String fileName) {
        System.out.println("*** Printing " + howMany + " words Descending frequency then Ascending chronological text from File " + fileName);
        Iterator<Entry<String, Integer>> entryIterator = sorted.iterator();
        for (int index = 0; index < howMany && index < sorted.size() && entryIterator.hasNext(); index++) {
            Entry<String, Integer> entry = entryIterator.next();
            System.out.println(entry.getKey() + " occurs " + entry.getValue() + " times;");
        }
        System.out.println("");
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

    private Map<String, Integer> createUniqueWordMap(List<String> words) {
        Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
        for (String word : words) {
            Integer frequency = frequencyMap.get(word);
            frequency = (frequency == null) ? 1 : ++frequency;
            frequencyMap.put(word, frequency);
        }
        return frequencyMap;
    }

    private void removeEmptyCleanedWord(ListIterator<String> iterator, String cleaned) {
        if (cleaned.isEmpty()) {
            iterator.remove();
        } else {
            iterator.set(cleaned);
        }
    }

    private void closeReader(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            throw new FileHandlingException(e);
        }
    }

}
