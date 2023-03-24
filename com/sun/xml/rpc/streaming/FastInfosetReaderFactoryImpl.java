/*    */ package com.sun.xml.rpc.streaming;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import org.xml.sax.InputSource;
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
/*    */ public class FastInfosetReaderFactoryImpl
/*    */   extends XMLReaderFactory
/*    */ {
/* 43 */   static ThreadLocal readerLocal = new ThreadLocal();
/*    */   
/*    */   static XMLReaderFactory _instance;
/*    */   
/*    */   public static XMLReaderFactory newInstance() {
/* 48 */     if (_instance == null) {
/* 49 */       _instance = new FastInfosetReaderFactoryImpl();
/*    */     }
/*    */     
/* 52 */     return _instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final XMLReader createXMLReader(InputStream in) {
/* 59 */     return createXMLReader(in, false);
/*    */   }
/*    */   
/*    */   public final XMLReader createXMLReader(InputSource source) {
/* 63 */     return createXMLReader(source, false);
/*    */   }
/*    */   
/*    */   public final XMLReader createXMLReader(InputSource source, boolean rejectDTDs) {
/* 67 */     return createXMLReader(source.getByteStream(), rejectDTDs);
/*    */   }
/*    */   
/*    */   public final XMLReader createXMLReader(InputStream in, boolean rejectDTDs) {
/* 71 */     FastInfosetReader reader = readerLocal.get();
/* 72 */     if (reader == null) {
/* 73 */       readerLocal.set(reader = new FastInfosetReader(in));
/*    */     } else {
/*    */       
/* 76 */       reader.setInputStream(in);
/*    */     } 
/* 78 */     return reader;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\FastInfosetReaderFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */