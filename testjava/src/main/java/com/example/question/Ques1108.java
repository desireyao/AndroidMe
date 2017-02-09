package com.example.question;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Package com.example.question.
 * Created by yaoh on 2016/11/08.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class Ques1108 {

    public static void main(String[] args) {

        Animal dog = new Animal("dog",1);
        Animal cat = new Animal("cat",2);

        TreeSet<Animal> animals = new TreeSet<Animal>();
//        List<Animal> animals = new ArrayList<Animal>();
        animals.add(dog);
        animals.add(cat);
        System.out.println(animals);  // 1
    }
}
    class Animal implements Comparable<Animal>{
        private String name;
        private int order;
        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        Animal(String name,int order){
            this.name = name;
            this.order = order;
        }

        @Override
        public int compareTo(Animal animal){
            return order;
        }

        @Override
        public String toString(){
            return name;
        }
}
