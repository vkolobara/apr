package hr.vinko.apr;

import java.util.Arrays;

public class CoordinateSearch implements IAlgorithm {
	
	private double[] e;	
	private GoldenSection gss;
	
	public CoordinateSearch(double[] e) {
		this.e = Arrays.copyOf(e, e.length);
		gss = new GoldenSection();
	}

	@Override
	public double[] solve(double[] x0, IFunction f) {
		double[] x = Arrays.copyOf(x0, x0.length);
		while(true) {
			double[] xS = Arrays.copyOf(x, x.length);
			for (int i=0; i<x0.length; i++) {
				final int j = i;
				IFunction fx = xi -> {
					double[] xs = Arrays.copyOf(x, x.length);
					xs[j] += xi[0];
					return f.getValueAt(xs);
				};
				gss.setE(e[i]);
				double[] ab = gss.solve(new double[]{0}, fx);
				x[i] += (ab[0] + ab[1]) / 2.0;
			}
			if (stopCondition(xS, x0)) break;
		}
		return x;
	}

	private boolean stopCondition(double[] xS, double[] x0) {
		for (int i=0; i<x0.length; i++) {
			if (Math.abs(xS[i] - x0[i]) > e[i]) return false;
		}
		return true;
	}
	
}
