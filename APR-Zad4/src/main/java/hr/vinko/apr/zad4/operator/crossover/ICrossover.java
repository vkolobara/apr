package hr.vinko.apr.zad4.operator.crossover;

import java.util.Random;

import hr.vinko.apr.zad4.solution.ISolution;

public interface ICrossover<T extends ISolution<?>> {
	static Random rand = new Random();
	public T mate(T parent1, T parent2);
}
