package com.sap.icn.hello.singleton;

/**
 * Created by I321761 on 2017/5/31.
 */
public class Singleton2 {
    private static class SingletonHolder {
        private static Singleton2 instance = new Singleton2();
    }

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return SingletonHolder.instance;
    }
}
