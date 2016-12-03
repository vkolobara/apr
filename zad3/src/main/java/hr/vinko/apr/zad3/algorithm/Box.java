package hr.vinko.apr.zad3.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.vinko.apr.zad3.constraints.ExplicitConstraint;
import hr.vinko.apr.zad3.constraints.IConstraint;
import hr.vinko.apr.zad3.constraints.ImplicitConstraintInequality;
import hr.vinko.apr.zad3.function.IFunction;

public class Box implements IAlgorithm{
  
  private Random rand = new Random();

  private double epsilon;
  private int iterWithoutProgress;
  private double alpha;
  private ExplicitConstraint expConst;
  private List<ImplicitConstraintInequality> constraints;


  /**
  * @param epsilon
  * @param iterWithoutProgress
  * @param alpha
  * @param expConst
  * @param constraints
  */
  public Box(double epsilon, int iterWithoutProgress, double alpha, ExplicitConstraint expConst,
      List<ImplicitConstraintInequality> constraints) {
    this.epsilon = epsilon;
    this.iterWithoutProgress = iterWithoutProgress;
    this.alpha = alpha;
    this.expConst = expConst;
    this.constraints = constraints;
  }

  @Override
  public double[] solve(double[] x0, IFunction f) {

    if (expConst.check(x0)) {
      boolean constSatisfied = true;
      for (IConstraint cons : constraints) {
        if (!cons.check(x0)) {
          constSatisfied = false;
          break;
        }
      }

      if (constSatisfied) {
        List<double[]> simplex = generateSimplex(x0);
        double[] xC = null;
        double bestF = Double.MAX_VALUE;
        int iters = 0;
        while(true) {
          Collections.sort(simplex, (x1, x2) -> -Double.compare(f.getValueAt(x1), f.getValueAt(x2)));
          xC = calculateCentroid(simplex.subList(1, simplex.size()));
          double value = f.getValueAt(xC);
          if (value < bestF) {
            bestF = value;
            iters = 0;
          }
          iters++;

          if (iters >= iterWithoutProgress) break;
          if (stopCondition(xC, simplex, f)) break;



          double[] xR = reflexion(xC, simplex.get(0));

          fixExplicit(xR);
          fixImplicit(xR, xC);
          fixWorst(xR, simplex.get(1), f);

        }
        return xC;
      }

    }

   return null;
  }

  private boolean stopCondition(double[] xC, List<double[]> simplex, IFunction f) {
    double sum = 0;

    for (double[] x : simplex) {
      sum += Math.pow(f.getValueAt(xC) - f.getValueAt(x), 2);
    }

    sum /= simplex.size();
    
    return Math.sqrt(sum) <= epsilon;

  }

  private void fixWorst(double[] xR, double[] xH2, IFunction f) {
    if (f.getValueAt(xR) > f.getValueAt(xH2)) {
      xR = moveTowardCentroid(xR, xH2);
    }

  }

  private void fixImplicit(double[] xR, double[] xC) {
    boolean constSatisfied = false;

    while(!constSatisfied) {
      constSatisfied = true;
      
      for (IConstraint cons : constraints) {
        if (!cons.check(xR)) {
          constSatisfied = false;
          break;
        }
      }
    
      xR = moveTowardCentroid(xR, xC);
    }



  }

  private void fixExplicit(double[] xR) {
    double[] min = expConst.getMin();
    double[] max = expConst.getMax();

    for (int i=0; i<xR.length; i++) {
      if (xR[i] < min[i]) xR[i] = min[i];
      else if (xR[i] > max[i]) xR[i] = max[i];
    }

  }

  private double[] reflexion(double[] xC, double[] xH) {
    double[] xR = new double[xC.length];
    for (int i=0; i<xR.length; i++) {
      xR[i] = (1 + alpha) * xC[i] - alpha * xH[i];
    }

    return xR;

  }

  private List<double[]> generateSimplex(double[] x0) {
    int size = 2 * x0.length;
    List<double[]> simplex = new ArrayList<double[]>(size);

    simplex.add(Arrays.copyOf(x0, x0.length));

    double[] xC = Arrays.copyOf(x0, x0.length);

    double[] min = expConst.getMin();
    double[] max = expConst.getMax();

    while (simplex.size() < size) {
      
      double[] newX = new double[xC.length];
      
      for (int i=0; i<newX.length; i++) {
        double r = rand.nextDouble();
        newX[i] = min[i] + r * (max[i] - min[i]);
      }
      
      boolean areSatisfied = false;
      while(!areSatisfied) {
      
        areSatisfied = true;
        
        for (IConstraint cons : constraints) {
             if (!cons.check(newX)) {
               areSatisfied = false;
              break;
             }
        }
      
        newX = moveTowardCentroid(newX, xC);
      }

      simplex.add(newX);
      xC = calculateCentroid(simplex);
    }

    return simplex;


  }

  private double[] moveTowardCentroid(double[] x, double[] xC) {

    double[] ret = new double[x.length];

    for (int i=0; i<ret.length; i++) {
      ret[i] = 0.5 * (x[i] + xC[i]);
    }
    return ret;

  }

  private double[] calculateCentroid(List<double[]> points) {

    double[] xC = new double[points.get(0).length];

    points.forEach(point -> {
      for (int i=0; i<xC.length; i++) {
        xC[i] += point[i];
      }
    });

    for (int i=0; i<xC.length; i++) xC[i] /= (1.0 * xC.length);
   
    return xC;
  }


}
