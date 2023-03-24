/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.AlgorithmSuite;
/*     */ import com.sun.xml.wss.impl.MessageLayout;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.WSSAssertion;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ public class MessagePolicy
/*     */   implements SecurityPolicy
/*     */ {
/*  74 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.configuration", "com.sun.xml.wss.logging.impl.configuration.LogStrings");
/*     */   
/*     */   private ArrayList info;
/*     */   
/*     */   private ArrayList optionals;
/*     */   
/*     */   private boolean dumpMessages = false;
/*     */   
/*     */   private boolean enableDynamicPolicyFlag = false;
/*     */   private boolean bsp = false;
/*     */   private boolean enableWSS11PolicyFlag = false;
/*     */   private boolean enableSignatureConfirmation = false;
/*     */   private WSSAssertion wssAssertion;
/*  87 */   private MessageLayout layout = MessageLayout.Lax;
/*     */   private boolean onSSL = false;
/*  89 */   private int optimizedType = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   private AlgorithmSuite algoSuite;
/*     */ 
/*     */ 
/*     */   
/*     */   private String polAltId;
/*     */ 
/*     */ 
/*     */   
/*     */   public MessagePolicy() {
/* 102 */     this.info = new ArrayList();
/* 103 */     this.optionals = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimizedType() throws XWSSecurityException {
/* 109 */     if (this.optimizedType != -1) {
/* 110 */       return this.optimizedType;
/*     */     }
/* 112 */     if (enableDynamicPolicy()) {
/* 113 */       this.optimizedType = 0;
/* 114 */       return this.optimizedType;
/*     */     } 
/*     */     
/* 117 */     StringBuffer securityOperation = new StringBuffer();
/* 118 */     securityOperation.append("_BODY");
/*     */     
/* 120 */     StringBuffer tmpBuffer = new StringBuffer("");
/*     */     
/* 122 */     SignatureTarget sigTarget = null;
/* 123 */     EncryptionTarget encTarget = null;
/*     */     
/* 125 */     WSSPolicy policy = null;
/* 126 */     String targetValue = null;
/* 127 */     int secureHeaders = -1;
/* 128 */     int secureAttachments = -1;
/*     */     
/* 130 */     HashMap<Object, Object> map = new HashMap<Object, Object>();
/*     */     
/* 132 */     ArrayList<WSSPolicy> primaryPolicies = getPrimaryPolicies();
/* 133 */     ArrayList<WSSPolicy> secondaryPolicies = getSecondaryPolicies();
/*     */     
/* 135 */     int size = primaryPolicies.size();
/* 136 */     int secondaryPoliciesSize = secondaryPolicies.size();
/*     */ 
/*     */     
/* 139 */     if (size == 0 && secondaryPoliciesSize > 0) {
/* 140 */       this.optimizedType = 4;
/* 141 */       return this.optimizedType;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 146 */     int iterator = 0;
/*     */     
/* 148 */     for (iterator = 0; iterator < secondaryPoliciesSize; iterator++) {
/* 149 */       policy = secondaryPolicies.get(iterator);
/* 150 */       if (policy.getType().intern() == "uri") {
/* 151 */         if (PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)policy)) {
/* 152 */           map.put("UsernameToken", policy.getUUID());
/* 153 */         } else if (PolicyTypeUtil.timestampPolicy((SecurityPolicy)policy)) {
/* 154 */           map.put("Timestamp", policy.getUUID());
/* 155 */         } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)policy)) {
/* 156 */           map.put("Assertion", policy.getUUID());
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 163 */     for (iterator = 0; iterator < size; iterator++) {
/* 164 */       policy = primaryPolicies.get(iterator);
/*     */       
/* 166 */       if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)policy)) {
/* 167 */         tmpBuffer.delete(0, tmpBuffer.length());
/* 168 */         SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)policy.getFeatureBinding();
/*     */ 
/*     */         
/* 171 */         int targetBindingSize = featureBinding.getTargetBindings().size();
/* 172 */         for (int targetIterator = 0; targetIterator < targetBindingSize; targetIterator++) {
/* 173 */           sigTarget = featureBinding.getTargetBindings().get(targetIterator);
/*     */           
/* 175 */           if (sigTarget == null) {
/* 176 */             throw new XWSSecurityException("Signature Target is null.");
/*     */           }
/*     */           
/* 179 */           if (sigTarget != null && sigTarget.getTransforms().size() > 1) {
/*     */             
/* 181 */             this.optimizedType = 0;
/* 182 */             return this.optimizedType;
/*     */           } 
/*     */           
/* 185 */           if (sigTarget.getTransforms().size() == 1) {
/* 186 */             SignatureTarget.Transform transform = sigTarget.getTransforms().get(0);
/* 187 */             if (transform != null && 
/* 188 */               transform.getTransform().intern() != "http://www.w3.org/2001/10/xml-exc-c14n#") {
/*     */               
/* 190 */               this.optimizedType = 0;
/* 191 */               return this.optimizedType;
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 197 */           if (sigTarget.getType().intern() == "qname") {
/* 198 */             targetValue = sigTarget.getQName().getLocalPart().intern();
/* 199 */           } else if (sigTarget.getType().intern() == "uri") {
/* 200 */             if (map.containsKey(sigTarget.getValue())) {
/* 201 */               targetValue = map.get(sigTarget.getValue()).toString();
/* 202 */             } else if (sigTarget.getValue().intern() == "attachmentRef:attachment" || sigTarget.getValue().startsWith("cid:")) {
/*     */               
/* 204 */               targetValue = "Attachment";
/*     */             } 
/* 206 */           } else if (sigTarget.getType().intern() == "xpath") {
/* 207 */             this.optimizedType = 0;
/* 208 */             return this.optimizedType;
/*     */           } 
/*     */           
/* 211 */           if (targetValue == "Body") {
/* 212 */             if (tmpBuffer.indexOf("_SIGN") == -1) {
/* 213 */               tmpBuffer.append("_SIGN");
/* 214 */               if (secureHeaders == 1 || secureHeaders == -1)
/* 215 */                 secureHeaders = 0; 
/* 216 */               if (secureAttachments == 1 || secureAttachments == -1)
/* 217 */                 secureAttachments = 0; 
/*     */             } 
/* 219 */           } else if (targetValue == "Timestamp" || targetValue == "UsernameToken" || targetValue == "Assertion") {
/*     */ 
/*     */             
/* 222 */             if (secureHeaders == -1)
/* 223 */               secureHeaders = 1; 
/* 224 */           } else if (targetValue == "Attachment") {
/* 225 */             if (secureAttachments == -1)
/* 226 */               secureAttachments = 1; 
/*     */           } else {
/* 228 */             return 0;
/*     */           } 
/*     */         } 
/* 231 */         securityOperation.insert(securityOperation.indexOf("_BODY"), tmpBuffer.toString());
/* 232 */       } else if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy)) {
/* 233 */         tmpBuffer.delete(0, tmpBuffer.length());
/* 234 */         EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)policy.getFeatureBinding();
/*     */ 
/*     */         
/* 237 */         int targetBindingSize = featureBinding.getTargetBindings().size();
/* 238 */         for (int targetIterator = 0; targetIterator < targetBindingSize; targetIterator++) {
/* 239 */           encTarget = featureBinding.getTargetBindings().get(targetIterator);
/*     */ 
/*     */ 
/*     */           
/* 243 */           if (encTarget.getType().intern() == "qname") {
/* 244 */             targetValue = encTarget.getQName().getLocalPart().intern();
/* 245 */           } else if (encTarget.getType().intern() == "uri") {
/* 246 */             if (map.containsKey(encTarget.getValue())) {
/* 247 */               targetValue = map.get(encTarget.getValue()).toString();
/* 248 */             } else if (encTarget.getValue().intern() == "attachmentRef:attachment" || encTarget.getValue().startsWith("cid:")) {
/*     */               
/* 250 */               targetValue = "Attachment";
/*     */             } 
/* 252 */           } else if (encTarget.getType().intern() == "xpath") {
/* 253 */             this.optimizedType = 0;
/* 254 */             return this.optimizedType;
/*     */           } 
/*     */           
/* 257 */           if (targetValue == "Body") {
/* 258 */             if (tmpBuffer.indexOf("_ENCRYPT") == -1) {
/* 259 */               tmpBuffer.append("_ENCRYPT");
/* 260 */               if (secureHeaders == 1 || secureHeaders == -1)
/* 261 */                 secureHeaders = 0; 
/* 262 */               if (secureAttachments == 1 || secureAttachments == -1)
/* 263 */                 secureAttachments = 0; 
/*     */             } 
/* 265 */           } else if (targetValue == "Timestamp" || targetValue == "UsernameToken" || targetValue == "Assertion") {
/*     */ 
/*     */             
/* 268 */             if (secureHeaders == -1)
/* 269 */               secureHeaders = 1; 
/* 270 */           } else if (targetValue == "Attachment") {
/* 271 */             if (secureAttachments == -1)
/* 272 */               secureAttachments = 1; 
/*     */           } else {
/* 274 */             return 0;
/*     */           } 
/*     */         } 
/* 277 */         securityOperation.insert(securityOperation.indexOf("_BODY"), tmpBuffer.toString());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 283 */     if (secureHeaders == 1 && secureAttachments != 1) {
/* 284 */       this.optimizedType = 4;
/* 285 */       return this.optimizedType;
/* 286 */     }  if (secureAttachments == 1 && secureAttachments != 1) {
/* 287 */       this.optimizedType = 6;
/* 288 */       return this.optimizedType;
/* 289 */     }  if (secureHeaders == 1 && secureAttachments == 1) {
/* 290 */       this.optimizedType = 7;
/* 291 */       return this.optimizedType;
/*     */     } 
/*     */     
/* 294 */     String type = securityOperation.toString().intern();
/*     */     
/* 296 */     if (type == "_SIGN_BODY") {
/* 297 */       this.optimizedType = 1;
/* 298 */     } else if (type == "_SIGN_ENCRYPT_BODY") {
/* 299 */       this.optimizedType = 2;
/* 300 */     } else if (type == "_ENCRYPT_SIGN_BODY") {
/* 301 */       this.optimizedType = 0;
/* 302 */     } else if (type == "_ENCRYPT_BODY") {
/* 303 */       this.optimizedType = 0;
/*     */     } 
/*     */     
/* 306 */     return this.optimizedType;
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
/*     */ 
/*     */   
/*     */   public void append(SecurityPolicy item) throws PolicyGenerationException {
/* 320 */     this.info.add(item);
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
/*     */   
/*     */   public void prepend(SecurityPolicy item) throws PolicyGenerationException {
/* 333 */     int i = 0;
/* 334 */     for (i = 0; i < this.info.size(); i++) {
/* 335 */       SecurityPolicy sp = this.info.get(i);
/* 336 */       if (!sp.getType().equals("SignatureConfirmationPolicy")) {
/*     */         break;
/*     */       }
/*     */     } 
/* 340 */     this.info.add(i, item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendAll(Collection items) throws PolicyGenerationException {
/* 351 */     Iterator<SecurityPolicy> i = items.iterator();
/* 352 */     while (i.hasNext()) {
/* 353 */       SecurityPolicy item = i.next();
/*     */     }
/*     */     
/* 356 */     this.info.addAll(items);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 363 */     this.info.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 370 */     return this.info.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityPolicy get(int index) throws Exception {
/* 381 */     if (!this.optionals.isEmpty()) addOptionals();
/*     */     
/* 383 */     return this.info.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/* 391 */     if (!this.optionals.isEmpty()) addOptionals();
/*     */     
/* 393 */     return this.info.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 400 */     return this.info.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(SecurityPolicy item) {
/* 408 */     int i = this.info.indexOf(item);
/* 409 */     if (i == -1) {
/*     */       return;
/*     */     }
/* 412 */     this.info.remove(i);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertBefore(SecurityPolicy existing, SecurityPolicy additional) throws PolicyGenerationException {
/* 428 */     int i = this.info.indexOf(existing);
/* 429 */     if (i == -1) {
/*     */       return;
/*     */     }
/* 432 */     this.info.add(i, additional);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dumpMessages(boolean dump) {
/* 439 */     this.dumpMessages = dump;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dumpMessages() {
/* 446 */     return this.dumpMessages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableDynamicPolicy(boolean flag) {
/* 453 */     this.enableDynamicPolicyFlag = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enableDynamicPolicy() {
/* 460 */     return this.enableDynamicPolicyFlag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWSSAssertion(WSSAssertion wssAssertion) throws PolicyGenerationException {
/* 465 */     this.wssAssertion = wssAssertion;
/* 466 */     if ("1.1".equals(this.wssAssertion.getType())) {
/* 467 */       this.enableWSS11PolicyFlag = true;
/*     */     }
/* 469 */     if (this.wssAssertion.getRequiredProperties().contains("RequireSignatureConfirmation")) {
/* 470 */       this.enableSignatureConfirmation = true;
/*     */     }
/* 472 */     if (this.enableSignatureConfirmation) {
/* 473 */       SignatureConfirmationPolicy signConfirmPolicy = new SignatureConfirmationPolicy();
/* 474 */       String id = SecurityUtil.generateUUID();
/* 475 */       signConfirmPolicy.setUUID(id);
/* 476 */       prepend((SecurityPolicy)signConfirmPolicy);
/*     */     } 
/*     */   }
/*     */   
/*     */   public WSSAssertion getWSSAssertion() {
/* 481 */     return this.wssAssertion;
/*     */   }
/*     */   
/*     */   public void enableSignatureConfirmation(boolean flag) throws PolicyGenerationException {
/* 485 */     this.enableSignatureConfirmation = flag;
/* 486 */     if (this.enableSignatureConfirmation) {
/* 487 */       SignatureConfirmationPolicy signConfirmPolicy = new SignatureConfirmationPolicy();
/* 488 */       String id = SecurityUtil.generateUUID();
/* 489 */       signConfirmPolicy.setUUID(id);
/* 490 */       append((SecurityPolicy)signConfirmPolicy);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean enableSignatureConfirmation() {
/* 495 */     return this.enableSignatureConfirmation;
/*     */   }
/*     */   
/*     */   public void enableWSS11Policy(boolean flag) {
/* 499 */     this.enableWSS11PolicyFlag = flag;
/*     */   }
/*     */   
/*     */   public boolean enableWSS11Policy() {
/* 503 */     return this.enableWSS11PolicyFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void isBSP(boolean flag) {
/* 509 */     this.bsp = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBSP() {
/* 515 */     return this.bsp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeOptionalTargets() {
/* 521 */     this.optionals.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOptionalTargets(ArrayList optionls) throws XWSSecurityException {
/* 529 */     Iterator<Target> i = optionls.iterator();
/*     */     
/* 531 */     while (i.hasNext()) {
/*     */       try {
/* 533 */         Target target = i.next();
/* 534 */         target.setEnforce(false);
/* 535 */       } catch (ClassCastException cce) {
/* 536 */         String message = "Target should be of types: com.sun.xml.wss.impl.policy.mls.SignatureTarget OR com.sun.xml.wss.impl.policy.mls.EncryptionTarget";
/*     */ 
/*     */         
/* 539 */         log.log(Level.SEVERE, "WSS1100.classcast.target", new Object[] { message });
/*     */         
/* 541 */         throw new XWSSecurityException(message, cce);
/*     */       } 
/*     */     } 
/*     */     
/* 545 */     this.optionals.addAll(optionls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOptionalTarget(Target target) {
/* 553 */     target.setEnforce(false);
/* 554 */     this.optionals.add(target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(MessagePolicy policy) {
/* 564 */     boolean assrt = (policy.dumpMessages() && policy.enableDynamicPolicy());
/*     */     
/* 566 */     if (assrt) {
/* 567 */       ArrayList primary0 = getPrimaryPolicies();
/* 568 */       ArrayList secdary0 = getSecondaryPolicies();
/*     */       
/* 570 */       ArrayList primary1 = policy.getPrimaryPolicies();
/* 571 */       ArrayList secdary1 = policy.getSecondaryPolicies();
/*     */       
/* 573 */       if (primary0.equals(primary1) && secdary0.equals(secdary1)) assrt = true;
/*     */     
/*     */     } 
/* 576 */     return assrt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList getPrimaryPolicies() {
/* 584 */     ArrayList<SecurityPolicy> list = new ArrayList();
/*     */     
/* 586 */     Iterator<SecurityPolicy> i = iterator();
/* 587 */     while (i.hasNext()) {
/* 588 */       SecurityPolicy policy = i.next();
/* 589 */       if (PolicyTypeUtil.encryptionPolicy(policy) || PolicyTypeUtil.signaturePolicy(policy)) {
/* 590 */         list.add(policy);
/*     */       }
/*     */     } 
/*     */     
/* 594 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList getSecondaryPolicies() {
/* 602 */     ArrayList<SecurityPolicy> list = new ArrayList();
/*     */     
/* 604 */     Iterator<SecurityPolicy> i = iterator();
/* 605 */     while (i.hasNext()) {
/* 606 */       SecurityPolicy policy = i.next();
/*     */       
/* 608 */       if (PolicyTypeUtil.authenticationTokenPolicy(policy) || PolicyTypeUtil.timestampPolicy(policy)) {
/* 609 */         list.add(policy);
/*     */       }
/*     */     } 
/*     */     
/* 613 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addOptionals() {
/* 618 */     Iterator<SecurityPolicy> j = this.info.iterator();
/*     */     
/* 620 */     while (j.hasNext()) {
/*     */       
/* 622 */       SecurityPolicy policy = j.next();
/*     */       
/* 624 */       if (policy instanceof WSSPolicy) {
/* 625 */         processWSSPolicy((WSSPolicy)policy);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 633 */     this.optionals.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processWSSPolicy(WSSPolicy policy) {
/* 640 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)policy)) {
/* 641 */       SignaturePolicy sPolicy = (SignaturePolicy)policy;
/* 642 */       SignaturePolicy.FeatureBinding fBinding = (SignaturePolicy.FeatureBinding)sPolicy.getFeatureBinding();
/*     */ 
/*     */       
/* 645 */       Iterator<Target> it = this.optionals.iterator();
/* 646 */       while (it.hasNext()) {
/* 647 */         fBinding.addTargetBinding(it.next());
/*     */       }
/*     */     }
/* 650 */     else if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy)) {
/* 651 */       EncryptionPolicy ePolicy = (EncryptionPolicy)policy;
/* 652 */       EncryptionPolicy.FeatureBinding fBinding = (EncryptionPolicy.FeatureBinding)ePolicy.getFeatureBinding();
/*     */ 
/*     */       
/* 655 */       Iterator<Target> it = this.optionals.iterator();
/* 656 */       while (it.hasNext()) {
/* 657 */         fBinding.addTargetBinding(it.next());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 683 */     return "MessagePolicy";
/*     */   }
/*     */   
/*     */   public void setAlgorithmSuite(AlgorithmSuite algSuite) {
/* 687 */     this.algoSuite = algSuite;
/*     */   }
/*     */   
/*     */   public AlgorithmSuite getAlgorithmSuite() {
/* 691 */     return this.algoSuite;
/*     */   }
/*     */   
/*     */   public MessageLayout getLayout() {
/* 695 */     return this.layout;
/*     */   }
/*     */   
/*     */   public void setLayout(MessageLayout layout) {
/* 699 */     this.layout = layout;
/*     */   }
/*     */   
/*     */   public void setSSL(boolean value) {
/* 703 */     this.onSSL = value;
/*     */   }
/*     */   public boolean isSSL() {
/* 706 */     return this.onSSL;
/*     */   }
/*     */   
/*     */   public String getPolicyAlternativeId() {
/* 710 */     return this.polAltId;
/*     */   }
/*     */   public void setPolicyAlternativeId(String polId) {
/* 713 */     this.polAltId = polId;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\MessagePolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */