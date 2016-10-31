package com.example;

public class MyClass {

//    private static Bank bank;

    public static void main(String[] args){
        System.out.print("------------");
//        bank = new Bank();
        for (int i = 0; i < 10; i ++){
            new Thread(new Task()).start();
        }
    }

    static class Task implements Runnable{

        @Override
        public void run() {
            Bank bank = new Bank();
            bank.getMoney();
        }
    }
}
