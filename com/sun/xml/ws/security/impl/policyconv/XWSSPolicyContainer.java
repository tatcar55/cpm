/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.security.policy.MessageLayout;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XWSSPolicyContainer
/*     */ {
/*     */   private boolean isServer;
/*     */   private boolean isIncoming;
/*     */   private Section section;
/*     */   private List<SecurityPolicy> policyList;
/*     */   private List<SecurityPolicy> effectivePolicyList;
/*     */   private MessageLayout mode;
/*     */   
/*     */   private boolean encPoliciesContain(QName qName, List<SecurityPolicy> encPolicies) {
/*  66 */     if (qName.equals(Target.BODY_QNAME)) {
/*  67 */       return false;
/*     */     }
/*  69 */     for (SecurityPolicy sp : encPolicies) {
/*  70 */       if (PolicyTypeUtil.encryptionPolicy(sp)) {
/*  71 */         EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)((EncryptionPolicy)sp).getFeatureBinding();
/*  72 */         ArrayList<Target> targets = fb.getTargetBindings();
/*  73 */         for (int i = 0; i < targets.size(); i++) {
/*  74 */           Target t = targets.get(i);
/*  75 */           if (t.getType() == "qname" && 
/*  76 */             qName.equals(t.getQName())) {
/*  77 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   private void fixEncryptedTargetsInSignature(MessagePolicy msgPolicy, boolean isWSS11) {
/*  87 */     boolean encryptBeforeSign = false;
/*  88 */     boolean seenEncryptPolicy = false;
/*  89 */     boolean seenSignPolicy = false;
/*  90 */     List<SecurityPolicy> encPolicies = new ArrayList<SecurityPolicy>();
/*     */     
/*  92 */     for (Object policy : msgPolicy.getPrimaryPolicies()) {
/*     */       
/*  94 */       if (policy instanceof SecurityPolicy) {
/*  95 */         SecurityPolicy secPolicy = (SecurityPolicy)policy;
/*  96 */         if (PolicyTypeUtil.signaturePolicy(secPolicy)) {
/*  97 */           seenSignPolicy = true;
/*  98 */           if (!seenEncryptPolicy && this.isIncoming)
/*  99 */             encryptBeforeSign = true;  continue;
/*     */         } 
/* 101 */         if (PolicyTypeUtil.encryptionPolicy(secPolicy)) {
/* 102 */           seenEncryptPolicy = true;
/* 103 */           if (!seenSignPolicy && !this.isIncoming) {
/* 104 */             encryptBeforeSign = true;
/*     */           }
/* 106 */           encPolicies.add(secPolicy);
/*     */         } 
/*     */       } 
/*     */     } 
/* 110 */     if (encryptBeforeSign) {
/* 111 */       for (Object policy : msgPolicy.getPrimaryPolicies()) {
/* 112 */         boolean containsEncryptedHeader = false;
/* 113 */         if (policy instanceof SecurityPolicy) {
/* 114 */           SecurityPolicy secPolicy = (SecurityPolicy)policy;
/* 115 */           if (PolicyTypeUtil.signaturePolicy(secPolicy)) {
/* 116 */             SignaturePolicy.FeatureBinding sfb = (SignaturePolicy.FeatureBinding)((SignaturePolicy)secPolicy).getFeatureBinding();
/* 117 */             ArrayList<Target> targets = sfb.getTargetBindings();
/* 118 */             for (int i = 0; i < targets.size(); i++) {
/* 119 */               Target t = targets.get(i);
/* 120 */               if (t.getType() == "qname" && 
/* 121 */                 encPoliciesContain(t.getQName(), encPolicies)) {
/* 122 */                 if (isWSS11) {
/* 123 */                   if (!containsEncryptedHeader) {
/* 124 */                     QName encHeaderQName = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "EncryptedHeader");
/* 125 */                     t.setQName(encHeaderQName);
/* 126 */                     containsEncryptedHeader = true;
/*     */                   } else {
/* 128 */                     targets.remove(i);
/*     */                   }
/*     */                 
/* 131 */                 } else if (!containsEncryptedHeader) {
/* 132 */                   QName encDataQName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "EncryptedData");
/* 133 */                   t.setQName(encDataQName);
/* 134 */                   containsEncryptedHeader = true;
/*     */                 } else {
/* 136 */                   targets.remove(i);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum Section
/*     */   {
/* 150 */     ClientIncomingPolicy,
/* 151 */     ClientOutgoingPolicy,
/* 152 */     ServerIncomingPolicy,
/* 153 */     ServerOutgoingPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   private int foundTimestamp = -1;
/*     */   
/*     */   private boolean modified = false;
/*     */ 
/*     */   
/*     */   public XWSSPolicyContainer(MessageLayout mode, boolean isServer, boolean isIncoming) {
/* 166 */     this.mode = mode;
/* 167 */     this.isServer = isServer;
/* 168 */     this.isIncoming = isIncoming;
/* 169 */     setMessageMode(isServer, isIncoming);
/* 170 */     this.effectivePolicyList = new ArrayList<SecurityPolicy>();
/*     */   }
/*     */   public XWSSPolicyContainer(boolean isServer, boolean isIncoming) {
/* 173 */     setMessageMode(isServer, isIncoming);
/* 174 */     this.isServer = isServer;
/* 175 */     this.isIncoming = isIncoming;
/* 176 */     this.effectivePolicyList = new ArrayList<SecurityPolicy>();
/*     */   }
/*     */   public void setMessageMode(boolean isServer, boolean isIncoming) {
/* 179 */     if (isServer && isIncoming) {
/* 180 */       this.section = Section.ServerIncomingPolicy;
/* 181 */     } else if (isServer && !isIncoming) {
/* 182 */       this.section = Section.ServerOutgoingPolicy;
/* 183 */     } else if (!isServer && isIncoming) {
/* 184 */       this.section = Section.ClientIncomingPolicy;
/* 185 */     } else if (!isServer && !isIncoming) {
/* 186 */       this.section = Section.ClientOutgoingPolicy;
/*     */     } 
/*     */   }
/*     */   public void setPolicyContainerMode(MessageLayout mode) {
/* 190 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(SecurityPolicy secPolicy) {
/* 198 */     if (secPolicy == null) {
/*     */       return;
/*     */     }
/* 201 */     if (this.policyList == null) {
/* 202 */       this.policyList = new ArrayList<SecurityPolicy>();
/*     */     }
/* 204 */     if (isSupportingToken(secPolicy)) {
/* 205 */       switch (this.section) {
/*     */         case ServerOutgoingPolicy:
/*     */         case ClientIncomingPolicy:
/*     */           return;
/*     */       } 
/*     */     }
/* 211 */     this.modified = true;
/* 212 */     this.policyList.add(secPolicy);
/*     */   }
/*     */   public MessagePolicy getMessagePolicy(boolean isWSS11) throws PolicyGenerationException {
/* 215 */     if (this.modified) {
/* 216 */       convert();
/* 217 */       this.modified = false;
/*     */     } 
/* 219 */     MessagePolicy msgPolicy = new MessagePolicy();
/*     */     
/* 221 */     msgPolicy.appendAll(this.effectivePolicyList);
/* 222 */     removeEmptyPrimaryPolicies(msgPolicy);
/* 223 */     fixEncryptedTargetsInSignature(msgPolicy, isWSS11);
/* 224 */     return msgPolicy;
/*     */   }
/*     */   
/*     */   private void removeEmptyPrimaryPolicies(MessagePolicy msgPolicy) {
/* 228 */     for (Object policy : msgPolicy.getPrimaryPolicies()) {
/* 229 */       if (policy instanceof SecurityPolicy) {
/* 230 */         SecurityPolicy secPolicy = (SecurityPolicy)policy;
/* 231 */         if (PolicyTypeUtil.signaturePolicy(secPolicy)) {
/* 232 */           if (((SignaturePolicy.FeatureBinding)((SignaturePolicy)secPolicy).getFeatureBinding()).getTargetBindings().size() == 0)
/* 233 */             msgPolicy.remove(secPolicy);  continue;
/*     */         } 
/* 235 */         if (PolicyTypeUtil.encryptionPolicy(secPolicy) && (
/* 236 */           (EncryptionPolicy.FeatureBinding)((EncryptionPolicy)secPolicy).getFeatureBinding()).getTargetBindings().size() == 0) {
/* 237 */           msgPolicy.remove(secPolicy);
/*     */         }
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
/*     */   private void appendAfterToken(SecurityPolicy xwssPolicy) {
/* 250 */     int pos = -1;
/* 251 */     for (SecurityPolicy secPolicy : this.effectivePolicyList) {
/* 252 */       if (isSupportingToken(secPolicy) || isTimestamp(secPolicy)) {
/*     */         continue;
/*     */       }
/* 255 */       pos = this.effectivePolicyList.indexOf(secPolicy);
/*     */     } 
/*     */ 
/*     */     
/* 259 */     if (pos != -1) {
/* 260 */       this.effectivePolicyList.add(pos, xwssPolicy);
/*     */     } else {
/* 262 */       this.effectivePolicyList.add(xwssPolicy);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void prependBeforeToken(SecurityPolicy xwssPolicy) {
/* 270 */     int pos = -1;
/* 271 */     for (SecurityPolicy secPolicy : this.effectivePolicyList) {
/* 272 */       if (!isSupportingToken(secPolicy)) {
/*     */         continue;
/*     */       }
/* 275 */       pos = this.effectivePolicyList.indexOf(secPolicy);
/*     */     } 
/*     */     
/* 278 */     if (pos != -1) {
/* 279 */       this.effectivePolicyList.add(pos, xwssPolicy);
/*     */     } else {
/* 281 */       this.effectivePolicyList.add(xwssPolicy);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void append(SecurityPolicy xwssPolicy) {
/* 289 */     this.effectivePolicyList.add(xwssPolicy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void prepend(SecurityPolicy xwssPolicy) {
/* 296 */     this.effectivePolicyList.add(0, xwssPolicy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSupportingToken(SecurityPolicy xwssPolicy) {
/* 303 */     if (xwssPolicy == null) {
/* 304 */       return false;
/*     */     }
/*     */     
/* 307 */     if (PolicyTypeUtil.authenticationTokenPolicy(xwssPolicy)) {
/* 308 */       MLSPolicy binding = ((AuthenticationTokenPolicy)xwssPolicy).getFeatureBinding();
/* 309 */       if (PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)binding) || PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)binding) || PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)binding) || PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)binding))
/*     */       {
/*     */ 
/*     */         
/* 313 */         return true;
/*     */       }
/*     */     } 
/* 316 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isTimestamp(SecurityPolicy xwssPolicy) {
/* 323 */     if (xwssPolicy != null && PolicyTypeUtil.timestampPolicy(xwssPolicy)) {
/* 324 */       return true;
/*     */     }
/* 326 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertLax() {
/* 333 */     for (SecurityPolicy xwssPolicy : this.policyList) {
/* 334 */       if (isTimestamp(xwssPolicy)) {
/* 335 */         this.foundTimestamp = this.policyList.indexOf(xwssPolicy);
/* 336 */         prepend(xwssPolicy);
/*     */         
/*     */         continue;
/*     */       } 
/* 340 */       if (!isSupportingToken(xwssPolicy)) {
/* 341 */         switch (this.section) {
/*     */           case ClientIncomingPolicy:
/* 343 */             prepend(xwssPolicy);
/*     */             continue;
/*     */           case ClientOutgoingPolicy:
/* 346 */             append(xwssPolicy);
/*     */             continue;
/*     */           case ServerIncomingPolicy:
/* 349 */             appendAfterToken(xwssPolicy);
/*     */             continue;
/*     */           case ServerOutgoingPolicy:
/* 352 */             append(xwssPolicy); continue;
/*     */         }  continue;
/*     */       } 
/* 355 */       if (isSupportingToken(xwssPolicy) || isTimestamp(xwssPolicy)) {
/* 356 */         prepend(xwssPolicy);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertStrict() {
/* 367 */     for (SecurityPolicy xwssPolicy : this.policyList) {
/* 368 */       if (isSupportingToken(xwssPolicy)) {
/* 369 */         prepend(xwssPolicy); continue;
/*     */       } 
/* 371 */       if (isTimestamp(xwssPolicy)) {
/* 372 */         prepend(xwssPolicy); continue;
/*     */       } 
/* 374 */       switch (this.section) {
/*     */         case ClientIncomingPolicy:
/* 376 */           appendAfterToken(xwssPolicy);
/*     */         
/*     */         case ClientOutgoingPolicy:
/* 379 */           append(xwssPolicy);
/*     */         
/*     */         case ServerIncomingPolicy:
/* 382 */           appendAfterToken(xwssPolicy);
/*     */         
/*     */         case ServerOutgoingPolicy:
/* 385 */           append(xwssPolicy);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertLaxTsFirst() {
/* 396 */     convertLax();
/* 397 */     if (this.foundTimestamp != -1) {
/* 398 */       switch (this.section) {
/*     */         case ClientOutgoingPolicy:
/* 400 */           this.effectivePolicyList.add(0, this.effectivePolicyList.remove(this.foundTimestamp));
/*     */           break;
/*     */         case ServerOutgoingPolicy:
/* 403 */           this.effectivePolicyList.add(0, this.effectivePolicyList.remove(this.foundTimestamp));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertLaxTsLast() {
/* 414 */     convertLax();
/* 415 */     if (this.foundTimestamp != -1) {
/* 416 */       switch (this.section) {
/*     */         case ClientOutgoingPolicy:
/* 418 */           this.effectivePolicyList.add(this.effectivePolicyList.size() - 1, this.effectivePolicyList.remove(this.foundTimestamp));
/*     */           break;
/*     */         case ServerOutgoingPolicy:
/* 421 */           this.effectivePolicyList.add(this.effectivePolicyList.size() - 1, this.effectivePolicyList.remove(this.foundTimestamp));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void convert() {
/* 431 */     if (MessageLayout.Lax == this.mode) {
/* 432 */       convertLax();
/* 433 */     } else if (MessageLayout.Strict == this.mode) {
/* 434 */       convertStrict();
/* 435 */     } else if (MessageLayout.LaxTsFirst == this.mode) {
/* 436 */       convertLaxTsFirst();
/* 437 */     } else if (MessageLayout.LaxTsLast == this.mode) {
/* 438 */       convertLaxTsLast();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\XWSSPolicyContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */