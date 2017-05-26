package com.sap.icn.hello.storage;

/**
 * Created by I321761 on 2017/5/26.
 */
public class StorageFileNotFoundException extends StorageException {
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
