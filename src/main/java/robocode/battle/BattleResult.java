package robocode.battle;

import java.io.Serializable;

public class BattleResult implements Serializable {

  private String opponent;
  private int wins;
  private int losses;

  public BattleResult(String opponent, int wins, int losses){
    this.opponent=opponent;
    this.wins=wins;
    this.losses=losses;
  }

  public String getOpponent() {
    return opponent;
  }

  public int getWins() {
    return wins;
  }

  public int getLosses() {
    return losses;
  }



}
