/*    */ package org.jvnet.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.sax.SAXDocumentParser;
/*    */ import java.io.InputStream;
/*    */ import javax.xml.transform.sax.SAXSource;
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
/*    */ public class FastInfosetSource
/*    */   extends SAXSource
/*    */ {
/*    */   public FastInfosetSource(InputStream inputStream) {
/* 55 */     super(new InputSource(inputStream));
/*    */   }
/*    */   public XMLReader getXMLReader() {
/*    */     SAXDocumentParser sAXDocumentParser;
/* 59 */     XMLReader reader = super.getXMLReader();
/* 60 */     if (reader == null) {
/* 61 */       sAXDocumentParser = new SAXDocumentParser();
/* 62 */       setXMLReader((XMLReader)sAXDocumentParser);
/*    */     } 
/* 64 */     sAXDocumentParser.setInputStream(getInputStream());
/* 65 */     return (XMLReader)sAXDocumentParser;
/*    */   }
/*    */   
/*    */   public InputStream getInputStream() {
/* 69 */     return getInputSource().getByteStream();
/*    */   }
/*    */   
/*    */   public void setInputStream(InputStream inputStream) {
/* 73 */     setInputSource(new InputSource(inputStream));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\FastInfosetSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */