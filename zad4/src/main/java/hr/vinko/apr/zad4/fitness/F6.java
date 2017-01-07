package hr.vinko.apr.zad4.fitness;

public class F6 extends F3 {

	public F6(int dimensionality) {
		super(dimensionality);
	}

	@Override
	protected double getValue(double[] x) {
		double squareSum = getSquareSum(x);

		return 0.5 + (Math.pow(Math.sin(Math.sqrt(squareSum)), 2) - 0.5) / Math.pow(1 + 0.001 * squareSum, 2);
	}

	double getSquareSum(double[] x) {
		double squareSum = 0;

		for (int i = 0; i < getDimensionality(); i++) {
			squareSum += Math.pow(x[i], 2);
		}

		return squareSum;
	}

}
