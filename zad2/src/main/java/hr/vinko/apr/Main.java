package hr.vinko.apr;

import hr.vinko.apr.GoldenSection;
import hr.vinko.apr.zad1.Matrix;

public class Main {

	public static void main(String[] args) {
		Matrix x0 = new Matrix(1, 1, new double[][]{{10}});
		Matrix dx = new Matrix(1, 1, new double[][]{{0.5}});
		Matrix e = new Matrix(1, 1, new double[][]{{1e-6}});
		
		AbstractFunction f3 = generateF3(1, 2);
		System.out.println(GoldenSection.golden_section_search(10, 1, f3));
		System.out.println(f3.getNumCalls());
		f3 = generateF3(1, 2);
		System.out.println(Simplex.simplex_algorithm(new Matrix(1, 1, new double[][]{{10}}), 1, 1, 0.5, 2, 0.5, f3));
		System.out.println(f3.getNumCalls());
		f3 = generateF3(1, 2);
		System.out.println(HookeJeves.hookeJeves(x0, dx, e, f3));
		System.out.println(f3.getNumCalls());
	}

	AbstractFunction f1 = new AbstractFunction(2) {
		
		@Override
		public double funcValue(Matrix x) {
			return 100 * Math.pow((x.getAt(1, 0) - Math.pow(x.getAt(0, 0), 2)), 2) + Math.pow(1 - x.getAt(0,0), 2);
		}
	};
	
	AbstractFunction f2 = new AbstractFunction(2) {
		
		@Override
		public double funcValue(Matrix x) {
			return Math.pow(x.getAt(0, 0) - 4, 2) + 4 * Math.pow(x.getAt(1, 0) - 2, 2);
		}
	};
	
	private static AbstractFunction generateF3(int dimensionality, double offset) {
		return new AbstractFunction(dimensionality) {
			
			@Override
			public double funcValue(Matrix x) {
				double sum = 0;
				for (int i=0; i<getDimensionality(); i++) {
					sum += Math.pow(x.getAt(i, 0) - (i+1 + offset), 2);
				}
				return sum;
			}
		};
	}
	
	AbstractFunction f4 = new AbstractFunction(2) {
		
		@Override
		public double funcValue(Matrix x) {
			return Math.abs((x.getAt(0, 0) - x.getAt(1, 0)) * (x.getAt(0, 0) + x.getAt(1, 0))) + Math.sqrt(Math.pow(x.getAt(0, 0), 2) + Math.pow(x.getAt(1, 0), 2));
		}
	};
	
	private static AbstractFunction generateF6(int dimensionality) {
		return new AbstractFunction(dimensionality) {
			
			@Override
			protected double funcValue(Matrix x) {
				double sum = 0;
				for (int i=0; i<getDimensionality(); i++) {
					sum += Math.pow(x.getAt(i, 0), 2);
				}
				return 0.5 + (Math.pow(Math.sin(Math.sqrt(sum)), 2) - 0.5) / Math.pow((1 + 0.001 * sum), 2);
			}
		};
	}
		
}
