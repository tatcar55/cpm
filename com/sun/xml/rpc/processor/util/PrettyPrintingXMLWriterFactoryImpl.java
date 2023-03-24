/*    */ package com.sun.xml.rpc.processor.util;
/*    */ 
/*    */ import com.sun.xml.rpc.streaming.XMLWriter;
/*    */ import com.sun.xml.rpc.streaming.XMLWriterFactory;
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
/*    */ public class PrettyPrintingXMLWriterFactoryImpl
/*    */   extends XMLWriterFactory
/*    */ {
/*    */   public XMLWriter createXMLWriter(OutputStream stream) {
/* 48 */     return createXMLWriter(stream, "UTF-8");
/*    */   }
/*    */   
/*    */   public XMLWriter createXMLWriter(OutputStream stream, String encoding) {
/* 52 */     return createXMLWriter(stream, encoding, true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLWriter createXMLWriter(OutputStream stream, String encoding, boolean declare) {
/* 58 */     return (XMLWriter)new PrettyPrintingXMLWriterImpl(stream, encoding, declare);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\PrettyPrintingXMLWriterFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */