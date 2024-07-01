package com.resolute.zero.templates;

import java.util.Map;

public class Test {

    public static String getFormattedTemplate(Map<Integer, String> arguments, Templates template) {
        String formattedString = template.template;
        for (int i = 1; i <= template.arguments.size(); i++) {
            Object value = arguments.get(i);
            if (value != null) {
                formattedString = formattedString.replace("{{" + i + "}}", value.toString());
            }
        }
        return formattedString;
    }


}
