package com.mrd.altassianchatbot.parser;

import com.mrd.altassianchatbot.controller.ChatController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
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
                Document doc = null;
                JSONObject url = new JSONObject();
                try {
                    url.put("url",mention);
                    doc = Jsoup.connect(mention).get();
                    String title = doc.title();

                    url.put("title",title);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    jsonArray.put(url);
                }

            }
        }
        return jsonArray;
    }

}
