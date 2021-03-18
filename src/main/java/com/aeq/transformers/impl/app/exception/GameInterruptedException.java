package com.aeq.transformers.impl.app.exception;

/**
 * Thrown when there is battle between special transformers(Optimus Prime, Predaking)
 */
public class GameInterruptedException extends  RuntimeException {
    public GameInterruptedException(String errorMsg) {
        super(errorMsg);
    }
}
