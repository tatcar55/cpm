/*    */ package com.sun.xml.messaging.saaj.soap.dynamic;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPFactoryImpl;
/*    */ import javax.xml.soap.Detail;
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
/*    */ public class SOAPFactoryDynamicImpl
/*    */   extends SOAPFactoryImpl
/*    */ {
/*    */   protected SOAPDocumentImpl createDocument() {
/* 55 */     return null;
/*    */   }
/*    */   
/*    */   public Detail createDetail() throws SOAPException {
/* 59 */     throw new UnsupportedOperationException("createDetail() not supported for Dynamic Protocol");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\dynamic\SOAPFactoryDynamicImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */