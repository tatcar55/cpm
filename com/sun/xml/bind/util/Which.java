/*    */ package com.sun.xml.bind.util;
/*    */ 
/*    */ import java.net.URL;
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
/*    */ 
/*    */ public class Which
/*    */ {
/*    */   public static String which(Class clazz) {
/* 54 */     return which(clazz.getName(), SecureLoader.getClassClassLoader(clazz));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String which(String classname, ClassLoader loader) {
/* 66 */     String classnameAsResource = classname.replace('.', '/') + ".class";
/*    */     
/* 68 */     if (loader == null) {
/* 69 */       loader = SecureLoader.getSystemClassLoader();
/*    */     }
/*    */     
/* 72 */     URL it = loader.getResource(classnameAsResource);
/* 73 */     if (it != null) {
/* 74 */       return it.toString();
/*    */     }
/* 76 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bin\\util\Which.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */