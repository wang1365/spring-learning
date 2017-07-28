package com.sap.icn.other.singleton;

/**
 * Created by I321761 on 2017/5/31.
 */
public class MyTest {
    static class A {}
    static class B extends A {
        public void foo() {
            System.out.println(super.getClass().getName());
        }
    }

    public static void main(String[] args) {
        new B().foo();
    }
}
