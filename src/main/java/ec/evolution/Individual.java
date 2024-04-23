package ec.evolution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.IOUtils;
import converter.JavaCodeConverter;
import gp.algorithm.GPConstants;
import gp.structure.DerivationTree;
import net.sf.robocode.battle.Battle;
import robocode.BattleResults;
import robocode.RobocodeConstants;
import robocode.battle.BattleOpponent;
import robocode.battle.BattleResult;
import robocode.battle.BattleSimulator;

public abstract class Individual implements Serializable {

  //Si es GA es el derivation tree proveniente de gp (sin sustituciones)
  private DerivationTree derivationTree;

  private float fitness;
  private float fitness1;

  private List<Float> hitWallsList=new ArrayList<>();
  private List<Float> spacesAchieved=new ArrayList<>();
  private List<Float> totalTurnsList=new ArrayList<>();
  private List<Float> bulletHitAnotherRobotList=new ArrayList<>();
  private List<Float> scannedRobotList=new ArrayList<>();
  private List<Float> bulletHitMissedList=new ArrayList<>();
  private List<BattleResult> results=new ArrayList<>();

  public Individual(DerivationTree derivationTree){
    this.derivationTree=derivationTree;
    this.fitness=Float.NEGATIVE_INFINITY;
    this.fitness1=Float.NEGATIVE_INFINITY;
  }

  public float getFitness(){
    return fitness;
  }

  public DerivationTree getDerivationTree(){
    return derivationTree;
  }

  public void setFitness(float fitness){
    this.fitness=fitness;
  }

  public void setDerivationTree(DerivationTree derivationTree){
    this.derivationTree=derivationTree;
  }

  public abstract List<String> getPhenotype();

  public List<Float> getHitWallsList() {
    return hitWallsList;
  }

  public List<Float> getSpacesAchieved() {
    return spacesAchieved;
  }

  public List<Float> getTotalTurnsList() {
    return totalTurnsList;
  }

  public List<Float> getBulletHitAnotherRobotList() {
    return bulletHitAnotherRobotList;
  }

  public List<Float> getScannedRobotList() {
    return scannedRobotList;
  }

  public List<Float> getBulletHitMissedList() {
    return bulletHitMissedList;
  }

  public float getFitness1() {
    return fitness1;
  }

  /**
   * Set fitness
   * @param battleResults battle results
   */
  public void setFitness(BattleResults[] battleResults, BattleOpponent opponent){
    int gpRobotScore=0;
    int opponentScore=0;
    int wins=0;
    for (robocode.BattleResults result : battleResults) {
      if(result.getTeamLeaderName().equals(GPConstants.GP_ROBOT)){
        gpRobotScore=result.getScore();
      } else {
        opponentScore=result.getScore();
        results.add(new BattleResult(result.getTeamLeaderName(),result.getSeconds(), result.getFirsts()));
        wins=result.getSeconds();
      }
    }
    if (fitness==Float.NEGATIVE_INFINITY) {
      fitness1 = gpRobotScore - opponentScore;
      //fitness=wins+(float)gpRobotScore/(gpRobotScore+opponentScore);
      /*if (gpRobotScore >= opponentScore) {
        fitness = (float)(1200 - opponent.getRanking() + 1)/1200 * (gpRobotScore-opponentScore);
      } else {
        fitness = (float)(opponent.getRanking())/1200 * (gpRobotScore-opponentScore);
      }*/
      //fitness = (float)(1200 - opponent.getRanking() + 1)/1200 * (gpRobotScore-opponentScore);
      fitness = gpRobotScore - opponentScore;
    } else {
      fitness1 = fitness1 + gpRobotScore - opponentScore;
      //fitness=fitness+wins+(float)gpRobotScore/(gpRobotScore+opponentScore);
      /*if (gpRobotScore >= opponentScore) {
        fitness = fitness + (float)(1200 - opponent.getRanking() + 1)/1200 * (gpRobotScore-opponentScore);
      } else {
        fitness = fitness + (float)(opponent.getRanking())/1200 * (gpRobotScore-opponentScore);
      }*/
      fitness = fitness + gpRobotScore - opponentScore;
      //fitness = fitness + (float)(1200 - opponent.getRanking() + 1)/1200 * (gpRobotScore-opponentScore);
    }

    /*BufferedReader reader;
    try {
      File file = new File("/home/jfernandez/robocode/robots/gp/GPRobot.data/battleData.dat");
      reader = new BufferedReader(new FileReader(file));
      String line;
      float hitByBullet=0;
      while((line=reader.readLine()) != null) {
        String[] lineElements = line.split(" ");
        if (lineElements[0].equals("Hit_by_bullet")) {
          hitByBullet = Float.parseFloat(lineElements[1]);
          hitWallsList.add(hitByBullet);
        }
      }
      fitness=fitness-10*hitByBullet;
      reader.close();
      file.delete();
    } catch (IOException e) {
      e.printStackTrace();
    }*/
  }

