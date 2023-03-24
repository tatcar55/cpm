/*    */ package com.sun.xml.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.Decoder;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import javax.xml.transform.Source;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerFactory;
/*    */ import javax.xml.transform.dom.DOMResult;
/*    */ import javax.xml.transform.dom.DOMSource;
/*    */ import javax.xml.transform.sax.SAXResult;
/*    */ import javax.xml.transform.sax.SAXSource;
/*    */ import javax.xml.transform.stream.StreamSource;
/*    */ import org.jvnet.fastinfoset.FastInfosetSource;
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
/*    */ public class FI_SAX_Or_XML_SAX_DOM_SAX_SAXEvent
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream document, OutputStream events, String workingDirectory) throws Exception {
/* 43 */     if (!document.markSupported()) {
/* 44 */       document = new BufferedInputStream(document);
/*    */     }
/*    */     
/* 47 */     document.mark(4);
/* 48 */     boolean isFastInfosetDocument = Decoder.isFastInfosetDocument(document);
/* 49 */     document.reset();
/*    */     
/* 51 */     TransformerFactory tf = TransformerFactory.newInstance();
/* 52 */     Transformer t = tf.newTransformer();
/* 53 */     DOMResult dr = new DOMResult();
/*    */     
/* 55 */     if (isFastInfosetDocument) {
/* 56 */       t.transform((Source)new FastInfosetSource(document), dr);
/* 57 */     } else if (workingDirectory != null) {
/* 58 */       SAXParser parser = getParser();
/* 59 */       XMLReader reader = parser.getXMLReader();
/* 60 */       reader.setEntityResolver(createRelativePathResolver(workingDirectory));
/* 61 */       SAXSource source = new SAXSource(reader, new InputSource(document));
/*    */       
/* 63 */       t.transform(source, dr);
/*    */     } else {
/* 65 */       t.transform(new StreamSource(document), dr);
/*    */     } 
/*    */     
/* 68 */     SAXEventSerializer ses = new SAXEventSerializer(events);
/* 69 */     t.transform(new DOMSource(dr.getNode()), new SAXResult(ses));
/*    */   }
/*    */   
/*    */   public void parse(InputStream document, OutputStream events) throws Exception {
/* 73 */     parse(document, events, (String)null);
/*    */   }
/*    */   
/*    */   private SAXParser getParser() {
/* 77 */     SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
/* 78 */     saxParserFactory.setNamespaceAware(true);
/*    */     try {
/* 80 */       return saxParserFactory.newSAXParser();
/* 81 */     } catch (Exception e) {
/* 82 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 87 */     FI_SAX_Or_XML_SAX_DOM_SAX_SAXEvent p = new FI_SAX_Or_XML_SAX_DOM_SAX_SAXEvent();
/* 88 */     p.parse(args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\FI_SAX_Or_XML_SAX_DOM_SAX_SAXEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */