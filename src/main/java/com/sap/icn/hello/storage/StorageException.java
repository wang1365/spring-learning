package com.sap.icn.hello.storage;

/**
 * Created by I321761 on 2017/5/26.
 */
public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
