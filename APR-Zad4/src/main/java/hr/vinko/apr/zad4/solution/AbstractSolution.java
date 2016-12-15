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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fitness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(solution);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSolution other = (AbstractSolution) obj;
		if (Double.doubleToLongBits(fitness) != Double.doubleToLongBits(other.fitness))
			return false;
		if (!Arrays.equals(solution, other.solution))
			return false;
		return true;
	}

}
