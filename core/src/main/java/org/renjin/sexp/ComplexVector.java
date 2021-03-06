package org.renjin.sexp;

import org.apache.commons.math.complex.Complex;
import org.renjin.parser.NumericLiterals;
import org.renjin.primitives.vector.ConvertingComplexVector;
import org.renjin.repackaged.guava.collect.UnmodifiableIterator;

import java.util.Iterator;

public abstract class ComplexVector extends AbstractAtomicVector implements Iterable<Complex> {
  public static final String TYPE_NAME = "complex";
  public static final ComplexVector EMPTY = new ComplexArrayVector();
  
  public static final ComplexVector NAMED_EMPTY = new ComplexArrayVector(new Complex[0], 
      AttributeMap.builder().setNames(StringVector.EMPTY).build());

  public static final Complex NA = new Complex(DoubleVector.NA, DoubleVector.NA);
  public static final Complex NaN = new Complex(DoubleVector.NaN, DoubleVector.NaN);

  public static final Type VECTOR_TYPE = new ComplexType();

  public static Complex complex(double real) {
    if(DoubleVector.isNA(real)) {
      return ComplexVector.NA;
    }
    return complex(real, 0);
  }
  
  public static Complex complex(double real, double imaginary) {
    return new Complex(real, imaginary);
  }

  public Complex[] toComplexArray() {
    Complex[] complexNumbers = new Complex[this.length()];
    for (int i = 0; i < this.length(); i++) {
      complexNumbers[i] = this.getElementAsComplex(i);
    }
    return complexNumbers;
  }

  public static ComplexVector valueOf(Complex value) {
    return new ComplexArrayVector(value);
  }

  public static ComplexVector valueOf(double real) {
    return new ComplexArrayVector(complex(real));
  }
  
  public ComplexVector() {
    super();
  }

  public ComplexVector(AttributeMap attributes) {
    super(attributes);
  }

  public static boolean isNA(Complex value) {
    return DoubleVector.isNA(value.getReal());
  }

  @Override
  public String getTypeName() {
    return TYPE_NAME;
  }

  @Override
  public void accept(SexpVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public abstract int length();

  @Override
  public abstract Complex getElementAsComplex(int index);

  /**
   * @return the imaginary part of the complex number at {@code index}
   */
  public double getElementAsComplexIm(int index) {
    return getElementAsComplex(index).getImaginary();
  }
  
  @Override
  public SEXP getElementAsSEXP(int index) {
    return new ComplexArrayVector(getElementAsComplex(index));
  }

  @Override
  public Comparable getElementAsObject(int index) {
    throw new UnsupportedOperationException("how can Complex not implement Comparable!!!");
  }

  @Override
  public double getElementAsDouble(int index) {
    return getElementAsComplex(index).getReal();
  }

  @Override
  public int getElementAsInt(int index) {
    double value = getElementAsDouble(index);
    return DoubleVector.isNA(value) ? IntVector.NA : (int)value;
  }

  @Override
  public String getElementAsString(int index) {
    return NumericLiterals.toString(getElementAsComplex(index));
  }

  @Override
  public int getElementAsRawLogical(int index) {
    double value = getElementAsDouble(index);
    if(value == 0) {
      return 0;
    } else if(DoubleVector.isNA(value)) {
      return IntVector.NA;
    } else {
      return 1;
    }
  }

  @Override
  public Builder newBuilderWithInitialSize(int initialSize) {
    return new ComplexArrayVector.Builder(initialSize);
  }

  @Override
  public Builder newBuilderWithInitialCapacity(int initialCapacity) {
    return ComplexArrayVector.Builder.withInitialCapacity(initialCapacity);
  }

  @Override
  public Builder newCopyBuilder() {
    return new ComplexArrayVector.Builder(this);
  }

  @Override
  public boolean isElementNA(int index) {
    return isNA(getElementAsComplex(index));
  }

  @Override
  public boolean isElementNaN(int index) {
    double real = getElementAsComplex(index).getReal();
    return Double.isNaN(real);
  }

  @Override
  public Type getVectorType() {
    return VECTOR_TYPE;
  }

  @Override
  public int compare(int index1, int index2) {
    throw new UnsupportedOperationException("implement me");
  }

  @Override
  public int indexOf(AtomicVector vector, int vectorIndex, int startIndex) {
    Complex value = vector.getElementAsComplex(vectorIndex);

    for (int i = startIndex; i < length(); ++i) {
      Complex match = getElementAsComplex(i);
      if (DoubleVector.match(value.getReal(), match.getReal()) &&
          DoubleVector.match(value.getImaginary(), match.getImaginary())) {
        return i;
      }
    }
    return -1;
  }
   

  private boolean isNaN(Complex value) {
    return Double.isNaN(value.getReal());
  }

  @Override
  public Iterator<Complex> iterator() {
    return new UnmodifiableIterator<Complex>() {
      private int index = 0;

      @Override
      public boolean hasNext() {
        return index < length();
      }

      @Override
      public Complex next() {
        return getElementAsComplex(index++);
      }
    };
  }



  private static class ComplexType extends Type {
    public ComplexType() {
      super(Order.COMPLEX);
    }

    @Override
    public ComplexArrayVector.Builder newBuilder() {
      return new ComplexArrayVector.Builder(0, 0);
    }

    @Override
    public ComplexArrayVector.Builder newBuilderWithInitialSize(int length) {
      return new ComplexArrayVector.Builder(length);
    }

    @Override
    public ComplexArrayVector.Builder newBuilderWithInitialCapacity(int initialCapacity) {
      return new ComplexArrayVector.Builder(0, initialCapacity);
    }

    @Override
    public int compareElements(Vector vector1, int index1, Vector vector2, int index2) {
      throw new UnsupportedOperationException("invalid comparison with complex values");
    }

    @Override
    public boolean elementsEqual(Vector vector1, int index1, Vector vector2, int index2) {
      return vector1.getElementAsComplex(index1).equals(vector2.getElementAsComplex(index2));
    }

    @Override
    public Vector to(final Vector x) {
      if(x instanceof ComplexVector) {
        return x;
      } else {
        return new ConvertingComplexVector(x, x.getAttributes());
      }
    }

    @Override
    public Vector getElementAsVector(Vector vector, int index) {
      return new ComplexArrayVector(vector.getElementAsComplex(index));
    }
  }
}
