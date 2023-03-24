/*    */ package com.sun.xml.fastinfoset.tools;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.dom.DOMDocumentSerializer;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
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
/*    */ public class XML_DOM_FI
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream document, OutputStream finf, String workingDirectory) throws Exception {
/* 33 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 34 */     dbf.setNamespaceAware(true);
/* 35 */     DocumentBuilder db = dbf.newDocumentBuilder();
/* 36 */     if (workingDirectory != null) {
/* 37 */       db.setEntityResolver(createRelativePathResolver(workingDirectory));
/*    */     }
/* 39 */     Document d = db.parse(document);
/*    */     
/* 41 */     DOMDocumentSerializer s = new DOMDocumentSerializer();
/* 42 */     s.setOutputStream(finf);
/* 43 */     s.serialize(d);
/*    */   }
/*    */   
/*    */   public void parse(InputStream document, OutputStream finf) throws Exception {
/* 47 */     parse(document, finf, (String)null);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 51 */     XML_DOM_FI p = new XML_DOM_FI();
/* 52 */     p.parse(args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\XML_DOM_FI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */