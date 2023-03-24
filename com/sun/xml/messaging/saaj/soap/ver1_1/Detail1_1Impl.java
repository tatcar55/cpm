/*    */ package com.sun.xml.messaging.saaj.soap.ver1_1;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.impl.DetailImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.DetailEntry;
/*    */ import javax.xml.soap.Name;
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
/*    */ 
/*    */ public class Detail1_1Impl
/*    */   extends DetailImpl
/*    */ {
/*    */   public Detail1_1Impl(SOAPDocumentImpl ownerDoc, String prefix) {
/* 58 */     super(ownerDoc, NameImpl.createDetail1_1Name(prefix));
/*    */   }
/*    */   public Detail1_1Impl(SOAPDocumentImpl ownerDoc) {
/* 61 */     super(ownerDoc, NameImpl.createDetail1_1Name());
/*    */   }
/*    */   protected DetailEntry createDetailEntry(Name name) {
/* 64 */     return (DetailEntry)new DetailEntry1_1Impl((SOAPDocumentImpl)getOwnerDocument(), name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected DetailEntry createDetailEntry(QName name) {
/* 69 */     return (DetailEntry)new DetailEntry1_1Impl((SOAPDocumentImpl)getOwnerDocument(), name);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_1\Detail1_1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */