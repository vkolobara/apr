package hr.vinko.apr.zad3.constraints;

import hr.vinko.apr.IFunction;

public class ImplicitConstraintInequality extends ImplicitConstraint{

  public ImplicitConstraintInequality(IFunction f) {
    super(f);
  }

  @Override
  public boolean check(double[] x) {
    return f.getValueAt(x) >= 0;
  }

}
