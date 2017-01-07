package hr.vinko.apr.zad4.operator.selection;

import java.util.Random;

import hr.vinko.apr.zad4.fitness.FitnessType;
import hr.vinko.apr.zad4.population.Population;
import hr.vinko.apr.zad4.solution.ISolution;

public interface ISelection<T extends ISolution<?>> {
	static Random rand = new Random();
	public T[] select(Population<T> population, FitnessType fitnessType);
	
}
