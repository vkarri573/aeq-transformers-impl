package com.aeq.transformers.impl.app.constants;

/**
 * Represents special Transformers.
 *
 * 1. Optimus Prime
 * 2. Predaking
 */
public enum SuperTransformer {
    OPTIMUS_PRIME("Optimus Prime"),
    PREDAKING("Predaking");

    private String name;

    SuperTransformer(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
