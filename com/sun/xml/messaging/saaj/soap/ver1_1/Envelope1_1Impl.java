/*    */ package com.sun.xml.messaging.saaj.soap.ver1_1;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.impl.EnvelopeImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*    */ import javax.xml.soap.Name;
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
/*    */ public class Envelope1_1Impl
/*    */   extends EnvelopeImpl
/*    */ {
/*    */   public Envelope1_1Impl(SOAPDocumentImpl ownerDoc, String prefix) {
/* 56 */     super(ownerDoc, (Name)NameImpl.createEnvelope1_1Name(prefix));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Envelope1_1Impl(SOAPDocumentImpl ownerDoc, String prefix, boolean createHeader, boolean createBody) throws SOAPException {
/* 64 */     super(ownerDoc, NameImpl.createEnvelope1_1Name(prefix), createHeader, createBody);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected NameImpl getBodyName(String prefix) {
/* 71 */     return NameImpl.createBody1_1Name(prefix);
/*    */   }
/*    */   
/*    */   protected NameImpl getHeaderName(String prefix) {
/* 75 */     return NameImpl.createHeader1_1Name(prefix);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_1\Envelope1_1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */