/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.verifier.TargetResolver;
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
/*     */ public class TargetResolverImpl
/*     */   implements TargetResolver
/*     */ {
/*  71 */   private ProcessingContext ctx = null;
/*  72 */   private StringBuffer tokenList = new StringBuffer();
/*  73 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TargetResolverImpl(ProcessingContext ctx) {
/*  79 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resolveAndVerifyTargets(List<Target> actualTargets, List<Target> inferredTargets, WSSPolicy actualPolicy) throws XWSSecurityException {
/*  86 */     String policyType = PolicyTypeUtil.signaturePolicy((SecurityPolicy)actualPolicy) ? "Signature" : "Encryption";
/*  87 */     boolean isEndorsing = false;
/*     */     
/*  89 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)actualPolicy)) {
/*  90 */       SignaturePolicy.FeatureBinding fp = (SignaturePolicy.FeatureBinding)actualPolicy.getFeatureBinding();
/*  91 */       if (fp.isEndorsingSignature()) {
/*  92 */         isEndorsing = true;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     for (Target actualTarget : actualTargets) {
/*  97 */       if ("Signature".equals(policyType) && ((SignatureTarget)actualTarget).isITNever() == true) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 102 */       boolean found = false;
/* 103 */       String targetInPolicy = getTargetValue(actualTarget);
/* 104 */       for (Target inferredTarget : inferredTargets) {
/* 105 */         String targetInMessage = getTargetValue(inferredTarget);
/* 106 */         if (targetInPolicy != null && targetInPolicy.equals(targetInMessage)) {
/* 107 */           found = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 111 */       if (targetInPolicy != null && targetInPolicy.equals("BinarySecurityToken") && !found) {
/* 112 */         if (!containsSTRTransform(actualTarget, inferredTargets)) {
/* 113 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0206_POLICY_VIOLATION_EXCEPTION());
/* 114 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0814_POLICY_VERIFICATION_ERROR_MISSING_TARGET(targetInPolicy, policyType));
/* 115 */           throw new XWSSecurityException("Policy verification error:Missing target " + targetInPolicy + " for " + policyType);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 120 */       if (!found && targetInPolicy != null)
/*     */       {
/*     */ 
/*     */         
/* 124 */         if (presentInMessage(targetInPolicy)) {
/* 125 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0206_POLICY_VIOLATION_EXCEPTION());
/* 126 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0814_POLICY_VERIFICATION_ERROR_MISSING_TARGET(targetInPolicy, policyType));
/* 127 */           if (isEndorsing) {
/* 128 */             throw new XWSSecurityException("Policy verification error:Missing target " + targetInPolicy + " for Endorsing " + policyType);
/*     */           }
/*     */           
/* 131 */           throw new XWSSecurityException("Policy verification error:Missing target " + targetInPolicy + " for " + policyType);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 138 */     if ("Signature".equals(policyType) && 
/* 139 */       countSTRTransforms(actualTargets, true) != countSTRTransforms(inferredTargets, false)) {
/* 140 */       if (isEndorsing) {
/* 141 */         throw new XWSSecurityException("Policy verification error:Missing reference for one of { " + this.tokenList.toString() + "} for Endorsing " + policyType);
/*     */       }
/*     */       
/* 144 */       this.tokenList.delete(this.tokenList.lastIndexOf(","), this.tokenList.length());
/* 145 */       throw new XWSSecurityException("Policy verification error:Missing reference for one of { " + this.tokenList.toString() + "}  for " + policyType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean containsSTRTransform(Target actualTarget, List<Target> inferredTargets) {
/* 153 */     for (Target inferredTarget : inferredTargets) {
/* 154 */       SignatureTarget st = (SignatureTarget)inferredTarget;
/* 155 */       ArrayList ar = st.getTransforms();
/* 156 */       Iterator<SignatureTarget.Transform> it = ar.iterator();
/* 157 */       while (it.hasNext()) {
/* 158 */         SignatureTarget.Transform str = it.next();
/* 159 */         if (str.getTransform().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform"))
/*     */         {
/* 161 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     return false;
/*     */   }
/*     */   
/*     */   private int countSTRTransforms(List<Target> targets, boolean isActualTarget) {
/* 170 */     int strTransformCount = 0;
/* 171 */     for (Target target : targets) {
/* 172 */       if (target instanceof SignatureTarget) {
/* 173 */         SignatureTarget st = (SignatureTarget)target;
/* 174 */         ArrayList ar = st.getTransforms();
/* 175 */         Iterator<SignatureTarget.Transform> it = ar.iterator();
/* 176 */         while (it.hasNext()) {
/* 177 */           SignatureTarget.Transform str = it.next();
/* 178 */           if (str.getTransform().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform")) {
/* 179 */             strTransformCount++;
/* 180 */             if (isActualTarget) {
/* 181 */               String localPart = (st.getPolicyQName() != null) ? st.getPolicyQName().getLocalPart() : "";
/* 182 */               this.tokenList.append(localPart); this.tokenList.append(", ");
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 188 */     return strTransformCount;
/*     */   }
/*     */   
/*     */   private String getTargetValue(Target target) {
/* 192 */     String targetInPolicy = null;
/* 193 */     if ("qname".equals(target.getType())) {
/* 194 */       targetInPolicy = target.getQName().getLocalPart();
/* 195 */     } else if ("uri".equals(target.getType())) {
/* 196 */       if (target.getPolicyQName() != null) {
/* 197 */         targetInPolicy = target.getPolicyQName().getLocalPart();
/*     */       } else {
/* 199 */         String val = target.getValue();
/* 200 */         String id = null;
/* 201 */         if (val.charAt(0) == '#') {
/* 202 */           id = val.substring(1, val.length());
/*     */         } else {
/* 204 */           id = val;
/*     */         } 
/* 206 */         targetInPolicy = getElementById(id);
/* 207 */         if (targetInPolicy == null && id.startsWith("SAML")) {
/* 208 */           return "Assertion";
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     return targetInPolicy;
/*     */   }
/*     */   
/*     */   private String getElementById(String id) {
/* 217 */     SecurityContext sc = ((JAXBFilterProcessingContext)this.ctx).getSecurityContext();
/*     */     
/* 219 */     HeaderList headers = sc.getNonSecurityHeaders();
/*     */     
/* 221 */     if (headers != null && headers.size() > 0) {
/* 222 */       Iterator<Header> listItr = headers.listIterator();
/* 223 */       while (listItr.hasNext()) {
/* 224 */         GenericSecuredHeader header = (GenericSecuredHeader)listItr.next();
/* 225 */         if (header.hasID(id)) {
/* 226 */           return header.getLocalPart();
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 232 */     ArrayList<SecurityHeaderElement> processedHeaders = sc.getProcessedSecurityHeaders();
/* 233 */     for (int j = 0; j < processedHeaders.size(); j++) {
/* 234 */       SecurityHeaderElement header = processedHeaders.get(j);
/* 235 */       if (id.equals(header.getId())) {
/* 236 */         return header.getLocalPart();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 241 */     ArrayList<SecurityHeaderElement> bufferedHeaders = sc.getBufferedSecurityHeaders();
/* 242 */     for (int i = 0; i < bufferedHeaders.size(); i++) {
/* 243 */       SecurityHeaderElement header = bufferedHeaders.get(i);
/* 244 */       if (id.equals(header.getId())) {
/* 245 */         return header.getLocalPart();
/*     */       }
/*     */     } 
/* 248 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean presentInMessage(String targetInPolicy) {
/* 253 */     if ("Body".equals(targetInPolicy)) {
/* 254 */       return true;
/*     */     }
/*     */     
/* 257 */     SecurityContext sc = ((JAXBFilterProcessingContext)this.ctx).getSecurityContext();
/*     */     
/* 259 */     HeaderList headers = sc.getNonSecurityHeaders();
/*     */     
/* 261 */     if (headers != null && headers.size() > 0) {
/* 262 */       Iterator<Header> listItr = headers.listIterator();
/* 263 */       while (listItr.hasNext()) {
/* 264 */         GenericSecuredHeader header = (GenericSecuredHeader)listItr.next();
/* 265 */         if (header != null && header.getLocalPart().equals(targetInPolicy)) {
/* 266 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 272 */     ArrayList<SecurityHeaderElement> processedHeaders = sc.getProcessedSecurityHeaders();
/* 273 */     for (int j = 0; j < processedHeaders.size(); j++) {
/* 274 */       SecurityHeaderElement header = processedHeaders.get(j);
/* 275 */       if (header != null && header.getLocalPart().equals(targetInPolicy)) {
/* 276 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 281 */     ArrayList<SecurityHeaderElement> bufferedHeaders = sc.getBufferedSecurityHeaders();
/* 282 */     for (int i = 0; i < bufferedHeaders.size(); i++) {
/* 283 */       SecurityHeaderElement header = bufferedHeaders.get(i);
/* 284 */       if (header != null && header.getLocalPart().equals(targetInPolicy)) {
/* 285 */         return true;
/*     */       }
/*     */     } 
/* 288 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTargetPresent(List<Target> actualTargets) throws XWSSecurityException {
/* 293 */     for (Target actualTarget : actualTargets) {
/* 294 */       String targetInPolicy = getTargetValue(actualTarget);
/* 295 */       if (presentInMessage(targetInPolicy)) {
/* 296 */         return true;
/*     */       }
/*     */     } 
/* 299 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\TargetResolverImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */