package hr.vinko.apr.zad4.solution;

import java.util.Arrays;

public abstract class AbstractSolution<T> implements ISolution<T> {

	public T[] solution;
	double[] min, max;
	private double fitness;

	public AbstractSolution(double[] min, double[] max) {
		this.min = Arrays.copyOf(min, min.length);
		this.max = Arrays.copyOf(max, max.length);
	}

	public double[] getMax() {
		return max;
	}
	
	public double[] getMin() {
		return min;
	}
	
	@Override
	public double getFitness() {
		return fitness;
	}

	@Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AbstractSolution<?> that = (AbstractSolution<?>) o;

		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		return Arrays.equals(solution, that.solution);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(solution);
	}

	@Override
	public String toString() {
		return Arrays.toString(decode());
	}
}
