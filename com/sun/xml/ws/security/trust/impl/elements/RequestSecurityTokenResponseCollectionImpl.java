/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedAttachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedUnattachedReference;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestSecurityTokenResponseCollectionType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestSecurityTokenResponseType;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RequestSecurityTokenResponseCollectionImpl
/*     */   extends RequestSecurityTokenResponseCollectionType
/*     */   implements RequestSecurityTokenResponseCollection
/*     */ {
/*     */   protected List<RequestSecurityTokenResponse> requestSecurityTokenResponses;
/*     */   
/*     */   public RequestSecurityTokenResponseCollectionImpl() {}
/*     */   
/*     */   public RequestSecurityTokenResponseCollectionImpl(URI tokenType, URI context, RequestedSecurityToken token, AppliesTo scopes, RequestedAttachedReference attached, RequestedUnattachedReference unattached, RequestedProofToken proofToken, Entropy entropy, Lifetime lt) {
/*  78 */     RequestSecurityTokenResponse rstr = new RequestSecurityTokenResponseImpl(tokenType, context, token, scopes, attached, unattached, proofToken, entropy, lt, null);
/*     */     
/*  80 */     addRequestSecurityTokenResponse(rstr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollectionImpl(RequestSecurityTokenResponseCollectionType rstrcType) throws URISyntaxException, WSTrustException {
/*  86 */     List<RequestSecurityTokenResponseType> list = rstrcType.getRequestSecurityTokenResponse();
/*  87 */     for (int i = 0; i < list.size(); i++) {
/*  88 */       addRequestSecurityTokenResponse(new RequestSecurityTokenResponseImpl(list.get(i)));
/*     */     }
/*     */   }
/*     */   
/*     */   public List<RequestSecurityTokenResponse> getRequestSecurityTokenResponses() {
/*  93 */     if (this.requestSecurityTokenResponses == null) {
/*  94 */       this.requestSecurityTokenResponses = new ArrayList<RequestSecurityTokenResponse>();
/*     */     }
/*  96 */     return this.requestSecurityTokenResponses;
/*     */   }
/*     */   
/*     */   public final void addRequestSecurityTokenResponse(RequestSecurityTokenResponse rstr) {
/* 100 */     getRequestSecurityTokenResponses().add(rstr);
/*     */ 
/*     */ 
/*     */     
/* 104 */     getRequestSecurityTokenResponse().add((RequestSecurityTokenResponseType)rstr);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\RequestSecurityTokenResponseCollectionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */