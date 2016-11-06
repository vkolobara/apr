package hr.vinko.apr;

import java.util.Arrays;

public class HookeJeves implements IAlgorithm{
	
	private double[] e;
	private double[] dx;

	public HookeJeves(int dimensionality) {
		e = new double[dimensionality];
		dx = new double[dimensionality];
		
		for (int i=0; i<dimensionality; i++) {
			e[i] = 1e-6;
			dx[i] = .5;
		}		
	}
	
	public HookeJeves(double[] e, double[] dx) {
		this.e = Arrays.copyOf(e, e.length);
		this.dx = Arrays.copyOf(dx, dx.length);
	}
	
	@Override
	public double[] solve(double[] x0, IFunction f) {
		double[] xP = Arrays.copyOf(x0, x0.length);
		double[] xB = Arrays.copyOf(x0, x0.length);
		double[] dxC = Arrays.copyOf(dx, dx.length);
		
		while(true) {
			double[] xN = explore(xP, dxC, f);
			if (f.getValueAt(xN) < f.getValueAt(xB)) {
				for (int i=0; i<xN.length; i++) {
					xP[i] = 2*xN[i] - xB[i];
				}
				xB = xN;
			} else {
				for (int i=0; i<dx.length; i++) {
					dxC[i] /= 2;
					xP = xB;
				}
			}
			int i=0;
			for (; i<dxC.length; i++) {
				if (dxC[i] > e[i]) break;
			}
			
			System.out.println(Arrays.toString(xB) + "\t" + Arrays.toString(xP) + "\t" + Arrays.toString(xN));
			System.out.println(f.getValueAt(xB) + "\t" + f.getValueAt(xP) + "\t" + f.getValueAt(xN));
			System.out.println();
			
			if (i == dxC.length) break;
		}
		
		return xB;
	}
	
	private double[] explore(double[] xP, double[] dx, IFunction f) {
		double[] x = Arrays.copyOf(xP, xP.length);
		
		for (int i=0; i<xP.length; i++) {
			double p = f.getValueAt(x);
			x[i] += dx[i];
			double n = f.getValueAt(x);
			if (n > p) {
				x[i] -= 2*dx[i];
				n = f.getValueAt(x);
				if (n > p) {
					x[i] += dx[i];
				}
			}
		}
		
		return x;
	}

	

}
