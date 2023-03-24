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
/*     */ public class FloatEncodingAlgorithm
/*     */   extends IEEE754FloatingPointEncodingAlgorithm
/*     */ {
/*     */   public final int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
/*  35 */     if (octetLength % 4 != 0) {
/*  36 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.lengthNotMultipleOfFloat", new Object[] { Integer.valueOf(4) }));
/*     */     }
/*     */ 
/*     */     
/*  40 */     return octetLength / 4;
/*     */   }
/*     */   
/*     */   public int getOctetLengthFromPrimitiveLength(int primitiveLength) {
/*  44 */     return primitiveLength * 4;
/*     */   }
/*     */   
/*     */   public final Object decodeFromBytes(byte[] b, int start, int length) throws EncodingAlgorithmException {
/*  48 */     float[] data = new float[getPrimtiveLengthFromOctetLength(length)];
/*  49 */     decodeFromBytesToFloatArray(data, 0, b, start, length);
/*     */     
/*  51 */     return data;
/*     */   }
/*     */   
/*     */   public final Object decodeFromInputStream(InputStream s) throws IOException {
/*  55 */     return decodeFromInputStreamToFloatArray(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeToOutputStream(Object data, OutputStream s) throws IOException {
/*  60 */     if (!(data instanceof float[])) {
/*  61 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotFloat"));
/*     */     }
/*     */     
/*  64 */     float[] fdata = (float[])data;
/*     */     
/*  66 */     encodeToOutputStreamFromFloatArray(fdata, s);
/*     */   }
/*     */   
/*     */   public final Object convertFromCharacters(char[] ch, int start, int length) {
/*  70 */     final CharBuffer cb = CharBuffer.wrap(ch, start, length);
/*  71 */     final List floatList = new ArrayList();
/*     */     
/*  73 */     matchWhiteSpaceDelimnatedWords(cb, new BuiltInEncodingAlgorithm.WordListener()
/*     */         {
/*     */           public void word(int start, int end) {
/*  76 */             String fStringValue = cb.subSequence(start, end).toString();
/*  77 */             floatList.add(Float.valueOf(fStringValue));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  82 */     return generateArrayFromList(floatList);
/*     */   }
/*     */   
/*     */   public final void convertToCharacters(Object data, StringBuffer s) {
/*  86 */     if (!(data instanceof float[])) {
/*  87 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotFloat"));
/*     */     }
/*     */     
/*  90 */     float[] fdata = (float[])data;
/*     */     
/*  92 */     convertToCharactersFromFloatArray(fdata, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void decodeFromBytesToFloatArray(float[] data, int fstart, byte[] b, int start, int length) {
/*  97 */     int size = length / 4;
/*  98 */     for (int i = 0; i < size; i++) {
/*  99 */       int bits = (b[start++] & 0xFF) << 24 | (b[start++] & 0xFF) << 16 | (b[start++] & 0xFF) << 8 | b[start++] & 0xFF;
/*     */ 
/*     */ 
/*     */       
/* 103 */       data[fstart++] = Float.intBitsToFloat(bits);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final float[] decodeFromInputStreamToFloatArray(InputStream s) throws IOException {
/* 108 */     List<Float> floatList = new ArrayList();
/* 109 */     byte[] b = new byte[4];
/*     */     
/*     */     while (true) {
/* 112 */       int n = s.read(b);
/* 113 */       if (n != 4) {
/* 114 */         if (n == -1) {
/*     */           break;
/*     */         }
/*     */         
/* 118 */         while (n != 4) {
/* 119 */           int m = s.read(b, n, 4 - n);
/* 120 */           if (m == -1) {
/* 121 */             throw new EOFException();
/*     */           }
/* 123 */           n += m;
/*     */         } 
/*     */       } 
/*     */       
/* 127 */       int bits = (b[0] & 0xFF) << 24 | (b[1] & 0xFF) << 16 | (b[2] & 0xFF) << 8 | b[3] & 0xFF;
/*     */ 
/*     */ 
/*     */       
/* 131 */       floatList.add(Float.valueOf(Float.intBitsToFloat(bits)));
/*     */     } 
/*     */     
/* 134 */     return generateArrayFromList(floatList);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void encodeToOutputStreamFromFloatArray(float[] fdata, OutputStream s) throws IOException {
/* 139 */     for (int i = 0; i < fdata.length; i++) {
/* 140 */       int bits = Float.floatToIntBits(fdata[i]);
/* 141 */       s.write(bits >>> 24 & 0xFF);
/* 142 */       s.write(bits >>> 16 & 0xFF);
/* 143 */       s.write(bits >>> 8 & 0xFF);
/* 144 */       s.write(bits & 0xFF);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void encodeToBytes(Object array, int astart, int alength, byte[] b, int start) {
/* 149 */     encodeToBytesFromFloatArray((float[])array, astart, alength, b, start);
/*     */   }
/*     */   
/*     */   public final void encodeToBytesFromFloatArray(float[] fdata, int fstart, int flength, byte[] b, int start) {
/* 153 */     int fend = fstart + flength;
/* 154 */     for (int i = fstart; i < fend; i++) {
/* 155 */       int bits = Float.floatToIntBits(fdata[i]);
/* 156 */       b[start++] = (byte)(bits >>> 24 & 0xFF);
/* 157 */       b[start++] = (byte)(bits >>> 16 & 0xFF);
/* 158 */       b[start++] = (byte)(bits >>> 8 & 0xFF);
/* 159 */       b[start++] = (byte)(bits & 0xFF);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void convertToCharactersFromFloatArray(float[] fdata, StringBuffer s) {
/* 165 */     int end = fdata.length - 1;
/* 166 */     for (int i = 0; i <= end; i++) {
/* 167 */       s.append(Float.toString(fdata[i]));
/* 168 */       if (i != end) {
/* 169 */         s.append(' ');
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final float[] generateArrayFromList(List array) {
/* 176 */     float[] fdata = new float[array.size()];
/* 177 */     for (int i = 0; i < fdata.length; i++) {
/* 178 */       fdata[i] = ((Float)array.get(i)).floatValue();
/*     */     }
/*     */     
/* 181 */     return fdata;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\FloatEncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */