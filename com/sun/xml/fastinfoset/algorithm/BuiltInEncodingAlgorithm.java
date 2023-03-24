/*    */ package com.sun.xml.fastinfoset.algorithm;
/*    */ 
/*    */ import java.nio.CharBuffer;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.jvnet.fastinfoset.EncodingAlgorithm;
/*    */ import org.jvnet.fastinfoset.EncodingAlgorithmException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuiltInEncodingAlgorithm
/*    */   implements EncodingAlgorithm
/*    */ {
/* 27 */   protected static final Pattern SPACE_PATTERN = Pattern.compile("\\s");
/*    */ 
/*    */   
/*    */   public abstract int getPrimtiveLengthFromOctetLength(int paramInt) throws EncodingAlgorithmException;
/*    */ 
/*    */   
/*    */   public abstract int getOctetLengthFromPrimitiveLength(int paramInt);
/*    */ 
/*    */   
/*    */   public abstract void encodeToBytes(Object paramObject, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3);
/*    */ 
/*    */   
/*    */   public void matchWhiteSpaceDelimnatedWords(CharBuffer cb, WordListener wl) {
/* 40 */     Matcher m = SPACE_PATTERN.matcher(cb);
/* 41 */     int i = 0;
/* 42 */     int s = 0;
/* 43 */     while (m.find()) {
/* 44 */       s = m.start();
/* 45 */       if (s != i) {
/* 46 */         wl.word(i, s);
/*    */       }
/* 48 */       i = m.end();
/*    */     } 
/* 50 */     if (i != cb.length())
/* 51 */       wl.word(i, cb.length()); 
/*    */   }
/*    */   
/*    */   public StringBuffer removeWhitespace(char[] ch, int start, int length) {
/* 55 */     StringBuffer buf = new StringBuffer();
/* 56 */     int firstNonWS = 0;
/* 57 */     int idx = 0;
/* 58 */     for (; idx < length; idx++) {
/* 59 */       if (Character.isWhitespace(ch[idx + start])) {
/* 60 */         if (firstNonWS < idx) {
/* 61 */           buf.append(ch, firstNonWS + start, idx - firstNonWS);
/*    */         }
/* 63 */         firstNonWS = idx + 1;
/*    */       } 
/*    */     } 
/* 66 */     if (firstNonWS < idx) {
/* 67 */       buf.append(ch, firstNonWS + start, idx - firstNonWS);
/*    */     }
/* 69 */     return buf;
/*    */   }
/*    */   
/*    */   public static interface WordListener {
/*    */     void word(int param1Int1, int param1Int2);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\BuiltInEncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */