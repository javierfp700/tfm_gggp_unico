package ec.algorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import ec.crossover.Crossover;
import ec.evolution.Individual;
import ec.evolution.Population;
import ec.initialization.PopulationGenerator;
import ec.mutation.Mutation;
import ec.replacement.Replacement;
import ec.selection.Selector;
import robocode.battle.BattleOpponent;

public abstract class EC {

  private final PopulationGenerator populationGenerator;
  private final Selector selector;
  private final Crossover crossover;
  private final Mutation mutation;
  private final Replacement replacement;

  public EC(PopulationGenerator populationGenerator,Selector selector, Crossover crossover, Mutation mutation, Replacement replacement, String algorithm){
    this.populationGenerator=populationGenerator;
    this.selector=selector;
    this.crossover=crossover;
    this.mutation=mutation;
    this.replacement=replacement;
  }

  public Individual execute(List<BattleOpponent> opponents){
    try{
      //String opponentName=opponent.split("\\.")[1];
      String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date());
      new File("/home/jfernandez/Desktop/"+timestamp+"/bests").mkdirs();
      FileWriter myWriter = new FileWriter("/home/jfernandez/Desktop/"+timestamp+"/generations.txt");
      System.out.println("Initializing "+ getAlgorithm() + " population");
      Population population= populationGenerator.initializePopulation();
      for(int i=0;i<getMaximumGenerations();i++){
        System.out.println(getAlgorithm()+" generation "+i);
        System.out.println("Evaluating "+ getAlgorithm() + " population"+" generation "+i);
        population.evaluate(opponents);
        ///////////////
        for(Individual individual: population.getIndividuals()){
          System.out.println(getAlgorithm() + " individual fitness: " + individual.getFitness()+" generation "+i+" id "+System.identityHashCode(individual));
          myWriter.write("Individual fitness: " + individual.getFitness()+" fitness1 " + individual.getFitness1() + " results: " + individual.getBattleResults()+ " hitWalls: " + individual.getHitWallsList()+ " spacesAchieved: " + individual.getSpacesAchieved()+ " bulletHitAnotherRobot: " + individual.getBulletHitAnotherRobotList()+ " scannedRobot: " + individual.getScannedRobotList()+ " bulletHitMissed: " + individual.getBulletHitMissedList()+ " totalTurns: " + individual.getTotalTurnsList()+" generation "+i+" id "+System.identityHashCode(individual)+"\n");
        }
        ////////////////
        System.out.println("Selecting individuals "+ getAlgorithm() + " population"+" generation "+i);
        List<Individual> selectedIndividuals = selector.select(population);
        System.out.println("Crossing individuals "+ getAlgorithm() + " population"+" generation "+i);
        List<Individual> crossedIndividuals = crossover.cross(selectedIndividuals);
        System.out.println("Mutating individuals "+ getAlgorithm() + " population"+" generation "+i);
        mutation.mutate(crossedIndividuals);
        System.out.println("Replacing individuals "+ getAlgorithm() + " population"+" generation "+i);
        replacement.replace(population,crossedIndividuals);
        Individual bestIndividual=population.getBestIndividual();
        System.out.println("Fitness best individual "+ getAlgorithm() +" generation "+i+" : "+ bestIndividual.getFitness());
        myWriter.write("Fitness best individual: " + bestIndividual.getFitness()+ " fitness1 " + bestIndividual.getFitness1() + " results: " + bestIndividual.getBattleResults()+ " hitWalls: " + bestIndividual.getHitWallsList()+ " spacesAchieved: " + bestIndividual.getSpacesAchieved()+ " bulletHitAnotherRobot: " + bestIndividual.getBulletHitAnotherRobotList()+ " scannedRobot: " + bestIndividual.getScannedRobotList()+ " bulletHitMissed: " + bestIndividual.getBulletHitMissedList()+" totalTurns: " + bestIndividual.getTotalTurnsList()+" generation "+i+"\n");
        myWriter.write("-------------------------------------"+"\n");
        bestIndividual.save("/home/jfernandez/Desktop/"+timestamp+"/bests/GPRobot"+i+".java");
      }
      population.evaluate(opponents);
      Individual bestIndividual = population.getBestIndividual();
      System.out.println("Fitness best individual "+ getAlgorithm() + bestIndividual.getFitness()+ " hitWalls: " + bestIndividual.getHitWallsList()+ " spacesAchieved: " + bestIndividual.getSpacesAchieved()+ " bulletHitAnotherRobot: " + bestIndividual.getBulletHitAnotherRobotList()+ " scannedRobot: " + bestIndividual.getScannedRobotList()+ " bulletHitMissed: " + bestIndividual.getBulletHitMissedList()+" totalTurns: " + bestIndividual.getTotalTurnsList()+"\n");
      myWriter.write("-------------------------------------"+"\n");
      myWriter.write("Fitness best individual GP: " + bestIndividual.getFitness()+ " fitness1 " + bestIndividual.getFitness1() + " results: " + bestIndividual.getBattleResults()+ " hitWalls: " + bestIndividual.getHitWallsList()+ " spacesAchieved: " + bestIndividual.getSpacesAchieved()+ " bulletHitAnotherRobot: " + bestIndividual.getBulletHitAnotherRobotList()+ " scannedRobot: " + bestIndividual.getScannedRobotList()+ " bulletHitMissed: " + bestIndividual.getBulletHitMissedList()+" totalTurns: " + bestIndividual.getTotalTurnsList()+"\n");
      bestIndividual.save("/home/jfernandez/Desktop/"+timestamp+"/bests/GPRobot_best.java");
      myWriter.close();
      return bestIndividual;
    } catch (IOException e) {
      System.err.println("Impossible to write generations");
      e.printStackTrace();
    }
    return null;
  }

  public abstract Integer getMaximumGenerations();

  public abstract String getAlgorithm();

  public PopulationGenerator getPopulationGenerator() {
    return populationGenerator;
  }

  public Selector getSelector() {
    return selector;
  }

  public Crossover getCrossover() {
    return crossover;
  }

  public Mutation getMutation() {
    return mutation;
  }

  public Replacement getReplacement() {
    return replacement;
  }
}
