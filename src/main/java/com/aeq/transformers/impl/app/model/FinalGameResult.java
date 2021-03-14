package com.aeq.transformers.impl.app.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FinalGameResult {

    private int numberOfBattles;
    private String winningTeam;
    private List<String> winnerMembersOfWinningTeam = new ArrayList<>();
    private String losingTeam;
    private List<String> survivingMembersOfLosingTeam = new ArrayList<>();

    public int getNumberOfBattles() {
        return numberOfBattles;
    }

    public void setNumberOfBattles(int numberOfBattles) {
        this.numberOfBattles = numberOfBattles;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }

    public List<String> getWinnerMembersOfWinningTeam() {
        return winnerMembersOfWinningTeam;
    }

    public void setWinnerMembersOfWinningTeam(List<String> winnerMembersOfWinningTeam) {
        this.winnerMembersOfWinningTeam = winnerMembersOfWinningTeam;
    }

    public String getLosingTeam() {
        return losingTeam;
    }

    public void setLosingTeam(String losingTeam) {
        this.losingTeam = losingTeam;
    }

    public List<String> getSurvivingMembersOfLosingTeam() {
        return survivingMembersOfLosingTeam;
    }

    public void setSurvivingMembersOfLosingTeam(List<String> survivingMembersOfLosingTeam) {
        this.survivingMembersOfLosingTeam = survivingMembersOfLosingTeam;
    }
}
