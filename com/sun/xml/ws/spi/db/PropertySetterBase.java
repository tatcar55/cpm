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
/*    */ public abstract class PropertySetterBase
/*    */   implements PropertySetter
/*    */ {
/*    */   protected Class type;
/*    */   
/*    */   public Class getType() {
/* 52 */     return this.type;
/*    */   }
/*    */   
/*    */   public static boolean setterPattern(Method method) {
/* 56 */     return (method.getName().startsWith("set") && method.getName().length() > 3 && method.getReturnType().equals(void.class) && method.getParameterTypes() != null && (method.getParameterTypes()).length == 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\PropertySetterBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */