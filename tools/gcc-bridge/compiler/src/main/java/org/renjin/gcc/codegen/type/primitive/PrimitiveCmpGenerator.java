package org.renjin.gcc.codegen.type.primitive;

import org.renjin.gcc.codegen.MethodGenerator;
import org.renjin.gcc.codegen.condition.ConditionGenerator;
import org.renjin.gcc.codegen.expr.JExpr;
import org.renjin.gcc.gimple.GimpleOp;
import org.renjin.repackaged.asm.Label;
import org.renjin.repackaged.asm.Type;
import org.renjin.repackaged.guava.base.Preconditions;

import static org.renjin.gcc.codegen.expr.Expressions.flip;
import static org.renjin.repackaged.asm.Opcodes.*;

/**
 * Generates codes for binary comparisons
 */
public class PrimitiveCmpGenerator implements ConditionGenerator {
  
  private GimpleOp op;
  private JExpr x;
  private JExpr y;

  
  public static PrimitiveCmpGenerator unsigned(GimpleOp op, JExpr x, JExpr y) {
    Preconditions.checkArgument(x.getType().equals(y.getType()));
    
    // For straight-up equality checks, nothing special is needed for unsigned numbers
    if(op == GimpleOp.EQ_EXPR || op == GimpleOp.NE_EXPR) {
      return new PrimitiveCmpGenerator(op, x, y);
    }
    
    // The JVM has exactly ONE unsigned integer type: char
    // So nothing special needed here.
    if (x.getType().equals(Type.CHAR_TYPE)) {
      return new PrimitiveCmpGenerator(op, x, y);
    }

    // Otherwise, map unsigned space to signed one [2^63,2^63-1] before the comparison
    return new PrimitiveCmpGenerator(op, flip(x), flip(y));
  }
  
  
  public PrimitiveCmpGenerator(GimpleOp op, JExpr x, JExpr y) {
    this.op = op;
    this.x = x;
    this.y = y;
  }

  @Override
  public void emitJump(MethodGenerator mv, Label trueLabel, Label falseLabel) {

    Type tx = x.getType();
    Type ty = y.getType();
    
    if(!tx.equals(ty)) {
      throw new UnsupportedOperationException("Type mismatch: " + tx + " != " + ty);
    }
    
    // Push two operands on the stack
    x.load(mv);
    y.load(mv);

    if(tx.equals(Type.DOUBLE_TYPE) ||
        ty.equals(Type.FLOAT_TYPE)) {

      emitRealJump(mv, trueLabel);

    } else {
      
      mv.visitJumpInsn(integerComparison(), trueLabel);
    } 
    
    mv.visitJumpInsn(GOTO, falseLabel);
  }

  private int integerComparison() {
    switch (op) {
      case LT_EXPR:
        return IF_ICMPLT;
      case LE_EXPR:
        return IF_ICMPLE;
      case EQ_EXPR:
        return IF_ICMPEQ;
      case NE_EXPR:
        return IF_ICMPNE;
      case GT_EXPR:
        return IF_ICMPGT;
      case GE_EXPR:
        return IF_ICMPGE;
    }
    throw new UnsupportedOperationException("op: " + op);
  }


  private void emitRealJump(MethodGenerator mv, Label trueLabel) {


    // Branching on floating point comparisons requires two steps:
    // First we have to do the actual comparison, using DCMPG/DCMPL/FCMPL/FCMPG,
    // which compares the two operands and pushes -1, 0, or 1 onto the stack

    // Then we can compare this value to zero and branch on the result.

    // But because we have floating points, we need to be mindful of NaN values.

    //            CMPG:     CMPL
    // x <  y        1         1
    // y == 0        0         0 
    // x >  y       -1        -1
    // NaN           1        -1

    // So if we're interested in whether x is less than y, we need to use
    // CMPL to make sure that our condition is false if either x or y is NaN
    switch (op) {
      case LT_EXPR:
      case LE_EXPR:
        mv.visitInsn(isDouble() ? DCMPG : FCMPG);
        break;
      default:
        mv.visitInsn(isDouble() ? DCMPL : FCMPL);
        break;
    }

    // Now we jump based on the comparison of the above result to zero
    switch (op) {
      case LT_EXPR:
        // 1: x < y
        mv.visitJumpInsn(IFLT, trueLabel);
        break;
      case LE_EXPR:
        // 1 : x < y
        // 0 : x == y
        mv.visitJumpInsn(IFLE, trueLabel);
        break;
      case EQ_EXPR:
        // 0 : x == y
        mv.visitJumpInsn(IFEQ, trueLabel);
        break;

      case NE_EXPR:
        mv.visitJumpInsn(IFNE, trueLabel);
        break;

      case GT_EXPR:
        mv.visitJumpInsn(IFGT, trueLabel);
        break;

      case GE_EXPR:
        mv.visitJumpInsn(IFGE, trueLabel);
        break;
    }
  }

  private boolean isDouble() {
    return x.getType().equals(Type.DOUBLE_TYPE);
  }
}
