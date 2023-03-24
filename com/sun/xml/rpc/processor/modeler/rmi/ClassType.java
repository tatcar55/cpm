/*    */ package com.sun.xml.rpc.processor.modeler.rmi;
/*    */ 
/*    */ import com.sun.xml.rpc.util.ClassNameInfo;
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
/*    */ public final class ClassType
/*    */   extends RmiType
/*    */ {
/*    */   String className;
/*    */   
/*    */   ClassType(String typeSig, String className) {
/* 38 */     super(10, typeSig);
/* 39 */     this.className = className;
/*    */   }
/*    */   
/*    */   public String getClassName() {
/* 43 */     return this.className;
/*    */   }
/*    */   
/*    */   public String typeString(boolean abbrev) {
/* 47 */     String tmp = this.className;
/* 48 */     if (abbrev)
/* 49 */       tmp = ClassNameInfo.getName(tmp); 
/* 50 */     return tmp;
/*    */   }
/*    */   
/*    */   public boolean isNillable() {
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\ClassType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */