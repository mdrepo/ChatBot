package com.mrd.altassianchatbot.parser;

/**
 * Created by mayurdube on 28/03/17.
 */

public class RuleFactory {

    public static Rule getInstance(String type) {
        Rule rule = null;
        switch (type) {
            case "hash":
                rule = new HashRule();
                break;
            case "emoticons":
                rule = new EmoticonsRule();
                break;
            case "urls":
                rule = new UrlRule();
                break;
            case "mentions":
                rule = new MentionsRule();
                break;

        }
        return rule;
    }
}
