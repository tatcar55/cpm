/*    */ package com.sun.xml.fastinfoset.tools;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import javax.xml.transform.Result;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerFactory;
/*    */ import javax.xml.transform.dom.DOMSource;
/*    */ import org.jvnet.fastinfoset.FastInfosetResult;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XML_DOM_SAX_FI
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream document, OutputStream finf, String workingDirectory) throws Exception {
/* 38 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 39 */     dbf.setNamespaceAware(true);
/* 40 */     DocumentBuilder db = dbf.newDocumentBuilder();
/* 41 */     if (workingDirectory != null) {
/* 42 */       db.setEntityResolver(createRelativePathResolver(workingDirectory));
/*    */     }
/* 44 */     Document d = db.parse(document);
/*    */     
/* 46 */     TransformerFactory tf = TransformerFactory.newInstance();
/* 47 */     Transformer t = tf.newTransformer();
/* 48 */     t.transform(new DOMSource(d), (Result)new FastInfosetResult(finf));
/*    */   }
/*    */   
/*    */   public void parse(InputStream document, OutputStream finf) throws Exception {
/* 52 */     parse(document, finf, (String)null);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 56 */     XML_DOM_SAX_FI p = new XML_DOM_SAX_FI();
/* 57 */     p.parse(args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\XML_DOM_SAX_FI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */