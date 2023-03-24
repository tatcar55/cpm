/*    */ package com.sun.xml.ws.security.opt.crypto.dsig.keyinfo;
/*    */ 
/*    */ import com.sun.xml.security.core.dsig.X509IssuerSerialType;
/*    */ import java.math.BigInteger;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "X509IssuerSerial", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*    */ public class X509IssuerSerial
/*    */   extends X509IssuerSerialType
/*    */   implements X509IssuerSerial
/*    */ {
/*    */   public String getIssuerName() {
/* 64 */     return this.x509IssuerName;
/*    */   }
/*    */   
/*    */   public BigInteger getSerialNumber() {
/* 68 */     return this.x509SerialNumber;
/*    */   }
/*    */   
/*    */   public boolean isFeatureSupported(String string) {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\keyinfo\X509IssuerSerial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */