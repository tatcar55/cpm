/*     */ package org.jvnet.staxex;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class Base64EncoderStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private byte[] buffer;
/*  65 */   private int bufsize = 0;
/*     */   
/*     */   private XMLStreamWriter outWriter;
/*     */   
/*     */   public Base64EncoderStream(OutputStream out) {
/*  70 */     super(out);
/*  71 */     this.buffer = new byte[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64EncoderStream(XMLStreamWriter outWriter, OutputStream out) {
/*  78 */     super(out);
/*  79 */     this.buffer = new byte[3];
/*  80 */     this.outWriter = outWriter;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  95 */     for (int i = 0; i < len; i++) {
/*  96 */       write(b[off + i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/* 106 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int c) throws IOException {
/* 116 */     this.buffer[this.bufsize++] = (byte)c;
/* 117 */     if (this.bufsize == 3) {
/* 118 */       encode();
/* 119 */       this.bufsize = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 130 */     if (this.bufsize > 0) {
/* 131 */       encode();
/* 132 */       this.bufsize = 0;
/*     */     } 
/* 134 */     this.out.flush();
/*     */     try {
/* 136 */       this.outWriter.flush();
/* 137 */     } catch (XMLStreamException ex) {
/* 138 */       Logger.getLogger(Base64EncoderStream.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 139 */       throw new IOException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 149 */     flush();
/* 150 */     this.out.close();
/*     */   }
/*     */ 
/*     */   
/* 154 */   private static final char[] pem_array = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
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
/*     */   private void encode() throws IOException {
/* 167 */     char[] buf = new char[4];
/* 168 */     if (this.bufsize == 1) {
/* 169 */       byte a = this.buffer[0];
/* 170 */       byte b = 0;
/* 171 */       byte c = 0;
/* 172 */       buf[0] = pem_array[a >>> 2 & 0x3F];
/* 173 */       buf[1] = pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)];
/* 174 */       buf[2] = '=';
/* 175 */       buf[3] = '=';
/* 176 */     } else if (this.bufsize == 2) {
/* 177 */       byte a = this.buffer[0];
/* 178 */       byte b = this.buffer[1];
/* 179 */       byte c = 0;
/* 180 */       buf[0] = pem_array[a >>> 2 & 0x3F];
/* 181 */       buf[1] = pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)];
/* 182 */       buf[2] = pem_array[(b << 2 & 0x3C) + (c >>> 6 & 0x3)];
/* 183 */       buf[3] = '=';
/*     */     } else {
/* 185 */       byte a = this.buffer[0];
/* 186 */       byte b = this.buffer[1];
/* 187 */       byte c = this.buffer[2];
/* 188 */       buf[0] = pem_array[a >>> 2 & 0x3F];
/* 189 */       buf[1] = pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)];
/* 190 */       buf[2] = pem_array[(b << 2 & 0x3C) + (c >>> 6 & 0x3)];
/* 191 */       buf[3] = pem_array[c & 0x3F];
/*     */     } 
/*     */     try {
/* 194 */       this.outWriter.writeCharacters(buf, 0, 4);
/* 195 */     } catch (XMLStreamException ex) {
/* 196 */       Logger.getLogger(Base64EncoderStream.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 197 */       throw new IOException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\staxex\Base64EncoderStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */