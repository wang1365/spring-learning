package com.sap.icn.hello.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by I321761 on 2017/5/26.
 */
@ConfigurationProperties("storage")
public class StorageProperties {
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
