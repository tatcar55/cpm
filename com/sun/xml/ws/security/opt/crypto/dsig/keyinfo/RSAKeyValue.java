/*    */ package com.sun.xml.ws.security.opt.crypto.dsig.keyinfo;
/*    */ 
/*    */ import com.sun.xml.security.core.dsig.RSAKeyValueType;
/*    */ import java.security.PublicKey;
/*    */ import java.security.interfaces.RSAPublicKey;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "RSAKeyValue", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*    */ public class RSAKeyValue
/*    */   extends RSAKeyValueType
/*    */ {
/*    */   public RSAKeyValue() {}
/*    */   
/*    */   public RSAKeyValue(PublicKey pubKey) {
/* 69 */     setExponent(((RSAPublicKey)pubKey).getPublicExponent().toByteArray());
/* 70 */     setModulus(((RSAPublicKey)pubKey).getModulus().toByteArray());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\keyinfo\RSAKeyValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */