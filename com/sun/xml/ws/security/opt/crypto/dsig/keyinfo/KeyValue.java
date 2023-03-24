/*     */ package com.sun.xml.ws.security.opt.crypto.dsig.keyinfo;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.KeyValueType;
/*     */ import java.math.BigInteger;
/*     */ import java.security.KeyException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.DSAPublicKeySpec;
/*     */ import java.security.spec.RSAPublicKeySpec;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "KeyValue", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*     */ public class KeyValue
/*     */   extends KeyValueType
/*     */   implements KeyValue
/*     */ {
/*     */   public PublicKey getPublicKey() throws KeyException {
/*  70 */     PublicKey publicKey = null;
/*  71 */     for (Object o : this.content) {
/*  72 */       if (o instanceof DSAKeyValue) {
/*  73 */         DSAKeyValue dsaKeyValue = (DSAKeyValue)o;
/*     */         
/*  75 */         DSAPublicKeySpec spec = new DSAPublicKeySpec(new BigInteger(dsaKeyValue.getY()), new BigInteger(dsaKeyValue.getP()), new BigInteger(dsaKeyValue.getQ()), new BigInteger(dsaKeyValue.getG()));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  81 */           KeyFactory fac = KeyFactory.getInstance("DSA");
/*  82 */           return fac.generatePublic(spec);
/*  83 */         } catch (Exception ex) {
/*  84 */           throw new KeyException(ex);
/*     */         } 
/*  86 */       }  if (o instanceof RSAKeyValue) {
/*  87 */         RSAKeyValue rsaKayValue = (RSAKeyValue)o;
/*     */         
/*  89 */         RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger(rsaKayValue.getModulus()), new BigInteger(rsaKayValue.getExponent()));
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  94 */           KeyFactory fac = KeyFactory.getInstance("RSA");
/*  95 */           return fac.generatePublic(spec);
/*  96 */         } catch (Exception ex) {
/*  97 */           throw new KeyException(ex);
/*     */         } 
/*     */       } 
/*     */     } 
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isFeatureSupported(String string) {
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   public void setContent(List<Object> content) {
/* 109 */     this.content = content;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\keyinfo\KeyValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */