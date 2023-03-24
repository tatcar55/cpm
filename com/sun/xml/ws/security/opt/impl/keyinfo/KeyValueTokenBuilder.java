/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.token.LogStringsMessages;
/*     */ import java.security.Key;
/*     */ import java.security.KeyPair;
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
/*     */ public class KeyValueTokenBuilder
/*     */   extends TokenBuilder
/*     */ {
/*  58 */   AuthenticationTokenPolicy.KeyValueTokenBinding binding = null;
/*     */   
/*     */   public KeyValueTokenBuilder(JAXBFilterProcessingContext context, AuthenticationTokenPolicy.KeyValueTokenBinding binding) {
/*  61 */     super(context);
/*  62 */     this.binding = binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderResult process() throws XWSSecurityException {
/*  72 */     String referenceType = this.binding.getReferenceType();
/*  73 */     if (logger.isLoggable(Level.FINEST)) {
/*  74 */       logger.log(Level.FINEST, LogStringsMessages.WSS_1851_REFERENCETYPE_X_509_TOKEN(referenceType));
/*     */     }
/*  76 */     Key dataProtectionKey = null;
/*  77 */     BuilderResult result = new BuilderResult();
/*  78 */     KeyPair keyPair = (KeyPair)this.context.getExtraneousProperties().get("UseKey-RSAKeyPair");
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
/*  97 */     if (keyPair != null) {
/*  98 */       dataProtectionKey = keyPair.getPrivate();
/*  99 */       if (dataProtectionKey == null)
/*     */       {
/* 101 */         throw new XWSSecurityException("PrivateKey null inside PrivateKeyBinding set for KeyValueToken/RsaToken Policy ");
/*     */       }
/* 103 */       buildKeyInfo(keyPair.getPublic());
/* 104 */       result.setDataProtectionKey(dataProtectionKey);
/* 105 */       result.setKeyInfo(this.keyInfo);
/*     */     } 
/* 107 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\KeyValueTokenBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */