/*    */ package com.sun.xml.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.sax.SAXDocumentSerializer;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Reader;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.XMLReader;
/*    */ import org.xml.sax.helpers.DefaultHandler;
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
/*    */ public class XML_SAX_FI
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream xml, OutputStream finf, String workingDirectory) throws Exception {
/* 35 */     SAXParser saxParser = getParser();
/* 36 */     SAXDocumentSerializer documentSerializer = getSerializer(finf);
/*    */     
/* 38 */     XMLReader reader = saxParser.getXMLReader();
/* 39 */     reader.setProperty("http://xml.org/sax/properties/lexical-handler", documentSerializer);
/* 40 */     reader.setContentHandler((ContentHandler)documentSerializer);
/*    */     
/* 42 */     if (workingDirectory != null) {
/* 43 */       reader.setEntityResolver(createRelativePathResolver(workingDirectory));
/*    */     }
/* 45 */     reader.parse(new InputSource(xml));
/*    */   }
/*    */   
/*    */   public void parse(InputStream xml, OutputStream finf) throws Exception {
/* 49 */     parse(xml, finf, (String)null);
/*    */   }
/*    */   
/*    */   public void convert(Reader reader, OutputStream finf) throws Exception {
/* 53 */     InputSource is = new InputSource(reader);
/*    */     
/* 55 */     SAXParser saxParser = getParser();
/* 56 */     SAXDocumentSerializer documentSerializer = getSerializer(finf);
/*    */     
/* 58 */     saxParser.setProperty("http://xml.org/sax/properties/lexical-handler", documentSerializer);
/* 59 */     saxParser.parse(is, (DefaultHandler)documentSerializer);
/*    */   }
/*    */   
/*    */   private SAXParser getParser() {
/* 63 */     SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
/* 64 */     saxParserFactory.setNamespaceAware(true);
/*    */     try {
/* 66 */       return saxParserFactory.newSAXParser();
/* 67 */     } catch (Exception e) {
/* 68 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   private SAXDocumentSerializer getSerializer(OutputStream finf) {
/* 73 */     SAXDocumentSerializer documentSerializer = new SAXDocumentSerializer();
/* 74 */     documentSerializer.setOutputStream(finf);
/* 75 */     return documentSerializer;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 79 */     XML_SAX_FI s = new XML_SAX_FI();
/* 80 */     s.parse(args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\XML_SAX_FI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */