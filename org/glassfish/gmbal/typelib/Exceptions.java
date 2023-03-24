/*    */ package org.glassfish.gmbal.typelib;
/*    */ 
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import org.glassfish.gmbal.logex.Chain;
/*    */ import org.glassfish.gmbal.logex.ExceptionWrapper;
/*    */ import org.glassfish.gmbal.logex.Log;
/*    */ import org.glassfish.gmbal.logex.Message;
/*    */ import org.glassfish.gmbal.logex.WrapperGenerator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ExceptionWrapper(idPrefix = "GMBALTLIB", resourceBundle = "org.glassfish.gmbal.logex.LogStrings")
/*    */ public interface Exceptions
/*    */ {
/* 57 */   public static final Exceptions self = (Exceptions)WrapperGenerator.makeWrapper(Exceptions.class);
/*    */   public static final int EXCEPTIONS_PER_CLASS = 100;
/*    */   public static final int TYPE_EVALUATOR_START = 1;
/*    */   
/*    */   @Message("Internal error in TypeEvaluator")
/*    */   @Log(id = 1)
/*    */   IllegalStateException internalTypeEvaluatorError(@Chain Exception paramException);
/*    */   
/*    */   @Message("evaluateType should not be called with a Method ({0})")
/*    */   @Log(id = 2)
/*    */   IllegalArgumentException evaluateTypeCalledWithMethod(Object paramObject);
/*    */   
/*    */   @Message("evaluateType should not be called with an unknown type ({0})")
/*    */   @Log(id = 3)
/*    */   IllegalArgumentException evaluateTypeCalledWithUnknownType(Object paramObject);
/*    */   
/*    */   @Message("Multiple upper bounds not supported on {0}")
/*    */   @Log(id = 4)
/*    */   UnsupportedOperationException multipleUpperBoundsNotSupported(Object paramObject);
/*    */   
/*    */   @Message("Type list and TypeVariable list are not the same length for {0}")
/*    */   @Log(id = 5)
/*    */   IllegalArgumentException listsNotTheSameLengthInParamType(ParameterizedType paramParameterizedType);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\Exceptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */