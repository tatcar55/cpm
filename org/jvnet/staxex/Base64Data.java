/*     */ package org.jvnet.staxex;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class Base64Data
/*     */   implements CharSequence, Cloneable
/*     */ {
/*     */   private DataHandler dataHandler;
/*     */   private byte[] data;
/*     */   private int dataLen;
/*     */   private boolean dataCloneByRef;
/*     */   private String mimeType;
/*     */   
/*     */   public Base64Data() {}
/*     */   
/* 104 */   private static final Logger logger = Logger.getLogger(Base64Data.class.getName());
/*     */ 
/*     */   
/*     */   private static final int CHUNK_SIZE;
/*     */ 
/*     */   
/*     */   public Base64Data(Base64Data that) {
/* 111 */     that.get();
/* 112 */     if (that.dataCloneByRef) {
/* 113 */       this.data = that.data;
/*     */     } else {
/* 115 */       this.data = new byte[that.dataLen];
/* 116 */       System.arraycopy(that.data, 0, this.data, 0, that.dataLen);
/*     */     } 
/*     */     
/* 119 */     this.dataCloneByRef = true;
/* 120 */     this.dataLen = that.dataLen;
/* 121 */     this.dataHandler = null;
/* 122 */     this.mimeType = that.mimeType;
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
/*     */   public void set(byte[] data, int len, String mimeType, boolean cloneByRef) {
/* 136 */     this.data = data;
/* 137 */     this.dataLen = len;
/* 138 */     this.dataCloneByRef = cloneByRef;
/* 139 */     this.dataHandler = null;
/* 140 */     this.mimeType = mimeType;
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
/*     */   public void set(byte[] data, int len, String mimeType) {
/* 152 */     set(data, len, mimeType, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(byte[] data, String mimeType) {
/* 163 */     set(data, data.length, mimeType, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(DataHandler data) {
/* 172 */     assert data != null;
/* 173 */     this.dataHandler = data;
/* 174 */     this.data = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataHandler getDataHandler() {
/* 185 */     if (this.dataHandler == null) {
/* 186 */       this.dataHandler = new Base64StreamingDataHandler(new Base64DataSource());
/* 187 */     } else if (!(this.dataHandler instanceof StreamingDataHandler)) {
/* 188 */       this.dataHandler = new FilterDataHandler(this.dataHandler);
/*     */     } 
/* 190 */     return this.dataHandler;
/*     */   }
/*     */   
/*     */   private final class Base64DataSource implements DataSource {
/*     */     public String getContentType() {
/* 195 */       return Base64Data.this.getMimeType();
/*     */     }
/*     */     private Base64DataSource() {}
/*     */     public InputStream getInputStream() {
/* 199 */       return new ByteArrayInputStream(Base64Data.this.data, 0, Base64Data.this.dataLen);
/*     */     }
/*     */     
/*     */     public String getName() {
/* 203 */       return null;
/*     */     }
/*     */     
/*     */     public OutputStream getOutputStream() {
/* 207 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Base64StreamingDataHandler
/*     */     extends StreamingDataHandler
/*     */   {
/*     */     Base64StreamingDataHandler(DataSource source) {
/* 215 */       super(source);
/*     */     }
/*     */     
/*     */     public InputStream readOnce() throws IOException {
/* 219 */       return getDataSource().getInputStream();
/*     */     }
/*     */     
/*     */     public void moveTo(File dst) throws IOException {
/* 223 */       FileOutputStream fout = new FileOutputStream(dst);
/*     */       try {
/* 225 */         fout.write(Base64Data.this.data, 0, Base64Data.this.dataLen);
/*     */       } finally {
/* 227 */         fout.close();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {}
/*     */   }
/*     */   
/*     */   private static final class FilterDataHandler
/*     */     extends StreamingDataHandler
/*     */   {
/*     */     FilterDataHandler(DataHandler dh) {
/* 239 */       super(dh.getDataSource());
/*     */     }
/*     */     
/*     */     public InputStream readOnce() throws IOException {
/* 243 */       return getDataSource().getInputStream();
/*     */     }
/*     */     
/*     */     public void moveTo(File dst) throws IOException {
/* 247 */       byte[] buf = new byte[8192];
/* 248 */       InputStream in = null;
/* 249 */       OutputStream out = null;
/*     */       try {
/* 251 */         in = getDataSource().getInputStream();
/* 252 */         out = new FileOutputStream(dst);
/*     */         while (true) {
/* 254 */           int amountRead = in.read(buf);
/* 255 */           if (amountRead == -1) {
/*     */             break;
/*     */           }
/* 258 */           out.write(buf, 0, amountRead);
/*     */         } 
/*     */       } finally {
/* 261 */         if (in != null) {
/*     */           try {
/* 263 */             in.close();
/* 264 */           } catch (IOException ioe) {}
/*     */         }
/*     */ 
/*     */         
/* 268 */         if (out != null) {
/*     */           try {
/* 270 */             out.close();
/* 271 */           } catch (IOException ioe) {}
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getExact() {
/* 289 */     get();
/* 290 */     if (this.dataLen != this.data.length) {
/* 291 */       byte[] buf = new byte[this.dataLen];
/* 292 */       System.arraycopy(this.data, 0, buf, 0, this.dataLen);
/* 293 */       this.data = buf;
/*     */     } 
/* 295 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() throws IOException {
/* 305 */     if (this.dataHandler != null) {
/* 306 */       return this.dataHandler.getInputStream();
/*     */     }
/* 308 */     return new ByteArrayInputStream(this.data, 0, this.dataLen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasData() {
/* 318 */     return (this.data != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] get() {
/* 327 */     if (this.data == null) {
/*     */       try {
/* 329 */         ByteArrayOutputStreamEx baos = new ByteArrayOutputStreamEx(1024);
/* 330 */         InputStream is = this.dataHandler.getDataSource().getInputStream();
/* 331 */         baos.readFrom(is);
/* 332 */         is.close();
/* 333 */         this.data = baos.getBuffer();
/* 334 */         this.dataLen = baos.size();
/* 335 */         this.dataCloneByRef = true;
/* 336 */       } catch (IOException e) {
/*     */         
/* 338 */         this.dataLen = 0;
/*     */       } 
/*     */     }
/* 341 */     return this.data;
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
/*     */   public int getDataLen() {
/* 355 */     get();
/* 356 */     return this.dataLen;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/* 360 */     if (this.mimeType == null)
/* 361 */       return "application/octet-stream"; 
/* 362 */     return this.mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 372 */     get();
/* 373 */     return (this.dataLen + 2) / 3 * 4;
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
/* 385 */     int offset = index % 4;
/* 386 */     int base = index / 4 * 3;
/*     */ 
/*     */ 
/*     */     
/* 390 */     switch (offset) {
/*     */       case 0:
/* 392 */         return Base64Encoder.encode(this.data[base] >> 2);
/*     */       case 1:
/* 394 */         if (base + 1 < this.dataLen) {
/* 395 */           b1 = this.data[base + 1];
/*     */         } else {
/* 397 */           b1 = 0;
/* 398 */         }  return Base64Encoder.encode((this.data[base] & 0x3) << 4 | b1 >> 4 & 0xF);
/*     */ 
/*     */       
/*     */       case 2:
/* 402 */         if (base + 1 < this.dataLen) {
/* 403 */           byte b2; b1 = this.data[base + 1];
/* 404 */           if (base + 2 < this.dataLen) {
/* 405 */             b2 = this.data[base + 2];
/*     */           } else {
/* 407 */             b2 = 0;
/*     */           } 
/* 409 */           return Base64Encoder.encode((b1 & 0xF) << 2 | b2 >> 6 & 0x3);
/*     */         } 
/*     */ 
/*     */         
/* 413 */         return '=';
/*     */       case 3:
/* 415 */         if (base + 2 < this.dataLen) {
/* 416 */           return Base64Encoder.encode(this.data[base + 2] & 0x3F);
/*     */         }
/* 418 */         return '=';
/*     */     } 
/*     */     
/* 421 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence subSequence(int start, int end) {
/* 430 */     StringBuilder buf = new StringBuilder();
/* 431 */     get();
/* 432 */     for (int i = start; i < end; i++)
/* 433 */       buf.append(charAt(i)); 
/* 434 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 442 */     get();
/* 443 */     return Base64Encoder.print(this.data, 0, this.dataLen);
/*     */   }
/*     */   
/*     */   public void writeTo(char[] buf, int start) {
/* 447 */     get();
/* 448 */     Base64Encoder.print(this.data, 0, this.dataLen, buf, start);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 453 */     int bufSize = 1024;
/*     */     try {
/* 455 */       String bufSizeStr = getProperty("org.jvnet.staxex.Base64DataStreamWriteBufferSize");
/* 456 */       if (bufSizeStr != null) {
/* 457 */         bufSize = Integer.parseInt(bufSizeStr);
/*     */       }
/* 459 */     } catch (Exception e) {
/* 460 */       logger.log(Level.INFO, "Error reading org.jvnet.staxex.Base64DataStreamWriteBufferSize property", e);
/*     */     } 
/* 462 */     CHUNK_SIZE = bufSize;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter output) throws IOException, XMLStreamException {
/* 466 */     if (this.data == null) {
/*     */       try {
/* 468 */         InputStream is = this.dataHandler.getDataSource().getInputStream();
/* 469 */         ByteArrayOutputStream outStream = new ByteArrayOutputStream();
/* 470 */         Base64EncoderStream encWriter = new Base64EncoderStream(output, outStream);
/*     */         
/* 472 */         byte[] buffer = new byte[CHUNK_SIZE]; int b;
/* 473 */         while ((b = is.read(buffer)) != -1) {
/* 474 */           encWriter.write(buffer, 0, b);
/*     */         }
/* 476 */         outStream.close();
/* 477 */         encWriter.close();
/* 478 */       } catch (IOException e) {
/* 479 */         this.dataLen = 0;
/* 480 */         throw e;
/*     */       } 
/*     */     } else {
/*     */       
/* 484 */       String s = Base64Encoder.print(this.data, 0, this.dataLen);
/* 485 */       output.writeCharacters(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Base64Data clone() {
/* 491 */     return new Base64Data(this);
/*     */   }
/*     */   
/*     */   static String getProperty(final String propName) {
/* 495 */     if (System.getSecurityManager() == null) {
/* 496 */       return System.getProperty(propName);
/*     */     }
/* 498 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public Object run() {
/* 501 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\staxex\Base64Data.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */