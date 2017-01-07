package hr.vinko.apr.zad4.algorithm;

import java.util.Random;

import hr.vinko.apr.zad4.fitness.AbstractFitnessFunction;
import hr.vinko.apr.zad4.solution.ISolution;

public interface IAlgorithm<T extends ISolution<?>> {
	static Random rand = new Random();

	public T solve(AbstractFitnessFunction f, Object ... parameters);

}
