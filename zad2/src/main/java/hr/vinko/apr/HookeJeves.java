package hr.vinko.apr;

import hr.vinko.apr.zad1.Matrix;

public class HookeJeves {
	
	public static Matrix hookeJeves(Matrix x0, Matrix dx, Matrix e, IFunction f) {
		Matrix xP = x0.copy();
		Matrix xB = x0.copy();
		
		System.out.println("Xb\tXp\tXn\tf(Xb)");
		while(true) {
			Matrix xN = explore(xP, dx, f);
			
			if (f.getValueAt(xN) < f.getValueAt(xB)) {
				xP = xN.scalarMultiply(2).substract(xB);
				xB = xN.copy();
			} else {
				dx = dx.scalarMultiply(0.5);
				xP = xB.copy();
			}
			
			int i = 0;
			for (; i<dx.getRows(); i++) {
				if (dx.getAt(i, 0) > e.getAt(i, 0)) break;
			}
			
			System.out.println(String.format("%s\t%s\t%s\t%s", xB, xP, xN, f.getValueAt(xB)));
			
			if (i == dx.getRows()) break;
			
		}		
		return xB;
	}

	private static Matrix explore(Matrix xP, Matrix dx, IFunction f) {
		Matrix x = xP.copy();
		
		for (int i=0; i<xP.getRows(); i++) {
			double p = f.getValueAt(x);
			x.setAt(i, 0, x.getAt(i, 0) + dx.getAt(i, 0));
			double n = f.getValueAt(x);
			if (n > p) {
				x.setAt(i, 0, x.getAt(i, 0) - 2 * dx.getAt(i, 0));
				n = f.getValueAt(x);
				if (n > p) {
					x.setAt(i, 0, x.getAt(i, 0) + dx.getAt(i, 0));
				}
			}
		}
		
		return x;
	}
	
	

}
