package hr.vinko.apr.zad3.constraints;

import hr.vinko.apr.IFunction;

public class ImplicitConstraintEquality extends ImplicitConstraint {

  public ImplicitConstraintEquality(IFunction f) {
    super(f);
  }

  @Override
  public boolean check(double[] x) {
    return Math.abs(f.getValueAt(x)) < 1e-6;
  }

}
