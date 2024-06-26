package robocode.battle;

import ec.evolution.Individual;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;

public class BattleObserver extends BattleAdaptor {

  private Individual individual;

  public BattleObserver(Individual individual){
    this.individual = individual;
  }

  // Called when the battle is completed successfully with battle results
  public void onBattleCompleted(BattleCompletedEvent e) {
    System.out.println("-- Battle has completed --");

    // Print out the sorted results with the robot names
    System.out.println("Battle results:");
    for (robocode.BattleResults result : e.getSortedResults()) {
      System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
    }
    individual.setFitness(e.getSortedResults());
    /*individual.setFitness();*/
  }

  // Called when the game sends out an information message during the battle
  public void onBattleMessage(BattleMessageEvent e) {
    System.out.println("Msg> " + e.getMessage());
  }

  // Called when the game sends out an error message during the battle
  public void onBattleError(BattleErrorEvent e) {
    System.out.println("Err> " + e.getError());
  }
}
