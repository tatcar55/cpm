/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedAttachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedUnattachedReference;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestSecurityTokenResponseCollectionType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestSecurityTokenResponseType;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   protected List<RequestSecurityTokenResponse> requestSecurityTokenResponseList;
/*     */   
/*     */   public RequestSecurityTokenResponseCollectionImpl() {}
/*     */   
/*     */   public RequestSecurityTokenResponseCollectionImpl(RequestSecurityTokenResponse rstr) {
/*  77 */     addRequestSecurityTokenResponse(rstr);
/*     */   }
/*     */   
/*     */   public RequestSecurityTokenResponseCollectionImpl(URI tokenType, URI context, RequestedSecurityToken token, AppliesTo scopes, RequestedAttachedReference attached, RequestedUnattachedReference unattached, RequestedProofToken proofToken, Entropy entropy, Lifetime lt) {
/*  81 */     RequestSecurityTokenResponse rstr = new RequestSecurityTokenResponseImpl(tokenType, context, token, scopes, attached, unattached, proofToken, entropy, lt, null);
/*     */     
/*  83 */     addRequestSecurityTokenResponse(rstr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollectionImpl(RequestSecurityTokenResponseCollectionType rstrcType) throws Exception {
/*  89 */     List<Object> list = rstrcType.getRequestSecurityTokenResponse();
/*  90 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/*  92 */       RequestSecurityTokenResponseType rstr = null;
/*  93 */       Object object = list.get(i);
/*  94 */       if (object instanceof JAXBElement) {
/*  95 */         JAXBElement<RequestSecurityTokenResponseType> obj = (JAXBElement)object;
/*     */         
/*  97 */         String local = obj.getName().getLocalPart();
/*  98 */         if (local.equalsIgnoreCase("RequestSecurityTokenResponse")) {
/*  99 */           rstr = obj.getValue();
/*     */         }
/*     */       }
/* 102 */       else if (object instanceof RequestSecurityTokenResponseType) {
/* 103 */         rstr = (RequestSecurityTokenResponseType)object;
/*     */       } 
/*     */ 
/*     */       
/* 107 */       if (rstr != null) {
/* 108 */         addRequestSecurityTokenResponse(new RequestSecurityTokenResponseImpl(rstr));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<RequestSecurityTokenResponse> getRequestSecurityTokenResponses() {
/* 114 */     if (this.requestSecurityTokenResponseList == null) {
/* 115 */       this.requestSecurityTokenResponseList = new ArrayList<RequestSecurityTokenResponse>();
/*     */     }
/* 117 */     return this.requestSecurityTokenResponseList;
/*     */   }
/*     */   
/*     */   public void addRequestSecurityTokenResponse(RequestSecurityTokenResponse rstr) {
/* 121 */     getRequestSecurityTokenResponses().add(rstr);
/*     */     
/* 123 */     JAXBElement<RequestSecurityTokenResponseType> rstrEl = (new ObjectFactory()).createRequestSecurityTokenResponse((RequestSecurityTokenResponseType)rstr);
/*     */     
/* 125 */     getRequestSecurityTokenResponse().add(rstrEl);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\RequestSecurityTokenResponseCollectionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */