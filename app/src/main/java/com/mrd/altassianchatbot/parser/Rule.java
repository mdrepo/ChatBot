package com.mrd.altassianchatbot.parser;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by mayurdube on 24/03/17.
 */
public interface Rule {

    public static final int PATTERN_CONTINUE = 1;
    public static final int PATTERN_ABORT = -1;
    public static final int PATTERN_FINISH = 0;
    public String getType();
    public boolean hasPatternStarted(char c);
    public int isEndFound(StringBuilder b, char c);
    public String regex();
    public boolean hasRegex();
    public StringBuilder add(StringBuilder b, String c) throws RuleFailedException;
    public JSONArray getJSON(ArrayList<String> list);
}
