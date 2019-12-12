package com.bd.repairs.Model;

public class StringConverter {
    public static int convert(String string){
        int result=0;
        String num="";
        for(char c:string.toCharArray()){
            if ((c >= '0') && (c <= '9')&&(num.length()<10)){
                num+=c;
            }
        }
        result=Integer.parseInt(num);
        return result;
    }
}
