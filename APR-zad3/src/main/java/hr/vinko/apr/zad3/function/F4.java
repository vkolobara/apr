package hr.vinko.apr.zad3.function;

public class F4 extends AbstractFunction {

  @Override
  protected double getFValue(double[] x) {
    return Math.pow(x[0] - 3, 2) + Math.pow(x[1], 2);
  }

  @Override
  protected double[] getDValue(double[] x) {
    double[] ret = new double[2];
    ret[0] = 2 * (x[0] - 2);
    ret[1] = 2 * (x[1] + 3);
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
