/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*     */ import com.sun.xml.ws.security.impl.DerivedKeyTokenImpl;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.DirectReference;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.AlgorithmSuite;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.token.LogStringsMessages;
/*     */ import java.security.Key;
/*     */ import java.util.logging.Level;
/*     */ import javax.crypto.SecretKey;
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
/*     */ public class DerivedKeyTokenBuilder
/*     */   extends TokenBuilder
/*     */ {
/*  74 */   private DerivedTokenKeyBinding dtk = null;
/*     */   
/*     */   public DerivedKeyTokenBuilder(JAXBFilterProcessingContext context, DerivedTokenKeyBinding dtk) {
/*  77 */     super(context);
/*  78 */     this.dtk = dtk;
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
/*  89 */     String algorithm = null;
/*  90 */     WSSPolicy originalKeyBinding = this.dtk.getOriginalKeyBinding();
/*  91 */     AlgorithmSuite algSuite = this.context.getAlgorithmSuite();
/*  92 */     BuilderResult dktResult = new BuilderResult();
/*     */     
/*  94 */     if (algSuite != null) {
/*  95 */       algorithm = algSuite.getEncryptionAlgorithm();
/*  96 */       if (logger.isLoggable(Level.FINEST)) {
/*  97 */         logger.log(Level.FINEST, "Algorithm used for Derived Keys: " + algorithm);
/*     */       }
/*     */     } else {
/* 100 */       throw new XWSSecurityException("Internal Error: Algorithm Suite is not set in context");
/*     */     } 
/*     */     
/* 103 */     long offset = 0L;
/* 104 */     long length = SecurityUtil.getLengthFromAlgorithm(algorithm);
/*     */     
/* 106 */     WSSPolicy policy = (WSSPolicy)this.context.getSecurityPolicy();
/* 107 */     if (length == 32L && PolicyTypeUtil.signaturePolicy((SecurityPolicy)policy)) {
/* 108 */       length = 24L;
/*     */     }
/* 110 */     String dpTokenID = "";
/* 111 */     byte[] secret = null;
/* 112 */     BuilderResult result = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)originalKeyBinding)) {
/* 120 */       AuthenticationTokenPolicy.UsernameTokenBinding utb; if (this.context.getusernameTokenBinding() != null) {
/* 121 */         utb = this.context.getusernameTokenBinding();
/* 122 */         this.context.setUsernameTokenBinding(null);
/*     */       } else {
/* 124 */         throw new XWSSecurityException("Internal Error: UsernameToken Binding not set on context");
/*     */       } 
/* 126 */       UsernameTokenBuilder br = new UsernameTokenBuilder(this.context, utb);
/* 127 */       result = br.process();
/* 128 */       SecretKey key = utb.getSecretKey();
/* 129 */       if (key == null) {
/* 130 */         throw new XWSSecurityException("Key obtained from the username token binding is null");
/*     */       }
/* 132 */       byte[] tempSecret = key.getEncoded();
/* 133 */       secret = new byte[16];
/* 134 */       for (int i = 0; i <= 15; i++) {
/* 135 */         secret[i] = tempSecret[i];
/*     */       }
/*     */     }
/* 138 */     else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)originalKeyBinding)) {
/*     */       
/* 140 */       SymmetricKeyBinding skb = null;
/* 141 */       if (this.context.getSymmetricKeyBinding() != null) {
/* 142 */         skb = this.context.getSymmetricKeyBinding();
/* 143 */         this.context.setSymmetricKeyBinding(null);
/*     */       } else {
/* 145 */         throw new XWSSecurityException("Internal Error: SymmetricBinding not set on context");
/*     */       } 
/* 147 */       String dataEncAlgo = SecurityUtil.getDataEncryptionAlgo(this.context);
/*     */       
/* 149 */       String keyAlgo = skb.getKeyAlgorithm();
/* 150 */       if ((keyAlgo == null || "".equals(keyAlgo)) && 
/* 151 */         this.context.getAlgorithmSuite() != null) {
/* 152 */         keyAlgo = this.context.getAlgorithmSuite().getAsymmetricKeyAlgorithm();
/*     */       }
/* 154 */       SymmetricTokenBuilder stb = new SymmetricTokenBuilder(skb, this.context, dataEncAlgo, keyAlgo);
/* 155 */       result = stb.process();
/* 156 */       Key originalKey = result.getDataProtectionKey();
/* 157 */       secret = originalKey.getEncoded();
/* 158 */       if (logger.isLoggable(Level.FINEST)) {
/* 159 */         logger.log(Level.FINEST, "SymmetricBinding under Derived Keys");
/* 160 */         logger.log(Level.FINEST, "DataEncryption Algorithm:" + dataEncAlgo);
/* 161 */         logger.log(Level.FINEST, "Key Algorithm:" + keyAlgo);
/*     */       } 
/* 163 */     } else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)originalKeyBinding)) {
/* 164 */       SecureConversationTokenKeyBinding skb = (SecureConversationTokenKeyBinding)originalKeyBinding;
/* 165 */       SCTBuilder builder = new SCTBuilder(this.context, (SecureConversationTokenKeyBinding)originalKeyBinding);
/* 166 */       result = builder.process();
/* 167 */       IssuedTokenContext ictx = this.context.getSecureConversationContext();
/* 168 */       SecurityContextToken sct = (SecurityContextToken)ictx.getSecurityToken();
/* 169 */       if (sct.getInstance() != null) {
/* 170 */         if (this.context.isExpired()) {
/* 171 */           secret = ictx.getProofKey();
/*     */         } else {
/* 173 */           SecurityContextTokenInfo sctInstanceInfo = ictx.getSecurityContextTokenInfo();
/*     */           
/* 175 */           if (sctInstanceInfo != null) {
/* 176 */             secret = sctInstanceInfo.getInstanceSecret(sct.getInstance());
/*     */           } else {
/* 178 */             secret = ictx.getProofKey();
/*     */           } 
/*     */         } 
/*     */       } else {
/* 182 */         secret = ictx.getProofKey();
/*     */       } 
/* 184 */       if (logger.isLoggable(Level.FINEST)) {
/* 185 */         logger.log(Level.FINEST, "SecureConversation token binding under Derived Keys");
/*     */       }
/* 187 */     } else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)originalKeyBinding)) {
/* 188 */       IssuedTokenBuilder itb = new IssuedTokenBuilder(this.context, (IssuedTokenKeyBinding)originalKeyBinding);
/* 189 */       result = itb.process();
/* 190 */       Key originalKey = result.getDataProtectionKey();
/*     */       
/* 192 */       if (this.context.getTrustContext().getProofKey() == null) {
/* 193 */         dktResult.setDataProtectionKey(originalKey);
/*     */         
/* 195 */         dktResult.setKeyInfo(result.getKeyInfo());
/* 196 */         return dktResult;
/*     */       } 
/*     */       
/* 199 */       secret = originalKey.getEncoded();
/* 200 */       dpTokenID = result.getDPTokenId();
/*     */       
/* 202 */       if (logger.isLoggable(Level.FINEST)) {
/* 203 */         logger.log(Level.FINEST, "Issued Token Binding token binding under Derived Keys");
/*     */       }
/*     */     } else {
/* 206 */       if (originalKeyBinding != null) {
/* 207 */         throw new XWSSecurityException("Unsupported Key Binding:" + originalKeyBinding);
/*     */       }
/* 209 */       throw new XWSSecurityException("Internal Error: Null original key binding");
/*     */     } 
/*     */ 
/*     */     
/* 213 */     DerivedKeyTokenImpl derivedKeyTokenImpl = new DerivedKeyTokenImpl(offset, length, secret);
/* 214 */     Key dataKey = null;
/*     */     try {
/* 216 */       String jceAlgo = SecurityUtil.getSecretKeyAlgorithm(algorithm);
/* 217 */       dataKey = derivedKeyTokenImpl.generateSymmetricKey(jceAlgo);
/* 218 */     } catch (Exception e) {
/* 219 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1806_ERROR_GENERATING_SYMMETRIC_KEY(), e);
/* 220 */       throw new XWSSecurityException(e);
/*     */     } 
/* 222 */     SecurityTokenReferenceType str = null;
/* 223 */     Object strObj = result.getKeyInfo().getContent().get(0);
/* 224 */     if (strObj instanceof JAXBElement) {
/* 225 */       str = ((JAXBElement<SecurityTokenReferenceType>)strObj).getValue();
/*     */     } else {
/* 227 */       str = (SecurityTokenReferenceType)strObj;
/*     */     } 
/* 229 */     if (str instanceof SecurityTokenReference) {
/* 230 */       str = this.elementFactory.createSecurityTokenReference(((SecurityTokenReference)str).getReference());
/*     */     }
/* 232 */     DerivedKey dk = null;
/* 233 */     if (dpTokenID.length() == 0) {
/* 234 */       dk = this.elementFactory.createDerivedKey(this.dtk.getUUID(), algorithm, derivedKeyTokenImpl.getNonce(), derivedKeyTokenImpl.getOffset(), derivedKeyTokenImpl.getLength(), derivedKeyTokenImpl.getLabel(), str, this.context.getSecurityPolicyVersion());
/*     */     } else {
/* 236 */       dk = this.elementFactory.createDerivedKey(this.dtk.getUUID(), algorithm, derivedKeyTokenImpl.getNonce(), derivedKeyTokenImpl.getOffset(), derivedKeyTokenImpl.getLength(), derivedKeyTokenImpl.getLabel(), str, dpTokenID, this.context.getSecurityPolicyVersion());
/*     */     } 
/* 238 */     DirectReference directReference = this.elementFactory.createDirectReference();
/* 239 */     directReference.setURI("#" + dk.getId());
/* 240 */     SecurityTokenReference str2 = buildSTR(this.context.generateID(), (Reference)directReference);
/* 241 */     this.context.getSecurityHeader().add(dk);
/*     */     
/* 243 */     buildKeyInfo(str2);
/* 244 */     dktResult.setKeyInfo(this.keyInfo);
/* 245 */     dktResult.setDataProtectionKey(dataKey);
/* 246 */     return dktResult;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\DerivedKeyTokenBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */