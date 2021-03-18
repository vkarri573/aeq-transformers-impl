package com.aeq.transformers.impl.app.rule.executor;

import static com.aeq.transformers.impl.app.constants.SuperTransformer.*;

import com.aeq.transformers.impl.app.exception.GameInterruptedException;
import com.aeq.transformers.impl.app.model.Battle;
import static com.aeq.transformers.impl.app.model.BattleResult.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility contains below battle special rules and executes as per the request.
 *
 * Rule #1: Check for 'Optimus prime'.
 * Rule #2: Check for 'Predaking'.
 */
@Component
public class BattleSpecialRuleExecutor {
    private Logger LOG = LoggerFactory.getLogger(BattleSpecialRuleExecutor.class);

    /**
     * Executes battle special rules.
     *
     * @param battle contains the participant details of the battle.
     * @return battle with result after executing rules.
     */
    public Battle executeBattleSpecialRules(Battle battle) {
        String autobotName = battle.getAutobot().getName();
        String decepticonName = battle.getDecepticon().getName();

        // Check for Special names -  Optimus Prime or Predaking
        if(isEqualToEitherOfSpecialNames(autobotName)
                && isEqualToEitherOfSpecialNames(decepticonName)) {
            LOG.info("Both participants are  either Optimus Prime or Predaking ");
            battle.setBattleResult(INTERRUPTED);
            throw new GameInterruptedException("Game interrupted, both participants are  either Optimus Prime or Predaking");
        } else if(isEqualToEitherOfSpecialNames(autobotName)) {
            LOG.info("Autobot is  either Optimus Prime or Predaking ");
            battle.setBattleResult(WINNER_AUTOBOT);
        } else if(isEqualToEitherOfSpecialNames(decepticonName)) {
            LOG.info("Decepticon is  either Optimus Prime or Predaking ");
            battle.setBattleResult(WINNER_DECEPTICON);
        }
        return battle;
    }

    /**
     * Utility to check for 'Optimus prime' or 'Predaking'.
     *
     * @param name contains name of the transformer.
     * @return true if transformer is either 'Optimus prime' or 'Predaking',
     *         otherwise return false.
     */
    private boolean isEqualToEitherOfSpecialNames(String name) {
        if(name != null)
            name = name.trim();

        return (OPTIMUS_PRIME.getName().equalsIgnoreCase(name)
                || PREDAKING.getName().equalsIgnoreCase(name));
    }
}
