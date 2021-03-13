package com.aeq.transformers.impl.app.constants;

import jdk.nashorn.internal.objects.annotations.Getter;

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
