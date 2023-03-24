/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Header;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ public class Header
/*     */   extends PolicyAssertion
/*     */   implements Header
/*     */ {
/*  57 */   String name = "";
/*  58 */   String uri = "";
/*  59 */   int hashCode = 0;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Header(String localName, String uri) {
/*  64 */     Map<QName, String> attrs = getAttributes();
/*  65 */     attrs.put(NAME, localName);
/*  66 */     attrs.put(URI, uri);
/*     */   }
/*     */   
/*     */   public Header(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) throws PolicyException {
/*  70 */     super(name, nestedAssertions, nestedAlternative);
/*     */ 
/*     */     
/*  73 */     String tmp = getAttributeValue(NAME);
/*  74 */     if (tmp != null) {
/*  75 */       this.name = tmp;
/*     */     }
/*     */     
/*  78 */     this.uri = getAttributeValue(URI);
/*     */     
/*  80 */     if (this.uri == null || this.uri.length() == 0) {
/*  81 */       throw new PolicyException("Namespace attribute is required under Header element ");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  87 */     if (object instanceof Header) {
/*  88 */       Header header = (Header)object;
/*  89 */       if (header.getLocalName() != null && header.getLocalName().equals(getLocalName()) && 
/*  90 */         header.getURI().equals(getURI())) {
/*  91 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  95 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  99 */     if (this.hashCode == 0) {
/* 100 */       if (this.uri != null) {
/* 101 */         this.hashCode = this.uri.hashCode();
/*     */       }
/* 103 */       if (this.name != null) {
/* 104 */         this.hashCode += this.name.hashCode();
/*     */       }
/*     */     } 
/* 107 */     return this.hashCode;
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 111 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getURI() {
/* 115 */     return this.uri;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Header.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */