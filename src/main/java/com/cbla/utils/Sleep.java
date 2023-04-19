package com.cbla.utils;

public class Sleep {

    public static void sleepthread(int delay) {
        try {
            int TimesThousand = delay * 1000;
            Thread.sleep(TimesThousand);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepthreadMillis(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
