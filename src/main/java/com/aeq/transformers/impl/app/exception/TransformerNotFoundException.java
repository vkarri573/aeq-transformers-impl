package com.aeq.transformers.impl.app.exception;

public class TransformerNotFoundException extends RuntimeException {
    public TransformerNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
