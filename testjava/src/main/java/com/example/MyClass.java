package com.example;

public class MyClass {

//    private static Bank bank;

    public static void main(String[] args){
        System.out.print("start: \n");
//        bank = new Bank();
//        for (int i = 0; i < 10; i ++){
//            new Thread(new Task()).start();
//        }
        testByte();

    }

    static class Task implements Runnable{
        @Override
        public void run() {
            Bank bank = new Bank();
            bank.getMoney();
        }
    }

    static void testByte(){
        byte[] res = new byte[]{0x0,0x12,0x33,0x44};
        System.out.print(res.length + "res[1]: " + (res[2] & 0xff));
    }
}
