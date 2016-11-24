package com.example.TESTList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Package com.example.TESTList.
 * Created by yaoh on 2016/11/24.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class Main {

    public static void main(String[] args){

        testArrayList();
        testLinkList();
    }

    private static void testArrayList() {
        List<String> list = new ArrayList<String>();
        System.out.println("ArrayList add start: " + System.currentTimeMillis());
        for (int i = 0; i < 100000; i++){
            list.add("i=" + i);
        }
        System.out.println("ArrayList add stop: " + System.currentTimeMillis());

        String string = list.get(50000);
        System.out.println("ArrayList get stop: " + System.currentTimeMillis());

    }

    private static void testLinkList() {
        List<String> list = new LinkedList<>();
        System.out.println("LinkedList add start: " + System.currentTimeMillis());
        for (int i = 0; i < 100000; i++){
            list.add("i=" + i);
        }
        System.out.println("LinkedList add stop: " + System.currentTimeMillis());
        String string = list.get(50000);
        System.out.println("LinkedList get stop: " + System.currentTimeMillis());
    }
}
