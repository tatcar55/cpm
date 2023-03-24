/*    */ package com.sun.xml.messaging.saaj.soap.ver1_1;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.impl.ElementImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.impl.FaultElementImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class FaultElement1_1Impl
/*    */   extends FaultElementImpl
/*    */ {
/*    */   public FaultElement1_1Impl(SOAPDocumentImpl ownerDoc, NameImpl qname) {
/* 58 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   public FaultElement1_1Impl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 62 */     super(ownerDoc, qname);
/*    */   }
/*    */ 
/*    */   
/*    */   public FaultElement1_1Impl(SOAPDocumentImpl ownerDoc, String localName) {
/* 67 */     super(ownerDoc, NameImpl.createFaultElement1_1Name(localName));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FaultElement1_1Impl(SOAPDocumentImpl ownerDoc, String localName, String prefix) {
/* 73 */     super(ownerDoc, NameImpl.createFaultElement1_1Name(localName, prefix));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isStandardFaultElement() {
/* 78 */     String localName = this.elementQName.getLocalPart();
/* 79 */     if (localName.equalsIgnoreCase("faultcode") || localName.equalsIgnoreCase("faultstring") || localName.equalsIgnoreCase("faultactor"))
/*    */     {
/*    */       
/* 82 */       return true;
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */   
/*    */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 88 */     if (!isStandardFaultElement()) {
/* 89 */       FaultElement1_1Impl copy = new FaultElement1_1Impl((SOAPDocumentImpl)getOwnerDocument(), newName);
/*    */       
/* 91 */       return replaceElementWithSOAPElement((Element)this, (ElementImpl)copy);
/*    */     } 
/* 93 */     return super.setElementQName(newName);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_1\FaultElement1_1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */