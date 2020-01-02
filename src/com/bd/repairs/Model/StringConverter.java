package com.bd.repairs.Model;

import java.util.ArrayList;

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

    public static int convertText(String string) {
        int result = 0;
        String num = "";
        boolean end=false;
        char[] chars=string.toCharArray();
        for (int i=0;i<chars.length||end;i++) {
            char c=chars[i];
            if (c==32) {
                end=true;
            }
            result+=c;
        }
        return result;
    }
}
