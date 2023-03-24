/*    */ package com.sun.xml.ws.util;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.lang.reflect.Constructor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FastInfosetReflection
/*    */ {
/*    */   public static final Constructor fiStAXDocumentParser_new;
/*    */   public static final Method fiStAXDocumentParser_setInputStream;
/*    */   public static final Method fiStAXDocumentParser_setStringInterning;
/*    */   
/*    */   static {
/* 69 */     Constructor<?> tmp_new = null;
/* 70 */     Method tmp_setInputStream = null;
/* 71 */     Method tmp_setStringInterning = null;
/*    */ 
/*    */     
/*    */     try {
/* 75 */       Class<?> clazz = Class.forName("com.sun.xml.fastinfoset.stax.StAXDocumentParser");
/* 76 */       tmp_new = clazz.getConstructor(new Class[0]);
/* 77 */       tmp_setInputStream = clazz.getMethod("setInputStream", new Class[] { InputStream.class });
/*    */       
/* 79 */       tmp_setStringInterning = clazz.getMethod("setStringInterning", new Class[] { boolean.class });
/*    */     
/*    */     }
/* 82 */     catch (Exception e) {}
/*    */ 
/*    */     
/* 85 */     fiStAXDocumentParser_new = tmp_new;
/* 86 */     fiStAXDocumentParser_setInputStream = tmp_setInputStream;
/* 87 */     fiStAXDocumentParser_setStringInterning = tmp_setStringInterning;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\FastInfosetReflection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */