/*    */ package org.jvnet.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.fastinfoset.sax.SAXDocumentSerializer;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.transform.sax.SAXResult;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.ext.LexicalHandler;
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
/*    */ public class FastInfosetResult
/*    */   extends SAXResult
/*    */ {
/*    */   OutputStream _outputStream;
/*    */   
/*    */   public FastInfosetResult(OutputStream outputStream) {
/* 50 */     this._outputStream = outputStream;
/*    */   }
/*    */   public ContentHandler getHandler() {
/*    */     SAXDocumentSerializer sAXDocumentSerializer;
/* 54 */     ContentHandler handler = super.getHandler();
/* 55 */     if (handler == null) {
/* 56 */       sAXDocumentSerializer = new SAXDocumentSerializer();
/* 57 */       setHandler((ContentHandler)sAXDocumentSerializer);
/*    */     } 
/* 59 */     sAXDocumentSerializer.setOutputStream(this._outputStream);
/* 60 */     return (ContentHandler)sAXDocumentSerializer;
/*    */   }
/*    */   
/*    */   public LexicalHandler getLexicalHandler() {
/* 64 */     return (LexicalHandler)getHandler();
/*    */   }
/*    */   
/*    */   public OutputStream getOutputStream() {
/* 68 */     return this._outputStream;
/*    */   }
/*    */   
/*    */   public void setOutputStream(OutputStream outputStream) {
/* 72 */     this._outputStream = outputStream;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\FastInfosetResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */