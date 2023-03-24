/*    */ package com.sun.xml.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.Decoder;
/*    */ import com.sun.xml.fastinfoset.sax.SAXDocumentParser;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.XMLReader;
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
/*    */ public class FI_SAX_Or_XML_SAX_SAXEvent
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream document, OutputStream events, String workingDirectory) throws Exception {
/* 37 */     if (!document.markSupported()) {
/* 38 */       document = new BufferedInputStream(document);
/*    */     }
/*    */     
/* 41 */     document.mark(4);
/* 42 */     boolean isFastInfosetDocument = Decoder.isFastInfosetDocument(document);
/* 43 */     document.reset();
/*    */     
/* 45 */     if (isFastInfosetDocument) {
/* 46 */       SAXDocumentParser parser = new SAXDocumentParser();
/* 47 */       SAXEventSerializer ses = new SAXEventSerializer(events);
/* 48 */       parser.setContentHandler(ses);
/* 49 */       parser.setProperty("http://xml.org/sax/properties/lexical-handler", ses);
/* 50 */       parser.parse(document);
/*    */     } else {
/* 52 */       SAXParserFactory parserFactory = SAXParserFactory.newInstance();
/* 53 */       parserFactory.setNamespaceAware(true);
/* 54 */       SAXParser parser = parserFactory.newSAXParser();
/* 55 */       SAXEventSerializer ses = new SAXEventSerializer(events);
/*    */       
/* 57 */       XMLReader reader = parser.getXMLReader();
/* 58 */       reader.setProperty("http://xml.org/sax/properties/lexical-handler", ses);
/* 59 */       reader.setContentHandler(ses);
/* 60 */       if (workingDirectory != null) {
/* 61 */         reader.setEntityResolver(createRelativePathResolver(workingDirectory));
/*    */       }
/* 63 */       reader.parse(new InputSource(document));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void parse(InputStream document, OutputStream events) throws Exception {
/* 68 */     parse(document, events, (String)null);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 72 */     FI_SAX_Or_XML_SAX_SAXEvent p = new FI_SAX_Or_XML_SAX_SAXEvent();
/* 73 */     p.parse(args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\FI_SAX_Or_XML_SAX_SAXEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */