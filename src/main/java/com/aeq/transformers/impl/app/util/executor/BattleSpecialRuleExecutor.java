package com.aeq.transformers.impl.app.util.executor;

import static com.aeq.transformers.impl.app.constants.SuperTransformer.*;
import com.aeq.transformers.impl.app.model.Battle;
import static com.aeq.transformers.impl.app.model.BattleResult.*;

import org.springframework.stereotype.Component;

@Component
public class BattleSpecialRuleExecutor {

    public Battle executeBattleSpecialRules(Battle battle) {
        String autobotName = battle.getAutobot().getName();
        String decepticonName = battle.getDecepticon().getName();

        if(isEqualToEitherOfSpecialNames(autobotName)
                && isEqualToEitherOfSpecialNames(decepticonName)) {
            battle.setBattleResult(INTERRUPTED);
            return battle;
        } else if(isEqualToEitherOfSpecialNames(autobotName)) {
            battle.setBattleResult(WINNER_AUTOBOT);
        } else if(isEqualToEitherOfSpecialNames(decepticonName)) {
            battle.setBattleResult(WINNER_DECEPTICON);
        }
        return battle;
    }

    private boolean isEqualToEitherOfSpecialNames(String name) {
        return (OPTIMUS_PRIME.getName().equals(name)
                || PREDAKING.getName().equals(name));
    }
}
