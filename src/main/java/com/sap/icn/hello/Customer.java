package com.sap.icn.hello;

/**
 * Created by I321761 on 2017/5/24.
 */
public class Customer {
    private int id;
    private String firstName, lastName;

    public Customer(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("Customer [id:%d, name:%s %s]", id, firstName, lastName);
    }
}
