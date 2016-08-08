package com.fed.file.app;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import org.junit.Test;

import com.fed.file.exception.FileHandlingException;
import com.fed.file.task.FedFileTask;
import com.fed.file.util.FileUtility;

public class FedFileTaskTest {

    private static final String IN_STRING = "When in the Course of human events it becomes necessary for one people to dissolve "
                    + "the political bands which have connected them with another and to assume among the powers "
                    + "of the earth, the separate and equal station to which the Laws of Nature and of Nature's "
                    + "God entitle them, a decent respect to the opinions of mankind requires that they should declare "
                    + "the causes which impel them to the separation.\nWe hold these truths to be self-evident, "
                    + "that all men are created equal, that they are endowed by their Creator with certain unalienable "
                    + "Rights, that among these are Life, Liberty and the pursuit of Happiness. ? That to secure these rights, "
                    + "Governments are instituted among Men, deriving their just powers from the consent of the governed, ? "
                    + "That whenever any Form of Government becomes destructive of these ends, it is the Right of the People "
                    + "to alter or to abolish it, and to institute new Government, laying its foundation on such principles "
                    + "and organizing its powers in such form, as to them shall seem most likely to effect their "
                    + "Safety and Happiness. Prudence, indeed, will dictate that Governments long established should "
                    + "not be changed for light and transient causes; and accordingly all experience hath shewn that mankind "
                    + "are more disposed to suffer, while evils are sufferable than to right themselves by abolishing the "
                    + "forms to which they are accustomed. But when a long train of abuses and usurpations, pursuing invariably "
                    + "the same Object evinces a design to reduce them under absolute Despotism, it is their right, it is their duty, "
                    + "to throw off such Government, and to provide new Guards for their future security. ? "
                    + "Such has been the patient sufferance of these Colonies; and such is now the necessity which constrains "
                    + "them to alter their former Systems of Government. The history of the present King of Great Britain is a "
                    + "history of repeated injuries and usurpations, all having in direct object the establishment of an absolute "
                    + "Tyranny over these States. To prove this, let Facts be submitted to a candid world.";

    @Test
    public void splitIntoWordsWhenFileIsValid() {
        File file = createFileFromString(IN_STRING);

        FedFileTask task = new FedFileTask();
        List<String> words = task.splitFileIntoWords(file);

        int expectedLastWordIndex = words.lastIndexOf("world.");
        assertEquals("When", words.get(0));
        assertEquals(expectedLastWordIndex, words.size() - 1);
        assertEquals("world.", words.get(expectedLastWordIndex));
    }

    @Test(expected = FileHandlingException.class)
    public void splitIntoWordsThrowsExceptionWhenInvalidFile() {
        FedFileTask task = new FedFileTask();
        task.splitFileIntoWords(null);
    }

    @Test
    public void removeStopWordsFromWordList() {
        String[] stopWordArray = new String[] { "in", "it" };
        List<String> stopWordList = Arrays.asList(stopWordArray);

        List<String> inputWordsWithTwoStopWords = new ArrayList<String>();
        inputWordsWithTwoStopWords.add("When");
        inputWordsWithTwoStopWords.add("in");
        inputWordsWithTwoStopWords.add("the");
        inputWordsWithTwoStopWords.add("Course");
        inputWordsWithTwoStopWords.add("of");
        inputWordsWithTwoStopWords.add("human");
        inputWordsWithTwoStopWords.add("events");
        inputWordsWithTwoStopWords.add("it");
        inputWordsWithTwoStopWords.add("becomes");
        inputWordsWithTwoStopWords.add("necessary");

        int originalNoOfWords = inputWordsWithTwoStopWords.size();

        FedFileTask task = new FedFileTask();
        task.removeStopWordsFromList(inputWordsWithTwoStopWords, stopWordList);

        int expectedNumberOfWordsAfterStopWordRemoval = originalNoOfWords - 2;
        assertEquals(expectedNumberOfWordsAfterStopWordRemoval, inputWordsWithTwoStopWords.size());
        assertEquals("the", inputWordsWithTwoStopWords.get(1));
        assertEquals("necessary", inputWordsWithTwoStopWords.get(7));
    }

    @Test
    public void cleanWordsByRemovingNonAlphabeticCharacters() {
        String[] wordArray = new String[] { "word", "inp,ut", "world." };
        List<String> words = Arrays.asList(wordArray);
        FedFileTask task = new FedFileTask();
        task.removeNonAlphabeticCharactersFromWords(words);
        assertEquals("word", words.get(0));
        assertEquals("input", words.get(1));
        assertEquals("world", words.get(2));
    }

    @Test
    public void stemWords() {
        String[] wordArray = new String[] { "Jumping", "jUmps", "jumP" };
        List<String> words = Arrays.asList(wordArray);
        FedFileTask task = new FedFileTask();
        task.stemWordsToRootForm(words);
        assertEquals("jump", words.get(0));
        assertEquals("jump", words.get(1));
        assertEquals("jump", words.get(2));
    }

    @Test
    public void countFrequency() {
        String[] wordArray = new String[] { "after", "before", "After", "before", "world", "before", "before" };
        List<String> words = Arrays.asList(wordArray);
        FedFileTask task = new FedFileTask();
        SortedSet<Map.Entry<String, Integer>> sortedSet = task.sortOnDescendingFrequencyThenChronologicalText(words);

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

    private File createFileFromString(String inputString) {
        InputStream inStream = new ByteArrayInputStream(IN_STRING.getBytes());
        return FileUtility.openFile(inStream);
    }

}
