package validate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import gp.algorithm.GPConstants;
import robocode.RobocodeConstants;

public class ValidationBattle {


  public void execute(String myRobot, List<String> opponents) {
    //try{
      String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
      //new File("/home/jfernandez/Desktop/"+timestamp+"_validate").mkdirs();
      copyGPRobot(myRobot);
      compileIndividual();
      for(String opponent: opponents){
        //FileWriter myWriter = new FileWriter("/home/jfernandez/Desktop/"+timestamp+"_validate/"+opponent+".txt",true);
        //simulateBattle(opponent,myWriter);
        //myWriter.close();
        simulateBattle(opponent,null);
        //myWriter.close();
      }
    //} catch (IOException e) {
    //  System.err.println("Impossible to write generations");
    //  e.printStackTrace();
    //}
  }

  public void copyGPRobot(String myRobot){
    String content= null;
    try {
      content = Files.readString(Path.of(myRobot));
    } catch (IOException e) {
      e.printStackTrace();
    }
    writeInJavaFile(content, RobocodeConstants.GP_ROBOT_PACKAGE+ GPConstants.ROBOT_TEMPLATE);
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
  private void simulateBattle(String opponent,FileWriter myWriter){
    ValidateBattleSimulator.simulateBattle(opponent,myWriter);
  }
}
