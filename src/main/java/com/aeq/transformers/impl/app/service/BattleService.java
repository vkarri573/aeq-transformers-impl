package com.aeq.transformers.impl.app.service;

import com.aeq.transformers.impl.app.model.*;
import com.aeq.transformers.impl.app.repository.BattleRepository;
import com.aeq.transformers.impl.app.util.executor.BattleRuleExecutor;
import com.aeq.transformers.impl.app.util.executor.BattleSpecialRuleExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.aeq.transformers.impl.app.constants.BattleConstants.*;
import static com.aeq.transformers.impl.app.model.BattleResult.*;

@Service
public class BattleService {

    @Autowired
    private BattleRepository battleRepository;
    @Autowired
    private BattleSpecialRuleExecutor specialRuleExecutor;
    @Autowired
    private BattleRuleExecutor basicRuleExecutor;

    public List<Transformer> getTransformersByIds(List<Long> ids) {
        List<Transformer> transformersList = new ArrayList<>();
        battleRepository.findAllById((Iterable<Long>) ids).forEach(transformersList::add);
        return transformersList;
    }

    public FinalBattleResult conductFight(List<Long> ids) {
        BattleSummary battleSummary = new BattleSummary();
        List<Transformer> transformersList = getTransformersByIds(ids);

        List<Transformer> autobots = transformersList.stream().filter(e -> TEAM_A.equals(e.getName())).collect(Collectors.toList());
        List<Transformer> decepticons = transformersList.stream().filter(e -> TEAM_D.equals(e.getName())).collect(Collectors.toList());

        Collections.sort(autobots);
        Collections.sort(decepticons);

        battleSummary = startFight(autobots, decepticons, battleSummary);

        return evaluateFinalBattleSummary(battleSummary);
    }

   public BattleSummary startFight(List<Transformer> autobots , List<Transformer> decepticons, BattleSummary battleSummary) {
       int noOfBattles = calculateNumberOfBattles(autobots, decepticons);
       battleSummary.setNoOfBattles(noOfBattles);

       Battle battle = null;
       int battleCounter = 0;
       while(battleCounter < noOfBattles) {
           battle.setAutobot(autobots.get(battleCounter));
           battle.setDecepticon(decepticons.get(battleCounter));

           battle = specialRuleExecutor.executeBattleSpecialRules(battle);

           if(isBattleResultEmpty(battle))
              battle = basicRuleExecutor.executeBattleBasicRules(battle);

           evaluateBattleResult(battle, battleSummary);
           battleCounter++;
       }
       return battleSummary;
   }

   private void evaluateBattleResult(Battle battle, BattleSummary battleSummary) {
        BattleResult battleResult = battle.getBattleResult();
        if(battleResult == WINNER_AUTOBOT)
            battleSummary.getWinnerAutobots().add(battle.getAutobot());
        else if(battleResult == WINNER_DECEPTICON)
            battleSummary.getWinnerDecepticons().add(battle.getDecepticon());
        else if(battleResult == INTERRUPTED)
            throw new NullPointerException("Interrupted");
            // Throw Interrupted exception
   }

   public FinalBattleResult evaluateFinalBattleSummary(BattleSummary battleSummary) {
       FinalBattleResult finalBattleResult = new FinalBattleResult();
       finalBattleResult.setNumberOfBattles(battleSummary.getNoOfBattles());

       List<String> winnerMembersOfWinningTeam = new ArrayList<>();
       List<String> survivingMembersOfLosingTeam = new ArrayList<>();

       if(battleSummary.getWinnerAutobots().size() > battleSummary.getWinnerDecepticons().size()) {
           finalBattleResult.setWinningTeam(TEAM_AUTOBOTS_STR);
           finalBattleResult.setLosingTeam(TEAM_DECEPTICONS_STR);

           battleSummary.getWinnerAutobots().forEach(e -> winnerMembersOfWinningTeam.add(e.getName()));


           battleSummary.getWinnerDecepticons().forEach(e -> survivingMembersOfLosingTeam.add(e.getName()));
           survivingMembersOfLosingTeam.addAll(deriveDefaultSkippedSurvivors(battleSummary.getNoOfBattles(), battleSummary.getDecepticons()));

       } else if(battleSummary.getWinnerDecepticons().size() > battleSummary.getWinnerAutobots().size()) {
           finalBattleResult.setWinningTeam(TEAM_DECEPTICONS_STR);
           finalBattleResult.setLosingTeam(TEAM_AUTOBOTS_STR);

           battleSummary.getWinnerDecepticons().forEach(e -> winnerMembersOfWinningTeam.add(e.getName()));

           battleSummary.getWinnerAutobots().forEach(e -> survivingMembersOfLosingTeam.add(e.getName()));
           survivingMembersOfLosingTeam.addAll(deriveDefaultSkippedSurvivors(battleSummary.getNoOfBattles(), battleSummary.getWinnerAutobots()));
       } else {
           finalBattleResult.setWinningTeam(NONE_TIE_STR);
           finalBattleResult.setLosingTeam(NONE_TIE_STR);
       }

       finalBattleResult.setWinnerMembersOfWinningTeam(winnerMembersOfWinningTeam);
       finalBattleResult.setSurvivingMembersOfLosingTeam(survivingMembersOfLosingTeam);

       return finalBattleResult;
   }

   private List<String> deriveDefaultSkippedSurvivors(int noOfBattles, List<Transformer> loosingTeam) {
       List<String> defaultSkippedSurvivors = new ArrayList<>();
        if(loosingTeam.size() > noOfBattles) {
         loosingTeam.subList(noOfBattles, loosingTeam.size()).forEach(e -> defaultSkippedSurvivors.add(e.getName()));
     }
        return defaultSkippedSurvivors;
   }

   private int calculateNumberOfBattles(List<Transformer> autobots , List<Transformer> decepticons) {
      return (autobots.size() > decepticons.size() ? decepticons.size() : autobots.size());
   }

    private boolean isBattleResultEmpty(Battle battle) {
        return ObjectUtils.isEmpty(battle.getBattleResult());
    }
}
