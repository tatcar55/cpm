/*    */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*    */ 
/*    */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*    */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*    */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*    */ import com.sun.xml.ws.security.opt.impl.reference.DirectReference;
/*    */ import com.sun.xml.ws.security.opt.impl.tokens.UsernameToken;
/*    */ import com.sun.xml.wss.XWSSecurityException;
/*    */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*    */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*    */ import com.sun.xml.wss.logging.impl.opt.token.LogStringsMessages;
/*    */ import java.util.logging.Level;
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
/*    */ public class UsernameTokenBuilder
/*    */   extends TokenBuilder
/*    */ {
/* 60 */   AuthenticationTokenPolicy.UsernameTokenBinding binding = null;
/*    */   
/*    */   public UsernameTokenBuilder(JAXBFilterProcessingContext context, AuthenticationTokenPolicy.UsernameTokenBinding binding) {
/* 63 */     super(context);
/* 64 */     this.binding = binding;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderResult process() throws XWSSecurityException {
/* 72 */     String untokenId = this.binding.getUUID();
/* 73 */     if (untokenId == null || untokenId.equals("")) {
/* 74 */       untokenId = this.context.generateID();
/*    */     }
/* 76 */     SecurityUtil.checkIncludeTokenPolicyOpt(this.context, this.binding, untokenId);
/* 77 */     String referenceType = this.binding.getReferenceType();
/* 78 */     BuilderResult result = new BuilderResult();
/* 79 */     if ("Direct".equals(referenceType)) {
/* 80 */       UsernameToken unToken = createUsernameToken(this.binding, this.binding.getUsernameToken());
/* 81 */       if (unToken == null) {
/* 82 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1856_NULL_USERNAMETOKEN());
/* 83 */         throw new XWSSecurityException("Username Token is NULL");
/*    */       } 
/* 85 */       DirectReference dr = buildDirectReference(unToken.getId(), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
/* 86 */       buildKeyInfo((Reference)dr, this.binding.getSTRID());
/*    */     } 
/* 88 */     result.setKeyInfo(this.keyInfo);
/* 89 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\UsernameTokenBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */