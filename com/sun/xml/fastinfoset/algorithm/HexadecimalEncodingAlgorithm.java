/*     */ package com.sun.xml.fastinfoset.algorithm;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
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
/*     */ 
/*     */ 
/*     */ public class HexadecimalEncodingAlgorithm
/*     */   extends BuiltInEncodingAlgorithm
/*     */ {
/*  27 */   private static final char[] NIBBLE_TO_HEXADECIMAL_TABLE = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final int[] HEXADECIMAL_TO_NIBBLE_TABLE = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15 };
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
/*     */   public final Object decodeFromBytes(byte[] b, int start, int length) throws EncodingAlgorithmException {
/*  58 */     byte[] data = new byte[length];
/*  59 */     System.arraycopy(b, start, data, 0, length);
/*  60 */     return data;
/*     */   }
/*     */   
/*     */   public final Object decodeFromInputStream(InputStream s) throws IOException {
/*  64 */     throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.notImplemented"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeToOutputStream(Object data, OutputStream s) throws IOException {
/*  69 */     if (!(data instanceof byte[])) {
/*  70 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotByteArray"));
/*     */     }
/*     */     
/*  73 */     s.write((byte[])data);
/*     */   }
/*     */   
/*     */   public final Object convertFromCharacters(char[] ch, int start, int length) {
/*  77 */     if (length == 0) {
/*  78 */       return new byte[0];
/*     */     }
/*     */     
/*  81 */     StringBuffer encodedValue = removeWhitespace(ch, start, length);
/*  82 */     int encodedLength = encodedValue.length();
/*  83 */     if (encodedLength == 0) {
/*  84 */       return new byte[0];
/*     */     }
/*     */     
/*  87 */     int valueLength = encodedValue.length() / 2;
/*  88 */     byte[] value = new byte[valueLength];
/*     */     
/*  90 */     int encodedIdx = 0;
/*  91 */     for (int i = 0; i < valueLength; i++) {
/*  92 */       int nibble1 = HEXADECIMAL_TO_NIBBLE_TABLE[encodedValue.charAt(encodedIdx++) - 48];
/*  93 */       int nibble2 = HEXADECIMAL_TO_NIBBLE_TABLE[encodedValue.charAt(encodedIdx++) - 48];
/*  94 */       value[i] = (byte)(nibble1 << 4 | nibble2);
/*     */     } 
/*     */     
/*  97 */     return value;
/*     */   }
/*     */   
/*     */   public final void convertToCharacters(Object data, StringBuffer s) {
/* 101 */     if (data == null) {
/*     */       return;
/*     */     }
/* 104 */     byte[] value = (byte[])data;
/* 105 */     if (value.length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     s.ensureCapacity(value.length * 2);
/* 110 */     for (int i = 0; i < value.length; i++) {
/* 111 */       s.append(NIBBLE_TO_HEXADECIMAL_TABLE[value[i] >>> 4 & 0xF]);
/* 112 */       s.append(NIBBLE_TO_HEXADECIMAL_TABLE[value[i] & 0xF]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
/* 119 */     return octetLength * 2;
/*     */   }
/*     */   
/*     */   public int getOctetLengthFromPrimitiveLength(int primitiveLength) {
/* 123 */     return primitiveLength / 2;
/*     */   }
/*     */   
/*     */   public final void encodeToBytes(Object array, int astart, int alength, byte[] b, int start) {
/* 127 */     System.arraycopy(array, astart, b, start, alength);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\HexadecimalEncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */