/*    */ package com.sun.xml.ws.util;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StreamUtils
/*    */ {
/*    */   public static InputStream hasSomeData(InputStream in) {
/* 59 */     if (in != null) {
/*    */       try {
/* 61 */         if (in.available() < 1) {
/* 62 */           if (!in.markSupported()) {
/* 63 */             in = new BufferedInputStream(in);
/*    */           }
/* 65 */           in.mark(1);
/* 66 */           if (in.read() != -1) {
/* 67 */             in.reset();
/*    */           } else {
/* 69 */             in = null;
/*    */           } 
/*    */         } 
/* 72 */       } catch (IOException ioe) {
/* 73 */         in = null;
/*    */       } 
/*    */     }
/* 76 */     return in;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\StreamUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */