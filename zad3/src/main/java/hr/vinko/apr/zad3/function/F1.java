package hr.vinko.apr.zad3.function;

public class F1 extends AbstractFunction{

  @Override
  protected double[] getDValue(double[] x) {
    double[] ret = new double[2];
    ret[0] = 2 * (200 * Math.pow(x[0], 3) - 200 * x[0] * x[1] + x[0] - 1);
    ret[1] = 200 * (x[1] - Math.pow(x[0], 2));
    return ret;
  }

  @Override
  protected double getFValue(double[] x) {
    return 100 * Math.pow((x[1] - Math.pow(x[0], 2)), 2) + Math.pow(1-x[0], 2);
  }

  @Override
  protected double[][] getHValue(double[] x) {
    double[][] ret = new double[2][2];
    ret[0][0] = -400 * (x[1] - Math.pow(x[0], 2)) + 800 * Math.pow(x[0], 2) + 2;
    ret[0][1] = -400 * x[0];
    ret[1][0] = -400 * x[0];
    ret[1][1] = 200;
    return ret;
  }

}
