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
/*     */ public class ShortEncodingAlgorithm
/*     */   extends IntegerEncodingAlgorithm
/*     */ {
/*     */   public final int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
/*  40 */     if (octetLength % 2 != 0) {
/*  41 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.lengthNotMultipleOfShort", new Object[] { Integer.valueOf(2) }));
/*     */     }
/*     */ 
/*     */     
/*  45 */     return octetLength / 2;
/*     */   }
/*     */   
/*     */   public int getOctetLengthFromPrimitiveLength(int primitiveLength) {
/*  49 */     return primitiveLength * 2;
/*     */   }
/*     */   
/*     */   public final Object decodeFromBytes(byte[] b, int start, int length) throws EncodingAlgorithmException {
/*  53 */     short[] data = new short[getPrimtiveLengthFromOctetLength(length)];
/*  54 */     decodeFromBytesToShortArray(data, 0, b, start, length);
/*     */     
/*  56 */     return data;
/*     */   }
/*     */   
/*     */   public final Object decodeFromInputStream(InputStream s) throws IOException {
/*  60 */     return decodeFromInputStreamToShortArray(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeToOutputStream(Object data, OutputStream s) throws IOException {
/*  65 */     if (!(data instanceof short[])) {
/*  66 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotShortArray"));
/*     */     }
/*     */     
/*  69 */     short[] idata = (short[])data;
/*     */     
/*  71 */     encodeToOutputStreamFromShortArray(idata, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object convertFromCharacters(char[] ch, int start, int length) {
/*  76 */     final CharBuffer cb = CharBuffer.wrap(ch, start, length);
/*  77 */     final List shortList = new ArrayList();
/*     */     
/*  79 */     matchWhiteSpaceDelimnatedWords(cb, new BuiltInEncodingAlgorithm.WordListener()
/*     */         {
/*     */           public void word(int start, int end) {
/*  82 */             String iStringValue = cb.subSequence(start, end).toString();
/*  83 */             shortList.add(Short.valueOf(iStringValue));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  88 */     return generateArrayFromList(shortList);
/*     */   }
/*     */   
/*     */   public final void convertToCharacters(Object data, StringBuffer s) {
/*  92 */     if (!(data instanceof short[])) {
/*  93 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotShortArray"));
/*     */     }
/*     */     
/*  96 */     short[] idata = (short[])data;
/*     */     
/*  98 */     convertToCharactersFromShortArray(idata, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void decodeFromBytesToShortArray(short[] sdata, int istart, byte[] b, int start, int length) {
/* 103 */     int size = length / 2;
/* 104 */     for (int i = 0; i < size; i++) {
/* 105 */       sdata[istart++] = (short)((b[start++] & 0xFF) << 8 | b[start++] & 0xFF);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final short[] decodeFromInputStreamToShortArray(InputStream s) throws IOException {
/* 111 */     List<Short> shortList = new ArrayList();
/* 112 */     byte[] b = new byte[2];
/*     */     
/*     */     while (true) {
/* 115 */       int n = s.read(b);
/* 116 */       if (n != 2) {
/* 117 */         if (n == -1) {
/*     */           break;
/*     */         }
/*     */         
/* 121 */         while (n != 2) {
/* 122 */           int m = s.read(b, n, 2 - n);
/* 123 */           if (m == -1) {
/* 124 */             throw new EOFException();
/*     */           }
/* 126 */           n += m;
/*     */         } 
/*     */       } 
/*     */       
/* 130 */       int i = (b[0] & 0xFF) << 8 | b[1] & 0xFF;
/*     */       
/* 132 */       shortList.add(Short.valueOf((short)i));
/*     */     } 
/*     */     
/* 135 */     return generateArrayFromList(shortList);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void encodeToOutputStreamFromShortArray(short[] idata, OutputStream s) throws IOException {
/* 140 */     for (int i = 0; i < idata.length; i++) {
/* 141 */       int bits = idata[i];
/* 142 */       s.write(bits >>> 8 & 0xFF);
/* 143 */       s.write(bits & 0xFF);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void encodeToBytes(Object array, int astart, int alength, byte[] b, int start) {
/* 148 */     encodeToBytesFromShortArray((short[])array, astart, alength, b, start);
/*     */   }
/*     */   
/*     */   public final void encodeToBytesFromShortArray(short[] sdata, int istart, int ilength, byte[] b, int start) {
/* 152 */     int iend = istart + ilength;
/* 153 */     for (int i = istart; i < iend; i++) {
/* 154 */       short bits = sdata[i];
/* 155 */       b[start++] = (byte)(bits >>> 8 & 0xFF);
/* 156 */       b[start++] = (byte)(bits & 0xFF);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void convertToCharactersFromShortArray(short[] sdata, StringBuffer s) {
/* 162 */     int end = sdata.length - 1;
/* 163 */     for (int i = 0; i <= end; i++) {
/* 164 */       s.append(Short.toString(sdata[i]));
/* 165 */       if (i != end) {
/* 166 */         s.append(' ');
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final short[] generateArrayFromList(List array) {
/* 173 */     short[] sdata = new short[array.size()];
/* 174 */     for (int i = 0; i < sdata.length; i++) {
/* 175 */       sdata[i] = ((Short)array.get(i)).shortValue();
/*     */     }
/*     */     
/* 178 */     return sdata;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\ShortEncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */