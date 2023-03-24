/*    */ package com.sun.xml.messaging.saaj.soap.ver1_1;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.impl.BodyElementImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.impl.ElementImpl;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Name;
/*    */ import javax.xml.soap.SOAPElement;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import org.w3c.dom.Element;
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
/*    */ public class BodyElement1_1Impl
/*    */   extends BodyElementImpl
/*    */ {
/*    */   public BodyElement1_1Impl(SOAPDocumentImpl ownerDoc, Name qname) {
/* 58 */     super(ownerDoc, qname);
/*    */   }
/*    */   public BodyElement1_1Impl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 61 */     super(ownerDoc, qname);
/*    */   }
/*    */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 64 */     BodyElementImpl copy = new BodyElement1_1Impl((SOAPDocumentImpl)getOwnerDocument(), newName);
/*    */     
/* 66 */     return replaceElementWithSOAPElement((Element)this, (ElementImpl)copy);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_1\BodyElement1_1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */