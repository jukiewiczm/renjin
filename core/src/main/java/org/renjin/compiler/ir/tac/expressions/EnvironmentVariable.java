package org.renjin.compiler.ir.tac.expressions;

import org.renjin.sexp.Symbol;


/**
 * A {@code Variable} that is bound to the R {@code Environment}.
 */
public class EnvironmentVariable extends Variable {

  private final Symbol name;
  
  public EnvironmentVariable(Symbol name) {
    this.name = name;
  }
  
  public EnvironmentVariable(String name) {
    this(Symbol.get(name));
  }
  
  public Symbol getName() {
    return name;
  }

  @Override
  public String toString() {
    return name.toString();
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    EnvironmentVariable other = (EnvironmentVariable) obj;
    return name == other.name;
  }

  @Override
  public boolean isDefinitelyPure() {
    return false;
  }


}
