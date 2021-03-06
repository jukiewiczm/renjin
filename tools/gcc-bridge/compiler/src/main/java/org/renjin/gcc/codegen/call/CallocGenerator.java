package org.renjin.gcc.codegen.call;

import org.renjin.gcc.codegen.MethodGenerator;
import org.renjin.gcc.codegen.expr.ExprFactory;
import org.renjin.gcc.codegen.expr.Expressions;
import org.renjin.gcc.codegen.expr.GExpr;
import org.renjin.gcc.codegen.expr.JExpr;
import org.renjin.gcc.codegen.type.TypeOracle;
import org.renjin.gcc.codegen.type.fun.FunctionRefGenerator;
import org.renjin.gcc.gimple.statement.GimpleCall;
import org.renjin.gcc.gimple.type.GimpleType;
import org.renjin.gcc.runtime.MallocThunk;
import org.renjin.repackaged.asm.Handle;
import org.renjin.repackaged.asm.Opcodes;
import org.renjin.repackaged.asm.Type;

/**
 * Allocates zeroed-out memory
 * 
 * <p>This actually what the JVM does by default.</p>
 */
public class CallocGenerator implements CallGenerator, MethodHandleGenerator {
  private TypeOracle typeOracle;

  public CallocGenerator(TypeOracle typeOracle) {
    this.typeOracle = typeOracle;
  }

  @Override
  public void emitCall(MethodGenerator mv, ExprFactory exprFactory, GimpleCall call) {
    // Obviously if we're not assigning this, it's a NO-OP
    if(call.getLhs() == null) {
      return;
    }

    // Generate the malloc for the given type
    GimpleType pointerType = call.getLhs().getType();

    // Find the size to allocate
    JExpr elements = exprFactory.findPrimitiveGenerator(call.getOperands().get(0));
    JExpr elementSize = exprFactory.findPrimitiveGenerator(call.getOperands().get(1));
    JExpr totalBytes = Expressions.product(elements, elementSize);

    GExpr mallocGenerator = typeOracle.forPointerType(pointerType).malloc(mv, totalBytes);
    GExpr lhs = exprFactory.findGenerator(call.getLhs());
    lhs.store(mv, mallocGenerator);
  }
  
  @Override
  public JExpr getMethodHandle() {
    return new FunctionRefGenerator(new Handle(Opcodes.H_INVOKESTATIC,
        Type.getInternalName(MallocThunk.class), "calloc",
        Type.getMethodDescriptor(Type.getType(Object.class), Type.INT_TYPE, Type.INT_TYPE)));
  }
}
