/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Signature;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBSignContext;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceAndPrefixMapper;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.security.Key;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.XMLSignContext;
/*     */ import javax.xml.crypto.dsig.XMLSignature;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureProcessor
/*     */ {
/*  81 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int sign(JAXBFilterProcessingContext context) throws XWSSecurityException {
/*     */     try {
/*  98 */       SignaturePolicy signaturePolicy = (SignaturePolicy)context.getSecurityPolicy();
/*  99 */       ((NamespaceContextEx)context.getNamespaceContext()).addSignatureNS();
/* 100 */       WSSPolicy keyBinding = (WSSPolicy)signaturePolicy.getKeyBinding();
/* 101 */       if (logger.isLoggable(Level.FINEST)) {
/* 102 */         logger.log(Level.FINEST, "KeyBinding is " + keyBinding);
/*     */       }
/*     */       
/* 105 */       Key signingKey = null;
/*     */       
/* 107 */       SignatureElementFactory signFactory = new SignatureElementFactory();
/*     */       
/* 109 */       KeyInfo keyInfo = null;
/* 110 */       SecurityHeader securityHeader = context.getSecurityHeader();
/*     */ 
/*     */       
/* 113 */       TokenProcessor tokenProcessor = new TokenProcessor(signaturePolicy, context);
/* 114 */       BuilderResult builderResult = tokenProcessor.process();
/* 115 */       signingKey = builderResult.getDataProtectionKey();
/* 116 */       KeyInfo keyInfo1 = builderResult.getKeyInfo();
/*     */       
/* 118 */       if (keyInfo1 != null || !keyBinding.isOptional()) {
/* 119 */         SignedInfo signedInfo = signFactory.constructSignedInfo(context);
/* 120 */         JAXBSignContext signContext = new JAXBSignContext(signingKey);
/* 121 */         signContext.setURIDereferencer(DSigResolver.getInstance());
/* 122 */         XMLSignature signature = signFactory.constructSignature(signedInfo, (KeyInfo)keyInfo1, signaturePolicy.getUUID());
/* 123 */         signContext.put("http://wss.sun.com#processingContext", context);
/* 124 */         NamespaceAndPrefixMapper npMapper = new NamespaceAndPrefixMapper(context.getNamespaceContext(), context.getDisableIncPrefix());
/* 125 */         signContext.put("NS_And_Prefix_Mapper", npMapper);
/* 126 */         signContext.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
/* 127 */         signature.sign((XMLSignContext)signContext);
/*     */         
/* 129 */         JAXBSignatureHeaderElement jaxBSign = new JAXBSignatureHeaderElement((Signature)signature, context.getSOAPVersion());
/* 130 */         securityHeader.add(jaxBSign);
/*     */ 
/*     */         
/* 133 */         List<String> scList = (ArrayList)context.getExtraneousProperty("SignatureConfirmation");
/* 134 */         if (scList != null) {
/* 135 */           scList.add(Base64.encode(signature.getSignatureValue().getValue()));
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 140 */     catch (XWSSecurityException xe) {
/* 141 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1701_SIGN_FAILED(), (Throwable)xe);
/* 142 */       throw xe;
/* 143 */     } catch (Exception ex) {
/* 144 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1701_SIGN_FAILED(), ex);
/* 145 */       throw new XWSSecurityException(ex);
/*     */     } 
/* 147 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\SignatureProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */