package hr.vinko.apr.zad4.algorithm;

import hr.vinko.apr.zad4.fitness.AbstractFitnessFunction;
import hr.vinko.apr.zad4.fitness.IFitnessFunction;
import hr.vinko.apr.zad4.operator.crossover.ICrossover;
import hr.vinko.apr.zad4.operator.mutation.IMutation;
import hr.vinko.apr.zad4.operator.selection.ISelection;
import hr.vinko.apr.zad4.population.Population;
import hr.vinko.apr.zad4.solution.ISolution;

public abstract class EliminationGeneticAlgorithm<T extends ISolution<?>> implements IAlgorithm<T> {
	ICrossover<T>[] crossovers;
	IMutation<T>[] mutations;
	ISelection<T> selection;
	int maxIter;
	double termVal;
	int maxEvaluations;
	int popSize;

	@Override
	public T solve(AbstractFitnessFunction f) {
		Population<T> population = randomizePopulation(f);

		int iter = 0;

		while (f.getNumEvaluations() <= maxEvaluations && population.getBest(f.getFitnessType()).getFitness() > termVal
				&& iter++ < maxIter) {

			T[] selected = selection.select(population, f.getFitnessType());
			population.removeSolution(selected[2]);

			while (!population.isFull()) {
				T child = cross(selected[0], selected[1]);
				child = mutate(child);
			}

			System.out.println(iter + " : " + population.getBest(f.getFitnessType()).getFitness());

		}

		return population.getBest(f.getFitnessType());
	}

	private T cross(T p1, T p2) {
		return crossovers[rand.nextInt(crossovers.length)].mate(p1, p2);
	}

	private T mutate(T child) {
		return mutations[rand.nextInt(mutations.length)].mutate(child);
	}

	protected abstract Population<T> randomizePopulation(IFitnessFunction f);

}
