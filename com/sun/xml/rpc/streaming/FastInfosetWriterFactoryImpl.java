/*    */ package com.sun.xml.rpc.streaming;
/*    */ 
/*    */ import java.io.OutputStream;
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
/*    */ public class FastInfosetWriterFactoryImpl
/*    */   extends XMLWriterFactory
/*    */ {
/*    */   static XMLWriterFactory _instance;
/* 43 */   static ThreadLocal writerLocal = new ThreadLocal();
/*    */   
/*    */   public static XMLWriterFactory newInstance() {
/* 46 */     if (_instance == null) {
/* 47 */       _instance = new FastInfosetWriterFactoryImpl();
/*    */     }
/*    */     
/* 50 */     return _instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final XMLWriter createXMLWriter(OutputStream stream) {
/* 57 */     return createXMLWriter(stream, "UTF-8");
/*    */   }
/*    */   
/*    */   public final XMLWriter createXMLWriter(OutputStream stream, String encoding) {
/* 61 */     return createXMLWriter(stream, encoding, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final XMLWriter createXMLWriter(OutputStream stream, String encoding, boolean declare) {
/* 67 */     FastInfosetWriter writer = writerLocal.get();
/* 68 */     if (writer == null) {
/* 69 */       writerLocal.set(writer = new FastInfosetWriter(stream, encoding));
/*    */     } else {
/*    */       
/* 72 */       writer.setOutputStream(stream);
/* 73 */       writer.setEncoding(encoding);
/*    */     } 
/* 75 */     writer.writeStartDocument();
/* 76 */     return writer;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\FastInfosetWriterFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */