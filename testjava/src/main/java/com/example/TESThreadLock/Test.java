package com.example.TESThreadLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Package com.example.TESThreadLock.
 * Created by yaoh on 2016/11/24.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class Test {

    ReentrantLock lock = new ReentrantLock();
    ReentrantLock lock2 = new ReentrantLock();

    public void test(){
        for (int i = 0; i < 10; i++){
            new Thread(new TaskOne()).start();
              new Thread(new TaskTwo()).start();
        }
    }

    class TaskOne implements Runnable{

        @Override
        public void run() {
            lock.lock();
            System.out.println("come in taskone");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
            System.out.println("come out taskone");
        }
    }

    class TaskTwo implements Runnable{
        @Override
        public void run() {
            lock2.lock();
            System.out.println("come in TaskTwo");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock2.unlock();
            System.out.println("come out TaskTwo");
        }
    }
}
