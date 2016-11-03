package hr.vinko.apr;

import hr.vinko.apr.zad1.Matrix;

public class CoordinateSearch {

	public static Matrix search(Matrix x0, Matrix e, IFunction f) {
		
		Matrix x = x0.copy();
		while(true) {
			Matrix xS = x.copy();
			for (int i=0; i<x0.getRows(); i++) {
				double[] ab = GoldenSection.golden_section_search(x.getAt(i, 0), 1, f);
				x.setAt(i, 0, ab[0] + ab[1] / 2.0);
			}
			
			if (lessThan(x.substract(xS), e)) break;
		}
		
		return x;
		
	}

	private static boolean lessThan(Matrix m, Matrix e) {

		for (int i=0; i<m.getRows(); i++) {
			if (m.getAt(i, 0) > e.getAt(i, 0)) return false;
		}
		
		return true;
	}
	
}
