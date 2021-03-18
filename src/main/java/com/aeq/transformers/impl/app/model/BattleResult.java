package com.aeq.transformers.impl.app.model;

/**
 * Represents various Battle results.
 *
 * WINNER_AUTOBOT - If Autobot wins
 * WINNER_DECEPTICON - If Decepticon wins
 * TIE - If Tie between the participants.
 * INTERRUPTED - If battle between special Transformers(Optimus Prime, Predaking)
 */
public enum BattleResult {
   WINNER_AUTOBOT,
   WINNER_DECEPTICON,
   TIE,
   INTERRUPTED;
}
