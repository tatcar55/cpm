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
/*     */ public class IntEncodingAlgorithm
/*     */   extends IntegerEncodingAlgorithm
/*     */ {
/*     */   public final int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
/*  35 */     if (octetLength % 4 != 0) {
/*  36 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.lengthNotMultipleOfInt", new Object[] { Integer.valueOf(4) }));
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
/*  48 */     int[] data = new int[getPrimtiveLengthFromOctetLength(length)];
/*  49 */     decodeFromBytesToIntArray(data, 0, b, start, length);
/*     */     
/*  51 */     return data;
/*     */   }
/*     */   
/*     */   public final Object decodeFromInputStream(InputStream s) throws IOException {
/*  55 */     return decodeFromInputStreamToIntArray(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeToOutputStream(Object data, OutputStream s) throws IOException {
/*  60 */     if (!(data instanceof int[])) {
/*  61 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotIntArray"));
/*     */     }
/*     */     
/*  64 */     int[] idata = (int[])data;
/*     */     
/*  66 */     encodeToOutputStreamFromIntArray(idata, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object convertFromCharacters(char[] ch, int start, int length) {
/*  71 */     final CharBuffer cb = CharBuffer.wrap(ch, start, length);
/*  72 */     final List integerList = new ArrayList();
/*     */     
/*  74 */     matchWhiteSpaceDelimnatedWords(cb, new BuiltInEncodingAlgorithm.WordListener()
/*     */         {
/*     */           public void word(int start, int end) {
/*  77 */             String iStringValue = cb.subSequence(start, end).toString();
/*  78 */             integerList.add(Integer.valueOf(iStringValue));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  83 */     return generateArrayFromList(integerList);
/*     */   }
/*     */   
/*     */   public final void convertToCharacters(Object data, StringBuffer s) {
/*  87 */     if (!(data instanceof int[])) {
/*  88 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotIntArray"));
/*     */     }
/*     */     
/*  91 */     int[] idata = (int[])data;
/*     */     
/*  93 */     convertToCharactersFromIntArray(idata, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void decodeFromBytesToIntArray(int[] idata, int istart, byte[] b, int start, int length) {
/*  98 */     int size = length / 4;
/*  99 */     for (int i = 0; i < size; i++) {
/* 100 */       idata[istart++] = (b[start++] & 0xFF) << 24 | (b[start++] & 0xFF) << 16 | (b[start++] & 0xFF) << 8 | b[start++] & 0xFF;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int[] decodeFromInputStreamToIntArray(InputStream s) throws IOException {
/* 108 */     List<Integer> integerList = new ArrayList();
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
/* 127 */       int i = (b[0] & 0xFF) << 24 | (b[1] & 0xFF) << 16 | (b[2] & 0xFF) << 8 | b[3] & 0xFF;
/*     */ 
/*     */ 
/*     */       
/* 131 */       integerList.add(Integer.valueOf(i));
/*     */     } 
/*     */     
/* 134 */     return generateArrayFromList(integerList);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void encodeToOutputStreamFromIntArray(int[] idata, OutputStream s) throws IOException {
/* 139 */     for (int i = 0; i < idata.length; i++) {
/* 140 */       int bits = idata[i];
/* 141 */       s.write(bits >>> 24 & 0xFF);
/* 142 */       s.write(bits >>> 16 & 0xFF);
/* 143 */       s.write(bits >>> 8 & 0xFF);
/* 144 */       s.write(bits & 0xFF);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void encodeToBytes(Object array, int astart, int alength, byte[] b, int start) {
/* 149 */     encodeToBytesFromIntArray((int[])array, astart, alength, b, start);
/*     */   }
/*     */   
/*     */   public final void encodeToBytesFromIntArray(int[] idata, int istart, int ilength, byte[] b, int start) {
/* 153 */     int iend = istart + ilength;
/* 154 */     for (int i = istart; i < iend; i++) {
/* 155 */       int bits = idata[i];
/* 156 */       b[start++] = (byte)(bits >>> 24 & 0xFF);
/* 157 */       b[start++] = (byte)(bits >>> 16 & 0xFF);
/* 158 */       b[start++] = (byte)(bits >>> 8 & 0xFF);
/* 159 */       b[start++] = (byte)(bits & 0xFF);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void convertToCharactersFromIntArray(int[] idata, StringBuffer s) {
/* 165 */     int end = idata.length - 1;
/* 166 */     for (int i = 0; i <= end; i++) {
/* 167 */       s.append(Integer.toString(idata[i]));
/* 168 */       if (i != end) {
/* 169 */         s.append(' ');
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final int[] generateArrayFromList(List array) {
/* 176 */     int[] idata = new int[array.size()];
/* 177 */     for (int i = 0; i < idata.length; i++) {
/* 178 */       idata[i] = ((Integer)array.get(i)).intValue();
/*     */     }
/*     */     
/* 181 */     return idata;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\IntEncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */