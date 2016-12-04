package hr.vinko.apr.zad3;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import hr.vinko.apr.zad3.algorithm.Box;
import hr.vinko.apr.zad3.algorithm.GradientDescent;
import hr.vinko.apr.zad3.algorithm.NewtonRaphson;
import hr.vinko.apr.zad3.algorithm.TransformationMixed;
import hr.vinko.apr.zad3.constraints.ExplicitConstraint;
import hr.vinko.apr.zad3.constraints.ImplicitConstraint;
import hr.vinko.apr.zad3.constraints.ImplicitConstraintEquality;
import hr.vinko.apr.zad3.constraints.ImplicitConstraintInequality;
import hr.vinko.apr.zad3.function.AbstractFunction;
import hr.vinko.apr.zad3.function.F1;
import hr.vinko.apr.zad3.function.F2;
import hr.vinko.apr.zad3.function.F3;
import hr.vinko.apr.zad3.function.F4;
import hr.vinko.apr.zad3.function.IFunction;

public class Main {
 
  public static void main(String[] args) {
	  
	  System.setOut(new PrintStream(new OutputStream() {

		     @Override
		     public void write(int arg0) throws IOException {

		     }
		  }));
	  
	  
	  zad5();
  }


  private static void zad1() {
    IFunction f3 = new F3();
    GradientDescent gd1 = new GradientDescent(false, 1e-6, 100);
    GradientDescent gd2 = new GradientDescent(true, 1e-6, 100);

    double[] x0 = new double[] {0, 0};

    System.err.println(Arrays.toString(gd1.solve(x0, f3)));
    System.err.println(Arrays.toString(gd2.solve(x0, f3)));
  }

  private static void zad2() {
	  AbstractFunction f1 = new F1();
	  GradientDescent gd = new GradientDescent(true, 1e-6, 100);
	  NewtonRaphson nr = new NewtonRaphson(true, 1e-6, 100);
	  
	  double[] x0 = new double[]{-1.9, 2};
	  
	  double[] sol = gd.solve(x0, f1);
	  
	  System.err.println("F1");
	  System.err.println("Gradient Descent");
	  
	  System.err.println(Arrays.toString(sol));
	  System.err.println(f1.getValueCounter() + " " + f1.getDerivationCounter() + " " + f1.getHessianCounter());
	  f1.clear();
	  
	  System.err.println("Newton Raphson");
	  
	  sol = nr.solve(x0, f1);
	  System.err.println(Arrays.toString(sol));
	  System.err.println(f1.getValueCounter() + " " + f1.getDerivationCounter() + " " + f1.getHessianCounter());
	  f1.clear();	  
	  
	  AbstractFunction f2 = new F2();
	  x0 = new double[] {0.1, 0.3};
	  System.err.println();
	  System.err.println("F2");
	  System.err.println("Gradient Descent");
	  
	  sol = gd.solve(x0, f2);
	  System.err.println(Arrays.toString(sol));
	  System.err.println(f2.getValueCounter() + " " + f2.getDerivationCounter() + " " + f2.getHessianCounter());
	  f2.clear();
	  
	  System.err.println("Newton Raphson");
	  
	  sol = nr.solve(x0, f2);
	  System.err.println(Arrays.toString(sol));
	  System.err.println(f2.getValueCounter() + " " + f2.getDerivationCounter() + " " + f2.getHessianCounter());
	  f2.clear();	  
  }
  
  private static void zad3() {
	  AbstractFunction f1 = new F1();
	  double[] x0 = new double[]{-1.9, 2};
	  
	  double[] min = new double[]{-100, -100};
	  double[] max = new double[]{100, 100};
	  
	  ExplicitConstraint expConst = new ExplicitConstraint(min, max);
	  ImplicitConstraintInequality cons1 = new ImplicitConstraintInequality(x -> x[1] - x[0]);
	  ImplicitConstraintInequality cons2 = new ImplicitConstraintInequality(x -> 2 - x[0]);
	  
	  Box box = new Box(1e-6, 100, 1.3, expConst, Arrays.asList(cons1, cons2));
	  
	  double[] sol = box.solve(x0, f1);
	  
	  System.err.println("F1");
	  System.err.println(Arrays.toString(sol));	  
	  
	  AbstractFunction f2 = new F2();
	  x0 = new double[] {0.1, 0.3};

	  sol = box.solve(x0, f2);
	  System.err.println("F2");
	  System.err.println(Arrays.toString(sol));
  }
  
  private static void zad4() {
	  AbstractFunction f1 = new F1();
	  double[] x0 = new double[]{-1.9, 2};
	  
	  ImplicitConstraintInequality cons1 = new ImplicitConstraintInequality(x -> x[1] - x[0]);
	  ImplicitConstraintInequality cons2 = new ImplicitConstraintInequality(x -> 2 - x[0]);
	  

	  TransformationMixed tm = new TransformationMixed(1, 1e-6, new ArrayList<>(), Arrays.asList(cons1, cons2), 100);
	  
	  double[] sol = tm.solve(x0, f1);
	  
	  System.err.println("F1");
	  System.err.println(Arrays.toString(sol));	  
	  
	  AbstractFunction f2 = new F2();
	  x0 = new double[] {1, 3};

	  sol = tm.solve(x0, f2);
	  System.err.println("F2");
	  System.err.println(Arrays.toString(sol));
  }
  
  private static void zad5() {
	  AbstractFunction f4 = new F4();
	  
	  double[] x0 = new double[] {0, 0};
	  
	  ImplicitConstraintInequality cons1 = new ImplicitConstraintInequality(x -> 3 - x[0] - x[1]);
	  ImplicitConstraintInequality cons2 = new ImplicitConstraintInequality(x -> 3 + 1.5*x[0] - x[1]);
	  ImplicitConstraintEquality cons3 = new ImplicitConstraintEquality(x -> x[1] - 1);
	  
	  TransformationMixed tm = new TransformationMixed(1, 1e-6, Arrays.asList(cons3), Arrays.asList(cons1, cons2), 100);
	  
	  double[] sol = tm.solve(x0, f4);
	  
	  System.err.println(Arrays.toString(sol));
	  
	  x0 = new double[] {5, 5};
	  System.err.println(Arrays.toString(tm.solve(x0, f4)));
	  
  }
  
}
