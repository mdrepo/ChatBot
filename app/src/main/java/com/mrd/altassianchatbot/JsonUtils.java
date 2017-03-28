package com.mrd.altassianchatbot;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by mayurdube on 28/03/17.
 */

public class JsonUtils {

    public static String formatJson(String text) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            formatJson(stringBuilder, 0, new JSONObject(text));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static void formatJson(StringBuilder builder, int level, JSONObject jsonObject) throws JSONException {
        if (jsonObject != null && builder != null) {
            builder
                    .append(tabs(level))
                    .append("{");

            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                builder.append("\n");

                String key = keys.next();
                Object o = jsonObject.get(key);

                builder
                        .append(tabs(level + 1))
                        .append("\"")
                        .append(key)
                        .append("\"")
                        .append(" : ");

                if (o instanceof JSONArray) {
                    formatJson(builder, level + 1, (JSONArray) o);
                } else if (o instanceof JSONObject) {
                    formatJson(builder, level + 1, (JSONObject) o);
                } else if (o instanceof String) {
                    builder
                            .append("\"")
                            .append(o)
                            .append("\"");
                } else {
                    builder.append(o);
                }

                if (keys.hasNext()) {
                    builder.append(",");
                }
            }

            builder
                    .append("\n")
                    .append(tabs(level))
                    .append("}");
        }
    }

    public static void formatJson(StringBuilder builder, int level, JSONArray jsonArray) throws JSONException {
        if (jsonArray != null && builder != null) {
            int len = jsonArray.length();

            builder.append(" [");

            for (int i = 0; i < len; i++) {
                builder.append("\n");

                Object o = jsonArray.get(i);
                if (o instanceof JSONObject) {
                    formatJson(builder, level + 1, (JSONObject) o);
                } else if (o instanceof String) {
                    builder
                            .append(tabs(level + 1))
                            .append("\"")
                            .append(o)
                            .append("\"");
                } else {
                    builder
                            .append(tabs(level + 1))
                            .append(o);
                }

                if (len - i != 1) {
                    builder.append(",");
                }
            }

            builder
                    .append("\n")
                    .append(tabs(level))
                    .append("]");
        }
    }

    private static String tabs(int level) {
        String tabs = "";

        for (int i = 0; i < level; i++) {
            tabs += "\t";
        }

        return tabs;
    }
}
