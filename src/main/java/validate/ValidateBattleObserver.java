package validate;

import java.io.FileWriter;
import java.io.IOException;
import ec.evolution.Individual;
import gp.algorithm.GPConstants;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;

public class ValidateBattleObserver extends BattleAdaptor{

  private FileWriter myWriter;

  public ValidateBattleObserver(FileWriter myWriter){
    this.myWriter = myWriter;
  }

  // Called when the battle is completed successfully with battle results
  public void onBattleCompleted(BattleCompletedEvent e) {
    System.out.println("-- Battle has completed --");

    // Print out the sorted results with the robot names
    System.out.println("Battle results:");
    int gpRobotScore=0;
    int opponentScore=0;
    for (robocode.BattleResults result : e.getSortedResults()) {
        //try {
          System.out.println(result.getTeamLeaderName()+"->wins:"+result.getFirsts()+", losses:"+result.getSeconds()+", score:"+result.getScore());
          if(result.getTeamLeaderName().equals(GPConstants.GP_ROBOT)){
            gpRobotScore=result.getScore();
          } else {
            opponentScore = result.getScore();
          }
          //myWriter.write(result.getTeamLeaderName()+"->wins:"+result.getFirsts()+", losses:"+result.getSeconds()+", score:"+result.getScore()+"\n");
        //} catch (IOException ioException) {
        //  ioException.printStackTrace();
        //}
    }
    if((gpRobotScore-opponentScore)>=0){
      ResultsValidateWin resultsValidateWin= ResultsValidateWin.getInstance();
      resultsValidateWin.setWinRobot(true);
    }
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
