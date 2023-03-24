/*    */ package com.sun.xml.messaging.saaj.soap.impl;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Name;
/*    */ import javax.xml.soap.SOAPBodyElement;
/*    */ import javax.xml.soap.SOAPElement;
/*    */ import javax.xml.soap.SOAPException;
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
/*    */ 
/*    */ public abstract class BodyElementImpl
/*    */   extends ElementImpl
/*    */   implements SOAPBodyElement
/*    */ {
/*    */   public BodyElementImpl(SOAPDocumentImpl ownerDoc, Name qname) {
/* 58 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   public BodyElementImpl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 62 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   public void setParentElement(SOAPElement element) throws SOAPException {
/* 66 */     if (!(element instanceof javax.xml.soap.SOAPBody)) {
/* 67 */       log.severe("SAAJ0101.impl.parent.of.body.elem.mustbe.body");
/* 68 */       throw new SOAPException("Parent of a SOAPBodyElement has to be a SOAPBody");
/*    */     } 
/* 70 */     super.setParentElement(element);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\BodyElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */