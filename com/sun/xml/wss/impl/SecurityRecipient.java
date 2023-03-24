/*      */ package com.sun.xml.wss.impl;
/*      */ 
/*      */ import com.sun.xml.wss.ProcessingContext;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.core.SecurityHeader;
/*      */ import com.sun.xml.wss.impl.callback.DynamicPolicyCallback;
/*      */ import com.sun.xml.wss.impl.config.ApplicationSecurityConfiguration;
/*      */ import com.sun.xml.wss.impl.config.DeclarativeSecurityConfiguration;
/*      */ import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
/*      */ import com.sun.xml.wss.impl.configuration.StaticApplicationContext;
/*      */ import com.sun.xml.wss.impl.filter.AuthenticationTokenFilter;
/*      */ import com.sun.xml.wss.impl.filter.DumpFilter;
/*      */ import com.sun.xml.wss.impl.filter.EncryptionFilter;
/*      */ import com.sun.xml.wss.impl.filter.SignatureConfirmationFilter;
/*      */ import com.sun.xml.wss.impl.filter.SignatureFilter;
/*      */ import com.sun.xml.wss.impl.filter.TimestampFilter;
/*      */ import com.sun.xml.wss.impl.policy.DynamicPolicyContext;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.DynamicSecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.Target;
/*      */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*      */ import com.sun.xml.wss.logging.LogStringsMessages;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.soap.AttachmentPart;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import javax.xml.soap.SOAPFactory;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SecurityRecipient
/*      */ {
/*  101 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void validateMessage(ProcessingContext context) throws XWSSecurityException {
/*  196 */     HarnessUtil.validateContext(context);
/*      */     
/*  198 */     SecurityPolicy policy = context.getSecurityPolicy();
/*  199 */     StaticPolicyContext staticContext = context.getPolicyContext();
/*      */     
/*  201 */     FilterProcessingContext fpContext = new FilterProcessingContext(context);
/*  202 */     fpContext.isInboundMessage(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  207 */     fpContext.setExtraneousProperty("EnableWSS11PolicyReceiver", "true");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  215 */     List scList = new ArrayList();
/*  216 */     fpContext.setExtraneousProperty("receivedSignValues", scList);
/*      */     
/*  218 */     if (policy != null) {
/*      */       DynamicSecurityPolicy dynamicSecurityPolicy;
/*  220 */       if (PolicyTypeUtil.messagePolicy(policy) && !PolicyTypeUtil.applicationSecurityConfiguration(policy) && ((MessagePolicy)policy).enableDynamicPolicy() && ((MessagePolicy)policy).size() == 0)
/*      */       {
/*      */ 
/*      */         
/*  224 */         dynamicSecurityPolicy = new DynamicSecurityPolicy();
/*      */       }
/*      */       
/*  227 */       if (PolicyTypeUtil.dynamicSecurityPolicy((SecurityPolicy)dynamicSecurityPolicy)) {
/*      */ 
/*      */         
/*  230 */         DynamicApplicationContext dynamicContext = new DynamicApplicationContext(staticContext);
/*  231 */         dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/*  232 */         dynamicContext.inBoundMessage(true);
/*  233 */         ProcessingContext.copy(dynamicContext.getRuntimeProperties(), context.getExtraneousProperties());
/*      */ 
/*      */         
/*  236 */         DynamicPolicyCallback dpCallback = new DynamicPolicyCallback((SecurityPolicy)dynamicSecurityPolicy, (DynamicPolicyContext)dynamicContext);
/*  237 */         HarnessUtil.makeDynamicPolicyCallback(dpCallback, context.getSecurityEnvironment().getCallbackHandler());
/*      */ 
/*      */ 
/*      */         
/*  241 */         SecurityPolicy result = dpCallback.getSecurityPolicy();
/*  242 */         fpContext.setSecurityPolicy(result);
/*  243 */         fpContext.setMode(0);
/*      */         
/*  245 */         if (PolicyTypeUtil.messagePolicy(result)) {
/*  246 */           processMessagePolicy(fpContext);
/*  247 */         } else if (result instanceof WSSPolicy) {
/*  248 */           HarnessUtil.processWSSPolicy(fpContext);
/*  249 */         } else if (result != null) {
/*  250 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0260_INVALID_DSP());
/*  251 */           throw new XWSSecurityException("Invalid dynamic security policy returned by callback handler");
/*      */         }
/*      */       
/*  254 */       } else if (dynamicSecurityPolicy instanceof WSSPolicy) {
/*      */         
/*  256 */         fpContext.setMode(0);
/*  257 */         HarnessUtil.processWSSPolicy(fpContext);
/*  258 */       } else if (PolicyTypeUtil.messagePolicy((SecurityPolicy)dynamicSecurityPolicy)) {
/*  259 */         fpContext.enableDynamicPolicyCallback(((MessagePolicy)dynamicSecurityPolicy).enableDynamicPolicy());
/*  260 */         fpContext.setMode(0);
/*  261 */         processMessagePolicy(fpContext);
/*  262 */         checkForExtraSecurity(fpContext);
/*  263 */       } else if (PolicyTypeUtil.applicationSecurityConfiguration((SecurityPolicy)dynamicSecurityPolicy)) {
/*      */ 
/*      */         
/*  266 */         fpContext.setMode(1);
/*  267 */         processApplicationSecurityConfiguration(fpContext);
/*  268 */         checkForExtraSecurity(fpContext);
/*      */       } else {
/*  270 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0251_INVALID_SECURITY_POLICY_INSTANCE());
/*  271 */         throw new XWSSecurityException("SecurityPolicy instance should be of type: WSSPolicy OR MessagePolicy OR DynamicSecurityPolicy OR ApplicationSecurityConfiguration");
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  277 */       pProcess(fpContext);
/*      */     } 
/*      */     
/*      */     try {
/*  281 */       if (!fpContext.retainSecurityHeader()) {
/*  282 */         fpContext.getSecurableSoapMessage().deleteSecurityHeader();
/*      */       } else {
/*  284 */         fpContext.getSecurableSoapMessage().resetMustUnderstandOnSecHeader();
/*      */       } 
/*  286 */       fpContext.getSOAPMessage().saveChanges();
/*      */     }
/*  288 */     catch (Exception ex) {
/*  289 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0370_ERROR_DELETING_SECHEADER(), ex);
/*  290 */       throw new XWSSecurityException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void processApplicationSecurityConfiguration(FilterProcessingContext fpContext) throws XWSSecurityException {
/*  302 */     ApplicationSecurityConfiguration configuration = (ApplicationSecurityConfiguration)fpContext.getSecurityPolicy();
/*      */     
/*  304 */     Collection mConfiguration = configuration.getAllReceiverPolicies();
/*      */     
/*  306 */     fpContext.setSecurityPolicy((SecurityPolicy)new MessagePolicy());
/*      */     
/*  308 */     SOAPElement current = fpContext.getSecurableSoapMessage().findSecurityHeader().getFirstChildElement();
/*  309 */     MessagePolicy policy = null;
/*  310 */     while (current != null) {
/*  311 */       fpContext.getSecurableSoapMessage().findSecurityHeader().setCurrentHeaderElement(current);
/*  312 */       pProcessOnce(fpContext, current, false);
/*  313 */       if (!mConfiguration.isEmpty())
/*      */         try {
/*  315 */           MessagePolicy mp = (MessagePolicy)fpContext.getSecurityPolicy();
/*  316 */           if (!mp.isEmpty()) {
/*  317 */             redux(mp, mConfiguration, fpContext.getSecurableSoapMessage(), false);
/*      */           }
/*      */         }
/*  320 */         catch (Exception e) {
/*  321 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0256_FAILED_CONFIGURE_ASC(), e);
/*  322 */           throw new XWSSecurityException(e);
/*      */         }  
/*  324 */       policy = resolveMP(fpContext, configuration);
/*  325 */       if (policy != null) {
/*  326 */         if (!mConfiguration.contains(policy)) {
/*      */           
/*  328 */           StringBuffer buf = null;
/*  329 */           if (PolicyTypeUtil.messagePolicy((SecurityPolicy)policy)) {
/*  330 */             for (int it = 0; it < policy.size(); it++) {
/*  331 */               if (buf == null)
/*  332 */                 buf = new StringBuffer(); 
/*      */               try {
/*  334 */                 buf.append(policy.get(it).getType() + " ");
/*  335 */               } catch (Exception e) {}
/*      */             } 
/*      */ 
/*      */             
/*  339 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0261_INVALID_MESSAGE_POLICYSET());
/*  340 */             throw new XWSSecurityException("Message does not conform to configured policy : [ " + buf.toString() + "] policy set is not present in Receiver requirements.");
/*      */           } 
/*      */           
/*  343 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0262_INVALID_MESSAGE_POLICYTYPE());
/*  344 */           throw new XWSSecurityException("Message does not conform to configured policy : " + policy.getType() + " is not present in Receiver requirements.");
/*      */         } 
/*      */ 
/*      */         
/*  348 */         MessagePolicy policyCopy = new MessagePolicy();
/*  349 */         int size = ((MessagePolicy)fpContext.getSecurityPolicy()).size();
/*  350 */         int ppCount = 0;
/*  351 */         for (int i = 0; i < policy.size(); i++) {
/*      */           try {
/*  353 */             WSSPolicy wp = (WSSPolicy)policy.get(i);
/*  354 */             if (PolicyTypeUtil.isSecondaryPolicy(wp)) {
/*  355 */               if (log.isLoggable(Level.FINEST)) {
/*  356 */                 log.log(Level.FINEST, wp.getType());
/*      */               }
/*  358 */               policyCopy.append((SecurityPolicy)wp);
/*      */             } else {
/*  360 */               if (ppCount >= size) {
/*  361 */                 if (log.isLoggable(Level.FINEST)) {
/*  362 */                   log.log(Level.FINEST, wp.getType());
/*      */                 }
/*  364 */                 policyCopy.append((SecurityPolicy)wp);
/*      */               }
/*  366 */               else if (log.isLoggable(Level.FINEST)) {
/*  367 */                 log.log(Level.FINEST, "skipped" + wp.getType());
/*      */               } 
/*      */               
/*  370 */               ppCount++;
/*      */             } 
/*  372 */           } catch (Exception e) {
/*  373 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0257_FAILEDTO_APPEND_SECURITY_POLICY_MESSAGE_POLICY(), e);
/*  374 */             throw new XWSSecurityException(e);
/*      */           } 
/*      */         } 
/*  377 */         fpContext.setMode(0);
/*  378 */         fpContext.setSecurityPolicy((SecurityPolicy)policyCopy);
/*  379 */         current = HarnessUtil.getNextElement(current);
/*  380 */         if (policy.dumpMessages()) {
/*  381 */           DumpFilter.process(fpContext);
/*      */         }
/*  383 */         processMessagePolicy(fpContext, current);
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/*  388 */       current = HarnessUtil.getNextElement(current);
/*      */     } 
/*  390 */     checkPolicyEquivalence(policy, mConfiguration);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MessagePolicy resolveMP(FilterProcessingContext fpContext, ApplicationSecurityConfiguration configuration) throws XWSSecurityException {
/*  404 */     String identifier = HarnessUtil.resolvePolicyIdentifier(fpContext.getSOAPMessage());
/*      */     
/*  406 */     if (identifier == null) {
/*  407 */       return null;
/*      */     }
/*  409 */     StaticPolicyContext context = fpContext.getPolicyContext();
/*      */ 
/*      */ 
/*      */     
/*  413 */     ((StaticApplicationContext)context).setOperationIdentifier(identifier);
/*      */ 
/*      */     
/*  416 */     SecurityPolicy policy = configuration.getSecurityConfiguration((StaticApplicationContext)context);
/*      */     
/*  418 */     MessagePolicy mPolicy = null;
/*      */     
/*  420 */     if (PolicyTypeUtil.dynamicSecurityPolicy(policy)) {
/*      */ 
/*      */       
/*  423 */       DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context);
/*  424 */       dynamicContext.setMessageIdentifier(fpContext.getMessageIdentifier());
/*  425 */       dynamicContext.inBoundMessage(true);
/*  426 */       ProcessingContext.copy(dynamicContext.getRuntimeProperties(), fpContext.getExtraneousProperties());
/*      */ 
/*      */       
/*  429 */       DynamicPolicyCallback dpCallback = new DynamicPolicyCallback(policy, (DynamicPolicyContext)dynamicContext);
/*  430 */       HarnessUtil.makeDynamicPolicyCallback(dpCallback, fpContext.getSecurityEnvironment().getCallbackHandler());
/*      */ 
/*      */       
/*  433 */       if (!PolicyTypeUtil.messagePolicy(dpCallback.getSecurityPolicy())) {
/*  434 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0271_FAILEDTO_RESOLVE_POLICY());
/*  435 */         throw new XWSSecurityException("Policy has to resolve to MessagePolicy");
/*      */       } 
/*  437 */       mPolicy = (MessagePolicy)dpCallback.getSecurityPolicy();
/*      */     
/*      */     }
/*  440 */     else if (PolicyTypeUtil.declarativeSecurityConfiguration(policy)) {
/*      */       
/*  442 */       DeclarativeSecurityConfiguration dsc = (DeclarativeSecurityConfiguration)policy;
/*  443 */       mPolicy = dsc.receiverSettings();
/*      */     } 
/*      */     
/*  446 */     return mPolicy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void redux(MessagePolicy mPolicy, Collection configuration, SecurableSoapMessage message, boolean isSecondary) throws Exception {
/*  469 */     WSSPolicy policy = null;
/*      */     
/*  471 */     int _spSize = mPolicy.getSecondaryPolicies().size() - 1;
/*  472 */     if (isSecondary && _spSize >= 0) {
/*  473 */       policy = mPolicy.getSecondaryPolicies().get(_spSize);
/*      */     } else {
/*  475 */       int _pSize = mPolicy.getPrimaryPolicies().size() - 1;
/*  476 */       if (_pSize >= 0) {
/*  477 */         policy = mPolicy.getPrimaryPolicies().get(_pSize);
/*      */       }
/*      */     } 
/*  480 */     if (policy == null) {
/*      */       return;
/*      */     }
/*  483 */     ArrayList<MessagePolicy> reduxx = new ArrayList();
/*      */     
/*  485 */     Iterator<MessagePolicy> i = configuration.iterator();
/*  486 */     while (i.hasNext()) {
/*      */       try {
/*  488 */         MessagePolicy policyx = i.next();
/*  489 */         int spSize = mPolicy.getSecondaryPolicies().size() - 1;
/*  490 */         ArrayList<WSSPolicy> policyxList = policyx.getPrimaryPolicies();
/*      */         
/*  492 */         WSSPolicy wssPolicyx = null;
/*  493 */         if (isSecondary && spSize >= 0) {
/*  494 */           wssPolicyx = (WSSPolicy)policyx.get(spSize);
/*      */         } else {
/*  496 */           int pSize = mPolicy.getPrimaryPolicies().size() - 1;
/*  497 */           if (pSize >= 0 && pSize < policyxList.size()) {
/*  498 */             wssPolicyx = policyxList.get(pSize);
/*      */           } else {
/*      */             continue;
/*      */           } 
/*      */         } 
/*  503 */         if (wssPolicyx != null)
/*      */         {
/*  505 */           if (!policy.equalsIgnoreTargets(wssPolicyx)) {
/*  506 */             reduxx.add(policyx);
/*      */           }
/*      */         }
/*      */       }
/*  510 */       catch (ClassCastException cce) {
/*      */         
/*  512 */         cce.printStackTrace();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  518 */     Iterator<MessagePolicy> j = configuration.iterator();
/*  519 */     while (j.hasNext()) {
/*      */       
/*      */       try {
/*  522 */         int spSize = mPolicy.getSecondaryPolicies().size() - 1;
/*  523 */         MessagePolicy policyy = j.next();
/*  524 */         ArrayList<WSSPolicy> policyyList = policyy.getPrimaryPolicies();
/*      */         
/*  526 */         WSSPolicy wssPolicyy = null;
/*  527 */         if (isSecondary && spSize >= 0) {
/*  528 */           wssPolicyy = (WSSPolicy)policyy.get(spSize);
/*      */         } else {
/*  530 */           int pSize = mPolicy.getPrimaryPolicies().size() - 1;
/*  531 */           if (pSize >= 0 && pSize < policyyList.size()) {
/*  532 */             wssPolicyy = policyyList.get(pSize);
/*      */           } else {
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */         
/*  538 */         if (wssPolicyy != null && 
/*  539 */           !checkTargetBasedRequirements(policy, wssPolicyy, message)) {
/*  540 */           reduxx.add(policyy);
/*      */         }
/*      */       }
/*  543 */       catch (ClassCastException cce) {
/*      */         
/*  545 */         cce.printStackTrace();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  551 */     configuration.removeAll(reduxx);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean checkTargetBasedRequirements(WSSPolicy inferred, WSSPolicy configured, SecurableSoapMessage message) {
/*  563 */     ArrayList inferredTargets = null;
/*  564 */     ArrayList configuredTargets = null;
/*  565 */     if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)configured) && !PolicyTypeUtil.encryptionPolicy((SecurityPolicy)inferred)) {
/*  566 */       return false;
/*      */     }
/*      */     
/*  569 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)configured) && !PolicyTypeUtil.signaturePolicy((SecurityPolicy)inferred)) {
/*  570 */       return false;
/*      */     }
/*      */     
/*  573 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)inferred) && PolicyTypeUtil.signaturePolicy((SecurityPolicy)configured))
/*  574 */       return verifySignatureTargets(inferred, configured, message); 
/*  575 */     if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)inferred) && PolicyTypeUtil.encryptionPolicy((SecurityPolicy)configured)) {
/*  576 */       return verifyEncryptionTargets(inferred, configured, message);
/*      */     }
/*  578 */     return false;
/*      */   }
/*      */   
/*      */   static boolean verifySignatureTargets(WSSPolicy inferred, WSSPolicy configured, SecurableSoapMessage message) {
/*  582 */     ArrayList inferredTargets = null;
/*  583 */     ArrayList configuredTargets = null;
/*      */     
/*  585 */     inferredTargets = ((SignaturePolicy.FeatureBinding)inferred.getFeatureBinding()).getTargetBindings();
/*  586 */     configuredTargets = ((SignaturePolicy.FeatureBinding)configured.getFeatureBinding()).getTargetBindings();
/*      */     
/*  588 */     ArrayList<EncryptedData> inferredNodeSet = new ArrayList();
/*  589 */     ArrayList<EncryptedData> configuredNodeSet = new ArrayList();
/*      */     try {
/*  591 */       dereferenceTargets(inferredTargets, inferredNodeSet, message, false);
/*  592 */       dereferenceTargets(configuredTargets, configuredNodeSet, message, false);
/*  593 */     } catch (XWSSecurityException xwsse) {
/*      */ 
/*      */       
/*  596 */       return false;
/*      */     } 
/*  598 */     if (inferredNodeSet.size() != configuredNodeSet.size())
/*      */     {
/*  600 */       return false;
/*      */     }
/*      */     
/*  603 */     for (int i = 0; i < configuredNodeSet.size(); i++) {
/*  604 */       EncryptedData cn = configuredNodeSet.get(i);
/*  605 */       for (int j = 0; j < inferredNodeSet.size(); j++) {
/*  606 */         EncryptedData ci = inferredNodeSet.get(j);
/*  607 */         boolean found = false;
/*  608 */         if (cn.isAttachmentData() && ci.isAttachmentData()) {
/*  609 */           found = cn.equals(ci);
/*  610 */         } else if (cn.isElementData() && ci.isElementData()) {
/*  611 */           found = ((EncryptedElement)cn).equals((EncryptedElement)ci);
/*      */         } 
/*  613 */         if (found) {
/*  614 */           inferredNodeSet.remove(j);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  619 */     if (inferredNodeSet.size() != 0)
/*      */     {
/*  621 */       return false;
/*      */     }
/*  623 */     return true;
/*      */   }
/*      */   
/*      */   static boolean verifyEncryptionTargets(WSSPolicy inferred, WSSPolicy configured, SecurableSoapMessage message) {
/*  627 */     ArrayList inferredTargets = null;
/*  628 */     ArrayList configuredTargets = null;
/*  629 */     inferredTargets = ((EncryptionPolicy.FeatureBinding)inferred.getFeatureBinding()).getTargetBindings();
/*  630 */     configuredTargets = ((EncryptionPolicy.FeatureBinding)configured.getFeatureBinding()).getTargetBindings();
/*      */     
/*  632 */     ArrayList<EncryptedData> inferredNodeSet = new ArrayList();
/*  633 */     ArrayList<EncryptedData> configuredNodeSet = new ArrayList();
/*      */     try {
/*  635 */       dereferenceTargets(inferredTargets, inferredNodeSet, message, true);
/*  636 */       dereferenceTargets(configuredTargets, configuredNodeSet, message, false);
/*  637 */     } catch (XWSSecurityException xwsse) {
/*  638 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  642 */     if (inferredNodeSet.size() != configuredNodeSet.size())
/*      */     {
/*  644 */       return false;
/*      */     }
/*  646 */     for (int i = 0; i < configuredNodeSet.size(); i++) {
/*  647 */       EncryptedData cn = configuredNodeSet.get(i);
/*  648 */       for (int j = 0; j < inferredNodeSet.size(); j++) {
/*  649 */         EncryptedData ci = inferredNodeSet.get(j);
/*  650 */         boolean found = false;
/*  651 */         if (cn.isAttachmentData() && ci.isAttachmentData()) {
/*  652 */           found = cn.equals(ci);
/*  653 */         } else if (cn.isElementData() && ci.isElementData()) {
/*  654 */           found = ((EncryptedElement)cn).equals((EncryptedElement)ci);
/*      */         } 
/*  656 */         if (found) {
/*  657 */           inferredNodeSet.remove(j);
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  663 */     if (inferredNodeSet.size() != 0)
/*      */     {
/*  665 */       return false;
/*      */     }
/*  667 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void dereferenceTargets(ArrayList targets, ArrayList<EncryptedData> nodeSet, SecurableSoapMessage message, boolean inferred) throws XWSSecurityException {
/*  680 */     Iterator<Target> i = targets.iterator();
/*  681 */     while (i.hasNext()) {
/*  682 */       Target t = i.next();
/*  683 */       boolean mandatory = t.getEnforce();
/*  684 */       boolean contentOnly = t.getContentOnly();
/*  685 */       Object object = null;
/*  686 */       EncryptedData data = null;
/*      */       try {
/*  688 */         if (!t.isAttachment()) {
/*  689 */           Element el = null;
/*  690 */           if (inferred && t instanceof EncryptionTarget) {
/*  691 */             el = ((EncryptionTarget)t).getElementData();
/*  692 */             data = new EncryptedElement(el, contentOnly);
/*  693 */             nodeSet.add(data); continue;
/*      */           } 
/*  695 */           object = message.getMessageParts(t);
/*  696 */           if (object instanceof Element) {
/*  697 */             data = new EncryptedElement((Element)object, contentOnly);
/*  698 */             nodeSet.add(data); continue;
/*  699 */           }  if (object instanceof NodeList) {
/*  700 */             NodeList nl = (NodeList)object;
/*  701 */             for (int j = 0; j < nl.getLength(); j++) {
/*  702 */               data = new EncryptedElement((Element)nl.item(j), contentOnly);
/*  703 */               nodeSet.add(data);
/*      */             }  continue;
/*  705 */           }  if (object instanceof Node) {
/*  706 */             data = new EncryptedElement((Element)object, contentOnly);
/*  707 */             nodeSet.add(data);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*  712 */         if (!inferred) {
/*  713 */           AttachmentPart ap = (AttachmentPart)message.getMessageParts(t);
/*  714 */           data = new AttachmentData(ap.getContentId(), contentOnly);
/*      */         } else {
/*  716 */           data = new AttachmentData(t.getValue(), contentOnly);
/*      */         } 
/*  718 */         nodeSet.add(data);
/*      */       }
/*  720 */       catch (XWSSecurityException ex) {
/*  721 */         if (!inferred && mandatory) {
/*  722 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0272_FAILEDTO_DEREFER_TARGETS());
/*  723 */           throw ex;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkPolicyEquivalence(MessagePolicy policy, Collection configuration) throws XWSSecurityException {
/*  751 */     if (policy != null) {
/*  752 */       Iterator<MessagePolicy> i = configuration.iterator();
/*      */       
/*  754 */       while (i.hasNext()) {
/*  755 */         MessagePolicy mPolicy = i.next();
/*      */         
/*  757 */         if (policy == mPolicy) {
/*      */           return;
/*      */         }
/*      */       } 
/*  761 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0263_INVALID_MESSAGE_POLICY());
/*  762 */       throw new XWSSecurityException("Message does not conform to configured policy");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void processMessagePolicy(FilterProcessingContext fpContext) throws XWSSecurityException {
/*  774 */     MessagePolicy policy = (MessagePolicy)fpContext.getSecurityPolicy();
/*      */     
/*  776 */     if (policy.dumpMessages()) {
/*  777 */       DumpFilter.process(fpContext);
/*      */     }
/*      */     
/*  780 */     if (policy.size() == 0) {
/*  781 */       fpContext.setMode(2);
/*  782 */       pProcess(fpContext);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*      */     try {
/*  789 */       if (policy.size() == 1 && PolicyTypeUtil.signatureConfirmationPolicy(policy.get(0))) {
/*      */         
/*  791 */         fpContext.setMode(2);
/*  792 */         pProcess(fpContext);
/*      */         return;
/*      */       } 
/*  795 */     } catch (Exception e) {
/*  796 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0273_FAILEDTO_PROCESS_POLICY(), e);
/*  797 */       throw new RuntimeException(e);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  802 */     SecurityHeader header = fpContext.getSecurableSoapMessage().findSecurityHeader();
/*  803 */     if (header == null) {
/*  804 */       StringBuffer buf = new StringBuffer();
/*  805 */       for (int it = 0; it < policy.size(); it++) {
/*      */         try {
/*  807 */           buf.append(policy.get(it).getType());
/*  808 */           if (PolicyTypeUtil.isPrimaryPolicy((WSSPolicy)policy.get(it))) {
/*  809 */             buf.append("(P) ");
/*      */           } else {
/*  811 */             buf.append("(S) ");
/*      */           } 
/*  813 */         } catch (Exception ex) {}
/*      */       } 
/*      */ 
/*      */       
/*  817 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0253_INVALID_MESSAGE());
/*  818 */       throw new XWSSecurityException("Message does not conform to configured policy [ " + buf.toString() + "]:  No Security Header found");
/*      */     } 
/*      */     
/*  821 */     SOAPElement current = header.getFirstChildElement();
/*  822 */     processMessagePolicy(fpContext, current);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void processMessagePolicy(FilterProcessingContext fpContext, SOAPElement current) throws XWSSecurityException {
/*  827 */     int idx = 0;
/*  828 */     MessagePolicy policy = (MessagePolicy)fpContext.getSecurityPolicy();
/*  829 */     SecurableSoapMessage secureMsg = fpContext.getSecurableSoapMessage();
/*  830 */     MessagePolicy secPolicy = null;
/*  831 */     ArrayList targets = null;
/*  832 */     StringBuffer buf = null;
/*      */     
/*  834 */     boolean foundPrimaryPolicy = false;
/*  835 */     while (idx < policy.size()) {
/*      */       
/*  837 */       WSSPolicy wssPolicy = null;
/*      */       try {
/*  839 */         wssPolicy = (WSSPolicy)policy.get(idx);
/*  840 */       } catch (Exception e) {
/*  841 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0270_FAILEDTO_GET_SECURITY_POLICY_MESSAGE_POLICY());
/*  842 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */ 
/*      */       
/*  846 */       if (PolicyTypeUtil.isPrimaryPolicy(wssPolicy)) {
/*  847 */         if (wssPolicy.getType().equals("EncryptionPolicy")) {
/*  848 */           targets = ((EncryptionPolicy.FeatureBinding)wssPolicy.getFeatureBinding()).getTargetBindings();
/*      */         } else {
/*  850 */           targets = ((SignaturePolicy.FeatureBinding)wssPolicy.getFeatureBinding()).getTargetBindings();
/*      */         } 
/*  852 */         foundPrimaryPolicy = true;
/*  853 */         Iterator<Target> ite = targets.iterator();
/*  854 */         while (ite.hasNext()) {
/*  855 */           Target t = ite.next();
/*  856 */           if (t.getEnforce()) {
/*      */ 
/*      */ 
/*      */             
/*  860 */             while (current != null && HarnessUtil.isSecondaryHeaderElement(current)) {
/*  861 */               current = HarnessUtil.getNextElement(current);
/*      */             }
/*      */             
/*  864 */             if (current != null) {
/*      */               
/*  866 */               secureMsg.findSecurityHeader().setCurrentHeaderElement(current);
/*      */ 
/*      */               
/*  869 */               fpContext.setSecurityPolicy((SecurityPolicy)wssPolicy);
/*  870 */               HarnessUtil.processDeep(fpContext);
/*      */               
/*  872 */               boolean keepCurrent = false;
/*  873 */               if ("EncryptedData".equals(current.getLocalName())) {
/*  874 */                 keepCurrent = true;
/*      */               }
/*      */               
/*  877 */               if (fpContext.isPrimaryPolicyViolation()) {
/*  878 */                 log.log(Level.SEVERE, LogStringsMessages.WSS_0265_ERROR_PRIMARY_POLICY());
/*  879 */                 throw new XWSSecurityException(fpContext.getPVE());
/*      */               } 
/*      */               
/*  882 */               if (fpContext.isOptionalPolicyViolation())
/*      */               {
/*      */ 
/*      */                 
/*  886 */                 secureMsg.findSecurityHeader().setCurrentHeaderElement(current);
/*      */               }
/*      */               
/*  889 */               if (!keepCurrent) {
/*  890 */                 current = secureMsg.findSecurityHeader().getCurrentHeaderBlockElement(); break;
/*      */               } 
/*  892 */               current = HarnessUtil.getNextElement(secureMsg.findSecurityHeader().getCurrentHeaderBlockElement());
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/*  898 */             if (buf == null)
/*  899 */               buf = new StringBuffer(); 
/*  900 */             buf.append(wssPolicy.getType() + " ");
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/*  907 */           while (current != null && HarnessUtil.isSecondaryHeaderElement(current)) {
/*  908 */             current = HarnessUtil.getNextElement(current);
/*      */           }
/*  910 */           if (current != null && wssPolicy.getType().equals("EncryptionPolicy") && current.getLocalName().equals("Signature")) {
/*      */             continue;
/*      */           }
/*  913 */           if (current != null && wssPolicy.getType().equals("SignaturePolicy") && (current.getLocalName().equals("EncryptedData") || current.getLocalName().equals("EncryptedKey") || current.getLocalName().equals("ReferenceList"))) {
/*      */             continue;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  921 */           if (current != null) {
/*      */             
/*  923 */             secureMsg.findSecurityHeader().setCurrentHeaderElement(current);
/*      */ 
/*      */             
/*  926 */             fpContext.setSecurityPolicy((SecurityPolicy)wssPolicy);
/*  927 */             HarnessUtil.processDeep(fpContext);
/*      */             
/*  929 */             boolean keepCurrent = false;
/*  930 */             if ("EncryptedData".equals(current.getLocalName())) {
/*  931 */               keepCurrent = true;
/*      */             }
/*      */             
/*  934 */             if (fpContext.isPrimaryPolicyViolation()) {
/*  935 */               log.log(Level.SEVERE, LogStringsMessages.WSS_0265_ERROR_PRIMARY_POLICY());
/*  936 */               throw new XWSSecurityException(fpContext.getPVE());
/*      */             } 
/*      */             
/*  939 */             if (fpContext.isOptionalPolicyViolation())
/*      */             {
/*      */ 
/*      */               
/*  943 */               secureMsg.findSecurityHeader().setCurrentHeaderElement(current);
/*      */             }
/*      */             
/*  946 */             if (!keepCurrent) {
/*  947 */               current = secureMsg.findSecurityHeader().getCurrentHeaderBlockElement(); break;
/*      */             } 
/*  949 */             current = HarnessUtil.getNextElement(secureMsg.findSecurityHeader().getCurrentHeaderBlockElement());
/*      */ 
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } else {
/*  957 */         if (secPolicy == null) {
/*  958 */           secPolicy = new MessagePolicy();
/*      */         }
/*  960 */         secPolicy.append((SecurityPolicy)wssPolicy);
/*      */       } 
/*      */       
/*  963 */       idx++;
/*      */     } 
/*      */     
/*  966 */     if (buf != null) {
/*  967 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0258_INVALID_REQUIREMENTS());
/*  968 */       throw new XWSSecurityException("More Receiver requirements [ " + buf + " ] specified" + " than present in the message");
/*      */     } 
/*      */ 
/*      */     
/*  972 */     if (!foundPrimaryPolicy) {
/*  973 */       SecurityHeader header = secureMsg.findSecurityHeader();
/*  974 */       if (header != null && header.getCurrentHeaderElement() == null) {
/*  975 */         header.setCurrentHeaderElement(header.getFirstChildElement());
/*      */       }
/*  977 */       checkForExtraSecurity(fpContext);
/*      */     } 
/*      */ 
/*      */     
/*  981 */     idx = 0;
/*  982 */     SecurityHeader securityHeader = secureMsg.findSecurityHeader();
/*      */     
/*  984 */     NodeList uList = securityHeader.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken");
/*  985 */     if (uList.getLength() > 1) {
/*  986 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0259_INVALID_SEC_USERNAME());
/*  987 */       throw new XWSSecurityException("More than one wsse:UsernameToken element present in security header");
/*      */     } 
/*      */     
/*  990 */     NodeList tList = securityHeader.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp");
/*  991 */     if (tList.getLength() > 1) {
/*  992 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0274_INVALID_SEC_TIMESTAMP());
/*  993 */       throw new XWSSecurityException("More than one wsu:Timestamp element present in security header");
/*      */     } 
/*      */     
/*  996 */     int unpCount = 0;
/*  997 */     int tspCount = 0;
/*  998 */     if (secPolicy != null)
/*      */     {
/* 1000 */       while (idx < secPolicy.size()) {
/* 1001 */         WSSPolicy wssPolicy = null;
/*      */         try {
/* 1003 */           wssPolicy = (WSSPolicy)secPolicy.get(idx);
/* 1004 */         } catch (Exception e) {
/* 1005 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0270_FAILEDTO_GET_SECURITY_POLICY_MESSAGE_POLICY());
/* 1006 */           throw new XWSSecurityException(e);
/*      */         } 
/* 1008 */         if (PolicyTypeUtil.authenticationTokenPolicy((SecurityPolicy)wssPolicy)) {
/* 1009 */           AuthenticationTokenPolicy atp = (AuthenticationTokenPolicy)wssPolicy;
/* 1010 */           WSSPolicy fb = (WSSPolicy)atp.getFeatureBinding();
/* 1011 */           if (PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)fb)) {
/* 1012 */             if (uList.getLength() == 0) {
/* 1013 */               log.log(Level.SEVERE, LogStringsMessages.WSS_0275_INVALID_POLICY_NO_USERNAME_SEC_HEADER());
/* 1014 */               throw new XWSSecurityException("Message does not conform to configured policy: wsse:UsernameToken element not found in security header");
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1019 */             unpCount++;
/* 1020 */           } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)fb)) {
/*      */           
/*      */           } 
/* 1023 */         } else if (PolicyTypeUtil.timestampPolicy((SecurityPolicy)wssPolicy)) {
/* 1024 */           if (tList.getLength() == 0) {
/* 1025 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0276_INVALID_POLICY_NO_TIMESTAMP_SEC_HEADER());
/* 1026 */             throw new XWSSecurityException("Message does not conform to configured policy: wsu:Timestamp element not found in security header");
/*      */           } 
/*      */ 
/*      */           
/* 1030 */           tspCount++;
/*      */         } 
/*      */         
/* 1033 */         fpContext.setSecurityPolicy((SecurityPolicy)wssPolicy);
/* 1034 */         HarnessUtil.processDeep(fpContext);
/*      */         
/* 1036 */         idx++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1041 */     if (uList.getLength() > unpCount) {
/* 1042 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0259_INVALID_SEC_USERNAME());
/* 1043 */       throw new XWSSecurityException("Message does not conform to configured policy: Additional wsse:UsernameToken element found in security header");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1060 */     fpContext.setSecurityPolicy((SecurityPolicy)policy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkForExtraSecurity(FilterProcessingContext context) throws XWSSecurityException {
/* 1067 */     SecurityHeader header = context.getSecurableSoapMessage().findSecurityHeader();
/*      */     
/* 1069 */     if (header == null || header.getCurrentHeaderElement() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1078 */     Node nextNode = header.getCurrentHeaderElement().getNextSibling();
/* 1079 */     for (; nextNode != null; 
/* 1080 */       nextNode = nextNode.getNextSibling()) {
/* 1081 */       if (nextNode instanceof SOAPElement) {
/* 1082 */         SOAPElement current = (SOAPElement)nextNode;
/* 1083 */         if (!HarnessUtil.isSecondaryHeaderElement(current)) {
/*      */           
/* 1085 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0277_INVALID_ADDTIONAL_SEC_MESSAGE_POLICY());
/* 1086 */           throw new XWSSecurityException("Message does not conform to configured policy (found " + current.getLocalName() + ") : " + "Additional security than required found");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkForExtraSecondarySecurity(FilterProcessingContext context) throws XWSSecurityException {
/* 1100 */     SecurityHeader header = context.getSecurableSoapMessage().findSecurityHeader();
/* 1101 */     MessagePolicy policy = (MessagePolicy)context.getSecurityPolicy();
/*      */     
/* 1103 */     boolean _UT = false;
/* 1104 */     boolean _TS = false;
/*      */     
/* 1106 */     SOAPElement current = header.getFirstChildElement();
/* 1107 */     for (; current != null; 
/* 1108 */       current = (SOAPElement)current.getNextSibling()) {
/*      */       try {
/* 1110 */         _UT = current.getLocalName().equals("UsernameToken");
/* 1111 */         _TS = current.getLocalName().equals("Timestamp");
/* 1112 */       } catch (Exception e) {
/* 1113 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0278_FAILEDTO_GET_LOCAL_NAME());
/* 1114 */         throw new XWSSecurityRuntimeException(e);
/*      */       } 
/*      */     } 
/*      */     
/* 1118 */     boolean throwFault = false;
/* 1119 */     StringBuffer buf = null;
/*      */     
/* 1121 */     if (!_UT)
/* 1122 */       for (int i = 0; i < policy.size(); i++) {
/*      */         try {
/* 1124 */           if (PolicyTypeUtil.usernameTokenPolicy(policy.get(i))) {
/* 1125 */             if (buf == null) {
/* 1126 */               buf = new StringBuffer();
/*      */             }
/* 1128 */             buf.append(policy.get(i).getType() + " ");
/* 1129 */             throwFault = true;
/*      */           } 
/* 1131 */         } catch (Exception e) {
/* 1132 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0279_FAILED_CHECK_SEC_SECURITY(), e);
/* 1133 */           throw new XWSSecurityRuntimeException(e);
/*      */         } 
/*      */       }  
/* 1136 */     if (!_TS)
/* 1137 */       for (int j = 0; j < policy.size(); j++) {
/*      */         try {
/* 1139 */           if (PolicyTypeUtil.timestampPolicy(policy.get(j))) {
/* 1140 */             if (buf == null) {
/* 1141 */               buf = new StringBuffer();
/*      */             }
/* 1143 */             buf.append(policy.get(j).getType() + " ");
/* 1144 */             throwFault = true;
/*      */           } 
/* 1146 */         } catch (Exception e) {
/* 1147 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0279_FAILED_CHECK_SEC_SECURITY(), e);
/* 1148 */           throw new XWSSecurityRuntimeException(e);
/*      */         } 
/*      */       }  
/* 1151 */     if (throwFault)
/* 1152 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0277_INVALID_ADDTIONAL_SEC_MESSAGE_POLICY()); 
/* 1153 */     throw new XWSSecurityException("Message does not conform to configured policy: Additional security [ " + buf.toString() + "] than required found");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean pProcessOnce(FilterProcessingContext fpContext, SOAPElement current, boolean isSecondary) throws XWSSecurityException {
/* 1170 */     boolean processed = false;
/*      */     
/* 1172 */     String elementName = current.getLocalName();
/*      */     
/* 1174 */     if (isSecondary) {
/* 1175 */       if ("UsernameToken".equals(elementName)) {
/* 1176 */         AuthenticationTokenFilter.processUserNameToken(fpContext);
/* 1177 */         processed = true;
/*      */       }
/* 1179 */       else if ("Timestamp".equals(elementName)) {
/* 1180 */         TimestampFilter.process(fpContext);
/* 1181 */         processed = true;
/*      */       }
/* 1183 */       else if ("SignatureConfirmation".equals(elementName)) {
/* 1184 */         SignatureConfirmationFilter.process(fpContext);
/* 1185 */         processed = true;
/* 1186 */       } else if (!"BinarySecurityToken".equals(elementName)) {
/*      */ 
/*      */         
/* 1189 */         if ("Assertion".equals(elementName)) {
/* 1190 */           AuthenticationTokenFilter.processSamlToken(fpContext);
/* 1191 */         } else if (!"SecurityTokenReference".equals(elementName)) {
/*      */           
/* 1193 */           if ("SecurityContextToken".equals(elementName));
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 1198 */     } else if ("Signature".equals(elementName)) {
/* 1199 */       SignatureFilter.process(fpContext);
/* 1200 */       processed = true;
/*      */     }
/* 1202 */     else if ("EncryptedKey".equals(elementName)) {
/* 1203 */       Iterator iter = null;
/*      */       
/*      */       try {
/* 1206 */         iter = current.getChildElements(SOAPFactory.newInstance().createName("ReferenceList", "xenc", "http://www.w3.org/2001/04/xmlenc#"));
/*      */       
/*      */       }
/* 1209 */       catch (Exception e) {
/* 1210 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0360_ERROR_CREATING_RLHB(e));
/* 1211 */         throw new XWSSecurityException(e);
/*      */       } 
/* 1213 */       if (iter.hasNext()) {
/* 1214 */         EncryptionFilter.process(fpContext);
/* 1215 */         processed = true;
/*      */       }
/*      */     
/* 1218 */     } else if ("ReferenceList".equals(elementName)) {
/* 1219 */       EncryptionFilter.process(fpContext);
/* 1220 */       processed = true;
/*      */     }
/* 1222 */     else if ("EncryptedData".equals(elementName)) {
/* 1223 */       EncryptionFilter.process(fpContext);
/* 1224 */       processed = true;
/*      */     }
/* 1226 */     else if (!HarnessUtil.isSecondaryHeaderElement(current)) {
/* 1227 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0204_ILLEGAL_HEADER_BLOCK(elementName));
/* 1228 */       HarnessUtil.throwWssSoapFault("Unrecognized header block: " + elementName);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1233 */     return processed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void pProcess(FilterProcessingContext fpContext) throws XWSSecurityException {
/* 1254 */     SecurityHeader header = fpContext.getSecurableSoapMessage().findSecurityHeader();
/*      */     
/* 1256 */     if (header == null) {
/* 1257 */       SecurityPolicy policy = fpContext.getSecurityPolicy();
/* 1258 */       if (policy != null) {
/* 1259 */         if (PolicyTypeUtil.messagePolicy(policy)) {
/* 1260 */           if (!((MessagePolicy)policy).isEmpty()) {
/* 1261 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0253_INVALID_MESSAGE());
/* 1262 */             throw new XWSSecurityException("Message does not conform to configured policy: No Security Header found in incoming message");
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1268 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0253_INVALID_MESSAGE());
/* 1269 */           throw new XWSSecurityException("Message does not conform to configured policy: No Security Header found in incoming message");
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1278 */     SOAPElement current = header.getCurrentHeaderBlockElement();
/* 1279 */     SOAPElement first = current;
/* 1280 */     SOAPElement prev = null;
/* 1281 */     while (current != null) {
/*      */       
/* 1283 */       pProcessOnce(fpContext, current, false);
/* 1284 */       if (fpContext.getMode() == 2 && "EncryptedData".equals(current.getLocalName()) && prev != null) {
/*      */ 
/*      */         
/* 1287 */         header.setCurrentHeaderElement(prev);
/*      */       } else {
/*      */         
/* 1290 */         prev = current;
/*      */       } 
/* 1292 */       current = header.getCurrentHeaderBlockElement();
/*      */     } 
/*      */     
/* 1295 */     current = first;
/* 1296 */     header.setCurrentHeaderElement(current);
/*      */     
/* 1298 */     while (current != null) {
/* 1299 */       pProcessOnce(fpContext, current, true);
/* 1300 */       current = header.getCurrentHeaderBlockElement();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void handleFault(ProcessingContext context) {}
/*      */ 
/*      */ 
/*      */   
/*      */   private static interface EncryptedData
/*      */   {
/*      */     boolean isElementData();
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isAttachmentData();
/*      */   }
/*      */ 
/*      */   
/*      */   private static class AttachmentData
/*      */     implements EncryptedData
/*      */   {
/* 1323 */     private String cid = null; private boolean contentOnly = false;
/*      */     
/*      */     public AttachmentData(String cid, boolean co) {
/* 1326 */       this.cid = cid;
/* 1327 */       this.contentOnly = co;
/*      */     }
/*      */     public String getCID() {
/* 1330 */       return this.cid;
/*      */     }
/*      */     public boolean isContentOnly() {
/* 1333 */       return this.contentOnly;
/*      */     }
/*      */     
/*      */     public boolean equals(AttachmentData data) {
/* 1337 */       if (this.cid != null && this.cid.equals(data.getCID()) && this.contentOnly == data.isContentOnly())
/*      */       {
/* 1339 */         return true;
/*      */       }
/* 1341 */       return false;
/*      */     }
/*      */     
/*      */     public boolean isElementData() {
/* 1345 */       return false;
/*      */     }
/*      */     
/*      */     public boolean isAttachmentData() {
/* 1349 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class EncryptedElement implements EncryptedData {
/*      */     private Element element;
/*      */     private boolean contentOnly;
/* 1356 */     private EncryptionPolicy policy = null;
/*      */     
/*      */     public EncryptedElement(Element element, boolean contentOnly) {
/* 1359 */       this.element = element;
/* 1360 */       this.contentOnly = contentOnly;
/*      */     }
/*      */     
/*      */     public Element getElement() {
/* 1364 */       return this.element;
/*      */     }
/*      */     
/*      */     public boolean getContentOnly() {
/* 1368 */       return this.contentOnly;
/*      */     }
/*      */     
/*      */     public boolean equals(EncryptedElement element) {
/* 1372 */       EncryptedElement encryptedElement = element;
/* 1373 */       return (encryptedElement.getElement() == this.element && encryptedElement.getContentOnly() == this.contentOnly);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setpolicy(EncryptionPolicy policy) {
/* 1380 */       this.policy = policy;
/*      */     }
/*      */     
/*      */     public EncryptionPolicy getPolicy() {
/* 1384 */       return this.policy;
/*      */     }
/*      */     
/*      */     public boolean isElementData() {
/* 1388 */       return true;
/*      */     }
/*      */     
/*      */     public boolean isAttachmentData() {
/* 1392 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\SecurityRecipient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */