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
/*     */ public class LongEncodingAlgorithm
/*     */   extends IntegerEncodingAlgorithm
/*     */ {
/*     */   public int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
/*  34 */     if (octetLength % 8 != 0) {
/*  35 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.lengthNotMultipleOfLong", new Object[] { Integer.valueOf(8) }));
/*     */     }
/*     */ 
/*     */     
/*  39 */     return octetLength / 8;
/*     */   }
/*     */   
/*     */   public int getOctetLengthFromPrimitiveLength(int primitiveLength) {
/*  43 */     return primitiveLength * 8;
/*     */   }
/*     */   
/*     */   public final Object decodeFromBytes(byte[] b, int start, int length) throws EncodingAlgorithmException {
/*  47 */     long[] data = new long[getPrimtiveLengthFromOctetLength(length)];
/*  48 */     decodeFromBytesToLongArray(data, 0, b, start, length);
/*     */     
/*  50 */     return data;
/*     */   }
/*     */   
/*     */   public final Object decodeFromInputStream(InputStream s) throws IOException {
/*  54 */     return decodeFromInputStreamToIntArray(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeToOutputStream(Object data, OutputStream s) throws IOException {
/*  59 */     if (!(data instanceof long[])) {
/*  60 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotLongArray"));
/*     */     }
/*     */     
/*  63 */     long[] ldata = (long[])data;
/*     */     
/*  65 */     encodeToOutputStreamFromLongArray(ldata, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object convertFromCharacters(char[] ch, int start, int length) {
/*  70 */     final CharBuffer cb = CharBuffer.wrap(ch, start, length);
/*  71 */     final List longList = new ArrayList();
/*     */     
/*  73 */     matchWhiteSpaceDelimnatedWords(cb, new BuiltInEncodingAlgorithm.WordListener()
/*     */         {
/*     */           public void word(int start, int end) {
/*  76 */             String lStringValue = cb.subSequence(start, end).toString();
/*  77 */             longList.add(Long.valueOf(lStringValue));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  82 */     return generateArrayFromList(longList);
/*     */   }
/*     */   
/*     */   public void convertToCharacters(Object data, StringBuffer s) {
/*  86 */     if (!(data instanceof long[])) {
/*  87 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotLongArray"));
/*     */     }
/*     */     
/*  90 */     long[] ldata = (long[])data;
/*     */     
/*  92 */     convertToCharactersFromLongArray(ldata, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void decodeFromBytesToLongArray(long[] ldata, int istart, byte[] b, int start, int length) {
/*  97 */     int size = length / 8;
/*  98 */     for (int i = 0; i < size; i++) {
/*  99 */       ldata[istart++] = (b[start++] & 0xFF) << 56L | (b[start++] & 0xFF) << 48L | (b[start++] & 0xFF) << 40L | (b[start++] & 0xFF) << 32L | (b[start++] & 0xFF) << 24L | (b[start++] & 0xFF) << 16L | (b[start++] & 0xFF) << 8L | (b[start++] & 0xFF);
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
/*     */   public final long[] decodeFromInputStreamToIntArray(InputStream s) throws IOException {
/* 112 */     List<Long> longList = new ArrayList();
/* 113 */     byte[] b = new byte[8];
/*     */     
/*     */     while (true) {
/* 116 */       int n = s.read(b);
/* 117 */       if (n != 8) {
/* 118 */         if (n == -1) {
/*     */           break;
/*     */         }
/*     */         
/* 122 */         while (n != 8) {
/* 123 */           int m = s.read(b, n, 8 - n);
/* 124 */           if (m == -1) {
/* 125 */             throw new EOFException();
/*     */           }
/* 127 */           n += m;
/*     */         } 
/*     */       } 
/*     */       
/* 131 */       long l = (b[0] << 56L) + ((b[1] & 0xFF) << 48L) + ((b[2] & 0xFF) << 40L) + ((b[3] & 0xFF) << 32L) + ((b[4] & 0xFF) << 24L) + ((b[5] & 0xFF) << 16) + ((b[6] & 0xFF) << 8) + ((b[7] & 0xFF) << 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 141 */       longList.add(Long.valueOf(l));
/*     */     } 
/*     */     
/* 144 */     return generateArrayFromList(longList);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void encodeToOutputStreamFromLongArray(long[] ldata, OutputStream s) throws IOException {
/* 149 */     for (int i = 0; i < ldata.length; i++) {
/* 150 */       long bits = ldata[i];
/* 151 */       s.write((int)(bits >>> 56L & 0xFFL));
/* 152 */       s.write((int)(bits >>> 48L & 0xFFL));
/* 153 */       s.write((int)(bits >>> 40L & 0xFFL));
/* 154 */       s.write((int)(bits >>> 32L & 0xFFL));
/* 155 */       s.write((int)(bits >>> 24L & 0xFFL));
/* 156 */       s.write((int)(bits >>> 16L & 0xFFL));
/* 157 */       s.write((int)(bits >>> 8L & 0xFFL));
/* 158 */       s.write((int)(bits & 0xFFL));
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void encodeToBytes(Object array, int astart, int alength, byte[] b, int start) {
/* 163 */     encodeToBytesFromLongArray((long[])array, astart, alength, b, start);
/*     */   }
/*     */   
/*     */   public final void encodeToBytesFromLongArray(long[] ldata, int lstart, int llength, byte[] b, int start) {
/* 167 */     int lend = lstart + llength;
/* 168 */     for (int i = lstart; i < lend; i++) {
/* 169 */       long bits = ldata[i];
/* 170 */       b[start++] = (byte)(int)(bits >>> 56L & 0xFFL);
/* 171 */       b[start++] = (byte)(int)(bits >>> 48L & 0xFFL);
/* 172 */       b[start++] = (byte)(int)(bits >>> 40L & 0xFFL);
/* 173 */       b[start++] = (byte)(int)(bits >>> 32L & 0xFFL);
/* 174 */       b[start++] = (byte)(int)(bits >>> 24L & 0xFFL);
/* 175 */       b[start++] = (byte)(int)(bits >>> 16L & 0xFFL);
/* 176 */       b[start++] = (byte)(int)(bits >>> 8L & 0xFFL);
/* 177 */       b[start++] = (byte)(int)(bits & 0xFFL);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void convertToCharactersFromLongArray(long[] ldata, StringBuffer s) {
/* 183 */     int end = ldata.length - 1;
/* 184 */     for (int i = 0; i <= end; i++) {
/* 185 */       s.append(Long.toString(ldata[i]));
/* 186 */       if (i != end) {
/* 187 */         s.append(' ');
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final long[] generateArrayFromList(List array) {
/* 194 */     long[] ldata = new long[array.size()];
/* 195 */     for (int i = 0; i < ldata.length; i++) {
/* 196 */       ldata[i] = ((Long)array.get(i)).longValue();
/*     */     }
/*     */     
/* 199 */     return ldata;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\LongEncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */