package com.sebastienguillemin.stups.util;

public class StringConverter {
    public static String toSnakeCase(String str) {
        String result = "";

        char c = str.charAt(0);
        result += Character.toLowerCase(c);

        for (int i = 1; i < str.length(); i++) {

            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                result = result + '_';
                result += Character.toLowerCase(ch);
            } 
            else
                result += ch;
        }

        return result;
    }
}
