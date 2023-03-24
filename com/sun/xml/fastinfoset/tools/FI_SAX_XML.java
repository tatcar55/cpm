/*    */ package com.sun.xml.fastinfoset.tools;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.transform.Source;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerFactory;
/*    */ import javax.xml.transform.stream.StreamResult;
/*    */ import org.jvnet.fastinfoset.FastInfosetSource;
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
/*    */ public class FI_SAX_XML
/*    */   extends TransformInputOutput
/*    */ {
/*    */   public void parse(InputStream finf, OutputStream xml) throws Exception {
/* 33 */     Transformer tx = TransformerFactory.newInstance().newTransformer();
/* 34 */     tx.transform((Source)new FastInfosetSource(finf), new StreamResult(xml));
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 38 */     FI_SAX_XML p = new FI_SAX_XML();
/* 39 */     p.parse(args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\FI_SAX_XML.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */