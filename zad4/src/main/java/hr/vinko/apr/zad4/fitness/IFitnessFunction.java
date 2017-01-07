package hr.vinko.apr.zad4.fitness;

import hr.vinko.apr.zad4.solution.ISolution;

public interface IFitnessFunction {
	
	public FitnessType getFitnessType();
	public double fitnessValue(ISolution<?> solution);
}
