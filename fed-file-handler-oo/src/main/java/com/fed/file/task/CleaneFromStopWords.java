package com.fed.file.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fed.file.api.ICleaneFromStopWords;

public final class CleaneFromStopWords implements ICleaneFromStopWords {
    private final List<String> allWords;
    private final List<String> stopWords;
    
    public CleaneFromStopWords(final List<String> allWords, final List<String> stopWords) {
        this.allWords = allWords;
        this.stopWords = stopWords;
    }
    
    @Override
    public List<String> clean() {
        final List<String> tempWordList = new ArrayList<String>();
        tempWordList.addAll(allWords);
        for (Iterator<String> iterator = tempWordList.iterator(); iterator.hasNext();) {
            String word = iterator.next();
            if (stopWords.contains(word)) {
                iterator.remove();
            }
        }
        return tempWordList;
    }

}
