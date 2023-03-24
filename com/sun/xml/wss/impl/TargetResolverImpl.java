/*     */ package com.sun.xml.wss.impl;
/*     */ 
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.SecurityHeader;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.verifier.TargetResolver;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathConstants;
/*     */ import javax.xml.xpath.XPathExpression;
/*     */ import javax.xml.xpath.XPathExpressionException;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TargetResolverImpl
/*     */   implements TargetResolver
/*     */ {
/*  70 */   private ProcessingContext ctx = null;
/*  71 */   private FilterProcessingContext fpContext = null;
/*  72 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TargetResolverImpl(ProcessingContext ctx) {
/*  78 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void resolveAndVerifyTargets(List<Target> actualTargets, List<Target> inferredTargets, WSSPolicy actualPolicy) throws XWSSecurityException {
/*  84 */     String policyType = PolicyTypeUtil.signaturePolicy((SecurityPolicy)actualPolicy) ? "Signature" : "Encryption";
/*  85 */     boolean isEndorsing = false;
/*     */     
/*  87 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)actualPolicy)) {
/*  88 */       SignaturePolicy.FeatureBinding fp = (SignaturePolicy.FeatureBinding)actualPolicy.getFeatureBinding();
/*  89 */       if (fp.isEndorsingSignature()) {
/*  90 */         isEndorsing = true;
/*     */       }
/*     */     } 
/*     */     
/*  94 */     this.fpContext = new FilterProcessingContext(this.ctx);
/*  95 */     SecurityHeader header = this.fpContext.getSecurableSoapMessage().findSecurityHeader();
/*  96 */     Document doc = header.getOwnerDocument();
/*     */     
/*  98 */     for (Target actualTarget : actualTargets) {
/*  99 */       boolean found = false;
/* 100 */       String targetInPolicy = getTargetValue(doc, actualTarget);
/* 101 */       for (Target inferredTarget : inferredTargets) {
/* 102 */         String targetInMessage = getTargetValue(doc, inferredTarget);
/* 103 */         if (targetInPolicy != null && targetInPolicy.equals(targetInMessage)) {
/* 104 */           found = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 108 */       if (!found && targetInPolicy != null) {
/*     */ 
/*     */         
/* 111 */         NodeList nl = doc.getElementsByTagName(targetInPolicy);
/* 112 */         if (nl != null && nl.getLength() > 0) {
/* 113 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0206_POLICY_VIOLATION_EXCEPTION());
/* 114 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0814_POLICY_VERIFICATION_ERROR_MISSING_TARGET(targetInPolicy, policyType));
/* 115 */           if (isEndorsing) {
/* 116 */             throw new XWSSecurityException("Policy verification error:Missing target " + targetInPolicy + " for Endorsing " + policyType);
/*     */           }
/*     */           
/* 119 */           throw new XWSSecurityException("Policy verification error:Missing target " + targetInPolicy + " for " + policyType);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getTargetValue(Document doc, Target actualTarget) {
/* 129 */     String targetInPolicy = null;
/* 130 */     if (actualTarget.getType() == "qname") {
/* 131 */       targetInPolicy = actualTarget.getQName().getLocalPart();
/* 132 */     } else if (actualTarget.getType() == "uri") {
/* 133 */       String val = actualTarget.getValue();
/* 134 */       String id = null;
/* 135 */       if (val.charAt(0) == '#') {
/* 136 */         id = val.substring(1, val.length());
/*     */       } else {
/* 138 */         id = val;
/* 139 */       }  Element signedElement = doc.getElementById(id);
/* 140 */       if (signedElement != null) {
/* 141 */         targetInPolicy = signedElement.getLocalName();
/*     */       }
/*     */     } 
/* 144 */     return targetInPolicy;
/*     */   }
/*     */   
/*     */   public boolean isTargetPresent(List<Target> actualTargets) throws XWSSecurityException {
/* 148 */     FilterProcessingContext fpContext = new FilterProcessingContext(this.ctx);
/* 149 */     SecurityHeader header = fpContext.getSecurableSoapMessage().findSecurityHeader();
/* 150 */     Document doc = header.getOwnerDocument();
/* 151 */     for (Target actualTarget : actualTargets) {
/* 152 */       if (actualTarget.getType() == "xpath") {
/* 153 */         String val = actualTarget.getValue();
/*     */         try {
/* 155 */           XPathFactory xpathFactory = WSITXMLFactory.createXPathFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 156 */           XPath xpath = xpathFactory.newXPath();
/* 157 */           xpath.setNamespaceContext(fpContext.getSecurableSoapMessage().getNamespaceContext());
/* 158 */           XPathExpression xpathExpr = xpath.compile(val);
/* 159 */           NodeList nodes = (NodeList)xpathExpr.evaluate(fpContext.getSecurableSoapMessage().getSOAPPart(), XPathConstants.NODESET);
/* 160 */           if (nodes != null && nodes.getLength() > 0) {
/* 161 */             return true;
/*     */           }
/* 163 */         } catch (XPathExpressionException xpe) {
/* 164 */           throw new XWSSecurityException(xpe);
/*     */         }  continue;
/*     */       } 
/* 167 */       String targetInPolicy = getTargetValue(doc, actualTarget);
/* 168 */       NodeList nl = doc.getElementsByTagName(targetInPolicy);
/* 169 */       if (nl != null && nl.getLength() > 0) {
/* 170 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 174 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\TargetResolverImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */