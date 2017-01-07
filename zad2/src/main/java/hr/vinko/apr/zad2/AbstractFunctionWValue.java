package hr.vinko.apr.zad2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFunctionWValue implements FunctionWValue{

	private Map<Integer, Double> calculations;
	private int numCalls;
	private int dimensionality;
	
	public AbstractFunctionWValue(int dimensionality) {
		calculations = new HashMap<>();
		numCalls = 0;
		this.dimensionality = dimensionality;
	}
	
	@Override
	public double getValueAt(double ... x) {
		int hashCode = Arrays.hashCode(x);
		if (calculations.containsKey(hashCode)) {
			return calculations.get(hashCode);
		}
		numCalls++;
		calculations.put(hashCode, funcValue(x));
		return calculations.get(hashCode);
	}
		
	protected abstract double funcValue(double[] x);

	public void clear() {
		numCalls = 0;
		calculations.clear();
	}
	
	public Map<Integer, Double> getCalculations() {
		return calculations;
	}
	
	public int getNumCalls() {
		return numCalls;
	}

	public int getDimensionality() {
		return dimensionality;
	}
	
	
}
