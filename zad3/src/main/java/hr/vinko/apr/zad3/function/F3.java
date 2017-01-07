package hr.vinko.apr.zad3.function;

public class F3 extends AbstractFunction {

  @Override
  protected double getFValue(double[] x) {
    return Math.pow(x[0] - 2, 2) + Math.pow(x[1] + 3, 2);
  }

  @Override
  protected double[] getDValue(double[] x) {
    double[] ret = new double[2];
    ret[0] = 2 * Math.pow(x[0] - 2, 2);
    ret[1] = 2 * Math.pow(x[1] + 3, 2);
    return ret;
  }

  @Override
  protected double[][] getHValue(double[] x) {
    double[][] ret = new double[2][2];
    ret[0][0] = ret[1][1] = 2;
    ret[0][1] = ret[1][0] = 0;
    return ret;
  }

}
