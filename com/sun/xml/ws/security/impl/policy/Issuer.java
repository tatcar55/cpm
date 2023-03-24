/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.addressing.policy.Address;
/*     */ import com.sun.xml.ws.security.policy.Issuer;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Issuer
/*     */   extends PolicyAssertion
/*     */   implements Issuer, SecurityAssertionValidator
/*     */ {
/*  66 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private Address address;
/*     */   private Address metadataAddress;
/*     */   private boolean populated = false;
/*  70 */   private PolicyAssertion refProps = null;
/*  71 */   private PolicyAssertion refParams = null;
/*  72 */   private PolicyAssertion serviceName = null;
/*  73 */   private String portType = null;
/*  74 */   private Element identityEle = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public Issuer() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Issuer(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  83 */     super(name, nestedAssertions, nestedAlternative);
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/*  87 */     return populate(isServer);
/*     */   }
/*     */   
/*     */   private void getAddressFromMetadata(PolicyAssertion addressingMetadata) {
/*  91 */     PolicyAssertion metadata = null;
/*  92 */     PolicyAssertion metadataSection = null;
/*  93 */     PolicyAssertion metadataReference = null;
/*  94 */     if (addressingMetadata != null) {
/*  95 */       if (addressingMetadata.hasParameters()) {
/*  96 */         Iterator<PolicyAssertion> iterator = addressingMetadata.getParametersIterator();
/*  97 */         while (iterator.hasNext()) {
/*  98 */           PolicyAssertion assertion = iterator.next();
/*  99 */           if (PolicyUtil.isMetadata(assertion)) {
/* 100 */             metadata = assertion;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 105 */       if (metadata != null) {
/* 106 */         if (metadata.hasParameters()) {
/* 107 */           Iterator<PolicyAssertion> iterator = metadata.getParametersIterator();
/* 108 */           while (iterator.hasNext()) {
/* 109 */             PolicyAssertion assertion = iterator.next();
/* 110 */             if (PolicyUtil.isMetadataSection(assertion)) {
/* 111 */               metadataSection = assertion;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 116 */         if (metadataSection != null) {
/* 117 */           if (metadataSection.hasParameters()) {
/* 118 */             Iterator<PolicyAssertion> iterator = metadataSection.getParametersIterator();
/* 119 */             while (iterator.hasNext()) {
/* 120 */               PolicyAssertion assertion = iterator.next();
/* 121 */               if (PolicyUtil.isMetadataReference(assertion)) {
/* 122 */                 metadataReference = assertion;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/* 127 */           if (metadataReference != null && 
/* 128 */             metadataReference.hasParameters()) {
/* 129 */             Iterator<PolicyAssertion> iterator = metadataReference.getParametersIterator();
/* 130 */             while (iterator.hasNext()) {
/* 131 */               PolicyAssertion assertion = iterator.next();
/* 132 */               if (PolicyUtil.isAddress(assertion)) {
/* 133 */                 this.metadataAddress = (Address)assertion;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void populate() {
/* 144 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 148 */     if (!this.populated) {
/* 149 */       if (hasNestedAssertions()) {
/* 150 */         Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 151 */         while (it.hasNext()) {
/* 152 */           PolicyAssertion assertion = it.next();
/* 153 */           if (PolicyUtil.isAddress(assertion)) {
/* 154 */             this.address = (Address)assertion; continue;
/* 155 */           }  if (PolicyUtil.isPortType(assertion)) {
/* 156 */             this.portType = assertion.getValue(); continue;
/* 157 */           }  if (PolicyUtil.isReferenceParameters(assertion)) {
/* 158 */             this.refParams = assertion; continue;
/* 159 */           }  if (PolicyUtil.isReferenceProperties(assertion)) {
/* 160 */             this.refProps = assertion; continue;
/* 161 */           }  if (PolicyUtil.isServiceName(assertion)) {
/* 162 */             this.serviceName = assertion; continue;
/* 163 */           }  if (PolicyUtil.isAddressingMetadata(assertion)) {
/* 164 */             getAddressFromMetadata(assertion); continue;
/* 165 */           }  if ("Identity".equals(assertion.getName().getLocalPart())) {
/* 166 */             Document doc = PolicyUtil.policyAssertionToDoc(assertion);
/* 167 */             this.identityEle = (Element)doc.getElementsByTagNameNS("*", "Identity").item(0);
/*     */           } 
/*     */         } 
/*     */       } 
/* 171 */       this.populated = true;
/*     */     } 
/* 173 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public Address getAddress() {
/* 177 */     populate();
/* 178 */     return this.address;
/*     */   }
/*     */   
/*     */   public String getPortType() {
/* 182 */     populate();
/* 183 */     return this.portType;
/*     */   }
/*     */   
/*     */   public PolicyAssertion getReferenceParameters() {
/* 187 */     populate();
/* 188 */     return this.refParams;
/*     */   }
/*     */   
/*     */   public PolicyAssertion getReferenceProperties() {
/* 192 */     populate();
/* 193 */     return this.refProps;
/*     */   }
/*     */   
/*     */   public PolicyAssertion getServiceName() {
/* 197 */     populate();
/* 198 */     return this.serviceName;
/*     */   }
/*     */   
/*     */   public Element getIdentity() {
/* 202 */     populate();
/* 203 */     return this.identityEle;
/*     */   }
/*     */   
/*     */   public Address getMetadataAddress() {
/* 207 */     populate();
/* 208 */     return this.metadataAddress;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Issuer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */