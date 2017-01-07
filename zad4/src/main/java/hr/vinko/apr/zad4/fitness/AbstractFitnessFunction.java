package hr.vinko.apr.zad4.fitness;

import java.util.HashMap;
import java.util.Map;

import hr.vinko.apr.zad4.solution.ISolution;

public abstract class AbstractFitnessFunction implements IFitnessFunction {

	private Map<Integer, Double> valueMap;
	private int numEvaluations;
	private FitnessType fitnessType;

	public AbstractFitnessFunction(FitnessType fitnessType) {
		valueMap = new HashMap<>();
		numEvaluations = 0;
		this.fitnessType = fitnessType;
	}

	public int getNumEvaluations() {
		return numEvaluations;
	}
	
	public void clear() {
		numEvaluations = 0;
		valueMap.clear();
	}
	
	@Override
	public FitnessType getFitnessType() {
		return fitnessType;
	}

	@Override
	public double fitnessValue(ISolution<?> solution) {
		int hashCode = solution.hashCode();

		if (!valueMap.containsKey(hashCode)) {
			valueMap.put(hashCode, getValue(solution.decode()));
			numEvaluations++;
		}

		return valueMap.get(hashCode);
	}

	protected abstract double getValue(double[] x);

}
