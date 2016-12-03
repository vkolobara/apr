package hr.vinko.apr.zad3.algorithm;

import hr.vinko.apr.zad3.function.IFunction;

public interface IAlgorithm {

  public double[] solve(double[] x0, IFunction f);

}
