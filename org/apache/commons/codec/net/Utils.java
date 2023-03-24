/*    */ package org.apache.commons.codec.net;
/*    */ 
/*    */ import org.apache.commons.codec.DecoderException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Utils
/*    */ {
/*    */   static int digit16(byte b) throws DecoderException {
/* 43 */     int i = Character.digit((char)b, 16);
/* 44 */     if (i == -1) {
/* 45 */       throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + b);
/*    */     }
/* 47 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\apache\commons\codec\net\Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */