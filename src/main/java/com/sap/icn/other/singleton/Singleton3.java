package com.sap.icn.other.singleton;

/**
 * Created by I321761 on 2017/5/31.
 */
public class Singleton3 {
    private static Singleton3 instance = new Singleton3();

//    static {
//        instance = new Singleton3();
//    }

    private Singleton3() {}

    public static Singleton3 getInstance() {
        return instance;
    }
}
