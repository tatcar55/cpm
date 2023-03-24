/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ import java.nio.ByteBuffer;
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
/*     */ public final class DumpUtils
/*     */ {
/*     */   public static String dumpBytes(ByteBuffer[] bb) {
/*  50 */     StringBuffer stringBuffer = new StringBuffer();
/*  51 */     for (int i = 0; i < bb.length; i++) {
/*  52 */       stringBuffer.append(dumpBytes(bb[i]));
/*     */     }
/*     */     
/*  55 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public static String dumpBytes(ByteBuffer buffer) {
/*  59 */     return dumpBytes(buffer, buffer.position(), buffer.limit() - buffer.position());
/*     */   }
/*     */   
/*     */   public static String dumpBytes(ByteBuffer buffer, int offset, int length) {
/*  63 */     byte[] array = new byte[length];
/*  64 */     int position = buffer.position();
/*  65 */     buffer.position(offset);
/*  66 */     buffer.get(array);
/*  67 */     buffer.position(position);
/*  68 */     return dumpBytes(array);
/*     */   }
/*     */   
/*     */   public static String dumpOctets(ByteBuffer[] bb) {
/*  72 */     StringBuffer stringBuffer = new StringBuffer();
/*  73 */     for (int i = 0; i < bb.length; i++) {
/*  74 */       stringBuffer.append(dumpOctets(bb[i]));
/*     */     }
/*     */     
/*  77 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public static String dumpOctets(ByteBuffer buffer) {
/*  81 */     return dumpOctets(buffer, buffer.position(), buffer.limit() - buffer.position());
/*     */   }
/*     */   
/*     */   public static String dumpOctets(ByteBuffer buffer, int offset, int length) {
/*  85 */     byte[] array = new byte[length];
/*  86 */     int position = buffer.position();
/*  87 */     buffer.position(offset);
/*  88 */     buffer.get(array);
/*  89 */     buffer.position(position);
/*  90 */     return dumpBytes(array);
/*     */   }
/*     */   
/*     */   public static String dump(ByteBuffer[] bb) {
/*  94 */     StringBuffer stringBuffer = new StringBuffer();
/*  95 */     for (int i = 0; i < bb.length; i++) {
/*  96 */       stringBuffer.append(dump(bb[i]));
/*     */     }
/*     */     
/*  99 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public static String dump(ByteBuffer buffer) {
/* 103 */     return dump(buffer, buffer.position(), buffer.limit() - buffer.position());
/*     */   }
/*     */   
/*     */   public static String dump(ByteBuffer buffer, int offset, int length) {
/* 107 */     byte[] array = new byte[length];
/* 108 */     int position = buffer.position();
/* 109 */     buffer.position(offset);
/* 110 */     buffer.get(array);
/* 111 */     buffer.position(position);
/* 112 */     return dump(array);
/*     */   }
/*     */   
/*     */   public static String dump(byte[] buffer) {
/* 116 */     return dump(buffer, 0, buffer.length);
/*     */   }
/*     */   
/*     */   public static String dump(byte[] buffer, int offset, int length) {
/* 120 */     StringBuffer stringBuffer = new StringBuffer();
/* 121 */     for (int i = 0; i < length; i++) {
/* 122 */       int value = buffer[offset + i] & 0xFF;
/* 123 */       String strValue = Integer.toHexString(value).toUpperCase();
/* 124 */       String str = "00".substring(strValue.length()) + strValue;
/* 125 */       stringBuffer.append(str);
/* 126 */       stringBuffer.append('(');
/* 127 */       stringBuffer.append((char)value);
/* 128 */       stringBuffer.append(')');
/* 129 */       stringBuffer.append(' ');
/*     */     } 
/*     */     
/* 132 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public static String dumpOctets(byte[] buffer) {
/* 136 */     return dumpOctets(buffer, 0, buffer.length);
/*     */   }
/*     */   
/*     */   public static String dumpOctets(byte[] buffer, int offset, int length) {
/* 140 */     StringBuffer stringBuffer = new StringBuffer();
/* 141 */     for (int i = 0; i < length; i++) {
/* 142 */       int value = buffer[offset + i] & 0xFF;
/* 143 */       String strValue = Integer.toHexString(value).toUpperCase();
/* 144 */       String str = "00".substring(strValue.length()) + strValue;
/* 145 */       stringBuffer.append(str);
/* 146 */       stringBuffer.append(' ');
/*     */     } 
/*     */     
/* 149 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public static String dumpBytes(byte[] buffer) {
/* 153 */     return dumpBytes(buffer, 0, buffer.length);
/*     */   }
/*     */   
/*     */   public static String dumpBytes(byte[] buffer, int offset, int length) {
/* 157 */     StringBuffer stringBuffer = new StringBuffer();
/* 158 */     for (int i = 0; i < length; i++) {
/* 159 */       int value = buffer[offset + i] & 0xFF;
/* 160 */       stringBuffer.append((char)value);
/*     */     } 
/*     */     
/* 163 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\DumpUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */