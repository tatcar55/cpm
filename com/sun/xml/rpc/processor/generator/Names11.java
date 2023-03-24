/*    */ package com.sun.xml.rpc.processor.generator;
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
/*    */ public class Names11
/*    */   extends Names
/*    */ {
/*    */   private String makeSafeClassName(String basePackage, String className) {
/* 36 */     if (className.startsWith("java.") || className.startsWith("javax."))
/* 37 */       className = basePackage + ".serializers." + className; 
/* 38 */     return className;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String serializerClassName(String basePackage, String className, String suffix) {
/* 45 */     className = makeSafeClassName(basePackage, className);
/* 46 */     if (this.serializerNameInfix != null)
/* 47 */       className = className + this.serializerNameInfix; 
/* 48 */     return (className + suffix).replace('$', '_');
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String builderClassName(String basePackage, String className, String suffix) {
/* 55 */     className = makeSafeClassName(basePackage, className);
/* 56 */     if (this.serializerNameInfix != null)
/* 57 */       className = className + this.serializerNameInfix; 
/* 58 */     return (className + suffix).replace('$', '_');
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\Names11.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */