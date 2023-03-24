/*    */ package com.sun.xml.messaging.saaj.soap.impl;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*    */ import java.util.logging.Level;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Name;
/*    */ import javax.xml.soap.SOAPElement;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPFaultElement;
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
/*    */ public abstract class FaultElementImpl
/*    */   extends ElementImpl
/*    */   implements SOAPFaultElement
/*    */ {
/*    */   protected FaultElementImpl(SOAPDocumentImpl ownerDoc, NameImpl qname) {
/* 58 */     super(ownerDoc, (Name)qname);
/*    */   }
/*    */   
/*    */   protected FaultElementImpl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 62 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   protected abstract boolean isStandardFaultElement();
/*    */   
/*    */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 68 */     log.log(Level.SEVERE, "SAAJ0146.impl.invalid.name.change.requested", new Object[] { this.elementQName.getLocalPart(), newName.getLocalPart() });
/*    */ 
/*    */ 
/*    */     
/* 72 */     throw new SOAPException("Cannot change name for " + this.elementQName.getLocalPart() + " to " + newName.getLocalPart());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\FaultElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */