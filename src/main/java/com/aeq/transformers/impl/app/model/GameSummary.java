package com.aeq.transformers.impl.app.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Uses to save battle results during the game.
 */
@Component
public class GameSummary {
    private List<Transformer> autobots;
    private List<Transformer> decepticons;
    private int noOfBattles;
    private List<Transformer> winnerAutobots = new ArrayList<>();
    private List<Transformer> winnerDecepticons = new ArrayList<>();

    public List<Transformer> getAutobots() {
        return autobots;
    }

    public void setAutobots(List<Transformer> autobots) {
        this.autobots = autobots;
    }

    public List<Transformer> getDecepticons() {
        return decepticons;
    }

    public void setDecepticons(List<Transformer> decepticons) {
        this.decepticons = decepticons;
    }

    public int getNoOfBattles() {
        return noOfBattles;
    }

    public void setNoOfBattles(int noOfBattles) {
        this.noOfBattles = noOfBattles;
    }


    public List<Transformer> getWinnerAutobots() {
        return winnerAutobots;
    }

    public void setWinnerAutobots(List<Transformer> winnerAutobots) {
        this.winnerAutobots = winnerAutobots;
    }

    public List<Transformer> getWinnerDecepticons() {
        return winnerDecepticons;
    }

    public void setWinnerDecepticons(List<Transformer> winnerDecepticons) {
        this.winnerDecepticons = winnerDecepticons;
    }
}
