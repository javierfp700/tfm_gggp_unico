import java.util.Arrays;
import java.util.List;
import robocode.RobocodeConstants;
import validate.ResultsValidateWin;
import validate.ValidationBattle;

public class RunValidate {

  public static void main(String[] args) {
    String myRobot="/home/jfernandez/Desktop/2024_03_31_00_48_08/bests/GPRobot_best.java";
    List<String> opponents= Arrays.asList(
        RobocodeConstants.FROG_ROBOCODE
    );
    ResultsValidateWin resultsValidateWin= ResultsValidateWin.getInstance();
    ValidationBattle validationBattle = new ValidationBattle();
    while(!resultsValidateWin.winRobot) {
      validationBattle.execute(myRobot, opponents);
    }
  }

}