  //0 is the best fitness
  public void setFitness(){
    BufferedReader reader;
    try {
      File file = new File("/home/jfernandez/robocode/robots/gp/GPRobot.data/battleData.dat");
      reader = new BufferedReader(new FileReader(file));
      String line;
      float hitWalls=0;
      float totalTurns=0;
      float bulletHitAnotherRobot=0;
      float scannedRobot=0;
      float bulletHitMissed=0;
      List<Float> fitnessBattle=new ArrayList<>();
      while((line=reader.readLine()) != null){
        String[] lineElements=line.split(" ");
        if(lineElements[0].equals("Hit_wall")){
          hitWalls=Float.parseFloat(lineElements[1]);
          hitWallsList.add(hitWalls);
        }
        if(lineElements[0].equals("Total_turns")){
          totalTurns=Float.parseFloat(lineElements[1]);
          totalTurnsList.add(totalTurns);
        }
        if(lineElements[0].equals("Bullet_hit_another_robot")){
          bulletHitAnotherRobot=Float.parseFloat(lineElements[1]);
          bulletHitAnotherRobotList.add(bulletHitAnotherRobot);
        }
        if(lineElements[0].equals("Scanned_robots")){
          scannedRobot=Float.parseFloat(lineElements[1]);
          scannedRobotList.add(scannedRobot);
        }
        if(lineElements[0].equals("Bullet_hit_missed")){
          bulletHitMissed=Float.parseFloat(lineElements[1]);
          bulletHitMissedList.add(bulletHitMissed);
        }
        if(lineElements[0].equals("Reached_spaces")){
          float reachedSpaces=Float.parseFloat(lineElements[1]);
          spacesAchieved.add(reachedSpaces);
          float fitnessRound=scannedRobot+10*bulletHitAnotherRobot-3*bulletHitMissed;
          ////float fitnessRound=weightHitsWalls*(100-(hitWalls/totalTurns)*100)+weightReachedSpaces*((reachedSpaces/totalSpaces)*100);
          //float fitnessRound=150*reachedSpaces+(totalTurns-hitWalls);
          ////float fitnessRound=14*(reachedSpaces/100)+((totalTurns-hitWalls)/totalTurns);
          fitnessBattle.add(fitnessRound);
        }
      }
      //fitness=totalTurns/hitWalls; //movimientos circulars sobre si mismo sin moverse del sitio
      fitness=fitnessBattle.stream().reduce(0F,Float::sum);
      reader.close();
      file.delete();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Evaluate individual
   */
  public void evaluate(List<BattleOpponent> opponents){
    String javaTemplate = getJavaTemplate();
    String templateWithCode = replaceJavaTemplateWithCode(javaTemplate);
    writeInJavaFile(templateWithCode,RobocodeConstants.GP_ROBOT_PACKAGE+GPConstants.ROBOT_TEMPLATE);
    compileIndividual();
    for(BattleOpponent opponent: opponents) {
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
    /*String[] codeBlocks=getPhenotype().split("###");
    String templateWithCode=javaTemplate.replace(GPConstants.RUN_CODE_TAG,codeBlocks[0]);
    /*templateWithCode=templateWithCode.replace(GPConstants.ON_BULLET_HIT_CODE_TAG,codeBlocks[1]);
    templateWithCode=templateWithCode.replace(GPConstants.ON_BULLET_HIT_BULLET_CODE_TAG,codeBlocks[2]);
    templateWithCode=templateWithCode.replace(GPConstants.ON_BULLET_MISSED_CODE_TAG,codeBlocks[3]);
    templateWithCode=templateWithCode.replace(GPConstants.ON_HIT_BY_BULLET_CODE_TAG,codeBlocks[4]);
    templateWithCode=templateWithCode.replace(GPConstants.ON_HIT_ROBOT_CODE_TAG,codeBlocks[5]);
    templateWithCode=templateWithCode.replace(GPConstants.ON_HIT_WALL_CODE_TAG,codeBlocks[6]);
    templateWithCode=templateWithCode.replace(GPConstants.ON_SCANNED_ROBOT_CODE_TAG,codeBlocks[7]);
    templateWithCode=templateWithCode.replace(GPConstants.EMPTY_TAG,GPConstants.EMPTY_VALUE);
    return templateWithCode;*/
    String[] codeBlocks=JavaCodeConverter.convertToJavaCode(getPhenotype());
    String templateWithCode=javaTemplate.replace(GPConstants.RUN_CODE_TAG,codeBlocks[0]);
    templateWithCode=templateWithCode.replace(GPConstants.ON_SCANNED_ROBOT_CODE_TAG,codeBlocks[1]);
    templateWithCode=templateWithCode.replace(GPConstants.ON_HIT_BY_BULLET_CODE_TAG,codeBlocks[2]);
    return templateWithCode;
  }

  /**
   * Write robot code in java file
   * @param content content with robot code
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
  private void simulateBattle(BattleOpponent opponent){
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
