package com.aeq.transformers.impl.app.util.executor;

import static com.aeq.transformers.impl.app.constants.BattleConstants.*;
import com.aeq.transformers.impl.app.model.Battle;
import static com.aeq.transformers.impl.app.model.BattleResult.*;
import com.aeq.transformers.impl.app.model.Transformer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class BattleRuleExecutor {

    public Battle executeBattleBasicRules(Battle battle) {
        Transformer autobot = battle.getAutobot();
        Transformer decepticon = battle.getDecepticon();

        battle = executeCourageAndStrengthRule(battle, autobot, decepticon);

        if(!isBattleResultEmpty(battle))
             return battle;

         battle = executeSkillCompareRule(battle, autobot, decepticon);

        if(!isBattleResultEmpty(battle))
            return battle;

        battle = executeOverallRatingCompareRule(battle, autobot, decepticon);

        return battle;
    }

    private Battle executeCourageAndStrengthRule(Battle battle, Transformer autobot, Transformer decepticon) {
        Integer autobotCourage = autobot.getCourage();
        Integer decepticonCourage = decepticon.getCourage();

        Integer autobotStrength = autobot.getStrength();
        Integer decepticonStrength = decepticon.getStrength();

        if(hasCourageDiffrence(decepticonCourage, autobotCourage)
                && hasStrengthDifference(decepticonStrength, autobotStrength)) {
            battle.setBattleResult(WINNER_DECEPTICON);
        } else if(hasCourageDiffrence(autobotCourage, decepticonCourage)
                && hasStrengthDifference(autobotStrength, decepticonStrength)) {
            battle.setBattleResult(WINNER_AUTOBOT);
        }
        return battle;
    }

    private Battle executeSkillCompareRule(Battle battle, Transformer autobot, Transformer decepticon) {
       Integer autobotSkill = autobot.getSkill();
       Integer decepticonSkill = decepticon.getSkill();

        if(hasSkillDifference(decepticonSkill, autobotSkill)) {
            battle.setBattleResult(WINNER_DECEPTICON);
        } else if(hasSkillDifference(autobotSkill, decepticonSkill)) {
            battle.setBattleResult(WINNER_AUTOBOT);
        }

        return battle;
    }

    private Battle executeOverallRatingCompareRule(Battle battle, Transformer autobot, Transformer decepticon) {
        Integer autobotRating = autobot.getOverallRating();
        Integer decepticRating = decepticon.getOverallRating();

        if(decepticRating > autobotRating)
            battle.setBattleResult(WINNER_DECEPTICON);
        else if(autobotRating > decepticRating)
            battle.setBattleResult(WINNER_AUTOBOT);

        return battle;
    }

    private boolean hasCourageDiffrence(Integer courage1, Integer courage2){
         return ((courage1 - courage2) >= COURAGE_MIN_DIFF);
    }

    private boolean hasStrengthDifference(Integer strength1, Integer strength2) {
        return ((strength1 - strength2) >= STRENGTH_MIN_DIFF);
    }

    private boolean hasSkillDifference(Integer skll1, Integer skill2){
        return ((skll1 - skill2) >= SKILL_MIN_DIFF);
    }

    private boolean isBattleResultEmpty(Battle battle) {
        return ObjectUtils.isEmpty(battle.getBattleResult());
    }
}
