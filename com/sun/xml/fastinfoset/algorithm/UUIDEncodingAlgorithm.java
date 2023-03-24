/*     */ package com.sun.xml.fastinfoset.algorithm;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import java.nio.CharBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jvnet.fastinfoset.EncodingAlgorithmException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UUIDEncodingAlgorithm
/*     */   extends LongEncodingAlgorithm
/*     */ {
/*     */   private long _msb;
/*     */   private long _lsb;
/*     */   
/*     */   public final int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
/*  29 */     if (octetLength % 16 != 0) {
/*  30 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.lengthNotMultipleOfUUID", new Object[] { Integer.valueOf(16) }));
/*     */     }
/*     */ 
/*     */     
/*  34 */     return octetLength / 8;
/*     */   }
/*     */   
/*     */   public final Object convertFromCharacters(char[] ch, int start, int length) {
/*  38 */     final CharBuffer cb = CharBuffer.wrap(ch, start, length);
/*  39 */     final List longList = new ArrayList();
/*     */     
/*  41 */     matchWhiteSpaceDelimnatedWords(cb, new BuiltInEncodingAlgorithm.WordListener()
/*     */         {
/*     */           public void word(int start, int end) {
/*  44 */             String uuidValue = cb.subSequence(start, end).toString();
/*  45 */             UUIDEncodingAlgorithm.this.fromUUIDString(uuidValue);
/*  46 */             longList.add(Long.valueOf(UUIDEncodingAlgorithm.this._msb));
/*  47 */             longList.add(Long.valueOf(UUIDEncodingAlgorithm.this._lsb));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  52 */     return generateArrayFromList(longList);
/*     */   }
/*     */   
/*     */   public final void convertToCharacters(Object data, StringBuffer s) {
/*  56 */     if (!(data instanceof long[])) {
/*  57 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotLongArray"));
/*     */     }
/*     */     
/*  60 */     long[] ldata = (long[])data;
/*     */     
/*  62 */     int end = ldata.length - 2;
/*  63 */     for (int i = 0; i <= end; i += 2) {
/*  64 */       s.append(toUUIDString(ldata[i], ldata[i + 1]));
/*  65 */       if (i != end) {
/*  66 */         s.append(' ');
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void fromUUIDString(String name) {
/*  76 */     String[] components = name.split("-");
/*  77 */     if (components.length != 5) {
/*  78 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.invalidUUID", new Object[] { name }));
/*     */     }
/*     */     
/*  81 */     for (int i = 0; i < 5; i++) {
/*  82 */       components[i] = "0x" + components[i];
/*     */     }
/*  84 */     this._msb = Long.parseLong(components[0], 16);
/*  85 */     this._msb <<= 16L;
/*  86 */     this._msb |= Long.parseLong(components[1], 16);
/*  87 */     this._msb <<= 16L;
/*  88 */     this._msb |= Long.parseLong(components[2], 16);
/*     */     
/*  90 */     this._lsb = Long.parseLong(components[3], 16);
/*  91 */     this._lsb <<= 48L;
/*  92 */     this._lsb |= Long.parseLong(components[4], 16);
/*     */   }
/*     */   
/*     */   final String toUUIDString(long msb, long lsb) {
/*  96 */     return digits(msb >> 32L, 8) + "-" + digits(msb >> 16L, 4) + "-" + digits(msb, 4) + "-" + digits(lsb >> 48L, 4) + "-" + digits(lsb, 12);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String digits(long val, int digits) {
/* 104 */     long hi = 1L << digits * 4;
/* 105 */     return Long.toHexString(hi | val & hi - 1L).substring(1);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\UUIDEncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */