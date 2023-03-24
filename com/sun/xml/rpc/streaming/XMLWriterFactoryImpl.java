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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLWriterFactoryImpl
/*    */   extends XMLWriterFactory
/*    */ {
/*    */   public XMLWriter createXMLWriter(OutputStream stream) {
/* 46 */     return createXMLWriter(stream, "UTF-8");
/*    */   }
/*    */   
/*    */   public XMLWriter createXMLWriter(OutputStream stream, String encoding) {
/* 50 */     return createXMLWriter(stream, encoding, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLWriter createXMLWriter(OutputStream stream, String encoding, boolean declare) {
/* 57 */     return new XMLWriterImpl(stream, encoding, declare);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLWriterFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */