package gp.initialization;

import ec.evolution.Individual;
import ec.initialization.PopulationGenerator;
import gp.algorithm.GPConstants;
import gp.evolution.IndividualGP;
import ec.evolution.Population;
import gp.grammar.Grammar;
import gp.structure.DerivationTree;

public class StandardGPPopulationGeneratorImpl implements PopulationGenerator {

  @Override
  public Population initializePopulation() {
    Grammar grammar= Grammar.getGrammar();
    Population population=new Population();
    while(population.getIndividuals().size()< GPConstants.GP_POPULATION_SIZE){
      DerivationTree derivationTree=grammar.generateDerivationTree(GPConstants.MAXIMUM_DEPTH_INDIVIDUAL_INITIALIZATION);
      if(derivationTree!=null){
        Individual individualGP =new IndividualGP(derivationTree);
        population.addIndividual(individualGP);
      }
    }
    return population;
  }
}
