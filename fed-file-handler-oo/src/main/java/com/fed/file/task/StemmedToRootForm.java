package com.fed.file.task;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import libraries.Stemmer;

public final class StemmedToRootForm {
    private final List<String> allWords;

    public StemmedToRootForm(final List<String> allWords) {
        this.allWords = allWords;
    }
    
    public List<String> stem() {
        final List<String> tempWordList = createNewList();

        final Stemmer stemmer = new Stemmer();
        
        for (ListIterator<String> iterator = tempWordList.listIterator(); iterator.hasNext();) {
            String lowerCasedWord = iterator.next().toLowerCase();
            stemmer.add(lowerCasedWord.toCharArray(), lowerCasedWord.length());
            stemmer.stem();
            String stemmedWord = new String(stemmer.getResultBuffer(), 0, stemmer.getResultLength());
            iterator.set(stemmedWord);
        }
        
        return tempWordList;
    }

    private List<String> createNewList() {
        final List<String> tempWordList = new ArrayList<String>();
        tempWordList.addAll(allWords);
        return tempWordList;
    }
}
