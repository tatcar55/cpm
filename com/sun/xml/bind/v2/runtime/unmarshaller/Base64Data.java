/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.bind.v2.runtime.output.Pcdata;
/*     */ import com.sun.xml.bind.v2.runtime.output.UTF8XmlOutput;
/*     */ import com.sun.xml.bind.v2.util.ByteArrayOutputStreamEx;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
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
/*     */ public final class Base64Data
/*     */   extends Pcdata
/*     */ {
/*     */   private DataHandler dataHandler;
/*     */   private byte[] data;
/*     */   private int dataLen;
/*     */   @Nullable
/*     */   private String mimeType;
/*     */   
/*     */   public void set(byte[] data, int len, @Nullable String mimeType) {
/* 103 */     this.data = data;
/* 104 */     this.dataLen = len;
/* 105 */     this.dataHandler = null;
/* 106 */     this.mimeType = mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(byte[] data, @Nullable String mimeType) {
/* 116 */     set(data, data.length, mimeType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(DataHandler data) {
/* 123 */     assert data != null;
/* 124 */     this.dataHandler = data;
/* 125 */     this.data = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataHandler getDataHandler() {
/* 132 */     if (this.dataHandler == null) {
/* 133 */       this.dataHandler = new DataHandler(new DataSource()
/*     */           {
/*     */             public String getContentType() {
/* 136 */               return Base64Data.this.getMimeType();
/*     */             }
/*     */             
/*     */             public InputStream getInputStream() {
/* 140 */               return new ByteArrayInputStream(Base64Data.this.data, 0, Base64Data.this.dataLen);
/*     */             }
/*     */             
/*     */             public String getName() {
/* 144 */               return null;
/*     */             }
/*     */             
/*     */             public OutputStream getOutputStream() {
/* 148 */               throw new UnsupportedOperationException();
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 153 */     return this.dataHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getExact() {
/* 160 */     get();
/* 161 */     if (this.dataLen != this.data.length) {
/* 162 */       byte[] buf = new byte[this.dataLen];
/* 163 */       System.arraycopy(this.data, 0, buf, 0, this.dataLen);
/* 164 */       this.data = buf;
/*     */     } 
/* 166 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() throws IOException {
/* 173 */     if (this.dataHandler != null) {
/* 174 */       return this.dataHandler.getInputStream();
/*     */     }
/* 176 */     return new ByteArrayInputStream(this.data, 0, this.dataLen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasData() {
/* 185 */     return (this.data != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] get() {
/* 192 */     if (this.data == null) {
/*     */       try {
/* 194 */         ByteArrayOutputStreamEx baos = new ByteArrayOutputStreamEx(1024);
/* 195 */         InputStream is = this.dataHandler.getDataSource().getInputStream();
/* 196 */         baos.readFrom(is);
/* 197 */         is.close();
/* 198 */         this.data = baos.getBuffer();
/* 199 */         this.dataLen = baos.size();
/* 200 */       } catch (IOException e) {
/*     */         
/* 202 */         this.dataLen = 0;
/*     */       } 
/*     */     }
/* 205 */     return this.data;
/*     */   }
/*     */   
/*     */   public int getDataLen() {
/* 209 */     return this.dataLen;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/* 213 */     if (this.mimeType == null) {
/* 214 */       return "application/octet-stream";
/*     */     }
/* 216 */     return this.mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 226 */     get();
/* 227 */     return (this.dataLen + 2) / 3 * 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char charAt(int index) {
/*     */     byte b1;
/* 239 */     int offset = index % 4;
/* 240 */     int base = index / 4 * 3;
/*     */ 
/*     */ 
/*     */     
/* 244 */     switch (offset) {
/*     */       case 0:
/* 246 */         return DatatypeConverterImpl.encode(this.data[base] >> 2);
/*     */       case 1:
/* 248 */         if (base + 1 < this.dataLen) {
/* 249 */           b1 = this.data[base + 1];
/*     */         } else {
/* 251 */           b1 = 0;
/*     */         } 
/* 253 */         return DatatypeConverterImpl.encode((this.data[base] & 0x3) << 4 | b1 >> 4 & 0xF);
/*     */ 
/*     */       
/*     */       case 2:
/* 257 */         if (base + 1 < this.dataLen) {
/* 258 */           byte b2; b1 = this.data[base + 1];
/* 259 */           if (base + 2 < this.dataLen) {
/* 260 */             b2 = this.data[base + 2];
/*     */           } else {
/* 262 */             b2 = 0;
/*     */           } 
/*     */           
/* 265 */           return DatatypeConverterImpl.encode((b1 & 0xF) << 2 | b2 >> 6 & 0x3);
/*     */         } 
/*     */ 
/*     */         
/* 269 */         return '=';
/*     */       
/*     */       case 3:
/* 272 */         if (base + 2 < this.dataLen) {
/* 273 */           return DatatypeConverterImpl.encode(this.data[base + 2] & 0x3F);
/*     */         }
/* 275 */         return '=';
/*     */     } 
/*     */ 
/*     */     
/* 279 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence subSequence(int start, int end) {
/* 288 */     StringBuilder buf = new StringBuilder();
/* 289 */     get();
/* 290 */     for (int i = start; i < end; i++) {
/* 291 */       buf.append(charAt(i));
/*     */     }
/* 293 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 300 */     get();
/* 301 */     return DatatypeConverterImpl._printBase64Binary(this.data, 0, this.dataLen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(char[] buf, int start) {
/* 306 */     get();
/* 307 */     DatatypeConverterImpl._printBase64Binary(this.data, 0, this.dataLen, buf, start);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(UTF8XmlOutput output) throws IOException {
/* 312 */     get();
/* 313 */     output.text(this.data, this.dataLen);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter output) throws IOException, XMLStreamException {
/* 317 */     get();
/* 318 */     DatatypeConverterImpl._printBase64Binary(this.data, 0, this.dataLen, output);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\Base64Data.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */