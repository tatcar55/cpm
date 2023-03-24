/*     */ package com.sun.xml.fastinfoset.algorithm;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
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
/*     */ public class BooleanEncodingAlgorithm
/*     */   extends BuiltInEncodingAlgorithm
/*     */ {
/*  44 */   private static final int[] BIT_TABLE = new int[] { 128, 64, 32, 16, 8, 4, 2, 1 };
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
/*     */   public int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
/*  56 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getOctetLengthFromPrimitiveLength(int primitiveLength) {
/*  60 */     if (primitiveLength < 5) {
/*  61 */       return 1;
/*     */     }
/*  63 */     int div = primitiveLength / 8;
/*  64 */     return (div == 0) ? 2 : (1 + div);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object decodeFromBytes(byte[] b, int start, int length) throws EncodingAlgorithmException {
/*  69 */     int blength = getPrimtiveLengthFromOctetLength(length, b[start]);
/*  70 */     boolean[] data = new boolean[blength];
/*     */     
/*  72 */     decodeFromBytesToBooleanArray(data, 0, blength, b, start, length);
/*  73 */     return data;
/*     */   }
/*     */   
/*     */   public final Object decodeFromInputStream(InputStream s) throws IOException {
/*  77 */     List<Boolean> booleanList = new ArrayList();
/*     */     
/*  79 */     int value = s.read();
/*  80 */     if (value == -1) {
/*  81 */       throw new EOFException();
/*     */     }
/*  83 */     int unusedBits = value >> 4 & 0xFF;
/*     */     
/*  85 */     int bitPosition = 4;
/*  86 */     int bitPositionEnd = 8;
/*  87 */     int valueNext = 0;
/*     */     do {
/*  89 */       valueNext = s.read();
/*  90 */       if (valueNext == -1) {
/*  91 */         bitPositionEnd -= unusedBits;
/*     */       }
/*     */       
/*  94 */       while (bitPosition < bitPositionEnd) {
/*  95 */         booleanList.add(Boolean.valueOf(((value & BIT_TABLE[bitPosition++]) > 0)));
/*     */       }
/*     */ 
/*     */       
/*  99 */       value = valueNext;
/* 100 */     } while (value != -1);
/*     */     
/* 102 */     return generateArrayFromList(booleanList);
/*     */   }
/*     */   
/*     */   public void encodeToOutputStream(Object data, OutputStream s) throws IOException {
/* 106 */     if (!(data instanceof boolean[])) {
/* 107 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotBoolean"));
/*     */     }
/*     */     
/* 110 */     boolean[] array = (boolean[])data;
/* 111 */     int alength = array.length;
/*     */     
/* 113 */     int mod = (alength + 4) % 8;
/* 114 */     int unusedBits = (mod == 0) ? 0 : (8 - mod);
/*     */     
/* 116 */     int bitPosition = 4;
/* 117 */     int value = unusedBits << 4;
/* 118 */     int astart = 0;
/* 119 */     while (astart < alength) {
/* 120 */       if (array[astart++]) {
/* 121 */         value |= BIT_TABLE[bitPosition];
/*     */       }
/*     */       
/* 124 */       if (++bitPosition == 8) {
/* 125 */         s.write(value);
/* 126 */         bitPosition = value = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     if (bitPosition != 8) {
/* 131 */       s.write(value);
/*     */     }
/*     */   }
/*     */   
/*     */   public final Object convertFromCharacters(char[] ch, int start, int length) {
/* 136 */     if (length == 0) {
/* 137 */       return new boolean[0];
/*     */     }
/*     */     
/* 140 */     final CharBuffer cb = CharBuffer.wrap(ch, start, length);
/* 141 */     final List booleanList = new ArrayList();
/*     */     
/* 143 */     matchWhiteSpaceDelimnatedWords(cb, new BuiltInEncodingAlgorithm.WordListener()
/*     */         {
/*     */           public void word(int start, int end) {
/* 146 */             if (cb.charAt(start) == 't') {
/* 147 */               booleanList.add(Boolean.TRUE);
/*     */             } else {
/* 149 */               booleanList.add(Boolean.FALSE);
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 155 */     return generateArrayFromList(booleanList);
/*     */   }
/*     */   
/*     */   public final void convertToCharacters(Object data, StringBuffer s) {
/* 159 */     if (data == null) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     boolean[] value = (boolean[])data;
/* 164 */     if (value.length == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 169 */     s.ensureCapacity(value.length * 5);
/*     */     
/* 171 */     int end = value.length - 1;
/* 172 */     for (int i = 0; i <= end; i++) {
/* 173 */       if (value[i]) {
/* 174 */         s.append("true");
/*     */       } else {
/* 176 */         s.append("false");
/*     */       } 
/* 178 */       if (i != end) {
/* 179 */         s.append(' ');
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getPrimtiveLengthFromOctetLength(int octetLength, int firstOctet) throws EncodingAlgorithmException {
/* 185 */     int unusedBits = firstOctet >> 4 & 0xFF;
/* 186 */     if (octetLength == 1) {
/* 187 */       if (unusedBits > 3) {
/* 188 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.unusedBits4"));
/*     */       }
/* 190 */       return 4 - unusedBits;
/*     */     } 
/* 192 */     if (unusedBits > 7) {
/* 193 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.unusedBits8"));
/*     */     }
/* 195 */     return octetLength * 8 - 4 - unusedBits;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void decodeFromBytesToBooleanArray(boolean[] bdata, int bstart, int blength, byte[] b, int start, int length) {
/* 200 */     int value = b[start++] & 0xFF;
/* 201 */     int bitPosition = 4;
/* 202 */     int bend = bstart + blength;
/* 203 */     while (bstart < bend) {
/* 204 */       if (bitPosition == 8) {
/* 205 */         value = b[start++] & 0xFF;
/* 206 */         bitPosition = 0;
/*     */       } 
/*     */       
/* 209 */       bdata[bstart++] = ((value & BIT_TABLE[bitPosition++]) > 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void encodeToBytes(Object array, int astart, int alength, byte[] b, int start) {
/* 214 */     if (!(array instanceof boolean[])) {
/* 215 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotBoolean"));
/*     */     }
/*     */     
/* 218 */     encodeToBytesFromBooleanArray((boolean[])array, astart, alength, b, start);
/*     */   }
/*     */   
/*     */   public void encodeToBytesFromBooleanArray(boolean[] array, int astart, int alength, byte[] b, int start) {
/* 222 */     int mod = (alength + 4) % 8;
/* 223 */     int unusedBits = (mod == 0) ? 0 : (8 - mod);
/*     */     
/* 225 */     int bitPosition = 4;
/* 226 */     int value = unusedBits << 4;
/* 227 */     int aend = astart + alength;
/* 228 */     while (astart < aend) {
/* 229 */       if (array[astart++]) {
/* 230 */         value |= BIT_TABLE[bitPosition];
/*     */       }
/*     */       
/* 233 */       if (++bitPosition == 8) {
/* 234 */         b[start++] = (byte)value;
/* 235 */         bitPosition = value = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 239 */     if (bitPosition > 0) {
/* 240 */       b[start] = (byte)value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean[] generateArrayFromList(List array) {
/* 253 */     boolean[] bdata = new boolean[array.size()];
/* 254 */     for (int i = 0; i < bdata.length; i++) {
/* 255 */       bdata[i] = ((Boolean)array.get(i)).booleanValue();
/*     */     }
/*     */     
/* 258 */     return bdata;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\BooleanEncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */