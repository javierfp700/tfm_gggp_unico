package converter;

import java.util.List;

public class JavaCodeConverter {

  public static String[] convertToJavaCode(List<String> word){
    int block=0;
    String[] javaCode=new String[3];
    int functionArguments=0;
    int argumentsCounter=0;
    for (String terminal : word) {
      if (javaCode[block] == null) {
        javaCode[block] = "";
      }
      switch (terminal) {
        case "if":
          if (!javaCode[block].isEmpty()) {
            javaCode[block] =
                javaCode[block] + newLineAndTabulate(block, false) + "}" + newLineAndTabulate(block, false) + "else if (";
          } else {
            javaCode[block] = javaCode[block] + "if (";
          }
          break;
        case "then":
          javaCode[block] =
              javaCode[block] + ") {";
          break;
        case "not":
          javaCode[block] = javaCode[block] + "!";
          break;
        case "robot_x":
          javaCode[block] = javaCode[block] + "getX()";
          break;
        case "robot_y":
          javaCode[block] = javaCode[block] + "getY()";
          break;
        case "robot_heading":
          javaCode[block] = javaCode[block] + "getHeading()";
          break;
        case "robot_velocity":
          javaCode[block] = javaCode[block] + "getVelocity()";
          break;
        case "and":
          javaCode[block] = javaCode[block] + "&&";
          break;
        case "or":
          javaCode[block] = javaCode[block] + "||";
          break;
        case "ahead":
          javaCode[block] = javaCode[block] + newLineAndTabulate(block, true) + "ahead(";
          functionArguments = 1;
          argumentsCounter = 0;
          break;
        case "back":
          javaCode[block] = javaCode[block] + newLineAndTabulate(block, true) + "back(";
          functionArguments = 1;
          argumentsCounter = 0;
          break;
        case "turn_right":
          javaCode[block] = javaCode[block] + newLineAndTabulate(block, true) + "turnRight(";
          functionArguments = 1;
          argumentsCounter = 0;
          break;
        case "turn_left":
          javaCode[block] = javaCode[block] + newLineAndTabulate(block, true) + "turnLeft(";
          functionArguments = 1;
          argumentsCounter = 0;
          break;
        case "default":
          javaCode[block] =
              javaCode[block] + newLineAndTabulate(block, false) + "}" + newLineAndTabulate(block, false) + "else{";
          break;
        case "###":
          //Run method are only actions (no conditionals)
          if (block != 0) {
            javaCode[block] =
                javaCode[block] + newLineAndTabulate(block, false) + "}";
          }
          block = block + 1;
          break;
        case "distance":
          javaCode[block] = javaCode[block] + "event.getDistance()";
          break;
        case "energy":
          javaCode[block] = javaCode[block] + "getEnergy()";
          break;
        case "fire":
          javaCode[block] = javaCode[block] + newLineAndTabulate(block, true) + "fire(";
          functionArguments = 1;
          argumentsCounter = 0;
          break;
        case "do_nothing":
          javaCode[block] = javaCode[block] + newLineAndTabulate(block, true) + "doNothing();";
          break;
        case "bearing":
          javaCode[block] = javaCode[block] + "event.getBearing()";
          break;
        default:
          javaCode[block] = javaCode[block] + terminal;
          break;
      }
      if (functionArguments > 0) {
        if (argumentsCounter == functionArguments) {
          javaCode[block] = javaCode[block] + ");";
          argumentsCounter = 0;
          functionArguments = 0;
        } else {
          argumentsCounter = argumentsCounter + 1;
        }
      }
    }
    //Close last condition
    javaCode[block] = javaCode[block] + System.lineSeparator() + "\t\t" + "}";
    return javaCode;
  }

  private static String newLineAndTabulate(int block, boolean insideIf){
    if(block==0){
      return newLineAndTabulate(3);
    }
    if(!insideIf){
      return newLineAndTabulate(2);
    } else {
      return newLineAndTabulate(3);
    }
  }

  private static String newLineAndTabulate(int times){
    String tab=System.lineSeparator();
    for(int i=0;i<times;i++){
      tab=tab+"\t";
    }
    return tab;
  }





}
