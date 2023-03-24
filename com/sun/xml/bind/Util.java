/*    */ package com.sun.xml.bind;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Util
/*    */ {
/*    */   public static Logger getClassLogger() {
/*    */     try {
/* 58 */       StackTraceElement[] trace = (new Exception()).getStackTrace();
/* 59 */       return Logger.getLogger(trace[1].getClassName());
/* 60 */     } catch (SecurityException e) {
/* 61 */       return Logger.getLogger("com.sun.xml.bind");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getSystemProperty(String name) {
/*    */     try {
/* 70 */       return System.getProperty(name);
/* 71 */     } catch (SecurityException e) {
/* 72 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */