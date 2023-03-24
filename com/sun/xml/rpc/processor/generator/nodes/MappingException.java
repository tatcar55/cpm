/*    */ package com.sun.xml.rpc.processor.generator.nodes;
/*    */ 
/*    */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
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
/*    */ public class MappingException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public MappingException(String key, Object[] args) {
/* 29 */     super(key, args);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 33 */     return "com.sun.xml.rpc.resources.j2ee";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\nodes\MappingException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */