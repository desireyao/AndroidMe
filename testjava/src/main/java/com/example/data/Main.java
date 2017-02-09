package com.example.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Package com.example.data.
 * Created by yaoh on 2016/11/23.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class Main {

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        int a = 0xee | 0xff;
        int b = 0xee & 0xff;

//         System.out.println(" a: " + Integer.toHexString(a) + "\n b: " + Integer.toHexString(b));
//        System.out.print(String.format("%.2f", Double.parseDouble("84.646")));

        getNumber();

    }

    private static void test2() {
        System.out.println("Byte.MAX_VALUE: " + Byte.MAX_VALUE + " Byte.MIN_VALUE: " + Byte.MIN_VALUE);
        System.out.println("Character.MAX_VALUE: " + Character.MAX_VALUE + " Character.MIN_VALUE: " + Character.MIN_VALUE);
        System.out.println("Integer.MAX_VALUE: " + Integer.MAX_VALUE + " Integer.MIN_VALUE: " + Integer.MIN_VALUE);
        System.out.println("Float.MAX_VALUE: " + Float.MAX_VALUE + " Float.MIN_VALUE: " + Float.MIN_VALUE);
        System.out.println("Short.MAX_VALUE: " + Short.MAX_VALUE + " Float.MIN_VALUE: " + Short.MIN_VALUE);
        System.out.println("Long.MAX_VALUE: " + Long.MAX_VALUE + " Float.MIN_VALUE: " + Long.MIN_VALUE);
        System.out.println("Double.MAX_VALUE: " + Double.MAX_VALUE + " Float.MIN_VALUE: " + Double.MIN_VALUE);
    }

    private static void getNumber() {
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 1; i < 999; i++) {
            list.add(i);
        }

        for (int i = 1002; i < 1999; i++) {
            list.add(i);
        }

        //-------------------------------------------
        int nearNum = 1000;

        int diffNum = Math.abs(list.get(0) - nearNum);
        int result = list.get(0);
        for (Integer integer : list) {
            int diffNumTemp = Math.abs(integer - nearNum);
            if (diffNumTemp < diffNum) {
                diffNum = diffNumTemp;
                result = integer;
            }
        }
        System.out.println(result);


    }
}
