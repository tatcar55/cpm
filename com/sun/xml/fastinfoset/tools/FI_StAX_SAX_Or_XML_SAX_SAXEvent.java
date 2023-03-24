/*    */ package com.sun.xml.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.Decoder;
/*    */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ public class FI_StAX_SAX_Or_XML_SAX_SAXEvent
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream document, OutputStream events) throws Exception {
/* 36 */     if (!document.markSupported()) {
/* 37 */       document = new BufferedInputStream(document);
/*    */     }
/*    */     
/* 40 */     document.mark(4);
/* 41 */     boolean isFastInfosetDocument = Decoder.isFastInfosetDocument(document);
/* 42 */     document.reset();
/*    */     
/* 44 */     if (isFastInfosetDocument) {
/* 45 */       StAXDocumentParser parser = new StAXDocumentParser();
/* 46 */       parser.setInputStream(document);
/* 47 */       SAXEventSerializer ses = new SAXEventSerializer(events);
/* 48 */       StAX2SAXReader reader = new StAX2SAXReader((XMLStreamReader)parser, ses);
/* 49 */       reader.setLexicalHandler(ses);
/* 50 */       reader.adapt();
/*    */     } else {
/* 52 */       SAXParserFactory parserFactory = SAXParserFactory.newInstance();
/* 53 */       parserFactory.setNamespaceAware(true);
/* 54 */       SAXParser parser = parserFactory.newSAXParser();
/* 55 */       SAXEventSerializer ses = new SAXEventSerializer(events);
/* 56 */       parser.setProperty("http://xml.org/sax/properties/lexical-handler", ses);
/* 57 */       parser.parse(document, ses);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 62 */     FI_StAX_SAX_Or_XML_SAX_SAXEvent p = new FI_StAX_SAX_Or_XML_SAX_SAXEvent();
/* 63 */     p.parse(args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\FI_StAX_SAX_Or_XML_SAX_SAXEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */