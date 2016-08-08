package com.fed.file.task;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.fed.file.api.ICleanFromNonAlphabeticCharacters;

public final class CleanFromNonAlphabeticCharacters implements ICleanFromNonAlphabeticCharacters {
    private final List<String> allWords;

    public CleanFromNonAlphabeticCharacters(final List<String> allWords) {
        this.allWords = allWords;
    }

    @Override
    public List<String> clean() {
        final List<String> tempWordList = new ArrayList<String>();
        tempWordList.addAll(allWords);

        for (ListIterator<String> iterator = tempWordList.listIterator(); iterator.hasNext();) {
            String word = iterator.next();
            String cleaned = word.replaceAll("[^A-Za-z]", "");
            removeEmptyCleanedWord(iterator, cleaned);
        }
        
        return tempWordList;
    }

    private void removeEmptyCleanedWord(ListIterator<String> iterator, String cleaned) {
        if (cleaned.isEmpty()) {
            iterator.remove();
        } else {
            iterator.set(cleaned);
        }
    }
}
