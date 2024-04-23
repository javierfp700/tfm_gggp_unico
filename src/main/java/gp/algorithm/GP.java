package gp.algorithm;

import ec.algorithm.EC;
import ec.replacement.SteadyStateReplacementImpl;
import ec.selection.TournamentSelectorImpl;
import gp.crossover.WhighamCrossoverImpl;
import gp.initialization.StandardGPPopulationGeneratorImpl;
import gp.mutation.StandardGPMutationImpl;

public class GP extends EC {

  public GP(){
    super(new StandardGPPopulationGeneratorImpl(),
        new TournamentSelectorImpl(GPConstants.GP_REPLACED_POPULATION_PERCENTAGE,GPConstants.GP_TOURNAMENT_SIZE),
        new WhighamCrossoverImpl(),
        new StandardGPMutationImpl(),
        new SteadyStateReplacementImpl(GPConstants.GP_REPLACED_POPULATION_PERCENTAGE));
  }

  @Override
  public Integer getMaximumGenerations(){
    return GPConstants.GP_MAXIMUM_GENERATIONS;
  }

  @Override
  public String getAlgorithm() {
    return "GP";
  }


}
