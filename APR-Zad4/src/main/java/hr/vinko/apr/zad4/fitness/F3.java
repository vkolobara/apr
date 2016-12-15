package hr.vinko.apr.zad4.fitness;

public class F3 extends AbstractFitnessFunction {

	private int dimensionality;

	public F3(int dimensionality) {
		super(FitnessType.FITNESS_MIN);
		this.dimensionality = dimensionality;
	}

	@Override
	protected double getValue(double[] x) {
		double sum = 0;

		for (int i = 1; i <= dimensionality; i++) {
			sum += Math.pow(x[i - 1] - i, 2);
		}

		return sum;
	}
	
	public int getDimensionality() {
		return dimensionality;
	}

}
