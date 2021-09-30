package com.until;

public class HelperSleep {

    static final Object monitor = new Object();

    public static void sleep(int time){
        synchronized (monitor){
            try {
                monitor.wait(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
