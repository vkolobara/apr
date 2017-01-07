package hr.vinko.apr.zad5.function;

import hr.vinko.apr.zad1.Matrix;

public class RungeKutta4Method implements DifferentialEquationMethod {

	@Override
	public Matrix getValue(Matrix A, Matrix B, Matrix x, double t, double T) {
		
		Matrix m1 = diffValue(A, x, B, t);
		Matrix m2 = diffValue(A, x.add(m1.scalarMultiply(T/2)), B, t + T/2);
		Matrix m3 = diffValue(A, x.add(m2.scalarMultiply(T/2)), B, t + T/2);
		Matrix m4 = diffValue(A, x.add(m3.scalarMultiply(T)), B, t + T);
		
		return x.add(m1.add(m2.scalarMultiply(2).add(m3.scalarMultiply(2).add(m4))).scalarMultiply(T/6));
	}
	
	

}
