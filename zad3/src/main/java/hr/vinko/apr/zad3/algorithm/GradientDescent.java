package hr.vinko.apr.zad3.algorithm;

import java.util.Arrays;

import hr.vinko.apr.GoldenSection;
import hr.vinko.apr.zad3.function.IFunction;

public class GradientDescent implements IAlgorithm {

  private boolean useGoldenSection;
  private double epsilon;
  private int iterWithoutProgress;


  /**
  * @param useGoldenSection
  * @param epsilon
  * @param iterWithoutProgress
  */
  public GradientDescent(boolean useGoldenSection, double epsilon, int iterWithoutProgress) {
    this.useGoldenSection = useGoldenSection;
    this.epsilon = epsilon;
    this.iterWithoutProgress = iterWithoutProgress;
  }

  @Override
  public double[] solve(double[] x0, IFunction f) {
    double[] xCur = Arrays.copyOf(x0, x0.length);
    int currIters = 0;
    double bestF = Double.MAX_VALUE;

    while(true) {
  
      double val = f.getValueAt(xCur);

      if (val < bestF) {
        bestF = val;
        currIters = 0;
      }
      currIters++;

      if (currIters >= iterWithoutProgress) {
    	  System.err.println("NO PROGRESS!");
    	  break;
      }

      double[] grad = f.getDerivationAt(xCur);
      double lambda = -1;
      
      if (norm(grad) < epsilon) break;

      if (useGoldenSection) {
        GoldenSection gs = new GoldenSection();
        
        hr.vinko.apr.IFunction f1 = new hr.vinko.apr.IFunction () {
          public double getValueAt(double... x) {
            double[] x1 = Arrays.copyOf(xCur, xCur.length);
            for (int i=0; i<grad.length; i++) {
              x1[i] += x[0] * grad[i];
            }
            return f.getValueAt(x1);
          }
        };
        double[] sol = gs.solve(new double[]{0}, f1);
        lambda = (sol[0] + sol[1]) / 2;
      }

      for (int i=0; i<xCur.length; i++) {
        xCur[i] += lambda * grad[i];
      }

  
    }

    return xCur;
  }

  private double norm(double[] x) {
    double sum = 0;
    for (int i=0; i<x.length; i++) {
      sum += x[i] * x[i];
    }

    return Math.sqrt(sum);

  }

}
