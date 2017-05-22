package com.sap.icn.hello;

/**
 * Created by I321761 on 2017/5/22.
 */
public class Greeting {
    private final long id;
    private final String content;
    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
