package robocode.battle;

public class BattleOpponent {

  private String name;

  private int ranking;

  public BattleOpponent(String name, int ranking) {
    this.name = name;
    this.ranking = ranking;
  }

  public String getName() {
    return name;
  }

  public int getRanking() {
    return ranking;
  }
}
