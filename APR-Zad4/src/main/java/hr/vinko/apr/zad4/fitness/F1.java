package hr.vinko.apr.zad4.fitness;

public class F1 extends AbstractFitnessFunction {

	public F1() {
		super(FitnessType.FITNESS_MIN);
	}
	
	
	@Override
	protected double getValue(double[] x) {
		return 100 * Math.pow(x[1] - x[0] * x[0], 2) + Math.pow(1 - x[0], 2);
	}

}
