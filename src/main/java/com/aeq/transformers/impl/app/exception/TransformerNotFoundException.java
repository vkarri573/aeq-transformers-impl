package com.aeq.transformers.impl.app.exception;

/**
 * Thrown when Transformer not found in database.
 */
public class TransformerNotFoundException extends RuntimeException {
    public TransformerNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
