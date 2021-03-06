package org.renjin.primitives.subset;

import org.renjin.eval.EvalException;
import org.renjin.sexp.IntVector;
import org.renjin.sexp.LogicalVector;

/**
 * Matrix subscript 
 */
class LogicalSubscript implements Subscript {
  
  private LogicalVector subscript;
  private int resultLength;

  public LogicalSubscript(LogicalVector subscript, int sourceLength) {
    this.subscript = subscript;
    this.resultLength = Math.max(sourceLength, subscript.length());
  }

  @Override
  public int computeUniqueIndex() {
    // In the context of the [[ operator, we treat logical subscripts as integers
    SubsetAssertions.checkUnitLength(subscript);

    int oneBasedIndex = subscript.getElementAsInt(0);
    if(IntVector.isNA(oneBasedIndex)) {
      throw new EvalException("subscript out of bounds");
    }
    if(oneBasedIndex == 0) {
      throw new EvalException("attempt to select less than one element");
    }

    return oneBasedIndex - 1;
  }

  @Override
  public IndexIterator computeIndexes() {
    if(subscript.length() == 0) {
      return EmptyIndexIterator.INSTANCE;
    }
    return new Iterator();
  }

  @Override
  public IndexPredicate computeIndexPredicate() {
    return new LogicalPredicate(subscript);
  }
  
  private class Iterator implements IndexIterator {

    int nextIndex = 0;

    @Override
    public int next() {
      while(true) {
        if(nextIndex >= resultLength) {
          return EOF;
        }
        int sourceIndex = nextIndex++;
        int subscriptValue = subscript.getElementAsRawLogical(sourceIndex % subscript.length());
        if(subscriptValue == 1) {
          return sourceIndex;
        }
        if(IntVector.isNA(subscriptValue)) {
          return IntVector.NA;
        }
      }
    }

    @Override
    public void restart() {
      nextIndex = 0;
    }
  }

}
