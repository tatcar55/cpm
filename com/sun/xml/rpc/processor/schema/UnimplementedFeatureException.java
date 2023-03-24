/*    */ package com.sun.xml.rpc.processor.schema;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.ModelException;
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
/*    */ public class UnimplementedFeatureException
/*    */   extends ModelException
/*    */ {
/*    */   public UnimplementedFeatureException(String arg) {
/* 40 */     super("model.schema.notImplemented", arg);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\UnimplementedFeatureException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */