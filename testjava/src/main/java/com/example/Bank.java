package com.example;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Package com.example.
 * Created by yaoh on 2016/10/21.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class Bank {

    private static int totalMoney = 1000;

    ReadWriteLock rwlock = new ReentrantReadWriteLock();

    public void getMoney() {
        rwlock.readLock().lock();
        System.out.println  ("start_getMoney,total: " + totalMoney);
        try {
            Thread.sleep(5000);
            System.out.println("end_getMoney,total: " + totalMoney);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rwlock.readLock().unlock();
    }

//    public  void addMoney() {
//
//        rwlock.writeLock().lock();
//        System.out.println("start_addMoney");
//        totalMoney += 1;
//        System.out.println("end_addMoney: " + totalMoney);
//        rwlock.writeLock().unlock();
//    }

    public  void addMoney() {

        synchronized (Bank.class){
           System.out.println("start_addMoney");
           totalMoney += 1;
           System.out.println("end_addMoney: " + totalMoney);
        }
    }
}