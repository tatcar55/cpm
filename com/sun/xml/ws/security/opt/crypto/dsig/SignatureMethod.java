/*    */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*    */ 
/*    */ import com.sun.xml.security.core.dsig.SignatureMethodType;
/*    */ import java.security.spec.AlgorithmParameterSpec;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlTransient;
/*    */ import javax.xml.crypto.dsig.SignatureMethod;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "SignatureMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*    */ public class SignatureMethod
/*    */   extends SignatureMethodType
/*    */   implements SignatureMethod
/*    */ {
/*    */   @XmlTransient
/* 61 */   private AlgorithmParameterSpec algSpec = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AlgorithmParameterSpec getParameterSpec() {
/* 67 */     return this.algSpec;
/*    */   }
/*    */   
/*    */   public void setParameter(AlgorithmParameterSpec algSpec) {
/* 71 */     this.algSpec = algSpec;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFeatureSupported(String string) {
/* 76 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\SignatureMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */