package hr.vinko.apr.zad4.fitness;

public class F7 extends F6 {

	public F7(int dimensionality) {
		super(dimensionality);
	}

	@Override
	protected double getValue(double[] x) {
		double squareSum = getSquareSum(x);
		return Math.pow(squareSum, 0.25) * (1 + Math.pow(Math.sin(50 * Math.pow(squareSum, 0.1)), 2));
	}

}
