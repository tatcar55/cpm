/*    */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*    */ 
/*    */ import com.sun.xml.security.core.dsig.SignatureValueType;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.crypto.dsig.XMLSignature;
/*    */ import javax.xml.crypto.dsig.XMLSignatureException;
/*    */ import javax.xml.crypto.dsig.XMLValidateContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "SignatureValue", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*    */ public class SignatureValue
/*    */   extends SignatureValueType
/*    */   implements XMLSignature.SignatureValue
/*    */ {
/*    */   public boolean validate(XMLValidateContext xMLValidateContext) throws XMLSignatureException {
/* 68 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isFeatureSupported(String string) {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\SignatureValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */