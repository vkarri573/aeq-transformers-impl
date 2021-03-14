package com.aeq.transformers.impl.app.service;

import com.aeq.transformers.impl.app.model.*;
import com.aeq.transformers.impl.app.repository.BattleRepository;
import com.aeq.transformers.impl.app.rule.executor.BattleRuleExecutor;
import com.aeq.transformers.impl.app.rule.executor.BattleSpecialRuleExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger LOG = LoggerFactory.getLogger(BattleService.class);

    public List<Transformer> getTransformersByIds(List<Long> ids) {
        List<Transformer> transformersList = new ArrayList<>();
        battleRepository.findAllById((Iterable<Long>) ids).forEach(transformersList::add);
        return transformersList;
    }

    public FinalGameResult conductGame(List<Long> transformerIds) throws Exception {
        GameSummary gameSummary = new GameSummary();
        List<Transformer> transformersList = getTransformersByIds(transformerIds);

        LOG.info("Transformers size: "+transformersList.size());
        List<Transformer> autobots = transformersList.stream().filter(e -> TEAM_A.equals(e.getTeam())).collect(Collectors.toList());
        List<Transformer> decepticons = transformersList.stream().filter(e -> TEAM_D.equals(e.getTeam())).collect(Collectors.toList());

        Collections.sort(autobots);
        Collections.sort(decepticons);

        LOG.info("Autobots size: "+autobots.size());
        LOG.info("Decepticons size: "+decepticons.size());

        gameSummary.setAutobots(autobots);
        gameSummary.setDecepticons(decepticons);

        gameSummary = startGame(autobots, decepticons, gameSummary);

        return evaluateGameSummary(gameSummary);
    }

   public GameSummary startGame(List<Transformer> autobots , List<Transformer> decepticons, GameSummary gameSummary) throws Exception {
       int noOfBattles = calculateNumberOfBattles(autobots, decepticons);
       LOG.info("Number of battles: "+noOfBattles);
       gameSummary.setNoOfBattles(noOfBattles);

       Battle battle = null;
       int battleCounter = 0;
       while(battleCounter < noOfBattles) {
           battle = new Battle();
           battle.setAutobot(autobots.get(battleCounter));
           battle.setDecepticon(decepticons.get(battleCounter));

           LOG.info("Battle between Autobot: "+battle.getAutobot().getName()+" and Decepticon: "+battle.getDecepticon().getName());
           battle = specialRuleExecutor.executeBattleSpecialRules(battle);

           if(isBattleResultEmpty(battle))
              battle = basicRuleExecutor.executeBattleBasicRules(battle);

           evaluateBattleResult(battle, gameSummary);
           battleCounter++;
       }
       return gameSummary;
   }

   private void evaluateBattleResult(Battle battle, GameSummary gameSummary) {
        BattleResult battleResult = battle.getBattleResult();
        if(battleResult == WINNER_AUTOBOT) {
            LOG.info("Battle winner is Autobot: "+battle.getAutobot().getName());
            gameSummary.getWinnerAutobots().add(battle.getAutobot());
        }
        else if(battleResult == WINNER_DECEPTICON) {
            LOG.info("Battle winner is Decepticon: "+battle.getDecepticon().getName());
            gameSummary.getWinnerDecepticons().add(battle.getDecepticon());
        } else {
            LOG.info("Battle tied, both transformers are destroyed");
            battle.setBattleResult(TIE);
        }
   }

   public FinalGameResult evaluateGameSummary(GameSummary gameSummary) {
        LOG.info("Evaluate game summary and prepare final result");
       FinalGameResult finalGameResult = new FinalGameResult();
       finalGameResult.setNumberOfBattles(gameSummary.getNoOfBattles());

       List<String> winnerMembersOfWinningTeam = new ArrayList<>();
       List<String> survivingMembersOfLosingTeam = new ArrayList<>();

       // Compare Autobots winning size and Decepticons winning size
       if(gameSummary.getWinnerAutobots().size() > gameSummary.getWinnerDecepticons().size()) {
           LOG.info("Autobots team has more number of winners, so winning team is: Autobots");
           finalGameResult.setWinningTeam(TEAM_AUTOBOTS_STR);
           finalGameResult.setLosingTeam(TEAM_DECEPTICONS_STR);

           gameSummary.getWinnerAutobots().forEach(transformer -> winnerMembersOfWinningTeam.add(transformer.getName()));

           gameSummary.getWinnerDecepticons().forEach(transformer -> survivingMembersOfLosingTeam.add(transformer.getName()));
           //Derive skipped transformers from losing team who didn't have fight.
           survivingMembersOfLosingTeam.addAll(deriveDefaultSkippedSurvivors(gameSummary.getNoOfBattles(), gameSummary.getDecepticons()));

       } else if(gameSummary.getWinnerDecepticons().size() > gameSummary.getWinnerAutobots().size()) {
           LOG.info("Decepticons team has more number of winners, so winning team is: Decepticons");
           finalGameResult.setWinningTeam(TEAM_DECEPTICONS_STR);
           finalGameResult.setLosingTeam(TEAM_AUTOBOTS_STR);

           gameSummary.getWinnerDecepticons().forEach(e -> winnerMembersOfWinningTeam.add(e.getName()));

           gameSummary.getWinnerAutobots().forEach(e -> survivingMembersOfLosingTeam.add(e.getName()));
           //Derive skipped transformers from losing team who didn't have fight.
           survivingMembersOfLosingTeam.addAll(deriveDefaultSkippedSurvivors(gameSummary.getNoOfBattles(), gameSummary.getAutobots()));
       } else {
           LOG.info("Both teams have equal number of winners (or) No Battles");
           finalGameResult.setWinningTeam(NONE);
           finalGameResult.setLosingTeam(NONE);
       }

       finalGameResult.setWinnerMembersOfWinningTeam(winnerMembersOfWinningTeam);
       finalGameResult.setSurvivingMembersOfLosingTeam(survivingMembersOfLosingTeam);

       return finalGameResult;
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
