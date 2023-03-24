/*    */ package com.sun.xml.ws.spi.db;
/*    */ 
/*    */ import java.lang.reflect.Method;
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
/*    */ public abstract class PropertyGetterBase
/*    */   implements PropertyGetter
/*    */ {
/*    */   protected Class type;
/*    */   
/*    */   public Class getType() {
/* 53 */     return this.type;
/*    */   }
/*    */   
/*    */   public static boolean getterPattern(Method method) {
/* 57 */     if (!method.getReturnType().equals(void.class) && (method.getParameterTypes() == null || (method.getParameterTypes()).length == 0)) {
/*    */ 
/*    */       
/* 60 */       if (method.getName().startsWith("get") && method.getName().length() > 3)
/*    */       {
/* 62 */         return true;
/*    */       }
/* 64 */       if (method.getReturnType().equals(boolean.class) && method.getName().startsWith("is") && method.getName().length() > 2)
/*    */       {
/*    */         
/* 67 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 71 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\PropertyGetterBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */