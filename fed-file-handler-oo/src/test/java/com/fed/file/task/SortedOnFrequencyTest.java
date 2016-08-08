package com.fed.file.task;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import org.junit.Test;

import com.fed.file.task.SortedOnFrequency;

public class SortedOnFrequencyTest {

    @Test
    public void sortOnFrequency() {
        String[] wordArray = new String[] { "after", "before", "After", "before", "world", "before", "before" };
        List<String> words = Arrays.asList(wordArray);
        SortedOnFrequency frequencySorter = new SortedOnFrequency(words);
        SortedSet<Map.Entry<String, Integer>> sortedSet = frequencySorter.sort();

        Iterator<Map.Entry<String, Integer>> iterator = sortedSet.iterator();

        Entry<String, Integer> first = iterator.next();
        assertEquals(new Integer(4), first.getValue());
        assertEquals("before", first.getKey());

        Entry<String, Integer> second = iterator.next();
        assertEquals(new Integer(1), second.getValue());
        assertEquals("After", second.getKey());

        Entry<String, Integer> third = iterator.next();
        assertEquals(new Integer(1), third.getValue());
        assertEquals("after", third.getKey());

        Entry<String, Integer> fourth = iterator.next();
        assertEquals(new Integer(1), fourth.getValue());
        assertEquals("world", fourth.getKey());
    }

}
