package com.fed.file.task;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.fed.file.task.StemmedToRootForm;

public class StemmedToRootFormTest {

    @Test
    public void stemWordsToRoot() {
        String[] wordArray = new String[] { "Jumping", "jUmps", "jumP" };
        List<String> words = Arrays.asList(wordArray);
        StemmedToRootForm stemmer = new StemmedToRootForm(words);
        List<String> stemmed = stemmer.stem();
        assertEquals("jump", stemmed.get(0));
        assertEquals("jump", stemmed.get(1));
        assertEquals("jump", stemmed.get(2));
    }

}
