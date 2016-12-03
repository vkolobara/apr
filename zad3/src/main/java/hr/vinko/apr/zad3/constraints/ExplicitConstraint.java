package hr.vinko.apr.zad3.constraints;

public class ExplicitConstraint implements IConstraint{

  private double[] min;
  private double[] max;

  /**
  * @param min
  * @param max
  */
  public ExplicitConstraint(double[] min, double[] max) {
    this.min = min;
    this.max = max;
  }

  @Override
  public boolean check(double[] x) {
    for (int i=0; i<x.length; i++) {
      if (x[i] < min[i] || x[i] > max[i]) return false;
    }
    return true;
  }

  /**
   * @return the min
   */
  public double[] getMin() {
    return min;
  }

  /**
   * @return the max
   */
  public double[] getMax() {
    return max;
  }

}
