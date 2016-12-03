package hr.vinko.apr.zad3.algorithm;

import java.util.Arrays;
import java.util.List;

import hr.vinko.apr.Simplex;
import hr.vinko.apr.zad3.constraints.ImplicitConstraintEquality;
import hr.vinko.apr.zad3.constraints.ImplicitConstraintInequality;
import hr.vinko.apr.zad3.function.IFunction;

public class TransformationMixed implements IAlgorithm{


  private double t;
  private double epsilon;
  private List<ImplicitConstraintEquality> equalityConstraints;
  private List<ImplicitConstraintInequality> inequalityConstraints;
  private int iterWithoutProgress;

  @Override
  public double[] solve(double[] x0, IFunction f) {

    hr.vinko.apr.IFunction u = new hr.vinko.apr.IFunction() {
      public double getValueAt(double... x) {
        double sum = 0;
        
        sum += f.getValueAt(x);


        for (ImplicitConstraintInequality cons : inequalityConstraints) {
          double value = Math.log(cons.getF().getValueAt(x));
          if (Double.isNaN(value)) value = Double.MAX_VALUE;
          sum -= 1.0/t * value;
        }

        for (ImplicitConstraintEquality cons : equalityConstraints) {
          sum += t * cons.getF().getValueAt(x);
        }

        return sum;
      }
    };

    //TODO provjeri ulaznu tocku i uradi pretrazivanje s unutarnjom tockom.

    double[] x = Arrays.copyOf(x0, x0.length);
 
    double bestF = Double.MAX_VALUE;
    int iters = 0;

    while(true) {
      Simplex simplex = new Simplex();
      double[] solution = simplex.solve(x, u);
      int i=0;
      for (; i<solution.length; i++) {
        if (Math.abs(solution[i] - x[i]) > epsilon) break;
      } 
      x = solution;
      
      double value = u.getValueAt(x);

      if (value < bestF) {
        bestF = value;
        iters = 0;
      }
      iters++;
      if (iters >= iterWithoutProgress) break;
      if (i==solution.length) break;


    }


    return x;
  }

}
