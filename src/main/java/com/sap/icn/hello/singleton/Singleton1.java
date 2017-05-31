package com.sap.icn.hello.singleton;

/**
 * Created by I321761 on 2017/5/31.
 */
public class Singleton1 {
    private static volatile Singleton1 instance;
    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        if (instance == null) {
            synchronized (Singleton1.class) {
                if (instance == null) {
                    instance = new Singleton1();
                }
            }
        }
        return instance;
    }
}
