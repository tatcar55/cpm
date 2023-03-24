/*     */ package com.sun.xml.ws.tx.coord.common;
/*     */ 
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EndpointReferenceBuilder<T extends EndpointReference>
/*     */ {
/*     */   protected String address;
/*  56 */   protected List<Element> referenceParameters = new ArrayList<Element>();
/*     */   
/*     */   public static EndpointReferenceBuilder newInstance(Transactional.Version version) {
/*  59 */     if (Transactional.Version.WSAT10 == version || Transactional.Version.DEFAULT == version)
/*  60 */       return MemberSubmission(); 
/*  61 */     if (Transactional.Version.WSAT11 == version || Transactional.Version.WSAT12 == version) {
/*  62 */       return W3C();
/*     */     }
/*  64 */     throw new IllegalArgumentException(version + "is not a supported ws-at version");
/*     */   }
/*     */   
/*     */   public static EndpointReferenceBuilder<W3CEndpointReference> W3C() {
/*  68 */     return new W3CEndpointReferenceBuilder();
/*     */   }
/*     */   
/*     */   public static EndpointReferenceBuilder<MemberSubmissionEndpointReference> MemberSubmission() {
/*  72 */     return new MemberSubmissionEndpointReferenceBuilder();
/*     */   }
/*     */   
/*     */   public EndpointReferenceBuilder<T> address(String address) {
/*  76 */     this.address = address;
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   public EndpointReferenceBuilder<T> referenceParameter(Element... elements) {
/*  81 */     for (Element element : elements) {
/*  82 */       this.referenceParameters.add(element);
/*     */     }
/*  84 */     return this;
/*     */   }
/*     */   
/*     */   public EndpointReferenceBuilder<T> referenceParameter(Node... elements) {
/*  88 */     for (Node element : elements) {
/*  89 */       this.referenceParameters.add((Element)element);
/*     */     }
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EndpointReferenceBuilder<T> referenceParameter(List<Element> elements) {
/*  96 */     this.referenceParameters.addAll(elements);
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   public abstract T build();
/*     */   
/*     */   static class W3CEndpointReferenceBuilder
/*     */     extends EndpointReferenceBuilder<W3CEndpointReference> {
/*     */     public W3CEndpointReference build() {
/* 105 */       javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder builder = new javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder();
/* 106 */       for (int i = 0; i < this.referenceParameters.size(); i++) {
/* 107 */         Element element = this.referenceParameters.get(i);
/* 108 */         builder.referenceParameter(element);
/*     */       } 
/* 110 */       W3CEndpointReference w3CEndpointReference = builder.address(this.address).build();
/* 111 */       return w3CEndpointReference;
/*     */     }
/*     */   }
/*     */   
/*     */   static class MemberSubmissionEndpointReferenceBuilder
/*     */     extends EndpointReferenceBuilder<MemberSubmissionEndpointReference> {
/*     */     public MemberSubmissionEndpointReference build() {
/* 118 */       MemberSubmissionEndpointReference epr = new MemberSubmissionEndpointReference();
/* 119 */       epr.addr = new MemberSubmissionEndpointReference.Address();
/* 120 */       epr.addr.uri = this.address;
/* 121 */       epr.referenceParameters = new MemberSubmissionEndpointReference.Elements();
/* 122 */       epr.referenceParameters.elements = new ArrayList();
/* 123 */       epr.referenceParameters.elements.addAll(this.referenceParameters);
/* 124 */       return epr;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\EndpointReferenceBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */