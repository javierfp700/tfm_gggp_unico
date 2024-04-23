import java.util.Arrays;
import java.util.List;
import gp.algorithm.GP;
import robocode.RobocodeConstants;

public class Run {

  public static void main(String[] args) {
    List<String> opponents= Arrays.asList(RobocodeConstants.TRACKER_OPPONENT_ROBOT,
                                          RobocodeConstants.SPINBOT_ROBOCODE,
                                          RobocodeConstants.PROTOTYPE_ROBOCODE,
                                          RobocodeConstants.FROG_ROBOCODE,
                                          RobocodeConstants.SNOW_ROBOCODE);
    GP gp = new GP();
    gp.execute(opponents);
  }

}

