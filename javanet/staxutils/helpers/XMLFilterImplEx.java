/*    */ package javanet.staxutils.helpers;
/*    */ 
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.ext.LexicalHandler;
/*    */ import org.xml.sax.helpers.XMLFilterImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLFilterImplEx
/*    */   extends XMLFilterImpl
/*    */   implements LexicalHandler
/*    */ {
/*    */   protected LexicalHandler lexicalHandler;
/*    */   protected boolean namespacePrefixes;
/*    */   
/*    */   public void setNamespacePrefixes(boolean v) {
/* 19 */     this.namespacePrefixes = v;
/*    */   }
/*    */   
/*    */   public boolean getNamespacePrefixes() {
/* 23 */     return this.namespacePrefixes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLexicalHandler(LexicalHandler handler) {
/* 32 */     this.lexicalHandler = handler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LexicalHandler getLexicalHandler() {
/* 41 */     return this.lexicalHandler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startDTD(String name, String publicId, String systemId) throws SAXException {
/* 51 */     if (this.lexicalHandler != null) {
/* 52 */       this.lexicalHandler.startDTD(name, publicId, systemId);
/*    */     }
/*    */   }
/*    */   
/*    */   public void endDTD() throws SAXException {
/* 57 */     if (this.lexicalHandler != null) {
/* 58 */       this.lexicalHandler.endDTD();
/*    */     }
/*    */   }
/*    */   
/*    */   public void startEntity(String name) throws SAXException {
/* 63 */     if (this.lexicalHandler != null) {
/* 64 */       this.lexicalHandler.startEntity(name);
/*    */     }
/*    */   }
/*    */   
/*    */   public void endEntity(String name) throws SAXException {
/* 69 */     if (this.lexicalHandler != null) {
/* 70 */       this.lexicalHandler.endEntity(name);
/*    */     }
/*    */   }
/*    */   
/*    */   public void startCDATA() throws SAXException {
/* 75 */     if (this.lexicalHandler != null) {
/* 76 */       this.lexicalHandler.startCDATA();
/*    */     }
/*    */   }
/*    */   
/*    */   public void endCDATA() throws SAXException {
/* 81 */     if (this.lexicalHandler != null) {
/* 82 */       this.lexicalHandler.endCDATA();
/*    */     }
/*    */   }
/*    */   
/*    */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 87 */     if (this.lexicalHandler != null)
/* 88 */       this.lexicalHandler.comment(ch, start, length); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\helpers\XMLFilterImplEx.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */