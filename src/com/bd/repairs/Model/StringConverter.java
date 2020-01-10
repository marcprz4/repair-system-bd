package com.bd.repairs.Model;

public class StringConverter {
    public static int convert(String string) {
        int result = 0;
        boolean wasNumber=false;
        String num = "";
        int i = 0;
        for (char c : string.toCharArray()) {
            if(!Character.isDigit(c)&&wasNumber){
                i=10;
            }
            if (Character.isDigit(c)&&i<6) {
                num += c;
                wasNumber=true;
            }
            ++i;
        }
        result = Integer.parseInt(num);
        return result;
    }

    public static String convertText(String string) {
        String result = "";
        boolean end = false;
        char[] chars = string.toCharArray();
        for (int i = 0; end != true; i++) {
            char c = chars[i];
            if (Character.isWhitespace(c)) {
                end = true;
            }
            if (!end)
                result += c;
        }
        return result;
    }
}
