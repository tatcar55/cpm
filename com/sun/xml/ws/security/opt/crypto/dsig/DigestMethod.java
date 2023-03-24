/*    */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*    */ 
/*    */ import com.sun.xml.security.core.dsig.DigestMethodType;
/*    */ import java.security.spec.AlgorithmParameterSpec;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlTransient;
/*    */ import javax.xml.crypto.dsig.DigestMethod;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "DigestMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*    */ public class DigestMethod
/*    */   extends DigestMethodType
/*    */   implements DigestMethod
/*    */ {
/*    */   @XmlTransient
/* 60 */   private AlgorithmParameterSpec algSpec = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setParameterSpec(AlgorithmParameterSpec algSpec) {
/* 67 */     this.algSpec = algSpec;
/*    */   }
/*    */   
/*    */   public AlgorithmParameterSpec getParameterSpec() {
/* 71 */     return this.algSpec;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFeatureSupported(String string) {
/* 76 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\DigestMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */