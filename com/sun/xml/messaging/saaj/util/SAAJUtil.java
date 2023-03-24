/*    */ package com.sun.xml.messaging.saaj.util;
/*    */ 
/*    */ import java.security.AccessControlException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SAAJUtil
/*    */ {
/*    */   public static boolean getSystemBoolean(String arg) {
/*    */     try {
/* 53 */       return Boolean.getBoolean(arg);
/* 54 */     } catch (AccessControlException ex) {
/* 55 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String getSystemProperty(String arg) {
/*    */     try {
/* 61 */       return System.getProperty(arg);
/* 62 */     } catch (SecurityException ex) {
/* 63 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\SAAJUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */