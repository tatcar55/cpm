/*     */ package com.sun.xml.wss.impl;
/*     */ 
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.callback.DynamicPolicyCallback;
/*     */ import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
/*     */ import com.sun.xml.wss.impl.filter.DumpFilter;
/*     */ import com.sun.xml.wss.impl.policy.DynamicPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.mls.DynamicSecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class SecurityAnnotator
/*     */ {
/*  73 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void secureMessage(ProcessingContext context) throws XWSSecurityException {
/*     */     DynamicSecurityPolicy dynamicSecurityPolicy;
/* 102 */     HarnessUtil.validateContext(context);
/*     */     
/* 104 */     SecurityPolicy policy = context.getSecurityPolicy();
/* 105 */     SecurityEnvironment handler = context.getSecurityEnvironment();
/* 106 */     StaticPolicyContext staticContext = context.getPolicyContext();
/*     */     
/* 108 */     FilterProcessingContext fpContext = setFilterProcessingContext(context);
/*     */     
/* 110 */     fpContext.isInboundMessage(false);
/* 111 */     if (fpContext.resetMustUnderstand()) {
/* 112 */       fpContext.getSecurableSoapMessage().setDoNotSetMU(true);
/*     */     }
/*     */     
/* 115 */     if (PolicyTypeUtil.messagePolicy(policy) && ((MessagePolicy)policy).enableDynamicPolicy() && ((MessagePolicy)policy).size() == 0)
/*     */     {
/*     */       
/* 118 */       dynamicSecurityPolicy = new DynamicSecurityPolicy();
/*     */     }
/*     */     
/* 121 */     if (PolicyTypeUtil.dynamicSecurityPolicy((SecurityPolicy)dynamicSecurityPolicy)) {
/*     */ 
/*     */       
/* 124 */       DynamicApplicationContext dynamicContext = new DynamicApplicationContext(staticContext);
/* 125 */       dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 126 */       dynamicContext.inBoundMessage(false);
/* 127 */       ProcessingContext.copy(dynamicContext.getRuntimeProperties(), context.getExtraneousProperties());
/*     */ 
/*     */       
/* 130 */       DynamicPolicyCallback dpCallback = new DynamicPolicyCallback((SecurityPolicy)dynamicSecurityPolicy, (DynamicPolicyContext)dynamicContext);
/*     */       try {
/* 132 */         HarnessUtil.makeDynamicPolicyCallback(dpCallback, handler.getCallbackHandler());
/*     */       
/*     */       }
/* 135 */       catch (Exception e) {
/* 136 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0237_FAILED_DYNAMIC_POLICY_CALLBACK(), e);
/* 137 */         throw new XWSSecurityException(e);
/*     */       } 
/*     */       
/* 140 */       SecurityPolicy result = dpCallback.getSecurityPolicy();
/* 141 */       fpContext.setSecurityPolicy(result);
/*     */       
/* 143 */       if (PolicyTypeUtil.messagePolicy(result)) {
/* 144 */         processMessagePolicy(fpContext);
/*     */       }
/* 146 */       else if (result instanceof com.sun.xml.wss.impl.policy.mls.WSSPolicy) {
/* 147 */         HarnessUtil.processWSSPolicy(fpContext);
/* 148 */       } else if (result != null) {
/* 149 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0260_INVALID_DSP());
/* 150 */         throw new XWSSecurityException("Invalid dynamic security policy returned by callback handler");
/*     */       }
/*     */     
/* 153 */     } else if (PolicyTypeUtil.messagePolicy((SecurityPolicy)dynamicSecurityPolicy)) {
/* 154 */       fpContext.enableDynamicPolicyCallback(((MessagePolicy)dynamicSecurityPolicy).enableDynamicPolicy());
/* 155 */       processMessagePolicy(fpContext);
/* 156 */     } else if (dynamicSecurityPolicy instanceof com.sun.xml.wss.impl.policy.mls.WSSPolicy) {
/* 157 */       HarnessUtil.processWSSPolicy(fpContext);
/*     */     } else {
/* 159 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0251_INVALID_SECURITY_POLICY_INSTANCE());
/* 160 */       throw new XWSSecurityException("SecurityPolicy instance should be of type: WSSPolicy OR MessagePolicy OR DynamicSecurityPolicy");
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
/*     */   
/*     */   private static void processMessagePolicy(FilterProcessingContext fpContext) throws XWSSecurityException {
/* 173 */     MessagePolicy policy = (MessagePolicy)fpContext.getSecurityPolicy();
/*     */     
/* 175 */     if (policy.enableWSS11Policy())
/*     */     {
/* 177 */       fpContext.setExtraneousProperty("EnableWSS11PolicySender", "true");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     List scList = new ArrayList();
/* 186 */     fpContext.setExtraneousProperty("SignatureConfirmation", scList);
/*     */ 
/*     */     
/* 189 */     Iterator<SecurityPolicy> i = policy.iterator();
/*     */     
/* 191 */     while (i.hasNext()) {
/* 192 */       SecurityPolicy sPolicy = i.next();
/* 193 */       fpContext.setSecurityPolicy(sPolicy);
/* 194 */       HarnessUtil.processDeep(fpContext);
/*     */     } 
/*     */     
/* 197 */     if (!(fpContext instanceof com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext) && 
/* 198 */       policy.dumpMessages()) {
/* 199 */       DumpFilter.process(fpContext);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void handleFault(ProcessingContext context) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FilterProcessingContext setFilterProcessingContext(ProcessingContext context) throws XWSSecurityException {
/* 214 */     if (context instanceof com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext)
/* 215 */       return (FilterProcessingContext)context; 
/* 216 */     return new FilterProcessingContext(context);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\SecurityAnnotator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */