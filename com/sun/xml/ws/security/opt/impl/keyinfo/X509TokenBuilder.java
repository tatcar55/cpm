/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BinarySecurityToken;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.SSEData;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.DirectReference;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.X509Data;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.X509IssuerSerial;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.token.LogStringsMessages;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
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
/*     */ public class X509TokenBuilder
/*     */   extends TokenBuilder
/*     */ {
/*  67 */   AuthenticationTokenPolicy.X509CertificateBinding binding = null;
/*     */ 
/*     */   
/*     */   public X509TokenBuilder(JAXBFilterProcessingContext context, AuthenticationTokenPolicy.X509CertificateBinding binding) {
/*  71 */     super(context);
/*  72 */     this.binding = binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderResult process() throws XWSSecurityException {
/*  83 */     String x509id = this.binding.getUUID();
/*  84 */     if (x509id == null || x509id.equals("")) {
/*  85 */       x509id = this.context.generateID();
/*     */     }
/*  87 */     SecurityUtil.checkIncludeTokenPolicyOpt(this.context, this.binding, x509id);
/*     */     
/*  89 */     String referenceType = this.binding.getReferenceType();
/*  90 */     if (logger.isLoggable(Level.FINEST)) {
/*  91 */       logger.log(Level.FINEST, LogStringsMessages.WSS_1851_REFERENCETYPE_X_509_TOKEN(referenceType));
/*     */     }
/*  93 */     BuilderResult result = new BuilderResult();
/*  94 */     if (referenceType.equals("Direct")) {
/*  95 */       BinarySecurityToken bst = createBinarySecurityToken(this.binding, this.binding.getX509Certificate());
/*  96 */       if (bst == null) {
/*  97 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1802_WRONG_TOKENINCLUSION_POLICY(), "creating binary security token failed");
/*  98 */         throw new XWSSecurityException(LogStringsMessages.WSS_1802_WRONG_TOKENINCLUSION_POLICY());
/*     */       } 
/* 100 */       DirectReference directReference = buildDirectReference(bst.getId(), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
/* 101 */       buildKeyInfo((Reference)directReference, this.binding.getSTRID());
/* 102 */     } else if (referenceType.equals("Identifier")) {
/* 103 */       BinarySecurityToken bst = createBinarySecurityToken(this.binding, this.binding.getX509Certificate());
/* 104 */       buildKeyInfoWithKI(this.binding, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier");
/*     */       try {
/* 106 */         if (this.binding.getSTRID() != null) {
/* 107 */           BinarySecurityToken binarySecurityToken = this.elementFactory.createBinarySecurityToken(null, this.binding.getX509Certificate().getEncoded());
/* 108 */           SSEData data = new SSEData((SecurityElement)binarySecurityToken, false, this.context.getNamespaceContext());
/* 109 */           this.context.getSTRTransformCache().put(this.binding.getSTRID(), data);
/*     */         } 
/* 111 */       } catch (CertificateEncodingException ce) {
/* 112 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1814_ERROR_ENCODING_CERTIFICATE(), ce);
/* 113 */         throw new XWSSecurityException(LogStringsMessages.WSS_1814_ERROR_ENCODING_CERTIFICATE(), ce);
/*     */       } 
/* 115 */     } else if (referenceType.equals("Thumbprint")) {
/* 116 */       BinarySecurityToken bst = createBinarySecurityToken(this.binding, this.binding.getX509Certificate());
/* 117 */       KeyIdentifier ki = buildKeyInfoWithKI(this.binding, "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1");
/*     */       try {
/* 119 */         if (this.binding.getSTRID() != null) {
/* 120 */           BinarySecurityToken binarySecurityToken = this.elementFactory.createBinarySecurityToken(null, this.binding.getX509Certificate().getEncoded());
/* 121 */           SSEData data = new SSEData((SecurityElement)binarySecurityToken, false, this.context.getNamespaceContext());
/* 122 */           this.context.getSTRTransformCache().put(this.binding.getSTRID(), data);
/*     */         } 
/* 124 */       } catch (CertificateEncodingException ce) {
/* 125 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1814_ERROR_ENCODING_CERTIFICATE(), ce);
/* 126 */         throw new XWSSecurityException(LogStringsMessages.WSS_1814_ERROR_ENCODING_CERTIFICATE(), ce);
/*     */       } 
/* 128 */     } else if (referenceType.equals("IssuerSerialNumber")) {
/* 129 */       BinarySecurityToken bst = createBinarySecurityToken(this.binding, this.binding.getX509Certificate());
/* 130 */       X509Certificate xCert = this.binding.getX509Certificate();
/* 131 */       X509IssuerSerial xis = this.elementFactory.createX509IssuerSerial(xCert.getIssuerDN().getName(), xCert.getSerialNumber());
/* 132 */       X509Data x509Data = this.elementFactory.createX509DataWithIssuerSerial(xis);
/* 133 */       buildKeyInfo((Reference)x509Data, this.binding.getSTRID());
/*     */       try {
/* 135 */         if (this.binding.getSTRID() != null) {
/* 136 */           BinarySecurityToken binarySecurityToken = this.elementFactory.createBinarySecurityToken(null, this.binding.getX509Certificate().getEncoded());
/* 137 */           SSEData data = new SSEData((SecurityElement)binarySecurityToken, false, this.context.getNamespaceContext());
/* 138 */           this.context.getSTRTransformCache().put(this.binding.getSTRID(), data);
/*     */         } 
/* 140 */       } catch (CertificateEncodingException ce) {
/* 141 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1814_ERROR_ENCODING_CERTIFICATE(), ce);
/* 142 */         throw new XWSSecurityException(LogStringsMessages.WSS_1814_ERROR_ENCODING_CERTIFICATE(), ce);
/*     */       } 
/*     */     } else {
/* 145 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1803_UNSUPPORTED_REFERENCE_TYPE(referenceType));
/* 146 */       throw new XWSSecurityException(LogStringsMessages.WSS_1803_UNSUPPORTED_REFERENCE_TYPE(referenceType));
/*     */     } 
/* 148 */     result.setKeyInfo(this.keyInfo);
/* 149 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\X509TokenBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */