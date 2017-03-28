package com.mrd.altassianchatbot.parser;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by mayurdube on 27/03/17.
 */
public class UrlRule implements Rule {

    @Override
    public String getType() {
        return "urls";
    }

    @Override
    public boolean hasPatternStarted(char c) {
        return false;
    }

    @Override
    public int isEndFound(StringBuilder b,char c) {
        return PATTERN_ABORT;
    }

    @Override
    public String regex() {
        return "((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";
    }

    @Override
    public boolean hasRegex() {
        return true;
    }

    @Override
    public StringBuilder add(StringBuilder b, String c) throws RuleFailedException {
        return null;
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
