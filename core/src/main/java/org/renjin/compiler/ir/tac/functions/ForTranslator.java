package org.renjin.compiler.ir.tac.functions;

import org.renjin.compiler.ir.tac.IRBodyBuilder;
import org.renjin.compiler.ir.tac.IRLabel;
import org.renjin.compiler.ir.tac.expressions.*;
import org.renjin.compiler.ir.tac.statements.Assignment;
import org.renjin.compiler.ir.tac.statements.GotoStatement;
import org.renjin.compiler.ir.tac.statements.IfStatement;
import org.renjin.sexp.Function;
import org.renjin.sexp.FunctionCall;
import org.renjin.sexp.SEXP;
import org.renjin.sexp.Symbol;

public class ForTranslator extends FunctionCallTranslator {

  @Override
  public Expression translateToExpression(IRBodyBuilder builder, TranslationContext context, Function resolvedFunction, FunctionCall call) {
    addForLoop(builder, context, call);
    
    return Constant.NULL;
  }


  @Override
  public void addStatement(IRBodyBuilder builder, TranslationContext context, Function resolvedFunction, FunctionCall call) {
    addForLoop(builder, context, call);
  }
 
  private void addForLoop(IRBodyBuilder factory, TranslationContext context, FunctionCall call) {

    Expression vector =
        factory.translateSimpleExpression(context, call.getArgument(1));

    // initialize the counter
    LocalVariable counter = factory.newLocalVariable("i");
    factory.addStatement(new Assignment(counter, new Constant(0)));

    buildLoop(context, factory, call, vector, counter);
  }

  public static void buildLoop(TranslationContext parentContext, IRBodyBuilder factory, FunctionCall call, Expression vector, LValue counter) {
    Symbol symbol = call.getArgument(0);
    Temp length = factory.newTemp();

    Variable elementVariable = factory.getEnvironmentVariable(symbol);


    SEXP body = call.getArgument(2);

    IRLabel counterLabel = factory.newLabel();
    IRLabel bodyLabel = factory.newLabel();
    IRLabel nextLabel = factory.newLabel();
    IRLabel exitLabel = factory.newLabel();

    factory.addStatement(new Assignment(length, new Length(vector)));

    // check the counter and potentially loop
    factory.addLabel(counterLabel);
    factory.addStatement(new IfStatement(new CmpGE(counter, length), exitLabel, bodyLabel));

    // start the body here
    factory.addLabel(bodyLabel);
    factory.addStatement(new Assignment(elementVariable, new ElementAccess(vector, counter)));

    LoopContext loopContext = new LoopContext(parentContext, nextLabel, exitLabel);
    factory.translateStatements(loopContext, body);

    // increment the counter
    factory.addLabel(nextLabel);
    factory.addStatement(new Assignment(counter, new Increment(counter)));
    factory.addStatement(new GotoStatement(counterLabel));

    factory.addLabel(exitLabel);
  }
}
