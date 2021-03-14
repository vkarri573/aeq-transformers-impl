package com.aeq.transformers.impl.app.exception;

public class GameInterruptedException extends  RuntimeException {
    public GameInterruptedException(String errorMsg) {
        super(errorMsg);
    }
}
