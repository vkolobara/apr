package hr.vinko.apr.zad3.function;

public class F2 extends AbstractFunction {

  @Override
  protected double[] getDValue(double[] x) {
    double[] ret = new double[2];
    ret[0] = 2 * (x[0] - 4);
    ret[1] = 8 * (x[1] - 2);
    return ret;
  }

  @Override
  protected double getFValue(double[] x) {
    return Math.pow(x[0] - 4, 2) + 4 * Math.pow(x[1] - 2, 2);
  }

  @Override
  protected double[][] getHValue(double[] x) {
    double[][] ret = new double[2][2];
    ret[0][0] = 2;
    ret[0][1] = ret[1][0] = 0;
    ret[1][1] = 8;
    return ret;
  }

}
