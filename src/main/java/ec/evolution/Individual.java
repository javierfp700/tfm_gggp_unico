package ec.evolution;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.IOUtils;
import converter.JavaCodeConverter;
import gp.algorithm.GPConstants;
import gp.structure.DerivationTree;
import robocode.BattleResults;
import robocode.RobocodeConstants;
import robocode.battle.BattleResult;
import robocode.battle.BattleSimulator;

public abstract class Individual implements Serializable {

  private DerivationTree derivationTree;

  private float fitness;

  private List<BattleResult> results=new ArrayList<>();

  public Individual(DerivationTree derivationTree){
    this.derivationTree=derivationTree;
    this.fitness=Float.NEGATIVE_INFINITY;
  }

  public float getFitness(){
    return fitness;
  }

  public DerivationTree getDerivationTree(){
    return derivationTree;
  }

  public abstract List<String> getPhenotype();

  /**
   * Set fitness
   * @param battleResults battle results
   */
  public void setFitness(BattleResults[] battleResults){
    int gpRobotScore=0;
    int opponentScore=0;
    for (robocode.BattleResults result : battleResults) {
      if(result.getTeamLeaderName().equals(GPConstants.GP_ROBOT)){
        gpRobotScore=result.getScore();
      } else {
        opponentScore=result.getScore();
        results.add(new BattleResult(result.getTeamLeaderName(),result.getSeconds(), result.getFirsts()));
      }
    }
    if (fitness==Float.NEGATIVE_INFINITY) {
      fitness = gpRobotScore - opponentScore;
    } else {
      fitness = fitness + gpRobotScore - opponentScore;
    }
  }

  /**
   * Evaluate individual
   */
  public void evaluate(List<String> opponents){
    String javaTemplate = getJavaTemplate();
    String templateWithCode = replaceJavaTemplateWithCode(javaTemplate);
    writeInJavaFile(templateWithCode,RobocodeConstants.GP_ROBOT_PACKAGE+GPConstants.ROBOT_TEMPLATE);
    compileIndividual();
    for(String opponent: opponents) {
      simulateBattle(opponent);
    }
  }

  /**
   * Get java template with skeleton of robot code
   * @return content with skeleton of robot code
   */
  private String getJavaTemplate(){
    try {
      ClassLoader classLoader = Individual.class.getClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream(GPConstants.ROBOT_TEMPLATE);
      return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    } catch (IOException e){
      System.err.println("Impossible to get individual java template");
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Replace skeleton of robot code with code of genetic programming
   * @param javaTemplate content with skeleton of robot code
   * @return robot code
   */
  private String replaceJavaTemplateWithCode(String javaTemplate){
    String[] codeBlocks=JavaCodeConverter.convertToJavaCode(getPhenotype());
    String templateWithCode=javaTemplate.replace(GPConstants.RUN_CODE_TAG,codeBlocks[0]);
    templateWithCode=templateWithCode.replace(GPConstants.ON_SCANNED_ROBOT_CODE_TAG,codeBlocks[1]);
    templateWithCode=templateWithCode.replace(GPConstants.ON_HIT_BY_BULLET_CODE_TAG,codeBlocks[2]);
    return templateWithCode;
  }

  /**
   * Write robot code in java file
   */
  private void writeInJavaFile(String content,String javaCodePath){
    try {
      FileWriter myWriter = new FileWriter(javaCodePath);
      myWriter.write(content);
      myWriter.close();
      System.out.println("Written code "+javaCodePath);
    } catch (IOException e) {
      System.err.println("Impossible to write individual code in "+javaCodePath);
      e.printStackTrace();
    }
  }

  /**
   * Compile robot code
   */
  private void compileIndividual(){
    try {
      String line = "javac " + RobocodeConstants.GP_ROBOT_PACKAGE+GPConstants.ROBOT_TEMPLATE + " -cp " + RobocodeConstants.ROBOCODE_JAR;
      CommandLine cmdLine = CommandLine.parse(line);
      DefaultExecutor executor = new DefaultExecutor();
      System.out.println("Executing "+line);
      executor.execute(cmdLine);
      System.out.println("Executed "+line);
    } catch(IOException e) {
      System.err.println("Impossible to compile individual in java");
      e.printStackTrace();
    }
  }

  /**
   * Simulate battle with individual
   */
  private void simulateBattle(String opponent){
    BattleSimulator.simulateBattle(this,opponent);
  }


  public void save(String path) {
    String javaTemplate = getJavaTemplate();
    String templateWithCode = replaceJavaTemplateWithCode(javaTemplate);
    writeInJavaFile(templateWithCode,path);
  }

  public String getBattleResults() {
    String results="";
    for(BattleResult battleResult: this.results){
      results=results+"["+battleResult.getOpponent()+"->W:"+battleResult.getWins()+",L:"+battleResult.getLosses()+"]";
    }
    return results;
  }
}
