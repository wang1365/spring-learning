package com.sap.icn.hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by I321761 on 2017/5/24.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {
    private Long id;
    private String quote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "Value {id=" + id + ", quote=" + quote + "}";
    }
}
