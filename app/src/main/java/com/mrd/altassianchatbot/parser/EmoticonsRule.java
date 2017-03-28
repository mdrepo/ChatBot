package com.mrd.altassianchatbot.parser;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by mayurdube on 24/03/17.
 */
public class EmoticonsRule implements Rule {

    @Override
    public String getType() {
        return "emoticons";
    }

    @Override
    public boolean hasPatternStarted(char c) {
        return c == '(';
    }

    @Override
    public int isEndFound(StringBuilder b,char c) {
        int pattern = PATTERN_CONTINUE;
        if(c == ')')
            pattern = PATTERN_FINISH;
        else if(!Character.isLetterOrDigit(c))
            pattern =  PATTERN_ABORT;
        else if(b.length() > 15){
            pattern = PATTERN_ABORT;
        }
        return pattern;
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
    public StringBuilder add(StringBuilder b,String c) throws RuleFailedException {
        if(b.length() < 15) {
            b.append(c);
        } else {
            throw new RuleFailedException("EmoticonsRule string cannot be more than 15 chars long");
        }
        return b;
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
