package gp.algorithm;

public class GPConstants {

  public static final String AXIOM="<S>";
  public static final String ROBOT_TEMPLATE ="GPRobot.java";
  public static final String RUN_CODE_TAG="/* RUN_CODE */";
  public static final String ON_HIT_BY_BULLET_CODE_TAG="/* ON_HIT_BY_BULLET_CODE */";
  public static final String ON_SCANNED_ROBOT_CODE_TAG="/* ON_SCANNED_ROBOT_CODE */";
  public static final String GP_ROBOT ="gp.GPRobot*";
  public static final Integer GP_TOURNAMENT_SIZE=5;
  public static final Integer GP_POPULATION_SIZE=50;
  public static final Integer MAXIMUM_DEPTH_INDIVIDUAL_INITIALIZATION=50;
  public static final Float GP_REPLACED_POPULATION_PERCENTAGE =0.2F;
  public static final Float GP_CROSSOVER_PROBABILITY =1F;
  public static final Float GP_MUTATION_PROBABILITY =0.15F;
  public static final Integer MAXIMUM_DEPTH_MUTATION_SUBTREE=20;
  public static final Integer GP_MAXIMUM_GENERATIONS=3;

}
