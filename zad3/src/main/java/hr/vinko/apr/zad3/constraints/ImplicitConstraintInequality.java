package hr.vinko.apr.zad3.constraints;

import hr.vinko.apr.zad2.FunctionWValue;;

public class ImplicitConstraintInequality extends ImplicitConstraint{

  public ImplicitConstraintInequality(FunctionWValue f) {
    super(f);
  }

  @Override
  public boolean check(double[] x) {
    return f.getValueAt(x) >= 0;
  }

}
