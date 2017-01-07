package hr.vinko.apr.zad4.population;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.vinko.apr.zad4.fitness.FitnessType;
import hr.vinko.apr.zad4.fitness.IFitnessFunction;
import hr.vinko.apr.zad4.solution.AbstractSolution;
import hr.vinko.apr.zad4.solution.ISolution;

public class Population<T extends ISolution<?>> {

	private List<T> population;
	private int popSize;

	public Population(int popSize) {
		super();
		this.popSize = popSize;
		population = new ArrayList<>();
	}
	
	public boolean isFull() {
		return popSize == population.size();
	}

	public boolean addSolution(T solution) {
		return !isFull() && population.add(solution);
	}

	public boolean removeSolution(T solution) {
		return population.remove(solution);
	}
	
	public List<T> getPopulation() {
		return population;
	}
	
	public T getBest(FitnessType fitnessType) {

		int mult = FitnessType.FITNESS_MAX.equals(fitnessType) ? 1 : -1;
		return population.parallelStream().max((sol1, sol2) -> {
			return mult * Double.compare(sol1.getFitness(), sol2.getFitness());
		}).get();

	}
	
	public T getWorst(FitnessType fitnessType) {

		int mult = FitnessType.FITNESS_MAX.equals(fitnessType) ? -1 : 1;
		return population.parallelStream().max((sol1, sol2) -> {
			return mult * Double.compare(sol1.getFitness(), sol2.getFitness());
		}).get();

	}

}
