package com.bd.repairs.Model;

public class StringConverter {
    public static int convert(String string) {
        int result = 0;
        String num = "";
        int i=0;
        for (char c : string.toCharArray()) {
            if ((c >= '0') && (c <= '9') && (i < 6)) {
                num += c;
            }
            ++i;
        }
        result = Integer.parseInt(num);
        return result;
    }
}
