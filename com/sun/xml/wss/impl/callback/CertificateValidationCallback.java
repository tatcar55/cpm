/*     */ package com.sun.xml.wss.impl.callback;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.callback.Callback;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateValidationCallback
/*     */   extends XWSSCallback
/*     */   implements Callback
/*     */ {
/*     */   private boolean result = false;
/*     */   private CertificateValidator validator;
/*     */   private X509Certificate certificate;
/*     */   private boolean revocationEnabled = false;
/*     */   
/*     */   public CertificateValidationCallback(X509Certificate certificate) {
/*  73 */     this.certificate = certificate;
/*     */   }
/*     */   
/*     */   public CertificateValidationCallback(X509Certificate certificate, Map context) {
/*  77 */     this.certificate = certificate;
/*  78 */     this.runtimeProperties = context;
/*     */   }
/*     */   
/*     */   public boolean getResult() {
/*     */     try {
/*  83 */       if (this.validator != null) {
/*  84 */         if (this.validator instanceof ValidatorExtension) {
/*  85 */           ((ValidatorExtension)this.validator).setRuntimeProperties(this.runtimeProperties);
/*     */         }
/*  87 */         this.result = this.validator.validate(this.certificate);
/*     */       } 
/*  89 */     } catch (CertificateValidationException ex) {
/*  90 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, ex.getMessage(), ex, true);
/*     */     }
/*  92 */     catch (Exception e) {
/*  93 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e);
/*     */     } 
/*     */     
/*  96 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValidator(CertificateValidator validator) {
/* 103 */     this.validator = validator;
/* 104 */     if (this.validator instanceof ValidatorExtension) {
/* 105 */       ((ValidatorExtension)this.validator).setRuntimeProperties(getRuntimeProperties());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isRevocationEnabled() {
/* 110 */     return this.revocationEnabled;
/*     */   }
/*     */   
/*     */   public void setRevocationEnabled(boolean revocationEnabled) {
/* 114 */     this.revocationEnabled = revocationEnabled;
/*     */   }
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
/*     */   public static class CertificateValidationException
/*     */     extends Exception
/*     */   {
/*     */     public CertificateValidationException(String message) {
/* 133 */       super(message);
/*     */     }
/*     */     
/*     */     public CertificateValidationException(String message, Throwable cause) {
/* 137 */       super(message, cause);
/*     */     }
/*     */     
/*     */     public CertificateValidationException(Throwable cause) {
/* 141 */       super(cause);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface CertificateValidator {
/*     */     boolean validate(X509Certificate param1X509Certificate) throws CertificateValidationCallback.CertificateValidationException;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\CertificateValidationCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */