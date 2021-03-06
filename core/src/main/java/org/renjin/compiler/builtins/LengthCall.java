package org.renjin.compiler.builtins;

import org.renjin.compiler.codegen.EmitContext;
import org.renjin.compiler.ir.ValueBounds;
import org.renjin.compiler.ir.tac.IRArgument;
import org.renjin.compiler.ir.tac.expressions.Expression;
import org.renjin.repackaged.asm.Type;
import org.renjin.repackaged.asm.commons.InstructionAdapter;
import org.renjin.sexp.SEXP;

import java.util.List;


public class LengthCall implements Specialization {
  @Override
  public Type getType() {
    return Type.INT_TYPE;
  }

  @Override
  public ValueBounds getValueBounds() {
    return ValueBounds.INT_PRIMITIVE;
  }

  @Override
  public void load(EmitContext emitContext, InstructionAdapter mv, List<IRArgument> arguments) {
    Expression argument = arguments.get(0).getExpression();
    argument.load(emitContext, mv);
    emitContext.convert(mv, argument.getType(), Type.getType(SEXP.class));
    
    mv.invokeinterface(Type.getInternalName(SEXP.class), "length", 
        Type.getMethodDescriptor(Type.INT_TYPE, Type.getType(SEXP.class)));
  }
}
