/*    */ package com.sun.xml.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.Decoder;
/*    */ import com.sun.xml.fastinfoset.dom.DOMDocumentParser;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerFactory;
/*    */ import javax.xml.transform.dom.DOMSource;
/*    */ import javax.xml.transform.sax.SAXResult;
/*    */ import org.w3c.dom.Document;
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
/*    */ public class FI_DOM_Or_XML_DOM_SAX_SAXEvent
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream document, OutputStream events, String workingDirectory) throws Exception {
/*    */     Document d;
/* 36 */     if (!document.markSupported()) {
/* 37 */       document = new BufferedInputStream(document);
/*    */     }
/*    */     
/* 40 */     document.mark(4);
/* 41 */     boolean isFastInfosetDocument = Decoder.isFastInfosetDocument(document);
/* 42 */     document.reset();
/*    */     
/* 44 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 45 */     dbf.setNamespaceAware(true);
/* 46 */     DocumentBuilder db = dbf.newDocumentBuilder();
/*    */ 
/*    */     
/* 49 */     if (isFastInfosetDocument) {
/* 50 */       d = db.newDocument();
/* 51 */       DOMDocumentParser ddp = new DOMDocumentParser();
/* 52 */       ddp.parse(d, document);
/*    */     } else {
/* 54 */       if (workingDirectory != null) {
/* 55 */         db.setEntityResolver(createRelativePathResolver(workingDirectory));
/*    */       }
/* 57 */       d = db.parse(document);
/*    */     } 
/*    */     
/* 60 */     SAXEventSerializer ses = new SAXEventSerializer(events);
/*    */     
/* 62 */     TransformerFactory tf = TransformerFactory.newInstance();
/* 63 */     Transformer t = tf.newTransformer();
/* 64 */     t.transform(new DOMSource(d), new SAXResult(ses));
/*    */   }
/*    */   
/*    */   public void parse(InputStream document, OutputStream events) throws Exception {
/* 68 */     parse(document, events, (String)null);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 72 */     FI_DOM_Or_XML_DOM_SAX_SAXEvent p = new FI_DOM_Or_XML_DOM_SAX_SAXEvent();
/* 73 */     p.parse(args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\FI_DOM_Or_XML_DOM_SAX_SAXEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */