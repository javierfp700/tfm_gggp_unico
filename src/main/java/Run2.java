public class Run2 {

  public static void main(String[] args) {
    int wins=10;
    int gpRobotScore=6;
    int opponentScore=4;
    System.out.println(gpRobotScore/(float)(gpRobotScore+opponentScore));
    float fitness=wins+(float)gpRobotScore/(gpRobotScore+opponentScore);
    System.out.println(fitness);
    /*Population population=new Population();
    for(int i=0;i< GPConstants.POPULATION_SIZE;i++){
      Random r = new Random();
      IndividualGP individualGP =new IndividualGP(r.nextFloat());
      population.addIndividual(individualGP);
    }
    System.out.println("Population");
    for(IndividualGP individualGP : population.getIndividuals()){
      System.out.println(individualGP.getFitness());
    }
    System.out.println("Tournament");
    List<IndividualGP> sons=TournamentSelector.selection(population);
    for(IndividualGP individualGP : sons){
      System.out.println("Fitness tournament"+ individualGP.getFitness());
    }
    SteadyStateReplacement.replacement(population,sons);
    System.out.println("Population replacement");
    for(IndividualGP individualGP : population.getIndividuals()){
      System.out.println("Fitness replacement "+ individualGP.getFitness());
    }
    System.out.println("Size "+population.getIndividuals().size());*/
  }

}
