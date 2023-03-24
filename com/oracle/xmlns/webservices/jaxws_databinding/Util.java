/*    */ package com.oracle.xmlns.webservices.jaxws_databinding;
/*    */ 
/*    */ import com.sun.xml.ws.model.RuntimeModelerException;
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
/*    */ 
/*    */ class Util
/*    */ {
/*    */   static String nullSafe(String value) {
/* 53 */     return (value == null) ? "" : value;
/*    */   }
/*    */   
/*    */   static <T> T nullSafe(T value, T defaultValue) {
/* 57 */     return (value == null) ? defaultValue : value;
/*    */   }
/*    */ 
/*    */   
/*    */   static <T extends Enum> T nullSafe(Enum value, T defaultValue) {
/* 62 */     return (value == null) ? defaultValue : (T)Enum.valueOf(defaultValue.getClass(), value.toString());
/*    */   }
/*    */   
/*    */   public static Class<?> findClass(String className) {
/*    */     try {
/* 67 */       return Class.forName(className);
/* 68 */     } catch (ClassNotFoundException e) {
/* 69 */       throw new RuntimeModelerException("runtime.modeler.external.metadata.generic", new Object[] { e });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\xmlns\webservices\jaxws_databinding\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */