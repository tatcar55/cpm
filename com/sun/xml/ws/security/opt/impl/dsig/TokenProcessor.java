/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.TokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.DerivedKeyTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.IssuedTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.KerberosTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.KeyValueTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SCTBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SamlTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SymmetricTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.UsernameTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.X509TokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.AlgorithmSuite;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.PrivateKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.security.Key;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ public class TokenProcessor
/*     */ {
/*  90 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */ 
/*     */   
/*  93 */   private Key signingKey = null;
/*     */   
/*  95 */   private TokenBuilder builder = null;
/*  96 */   private WSSPolicy keyBinding = null;
/*     */   
/*  98 */   private JAXBFilterProcessingContext context = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenProcessor(SignaturePolicy sp, JAXBFilterProcessingContext context) {
/* 107 */     this.context = context;
/* 108 */     this.keyBinding = (WSSPolicy)sp.getKeyBinding();
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
/* 119 */     String keyEncAlgo = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
/* 120 */     String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*     */     
/* 122 */     AlgorithmSuite algSuite = this.context.getAlgorithmSuite();
/* 123 */     String tmp = null;
/* 124 */     if (algSuite != null) {
/* 125 */       tmp = algSuite.getAsymmetricKeyAlgorithm();
/*     */     }
/* 127 */     if (tmp != null && !"".equals(tmp)) {
/* 128 */       keyEncAlgo = tmp;
/*     */     }
/* 130 */     if (algSuite != null) {
/* 131 */       tmp = algSuite.getEncryptionAlgorithm();
/*     */     }
/* 133 */     if (tmp != null && !"".equals(tmp)) {
/* 134 */       dataEncAlgo = tmp;
/*     */     }
/*     */     
/* 137 */     if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)this.keyBinding)) {
/* 138 */       AuthenticationTokenPolicy.UsernameTokenBinding usernameTokenBinding = null;
/* 139 */       if (this.context.getusernameTokenBinding() != null) {
/* 140 */         usernameTokenBinding = this.context.getusernameTokenBinding();
/* 141 */         this.context.setUsernameTokenBinding(null);
/*     */       } else {
/* 143 */         usernameTokenBinding = (AuthenticationTokenPolicy.UsernameTokenBinding)this.keyBinding;
/*     */       } 
/* 145 */       this.signingKey = usernameTokenBinding.getSecretKey();
/* 146 */       this.builder = (TokenBuilder)new UsernameTokenBuilder(this.context, usernameTokenBinding);
/* 147 */       BuilderResult untResult = this.builder.process();
/* 148 */       untResult.setDataProtectionKey(this.signingKey);
/* 149 */       return untResult;
/*     */     } 
/* 151 */     if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)this.keyBinding)) {
/* 152 */       AuthenticationTokenPolicy.X509CertificateBinding certificateBinding = null;
/* 153 */       if (this.context.getX509CertificateBinding() != null) {
/* 154 */         certificateBinding = this.context.getX509CertificateBinding();
/* 155 */         this.context.setX509CertificateBinding(null);
/*     */       } else {
/* 157 */         certificateBinding = (AuthenticationTokenPolicy.X509CertificateBinding)this.keyBinding;
/*     */       } 
/*     */       
/* 160 */       PrivateKeyBinding privKBinding = (PrivateKeyBinding)certificateBinding.getKeyBinding();
/* 161 */       this.signingKey = privKBinding.getPrivateKey();
/*     */       
/* 163 */       this.builder = (TokenBuilder)new X509TokenBuilder(this.context, certificateBinding);
/* 164 */       BuilderResult xtbResult = this.builder.process();
/*     */       
/* 166 */       xtbResult.setDataProtectionKey(this.signingKey);
/* 167 */       return xtbResult;
/* 168 */     }  if (PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)this.keyBinding)) {
/* 169 */       AuthenticationTokenPolicy.KerberosTokenBinding krbBinding = null;
/* 170 */       if (this.context.getKerberosTokenBinding() != null) {
/* 171 */         krbBinding = this.context.getKerberosTokenBinding();
/* 172 */         this.context.setKerberosTokenBinding(null);
/*     */       } else {
/* 174 */         krbBinding = (AuthenticationTokenPolicy.KerberosTokenBinding)this.keyBinding;
/*     */       } 
/*     */       
/* 177 */       this.signingKey = krbBinding.getSecretKey();
/* 178 */       this.builder = (TokenBuilder)new KerberosTokenBuilder(this.context, krbBinding);
/* 179 */       BuilderResult ktbResult = this.builder.process();
/* 180 */       ktbResult.setDataProtectionKey(this.signingKey);
/*     */       
/* 182 */       return ktbResult;
/* 183 */     }  if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)this.keyBinding)) {
/* 184 */       SymmetricKeyBinding skb = null;
/* 185 */       if (this.context.getSymmetricKeyBinding() != null) {
/* 186 */         skb = this.context.getSymmetricKeyBinding();
/* 187 */         this.context.setSymmetricKeyBinding(null);
/*     */       } else {
/* 189 */         skb = (SymmetricKeyBinding)this.keyBinding;
/*     */       } 
/*     */       
/* 192 */       this.builder = (TokenBuilder)new SymmetricTokenBuilder(skb, this.context, dataEncAlgo, keyEncAlgo);
/* 193 */       BuilderResult skbResult = this.builder.process();
/* 194 */       return skbResult;
/* 195 */     }  if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)this.keyBinding)) {
/* 196 */       DerivedTokenKeyBinding dtk = (DerivedTokenKeyBinding)this.keyBinding;
/* 197 */       ((NamespaceContextEx)this.context.getNamespaceContext()).addSCNS();
/* 198 */       this.builder = (TokenBuilder)new DerivedKeyTokenBuilder(this.context, dtk);
/* 199 */       BuilderResult dtkResult = this.builder.process();
/* 200 */       return dtkResult;
/* 201 */     }  if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)this.keyBinding)) {
/* 202 */       IssuedTokenBuilder itb = new IssuedTokenBuilder(this.context, (IssuedTokenKeyBinding)this.keyBinding);
/* 203 */       BuilderResult itbResult = itb.process();
/* 204 */       return itbResult;
/* 205 */     }  if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)this.keyBinding)) {
/* 206 */       ((NamespaceContextEx)this.context.getNamespaceContext()).addSCNS();
/* 207 */       SCTBuilder sctBuilder = new SCTBuilder(this.context, (SecureConversationTokenKeyBinding)this.keyBinding);
/* 208 */       BuilderResult sctResult = sctBuilder.process();
/* 209 */       return sctResult;
/* 210 */     }  if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)this.keyBinding)) {
/* 211 */       ((NamespaceContextEx)this.context.getNamespaceContext()).addSAMLNS();
/* 212 */       SamlTokenBuilder stb = new SamlTokenBuilder(this.context, (AuthenticationTokenPolicy.SAMLAssertionBinding)this.keyBinding, true);
/* 213 */       return stb.process();
/* 214 */     }  if (PolicyTypeUtil.keyValueTokenBinding((SecurityPolicy)this.keyBinding)) {
/* 215 */       ((NamespaceContextEx)this.context.getNamespaceContext()).addSAMLNS();
/* 216 */       KeyValueTokenBuilder sctBuilder = new KeyValueTokenBuilder(this.context, (AuthenticationTokenPolicy.KeyValueTokenBinding)this.keyBinding);
/* 217 */       BuilderResult kvtResult = sctBuilder.process();
/* 218 */       return kvtResult;
/*     */     } 
/* 220 */     logger.log(Level.SEVERE, LogStringsMessages.WSS_1703_UNSUPPORTED_KEYBINDING_SIGNATUREPOLICY(this.keyBinding));
/* 221 */     throw new UnsupportedOperationException("Unsupported Key Binding" + this.keyBinding);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\TokenProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */