package hr.vinko.apr;

import java.util.HashMap;
import java.util.Map;

import hr.vinko.apr.zad1.Matrix;

public abstract class AbstractFunction implements IFunction{

	private Map<Matrix, Double> calculations;
	private int numCalls;
	private int dimensionality;
	
	public AbstractFunction(int dimensionality) {
		calculations = new HashMap<>();
		numCalls = 0;
		this.dimensionality = dimensionality;
	}
	
	@Override
	public double getValueAt(Matrix x) {
		if (calculations.containsKey(x)) {
			return calculations.get(x);
		}
		numCalls++;
		calculations.put(x, funcValue(x));
		return calculations.get(x);
	}
	
	protected abstract double funcValue(Matrix x);

	
	public Map<Matrix, Double> getCalculations() {
		return calculations;
	}
	
	public int getNumCalls() {
		return numCalls;
	}

	public int getDimensionality() {
		return dimensionality;
	}
	
	
}
