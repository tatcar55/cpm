/*    */ package com.sun.xml.rpc.util;
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
/*    */ public final class ClassNameInfo
/*    */ {
/*    */   public static String getName(String className) {
/* 36 */     String qual = getQualifier(className);
/* 37 */     return (qual != null) ? className.substring(qual.length() + 1) : className;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getQualifier(String className) {
/* 43 */     int idot = className.indexOf(' ');
/* 44 */     if (idot <= 0) {
/* 45 */       idot = className.length();
/*    */     } else {
/* 47 */       idot--;
/* 48 */     }  int index = className.lastIndexOf('.', idot - 1);
/* 49 */     return (index < 0) ? null : className.substring(0, index);
/*    */   }
/*    */   
/*    */   public static String replaceInnerClassSym(String name) {
/* 53 */     return name.replace('$', '_');
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\ClassNameInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */