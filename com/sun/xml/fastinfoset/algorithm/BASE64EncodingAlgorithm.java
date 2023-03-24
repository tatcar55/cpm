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
/*     */ 
/*     */ public class BASE64EncodingAlgorithm
/*     */   extends BuiltInEncodingAlgorithm
/*     */ {
/*  28 */   static final char[] encodeBase64 = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   static final int[] decodeBase64 = new int[] { 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
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
/* 107 */     byte[] data = new byte[length];
/* 108 */     System.arraycopy(b, start, data, 0, length);
/* 109 */     return data;
/*     */   }
/*     */   
/*     */   public final Object decodeFromInputStream(InputStream s) throws IOException {
/* 113 */     throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.notImplemented"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeToOutputStream(Object data, OutputStream s) throws IOException {
/* 118 */     if (!(data instanceof byte[])) {
/* 119 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotByteArray"));
/*     */     }
/*     */     
/* 122 */     s.write((byte[])data);
/*     */   }
/*     */   
/*     */   public final Object convertFromCharacters(char[] ch, int start, int length) {
/* 126 */     if (length == 0) {
/* 127 */       return new byte[0];
/*     */     }
/*     */     
/* 130 */     StringBuffer encodedValue = removeWhitespace(ch, start, length);
/* 131 */     int encodedLength = encodedValue.length();
/* 132 */     if (encodedLength == 0) {
/* 133 */       return new byte[0];
/*     */     }
/*     */     
/* 136 */     int blockCount = encodedLength / 4;
/* 137 */     int partialBlockLength = 3;
/*     */     
/* 139 */     if (encodedValue.charAt(encodedLength - 1) == '=') {
/* 140 */       partialBlockLength--;
/* 141 */       if (encodedValue.charAt(encodedLength - 2) == '=') {
/* 142 */         partialBlockLength--;
/*     */       }
/*     */     } 
/*     */     
/* 146 */     int valueLength = (blockCount - 1) * 3 + partialBlockLength;
/* 147 */     byte[] value = new byte[valueLength];
/*     */     
/* 149 */     int idx = 0;
/* 150 */     int encodedIdx = 0;
/* 151 */     for (int i = 0; i < blockCount; i++) {
/* 152 */       int x1 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/* 153 */       int x2 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/* 154 */       int x3 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/* 155 */       int x4 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/*     */       
/* 157 */       value[idx++] = (byte)(x1 << 2 | x2 >> 4);
/* 158 */       if (idx < valueLength) {
/* 159 */         value[idx++] = (byte)((x2 & 0xF) << 4 | x3 >> 2);
/*     */       }
/* 161 */       if (idx < valueLength) {
/* 162 */         value[idx++] = (byte)((x3 & 0x3) << 6 | x4);
/*     */       }
/*     */     } 
/*     */     
/* 166 */     return value;
/*     */   }
/*     */   
/*     */   public final void convertToCharacters(Object data, StringBuffer s) {
/* 170 */     if (data == null) {
/*     */       return;
/*     */     }
/* 173 */     byte[] value = (byte[])data;
/*     */     
/* 175 */     convertToCharacters(value, 0, value.length, s);
/*     */   }
/*     */   
/*     */   public final int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
/* 179 */     return octetLength;
/*     */   }
/*     */   
/*     */   public int getOctetLengthFromPrimitiveLength(int primitiveLength) {
/* 183 */     return primitiveLength;
/*     */   }
/*     */   
/*     */   public final void encodeToBytes(Object array, int astart, int alength, byte[] b, int start) {
/* 187 */     System.arraycopy(array, astart, b, start, alength);
/*     */   }
/*     */   
/*     */   public final void convertToCharacters(byte[] data, int offset, int length, StringBuffer s) {
/* 191 */     if (data == null) {
/*     */       return;
/*     */     }
/* 194 */     byte[] value = data;
/* 195 */     if (length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 199 */     int partialBlockLength = length % 3;
/* 200 */     int blockCount = (partialBlockLength != 0) ? (length / 3 + 1) : (length / 3);
/*     */ 
/*     */ 
/*     */     
/* 204 */     int encodedLength = blockCount * 4;
/* 205 */     int originalBufferSize = s.length();
/* 206 */     s.ensureCapacity(encodedLength + originalBufferSize);
/*     */     
/* 208 */     int idx = offset;
/* 209 */     int lastIdx = offset + length;
/* 210 */     for (int i = 0; i < blockCount; i++) {
/* 211 */       int b1 = value[idx++] & 0xFF;
/* 212 */       int b2 = (idx < lastIdx) ? (value[idx++] & 0xFF) : 0;
/* 213 */       int b3 = (idx < lastIdx) ? (value[idx++] & 0xFF) : 0;
/*     */       
/* 215 */       s.append(encodeBase64[b1 >> 2]);
/*     */       
/* 217 */       s.append(encodeBase64[(b1 & 0x3) << 4 | b2 >> 4]);
/*     */       
/* 219 */       s.append(encodeBase64[(b2 & 0xF) << 2 | b3 >> 6]);
/*     */       
/* 221 */       s.append(encodeBase64[b3 & 0x3F]);
/*     */     } 
/*     */     
/* 224 */     switch (partialBlockLength) {
/*     */       case 1:
/* 226 */         s.setCharAt(originalBufferSize + encodedLength - 1, '=');
/* 227 */         s.setCharAt(originalBufferSize + encodedLength - 2, '=');
/*     */         break;
/*     */       case 2:
/* 230 */         s.setCharAt(originalBufferSize + encodedLength - 1, '=');
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\BASE64EncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */