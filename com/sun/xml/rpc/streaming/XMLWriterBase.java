/*    */ package com.sun.xml.rpc.streaming;
/*    */ 
/*    */ import com.sun.xml.rpc.util.xml.CDATA;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public abstract class XMLWriterBase
/*    */   implements XMLWriter
/*    */ {
/*    */   public void startElement(String localName) {
/* 43 */     startElement(localName, "");
/*    */   }
/*    */   
/*    */   public void startElement(QName name) {
/* 47 */     startElement(name.getLocalPart(), name.getNamespaceURI());
/*    */   }
/*    */   
/*    */   public void startElement(QName name, String prefix) {
/* 51 */     startElement(name.getLocalPart(), name.getNamespaceURI(), prefix);
/*    */   }
/*    */   
/*    */   public void writeAttribute(String localName, String value) {
/* 55 */     writeAttribute(localName, "", value);
/*    */   }
/*    */   
/*    */   public void writeAttribute(QName name, String value) {
/* 59 */     writeAttribute(name.getLocalPart(), name.getNamespaceURI(), value);
/*    */   }
/*    */   
/*    */   public void writeAttributeUnquoted(QName name, String value) {
/* 63 */     writeAttributeUnquoted(name.getLocalPart(), name.getNamespaceURI(), value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeAttributeUnquoted(String localName, String value) {
/* 70 */     writeAttributeUnquoted(localName, "", value);
/*    */   }
/*    */   
/*    */   public abstract void writeChars(CDATA paramCDATA);
/*    */   
/*    */   public abstract void writeChars(String paramString);
/*    */   
/*    */   public void writeComment(String comment) {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLWriterBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */