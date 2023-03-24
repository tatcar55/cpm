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
/*     */ public class DoubleEncodingAlgorithm
/*     */   extends IEEE754FloatingPointEncodingAlgorithm
/*     */ {
/*     */   public final int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
/*  35 */     if (octetLength % 8 != 0) {
/*  36 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.lengthIsNotMultipleOfDouble", new Object[] { Integer.valueOf(8) }));
/*     */     }
/*     */ 
/*     */     
/*  40 */     return octetLength / 8;
/*     */   }
/*     */   
/*     */   public int getOctetLengthFromPrimitiveLength(int primitiveLength) {
/*  44 */     return primitiveLength * 8;
/*     */   }
/*     */   
/*     */   public final Object decodeFromBytes(byte[] b, int start, int length) throws EncodingAlgorithmException {
/*  48 */     double[] data = new double[getPrimtiveLengthFromOctetLength(length)];
/*  49 */     decodeFromBytesToDoubleArray(data, 0, b, start, length);
/*     */     
/*  51 */     return data;
/*     */   }
/*     */   
/*     */   public final Object decodeFromInputStream(InputStream s) throws IOException {
/*  55 */     return decodeFromInputStreamToDoubleArray(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeToOutputStream(Object data, OutputStream s) throws IOException {
/*  60 */     if (!(data instanceof double[])) {
/*  61 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotDouble"));
/*     */     }
/*     */     
/*  64 */     double[] fdata = (double[])data;
/*     */     
/*  66 */     encodeToOutputStreamFromDoubleArray(fdata, s);
/*     */   }
/*     */   
/*     */   public final Object convertFromCharacters(char[] ch, int start, int length) {
/*  70 */     final CharBuffer cb = CharBuffer.wrap(ch, start, length);
/*  71 */     final List doubleList = new ArrayList();
/*     */     
/*  73 */     matchWhiteSpaceDelimnatedWords(cb, new BuiltInEncodingAlgorithm.WordListener()
/*     */         {
/*     */           public void word(int start, int end) {
/*  76 */             String fStringValue = cb.subSequence(start, end).toString();
/*  77 */             doubleList.add(Double.valueOf(fStringValue));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  82 */     return generateArrayFromList(doubleList);
/*     */   }
/*     */   
/*     */   public final void convertToCharacters(Object data, StringBuffer s) {
/*  86 */     if (!(data instanceof double[])) {
/*  87 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotDouble"));
/*     */     }
/*     */     
/*  90 */     double[] fdata = (double[])data;
/*     */     
/*  92 */     convertToCharactersFromDoubleArray(fdata, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void decodeFromBytesToDoubleArray(double[] data, int fstart, byte[] b, int start, int length) {
/*  97 */     int size = length / 8;
/*  98 */     for (int i = 0; i < size; i++) {
/*  99 */       long bits = (b[start++] & 0xFF) << 56L | (b[start++] & 0xFF) << 48L | (b[start++] & 0xFF) << 40L | (b[start++] & 0xFF) << 32L | (b[start++] & 0xFF) << 24L | (b[start++] & 0xFF) << 16L | (b[start++] & 0xFF) << 8L | (b[start++] & 0xFF);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 108 */       data[fstart++] = Double.longBitsToDouble(bits);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final double[] decodeFromInputStreamToDoubleArray(InputStream s) throws IOException {
/* 113 */     List<Double> doubleList = new ArrayList();
/* 114 */     byte[] b = new byte[8];
/*     */     
/*     */     while (true) {
/* 117 */       int n = s.read(b);
/* 118 */       if (n != 8) {
/* 119 */         if (n == -1) {
/*     */           break;
/*     */         }
/*     */         
/* 123 */         while (n != 8) {
/* 124 */           int m = s.read(b, n, 8 - n);
/* 125 */           if (m == -1) {
/* 126 */             throw new EOFException();
/*     */           }
/* 128 */           n += m;
/*     */         } 
/*     */       } 
/*     */       
/* 132 */       long bits = (b[0] & 0xFF) << 56L | (b[1] & 0xFF) << 48L | (b[2] & 0xFF) << 40L | (b[3] & 0xFF) << 32L | ((b[4] & 0xFF) << 24) | ((b[5] & 0xFF) << 16) | ((b[6] & 0xFF) << 8) | (b[7] & 0xFF);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       doubleList.add(Double.valueOf(Double.longBitsToDouble(bits)));
/*     */     } 
/*     */     
/* 145 */     return generateArrayFromList(doubleList);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void encodeToOutputStreamFromDoubleArray(double[] fdata, OutputStream s) throws IOException {
/* 150 */     for (int i = 0; i < fdata.length; i++) {
/* 151 */       long bits = Double.doubleToLongBits(fdata[i]);
/* 152 */       s.write((int)(bits >>> 56L & 0xFFL));
/* 153 */       s.write((int)(bits >>> 48L & 0xFFL));
/* 154 */       s.write((int)(bits >>> 40L & 0xFFL));
/* 155 */       s.write((int)(bits >>> 32L & 0xFFL));
/* 156 */       s.write((int)(bits >>> 24L & 0xFFL));
/* 157 */       s.write((int)(bits >>> 16L & 0xFFL));
/* 158 */       s.write((int)(bits >>> 8L & 0xFFL));
/* 159 */       s.write((int)(bits & 0xFFL));
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void encodeToBytes(Object array, int astart, int alength, byte[] b, int start) {
/* 164 */     encodeToBytesFromDoubleArray((double[])array, astart, alength, b, start);
/*     */   }
/*     */   
/*     */   public final void encodeToBytesFromDoubleArray(double[] fdata, int fstart, int flength, byte[] b, int start) {
/* 168 */     int fend = fstart + flength;
/* 169 */     for (int i = fstart; i < fend; i++) {
/* 170 */       long bits = Double.doubleToLongBits(fdata[i]);
/* 171 */       b[start++] = (byte)(int)(bits >>> 56L & 0xFFL);
/* 172 */       b[start++] = (byte)(int)(bits >>> 48L & 0xFFL);
/* 173 */       b[start++] = (byte)(int)(bits >>> 40L & 0xFFL);
/* 174 */       b[start++] = (byte)(int)(bits >>> 32L & 0xFFL);
/* 175 */       b[start++] = (byte)(int)(bits >>> 24L & 0xFFL);
/* 176 */       b[start++] = (byte)(int)(bits >>> 16L & 0xFFL);
/* 177 */       b[start++] = (byte)(int)(bits >>> 8L & 0xFFL);
/* 178 */       b[start++] = (byte)(int)(bits & 0xFFL);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void convertToCharactersFromDoubleArray(double[] fdata, StringBuffer s) {
/* 184 */     int end = fdata.length - 1;
/* 185 */     for (int i = 0; i <= end; i++) {
/* 186 */       s.append(Double.toString(fdata[i]));
/* 187 */       if (i != end) {
/* 188 */         s.append(' ');
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final double[] generateArrayFromList(List array) {
/* 195 */     double[] fdata = new double[array.size()];
/* 196 */     for (int i = 0; i < fdata.length; i++) {
/* 197 */       fdata[i] = ((Double)array.get(i)).doubleValue();
/*     */     }
/*     */     
/* 200 */     return fdata;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\DoubleEncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */