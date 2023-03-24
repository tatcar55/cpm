/*    */ package com.sun.xml.messaging.saaj.packaging.mime.util;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QDecoderStream
/*    */   extends QPDecoderStream
/*    */ {
/*    */   public QDecoderStream(InputStream in) {
/* 66 */     super(in);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 82 */     int c = this.in.read();
/*    */     
/* 84 */     if (c == 95)
/* 85 */       return 32; 
/* 86 */     if (c == 61) {
/*    */       
/* 88 */       this.ba[0] = (byte)this.in.read();
/* 89 */       this.ba[1] = (byte)this.in.read();
/*    */       
/*    */       try {
/* 92 */         return ASCIIUtility.parseInt(this.ba, 0, 2, 16);
/* 93 */       } catch (NumberFormatException nex) {
/* 94 */         throw new IOException("Error in QP stream " + nex.getMessage());
/*    */       } 
/*    */     } 
/* 97 */     return c;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\QDecoderStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */