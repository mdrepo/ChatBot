package com.mrd.altassianchatbot.parser;


import com.mrd.altassianchatbot.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mayurdube on 24/03/17.
 */
public class Parser {

    public static String stemsByRule(String message) {

        StringBuilder input = new StringBuilder(message);
        int patternStart = -1;
        ArrayList<Rule> rules = new ArrayList<>();
        rules.add(new MentionsRule());
        rules.add(new EmoticonsRule());
        rules.add(new UrlRule());
        rules.add(new HashRule());

        HashMap<String, ArrayList<String>> patternsFound = new HashMap<>();


        for (Rule rule : rules) {
            System.out.println("*********** " + rule.getType() + " ***********");
            if (!rule.hasRegex()) {

                StringBuilder pattern = new StringBuilder();
                boolean restart = false;
                do {
                    for (int i = 0; i < input.length(); i++) {
                        char c = input.charAt(i);
                        if (patternStart == -1 && rule.hasPatternStarted(c)) {
                            patternStart = i;
                        } else if (rule.isEndFound(pattern,c) == Rule.PATTERN_FINISH && patternStart != -1) {
                            input = input.replace(patternStart, i, "");
                            patternStart = -1;
                            System.out.println(pattern.toString());
                            ArrayList<String> patternsForThisRule = patternsFound.get(rule.getType());
                            if (patternsForThisRule == null) {
                                patternsForThisRule = new ArrayList<>();
                                patternsFound.put(rule.getType(),patternsForThisRule);
                            }
                            patternsForThisRule.add(pattern.toString());
                            pattern = new StringBuilder();
                            restart = true;
                            break;
                        } else if(rule.isEndFound(pattern,c) == Rule.PATTERN_ABORT && patternStart != -1) {
                            pattern = new StringBuilder();
                            patternStart = -1;
                        } else if (patternStart != -1) {
                            pattern.append(c);
                        }

                        if(i == input.length()-1) {
                            restart = false;
                            if(patternStart != -1 && Rule.PATTERN_CONTINUE == rule.isEndFound(input,input.charAt(i))) {
                                input = input.replace(patternStart, i+1, "");
                                patternStart = -1;
                                System.out.println(pattern.toString());
                                ArrayList<String> patternsForThisRule = patternsFound.get(rule.getType());
                                if (patternsForThisRule == null) {
                                    patternsForThisRule = new ArrayList<>();
                                    patternsFound.put(rule.getType(),patternsForThisRule);
                                }
                                patternsForThisRule.add(pattern.toString());

                                pattern = new StringBuilder();

                            }
                        }
                    }


                } while (restart);

            } else {
                String spaceRegex = "\\s";
                Pattern spacePattern = Pattern.compile(spaceRegex);
                Pattern rulePattern = Pattern.compile(rule.regex());
                String[] arr = spacePattern.split(input);
                System.out.println("Input : " +  input);
                for (String value : arr) {
                    Matcher a = rulePattern.matcher(value);
                    if (a.find()) {
                        ArrayList<String> patternsForThisRule = patternsFound.get(rule.getType());
                        if (patternsForThisRule == null) {
                            patternsForThisRule = new ArrayList<>();
                            patternsFound.put(rule.getType(),patternsForThisRule);
                        }
                        System.out.println(rule.getType() + " " + a.group());
                        patternsForThisRule.add(a.group());
                        input = input.replace(a.start(), a.end(), " ");
                    }
                }

            }
        }

        Iterator<Map.Entry<String,ArrayList<String>>> iterator = patternsFound.entrySet().iterator();
        JSONObject responseObject = new JSONObject();
        while (iterator.hasNext()) {
            Map.Entry<String,ArrayList<String>> mapEntry = iterator.next();
            Rule rule = RuleFactory.getInstance(mapEntry.getKey());
            JSONArray arr = rule.getJSON(mapEntry.getValue());
            try {
                responseObject.put(mapEntry.getKey(),arr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String str = JsonUtils.formatJson(responseObject.toString());
        System.out.println(str);
        return str;
    }
}
