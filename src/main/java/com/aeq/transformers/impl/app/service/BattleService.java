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

/**
 * Provides business logic for Battle API.
 */
@Service
public class BattleService {

    @Autowired
    private BattleRepository battleRepository;
    @Autowired
    private BattleSpecialRuleExecutor specialRuleExecutor;
    @Autowired
    private BattleRuleExecutor basicRuleExecutor;
    private Logger LOG = LoggerFactory.getLogger(BattleService.class);

    /**
     * Fetches requested transformers.
     *
     * @param ids list of transformer ids to retrieve.
     * @return list of transformers retrieved from database.
     */
    public List<Transformer> getTransformersByIds(List<Long> ids) {
        List<Transformer> transformersList = new ArrayList<>();
        battleRepository.findAllById((Iterable<Long>) ids).forEach(transformersList::add);
        return transformersList;
    }

    /**
     * Conducts game for all transformers.
     *
     * @return final game result after evaluation.
     */
    public FinalGameResult conductGameForAllTransformers() {
        List<Transformer> transformersList = new ArrayList<>();
        battleRepository.findAll().forEach(transformersList::add);
        return conductGame(transformersList);
    }

    /**
     * Conducts game for selected transformers.
     *
     * @param transformerIds List of transformers to conduct game.
     * @return final game result after evaluation.
     */
    public FinalGameResult conductGameForSelectedTransformers(List<Long> transformerIds) {
        List<Transformer> transformersList = getTransformersByIds(transformerIds);
        return conductGame(transformersList);
    }

    /**
     * Conduct game between transformers.
     *
     * @param transformersList List of transformers participate in the game.
     * @return Final game result.
     */
    public FinalGameResult conductGame(List<Transformer> transformersList) {
        GameSummary gameSummary = new GameSummary();

        LOG.info("Transformers size: "+transformersList.size());
        // Filter Autobots & Decepticons
        List<Transformer> autobots = transformersList.stream().filter(e -> TEAM_A.equals(e.getTeam())).collect(Collectors.toList());
        List<Transformer> decepticons = transformersList.stream().filter(e -> TEAM_D.equals(e.getTeam())).collect(Collectors.toList());

        // Sort by rank
        Collections.sort(autobots);
        Collections.sort(decepticons);

        LOG.info("Autobots size: "+autobots.size());
        LOG.info("Decepticons size: "+decepticons.size());

        gameSummary.setAutobots(autobots);
        gameSummary.setDecepticons(decepticons);

        gameSummary = startGame(autobots, decepticons, gameSummary);

        // Evaluate game summary and return prepared result
        return evaluateGameSummary(gameSummary);
    }

    /**
     * Starts game.
     *
     * @param autobots list of Autobots participate in the game.
     * @param decepticons list of Decepticons participate in the game.
     * @param gameSummary Game Summary.
     * @return Summary after the game completes.
     */
   public GameSummary startGame(List<Transformer> autobots , List<Transformer> decepticons, GameSummary gameSummary) {
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
           // Execute battle special rules
           battle = specialRuleExecutor.executeBattleSpecialRules(battle);

           // Execute battle basic rules if result is empty after execution of special rules
           if(isBattleResultEmpty(battle))
              battle = basicRuleExecutor.executeBattleBasicRules(battle);

           //Evaluate battle result after executing rules and save each battle result in the summary.
           evaluateBattleResult(battle, gameSummary);
           battleCounter++;
       }
       return gameSummary;
   }

    /**
     * Evaluates each battle result and saves result in summary.
     *
     * @param battle contains battle details.
     * @param gameSummary contains game summary.
     */
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

    /**
     * Evaluates summary of the game which contains each battle result and prepares final result.
     *
     * @param gameSummary contains summary.
     * @return final result of the game.
     */
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

    /**
     * Derives default skipped survivors from loosing team who doesn't have fight.
     *
     * @param noOfBattles number of battles conducted.
     * @param loosingTeam list of loosing team transformers.
     * @return list of default survivors
     */
   private List<String> deriveDefaultSkippedSurvivors(int noOfBattles, List<Transformer> loosingTeam) {
       List<String> defaultSkippedSurvivors = new ArrayList<>();
        if(loosingTeam.size() > noOfBattles) {
         loosingTeam.subList(noOfBattles, loosingTeam.size()).forEach(e -> defaultSkippedSurvivors.add(e.getName()));
     }
        return defaultSkippedSurvivors;
   }

    /**
     * Utility to calculate number of battles to be conducted in a game.
     *
     * @param autobots contains list of Autobots.
     * @param decepticons contains list of Decepticons.
     * @return number of battles.
     */
   private int calculateNumberOfBattles(List<Transformer> autobots , List<Transformer> decepticons) {
      return (autobots.size() > decepticons.size() ? decepticons.size() : autobots.size());
   }

    /**
     * Utility to check battle result.
     *
     * @param battle contains details of the battle.
     * @return true if battle result is empty,
     *         otherwise returns false.
     */
    private boolean isBattleResultEmpty(Battle battle) {
        return ObjectUtils.isEmpty(battle.getBattleResult());
    }
}
