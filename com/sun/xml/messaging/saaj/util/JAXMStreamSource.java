/*    */ package com.sun.xml.messaging.saaj.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.Reader;
/*    */ import javax.xml.transform.stream.StreamSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JAXMStreamSource
/*    */   extends StreamSource
/*    */ {
/*    */   InputStream in;
/*    */   Reader reader;
/* 57 */   private static final boolean lazyContentLength = SAAJUtil.getSystemBoolean("saaj.lazy.contentlength");
/*    */   
/*    */   public JAXMStreamSource(InputStream is) throws IOException {
/* 60 */     if (lazyContentLength) {
/* 61 */       this.in = is;
/* 62 */     } else if (is instanceof ByteInputStream) {
/* 63 */       this.in = is;
/*    */     } else {
/* 65 */       ByteOutputStream bout = new ByteOutputStream();
/* 66 */       bout.write(is);
/* 67 */       this.in = bout.newInputStream();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public JAXMStreamSource(Reader rdr) throws IOException {
/* 73 */     if (lazyContentLength) {
/* 74 */       this.reader = rdr;
/*    */       return;
/*    */     } 
/* 77 */     CharWriter cout = new CharWriter();
/* 78 */     char[] temp = new char[1024];
/*    */     
/*    */     int len;
/* 81 */     while (-1 != (len = rdr.read(temp))) {
/* 82 */       cout.write(temp, 0, len);
/*    */     }
/* 84 */     this.reader = new CharReader(cout.getChars(), cout.getCount());
/*    */   }
/*    */   
/*    */   public InputStream getInputStream() {
/* 88 */     return this.in;
/*    */   }
/*    */   
/*    */   public Reader getReader() {
/* 92 */     return this.reader;
/*    */   }
/*    */   
/*    */   public void reset() throws IOException {
/* 96 */     if (this.in != null)
/* 97 */       this.in.reset(); 
/* 98 */     if (this.reader != null)
/* 99 */       this.reader.reset(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\JAXMStreamSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */