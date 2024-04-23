import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import gp.algorithm.GP;
import robocode.RobocodeConstants;
import robocode.battle.BattleOpponent;

public class Run {

  public static void main(String[] args) {
    /*List<String> opponents= Arrays.asList(RobocodeConstants.CORNER_OPPONENT_ROBOT, RobocodeConstants.CRAZY_OPPONENT_ROBOT,
        RobocodeConstants.FIRE_OPPONENT_ROBOT,RobocodeConstants.MY_FIRST_JUNIOR_ROBOT_OPPONENT_ROBOT,RobocodeConstants.MY_FIRST_ROBOT_OPPONENT_ROBOT,
        RobocodeConstants.RAM_FIRE_OPPONENT_ROBOT);*/
    /*List<String> opponents= Arrays.asList(
        //RobocodeConstants.RL_FINAL_OPPONENT_ROBOT
        RobocodeConstants.FIRE_OPPONENT_ROBOT,
        RobocodeConstants.CRAZY_OPPONENT_ROBOT,
        RobocodeConstants.CORNER_OPPONENT_ROBOT,
        RobocodeConstants.TRACKER_OPPONENT_ROBOT,
        RobocodeConstants.MY_FIRST_ROBOT_OPPONENT_ROBOT,
        RobocodeConstants.TRACK_FIRE_OPPONENT_ROBOT,
        RobocodeConstants.RAM_FIRE_OPPONENT_ROBOT,
        RobocodeConstants.MY_FIRST_JUNIOR_ROBOT_OPPONENT_ROBOT,
        RobocodeConstants.VELOCI_OPPONENT_ROBOT,
        RobocodeConstants.SPIN_BOT_OPPONENT_ROBOT,
        RobocodeConstants.WALLS_OPPONENT_ROBOT
    );*/
    List<BattleOpponent> opponents= Arrays.asList(
        new BattleOpponent(RobocodeConstants.TRACKER_OPPONENT_ROBOT,1094),
        new BattleOpponent(RobocodeConstants.SPINBOT_ROBOCODE,829),
        new BattleOpponent(RobocodeConstants.PROTOTYPE_ROBOCODE,691),
        new BattleOpponent(RobocodeConstants.FROG_ROBOCODE,532),
        new BattleOpponent(RobocodeConstants.SNOW_ROBOCODE,532)
        //RobocodeConstants.WALLS_OPPONENT_ROBOT,
        //RobocodeConstants.KAWIGI_ROBOT,
        //RobocodeConstants.WESCO_ROBOT
        //RobocodeConstants.GUPPY_ROBOT
        //RobocodeConstants.KOWARI_ROBOT,
        //RobocodeConstants.WRAITH_ROBOCODE
    );
    /*for(int i=0;i<5;i++) {
        GP gp = new GP();
        gp.execute(opponents);
    }*/
    //for (int i=0;i<10;i++) {
    //  GP gp = new GP();
    //  gp.execute(opponents);
    //}
    for(BattleOpponent opponent: opponents){
      for(int i=0;i<2;i++){
        List<BattleOpponent> op=Arrays.asList(opponent);
        GP gp = new GP();
        gp.execute(op);
      }
    }

    //for(String opponent: opponents) {
    //  GP gp = new GP();
   //   gp.execute(opponent);
      //bestRobot.save();
    //}
  }

}
