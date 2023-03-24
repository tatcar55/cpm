/*     */ package com.sun.xml.ws.security.opt.impl.enc;
/*     */ 
/*     */ import com.sun.xml.security.core.xenc.EncryptedKeyType;
/*     */ import com.sun.xml.security.core.xenc.ReferenceList;
/*     */ import com.sun.xml.security.core.xenc.ReferenceType;
/*     */ import com.sun.xml.ws.security.opt.api.EncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.message.ETHandler;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.WSSElementFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.keyinfo.KeyInfoStrategy;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.crypto.LogStringsMessages;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.Key;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
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
/*     */ public class EncryptionProcessor
/*     */ {
/*  86 */   private static byte[] crlf = null;
/*  87 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.crypto", "com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*     */   
/*     */   static {
/*     */     try {
/*  91 */       crlf = "\r\n".getBytes("US-ASCII");
/*  92 */     } catch (UnsupportedEncodingException ue) {
/*     */       
/*  94 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1917_CRLF_INIT_FAILED(), ue);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(JAXBFilterProcessingContext context) throws XWSSecurityException {
/*     */     EncryptionPolicy encryptionPolicy;
/* 106 */     boolean ekRefList = false;
/* 107 */     String referenceType = null;
/* 108 */     String x509TokenId = null;
/* 109 */     WSSElementFactory elementFactory = new WSSElementFactory(context.getSOAPVersion());
/* 110 */     X509Certificate _x509Cert = null;
/* 111 */     KeyInfoStrategy keyInfoStrategy = null;
/* 112 */     String symmetricKeyName = null;
/* 113 */     AuthenticationTokenPolicy.X509CertificateBinding certificateBinding = null;
/* 114 */     ((NamespaceContextEx)context.getNamespaceContext()).addEncryptionNS();
/* 115 */     ((NamespaceContextEx)context.getNamespaceContext()).addSignatureNS();
/* 116 */     ReferenceList dataRefList = null;
/* 117 */     EncryptedKeyType ekt = null;
/* 118 */     WSSPolicy wssPolicy = (WSSPolicy)context.getSecurityPolicy();
/* 119 */     EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)wssPolicy.getFeatureBinding();
/* 120 */     WSSPolicy keyBinding = (WSSPolicy)wssPolicy.getKeyBinding();
/* 121 */     EncryptedKey ek = null;
/* 122 */     KeyInfo edKeyInfo = null;
/*     */ 
/*     */     
/* 125 */     if (logger.isLoggable(Level.FINEST)) {
/* 126 */       logger.log(Level.FINEST, LogStringsMessages.WSS_1952_ENCRYPTION_KEYBINDING_VALUE(keyBinding));
/*     */     }
/*     */     
/* 129 */     if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)keyBinding)) {
/* 130 */       DerivedTokenKeyBinding dtk = (DerivedTokenKeyBinding)keyBinding.clone();
/* 131 */       WSSPolicy originalKeyBinding = dtk.getOriginalKeyBinding();
/*     */       
/* 133 */       if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)originalKeyBinding)) {
/* 134 */         AuthenticationTokenPolicy.X509CertificateBinding ckBindingClone = (AuthenticationTokenPolicy.X509CertificateBinding)originalKeyBinding.clone();
/*     */ 
/*     */         
/* 137 */         SymmetricKeyBinding skb = new SymmetricKeyBinding();
/* 138 */         skb.setKeyBinding((MLSPolicy)ckBindingClone);
/*     */         
/* 140 */         dtk.setOriginalKeyBinding((WSSPolicy)skb);
/*     */         
/* 142 */         EncryptionPolicy ep = (EncryptionPolicy)wssPolicy.clone();
/* 143 */         ep.setKeyBinding((MLSPolicy)dtk);
/* 144 */         context.setSecurityPolicy((SecurityPolicy)ep);
/* 145 */         encryptionPolicy = ep;
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     TokenProcessor tp = new TokenProcessor(encryptionPolicy, context);
/* 150 */     BuilderResult tokenInfo = tp.process();
/* 151 */     Key dataEncKey = null;
/* 152 */     Key dkEncKey = null;
/* 153 */     dataEncKey = tokenInfo.getDataProtectionKey();
/* 154 */     ek = tokenInfo.getEncryptedKey();
/* 155 */     ArrayList targets = featureBinding.getTargetBindings();
/* 156 */     Iterator<EncryptionTarget> targetItr = targets.iterator();
/*     */     
/* 158 */     ETHandler edBuilder = new ETHandler(context.getSOAPVersion());
/* 159 */     EncryptionPolicy.FeatureBinding binding = (EncryptionPolicy.FeatureBinding)encryptionPolicy.getFeatureBinding();
/* 160 */     dataRefList = new ReferenceList();
/*     */     
/* 162 */     if (ek == null || binding.getUseStandAloneRefList()) {
/* 163 */       edKeyInfo = tokenInfo.getKeyInfo();
/*     */     }
/*     */     
/* 166 */     boolean refAdded = false;
/* 167 */     while (targetItr.hasNext()) {
/* 168 */       EncryptionTarget target = targetItr.next();
/* 169 */       boolean contentOnly = target.getContentOnly();
/*     */ 
/*     */ 
/*     */       
/* 173 */       List<SecurityElement> edList = edBuilder.buildEDList(encryptionPolicy, (Target)target, context, dataEncKey, edKeyInfo);
/* 174 */       for (int i = 0; i < edList.size(); i++) {
/* 175 */         JAXBElement<ReferenceType> rt = elementFactory.createDataReference(edList.get(i));
/* 176 */         dataRefList.getDataReferenceOrKeyReference().add(rt);
/*     */         
/* 178 */         refAdded = true;
/*     */       } 
/*     */     } 
/* 181 */     if (refAdded) {
/* 182 */       if (ek == null || binding.getUseStandAloneRefList()) {
/* 183 */         context.getSecurityHeader().add((SecurityHeaderElement)elementFactory.createGSHeaderElement(dataRefList));
/*     */       } else {
/* 185 */         ek.setReferenceList(dataRefList);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkBSP5607(String elemName, String uri, boolean contentOnly) throws XWSSecurityException {
/* 198 */     if (!contentOnly && ("http://schemas.xmlsoap.org/soap/envelope/".equalsIgnoreCase(uri) || "http://www.w3.org/2003/05/soap-envelope".equalsIgnoreCase(uri)) && ("Header".equalsIgnoreCase(elemName) || "Envelope".equalsIgnoreCase(elemName) || "Body".equalsIgnoreCase(elemName))) {
/*     */       
/* 200 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1918_ILLEGAL_ENCRYPTION_TARGET(uri, elemName));
/* 201 */       throw new XWSSecurityException("Encryption of SOAP " + elemName + " is not allowed");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\enc\EncryptionProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */