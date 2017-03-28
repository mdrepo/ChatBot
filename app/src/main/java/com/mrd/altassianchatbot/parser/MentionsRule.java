package com.mrd.altassianchatbot.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mayurdube on 24/03/17.
 */
public class MentionsRule implements Rule {
    @Override
    public String getType() {
        return "mentions";
    }

    @Override
    public boolean hasPatternStarted(char c) {
        return c == '@';
    }

    @Override
    public int isEndFound(StringBuilder b, char c) {
        return !Character.isLetterOrDigit(c) ? PATTERN_FINISH : PATTERN_CONTINUE;
    }

    @Override
    public String regex() {
        return null;
    }

    @Override
    public boolean hasRegex() {
        return false;
    }

    @Override
    public StringBuilder add(StringBuilder b, String c) throws RuleFailedException {
        return b.append(c);
    }

    public JSONArray getJSON(ArrayList<String> list) {
        JSONArray jsonArray = null;
        if(list != null && list.size() > 0) {
            jsonArray = new JSONArray();
            for(String mention:list) {
                jsonArray.put(mention);
            }
        }
        return jsonArray;
    }
}
