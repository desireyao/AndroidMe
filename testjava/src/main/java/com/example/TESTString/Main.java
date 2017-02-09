package com.example.TESTString;

/**
 * Package com.example.TESTString.
 * Created by yaoh on 2016/12/07.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class Main {

    public static void main(String[] args){

        String str=null;
        str=String.format("%1$d,%2$s", 99,"abc");
        System.out.println(str);
        System.out.printf("%+d%d%n", 99,-99);
        System.out.printf("%03d%n", 7);
        System.out.printf("% 8d%n", 7);
        System.out.printf("%,d%n", 9989997);
        System.out.printf("% 50.5f%n", 49.8);
    }
}
