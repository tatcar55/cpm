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
/*    */ public class XMLReaderFactoryImpl
/*    */   extends XMLReaderFactory
/*    */ {
/*    */   public XMLReader createXMLReader(InputStream in) {
/* 44 */     return createXMLReader(in, false);
/*    */   }
/*    */   
/*    */   public XMLReader createXMLReader(InputSource source) {
/* 48 */     return createXMLReader(source, false);
/*    */   }
/*    */   
/*    */   public XMLReader createXMLReader(InputStream in, boolean rejectDTDs) {
/* 52 */     return createXMLReader(new InputSource(in), rejectDTDs);
/*    */   }
/*    */   
/*    */   public XMLReader createXMLReader(InputSource source, boolean rejectDTDs) {
/* 56 */     return new XMLReaderImpl(source, rejectDTDs);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLReaderFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */