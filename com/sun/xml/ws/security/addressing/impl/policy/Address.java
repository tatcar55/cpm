/*     */ package com.sun.xml.ws.security.addressing.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.addressing.policy.Address;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Address
/*     */   extends PolicyAssertion
/*     */   implements Address
/*     */ {
/*     */   private boolean populated = false;
/*     */   private URI address;
/*     */   
/*     */   public Address() {}
/*     */   
/*     */   public Address(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  77 */     super(name, nestedAssertions, nestedAlternative);
/*     */   }
/*     */   private void populate() {
/*  80 */     if (!this.populated) {
/*     */       try {
/*  82 */         if (getValue() != null) {
/*  83 */           this.address = new URI(getValue().trim());
/*     */         }
/*  85 */         this.populated = true;
/*  86 */       } catch (URISyntaxException ex) {
/*  87 */         if (Constants.logger.getLevel() == Level.SEVERE) {
/*  88 */           Constants.logger.log(Level.SEVERE, LocalizationMessages.WSA_0004_INVALID_EPR_ADDRESS(), ex);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public URI getURI() {
/*  95 */     populate();
/*  96 */     return this.address;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\addressing\impl\policy\Address.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */