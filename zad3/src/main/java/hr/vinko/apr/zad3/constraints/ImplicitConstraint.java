package hr.vinko.apr.zad3.constraints;

import hr.vinko.apr.zad2.FunctionWValue;

public abstract class ImplicitConstraint implements IConstraint{

  protected FunctionWValue f;


  /**
  * @param f
  */
  public ImplicitConstraint(FunctionWValue f) {
    this.f = f;
  }

  /**
   * @return the f
   */
  public FunctionWValue getF() {
    return f;
  }

}
