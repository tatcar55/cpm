/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Claims;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.util.Collection;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class Claims
/*     */   extends PolicyAssertion
/*     */   implements Claims, SecurityAssertionValidator
/*     */ {
/*  64 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private boolean populated = false;
/*     */   private byte[] claimsBytes;
/*  67 */   private Element claimsElement = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public Claims() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Claims(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  76 */     super(name, nestedAssertions, nestedAlternative);
/*     */   }
/*     */   
/*     */   public byte[] getClaimsAsBytes() {
/*  80 */     populate();
/*  81 */     return this.claimsBytes;
/*     */   }
/*     */   
/*     */   public Element getClaimsAsElement() {
/*  85 */     populate();
/*  86 */     if (this.claimsElement == null) {
/*     */       try {
/*  88 */         DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*  89 */         dbf.setNamespaceAware(true);
/*  90 */         DocumentBuilder db = dbf.newDocumentBuilder();
/*  91 */         Document doc = db.parse(new ByteArrayInputStream(this.claimsBytes));
/*  92 */         this.claimsElement = (Element)doc.getElementsByTagNameNS("*", "Claims").item(0);
/*  93 */       } catch (Exception e) {
/*  94 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*  97 */     return this.claimsElement;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 101 */     return populate(isServer);
/*     */   }
/*     */   
/*     */   private void populate() {
/* 105 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean b) {
/* 109 */     if (!this.populated) {
/* 110 */       this.claimsBytes = PolicyUtil.policyAssertionToBytes(this);
/* 111 */       this.populated = true;
/*     */     } 
/* 113 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Claims.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */