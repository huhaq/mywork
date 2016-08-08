package com.fed.file.task;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.fed.file.task.CleanFromNonAlphabeticCharacters;

public class CleanFromNonAlphabeticCharactersTest {

    @Test
    public void cleanNonAlphabeticCharacters() {
        String[] wordArray = new String[] { "word", "inp,ut", "world." };
        List<String> words = Arrays.asList(wordArray);
        CleanFromNonAlphabeticCharacters cleaner = new CleanFromNonAlphabeticCharacters(words);
        List<String> cleaned = cleaner.clean();
        assertEquals("word", cleaned.get(0));
        assertEquals("input", cleaned.get(1));
        assertEquals("world", cleaned.get(2));
    }

}
