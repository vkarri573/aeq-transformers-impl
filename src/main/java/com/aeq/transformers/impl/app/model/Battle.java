package com.aeq.transformers.impl.app.model;

public class Battle {
    private Transformer autobot;
    private Transformer decepticon;
    private BattleResult battleResult;

    public Transformer getAutobot() {
        return autobot;
    }

    public void setAutobot(Transformer autobot) {
        this.autobot = autobot;
    }

    public Transformer getDecepticon() {
        return decepticon;
    }

    public void setDecepticon(Transformer decepticon) {
        this.decepticon = decepticon;
    }

    public BattleResult getBattleResult() {
        return battleResult;
    }

    public void setBattleResult(BattleResult battleResult) {
        this.battleResult = battleResult;
    }
}
