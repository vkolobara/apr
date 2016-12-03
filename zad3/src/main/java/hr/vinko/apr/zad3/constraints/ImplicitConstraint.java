package hr.vinko.apr.zad3.constraints;

import hr.vinko.apr.IFunction;

public abstract class ImplicitConstraint implements IConstraint{

  protected IFunction f;


  /**
  * @param f
  */
  public ImplicitConstraint(IFunction f) {
    this.f = f;
  }

  /**
   * @return the f
   */
  public IFunction getF() {
    return f;
  }

}
