package com.mrd.altassianchatbot;

import com.mrd.altassianchatbot.parser.Parser;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mayurdube on 28/03/17.
 */

public class ParserTest {

    @Test
    public void checkMentions() {
        HashMap<String,ArrayList<String>> sampleData = new HashMap<>();
        ArrayList<String> mentionList = new ArrayList<>();
        mentionList.add("chris");
        sampleData.put("mentions",mentionList);

        Parser parser = new Parser();
        parser.stemsByRule("@chris you around?");
        assertEquals(sampleData,parser.patternsFound);
    }

    @Test
    public void checkHash() {
        HashMap<String,ArrayList<String>> sampleData = new HashMap<>();
        ArrayList<String> mentionList = new ArrayList<>();
        mentionList.add("test");
        mentionList.add("awesome");
        sampleData.put("hash",mentionList);

        Parser parser = new Parser();
        parser.stemsByRule("#test #awesome");
        assertEquals(sampleData,parser.patternsFound);
    }

    @Test
    public void checkEmoticons() {
        HashMap<String,ArrayList<String>> sampleData = new HashMap<>();
        ArrayList<String> mentionList = new ArrayList<>();
        mentionList.add("megusta");
        mentionList.add("coffee");
        sampleData.put("emoticons",mentionList);

        Parser parser = new Parser();
        parser.stemsByRule("Good morning! (megusta) (coffee)");
        assertEquals(sampleData,parser.patternsFound);
    }

    @Test
    public void checkUrl() {
        HashMap<String,ArrayList<String>> sampleData = new HashMap<>();
        ArrayList<String> mentionList = new ArrayList<>();
        mentionList.add("https://tixdo.com");
        sampleData.put("urls",mentionList);

        Parser parser = new Parser();
        parser.stemsByRule("risk    https://tixdo.com    ");
        assertEquals(sampleData,parser.patternsFound);
    }

    @Test
    public void checkMix() {
        HashMap<String,ArrayList<String>> sampleData = new HashMap<>();
        ArrayList<String> mentionList = new ArrayList<>();
        mentionList.add("bob");
        mentionList.add("john");
        sampleData.put("mentions",mentionList);

        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("https://twitter.com/jdorfman/status/430511497475670016");
        sampleData.put("urls",urlList);

        ArrayList<String> emoticonList = new ArrayList<>();
        emoticonList.add("success");
        sampleData.put("emoticons",emoticonList);

        Parser parser = new Parser();
        parser.stemsByRule("@bob@john(success) such a cool feature;https://twitter.com/jdorfman/status/430511497475670016\"");
        assertEquals(sampleData,parser.patternsFound);
    }


}
