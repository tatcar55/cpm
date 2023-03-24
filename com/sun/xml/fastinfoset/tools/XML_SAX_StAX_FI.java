/*    */ package com.sun.xml.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import javax.xml.stream.XMLStreamWriter;
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
/*    */ public class XML_SAX_StAX_FI
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream xml, OutputStream finf, String workingDirectory) throws Exception {
/* 34 */     StAXDocumentSerializer documentSerializer = new StAXDocumentSerializer();
/* 35 */     documentSerializer.setOutputStream(finf);
/*    */     
/* 37 */     SAX2StAXWriter saxTostax = new SAX2StAXWriter((XMLStreamWriter)documentSerializer);
/*    */     
/* 39 */     SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
/* 40 */     saxParserFactory.setNamespaceAware(true);
/* 41 */     SAXParser saxParser = saxParserFactory.newSAXParser();
/*    */     
/* 43 */     XMLReader reader = saxParser.getXMLReader();
/* 44 */     reader.setProperty("http://xml.org/sax/properties/lexical-handler", saxTostax);
/* 45 */     reader.setContentHandler(saxTostax);
/*    */     
/* 47 */     if (workingDirectory != null) {
/* 48 */       reader.setEntityResolver(createRelativePathResolver(workingDirectory));
/*    */     }
/* 50 */     reader.parse(new InputSource(xml));
/*    */     
/* 52 */     xml.close();
/* 53 */     finf.close();
/*    */   }
/*    */   
/*    */   public void parse(InputStream xml, OutputStream finf) throws Exception {
/* 57 */     parse(xml, finf, (String)null);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 61 */     XML_SAX_StAX_FI s = new XML_SAX_StAX_FI();
/* 62 */     s.parse(args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\XML_SAX_StAX_FI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */