/*     */ package com.sun.xml.ws.api.policy;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapMutator;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface PolicyResolver
/*     */ {
/*     */   PolicyMap resolve(ServerContext paramServerContext) throws WebServiceException;
/*     */   
/*     */   PolicyMap resolve(ClientContext paramClientContext) throws WebServiceException;
/*     */   
/*     */   public static class ServerContext
/*     */   {
/*     */     private final PolicyMap policyMap;
/*     */     private final Class endpointClass;
/*     */     private final Container container;
/*     */     private final boolean hasWsdl;
/*     */     private final Collection<PolicyMapMutator> mutators;
/*     */     
/*     */     public ServerContext(@Nullable PolicyMap policyMap, Container container, Class endpointClass, PolicyMapMutator... mutators) {
/* 109 */       this.policyMap = policyMap;
/* 110 */       this.endpointClass = endpointClass;
/* 111 */       this.container = container;
/* 112 */       this.hasWsdl = true;
/* 113 */       this.mutators = Arrays.asList(mutators);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ServerContext(@Nullable PolicyMap policyMap, Container container, Class endpointClass, boolean hasWsdl, PolicyMapMutator... mutators) {
/* 133 */       this.policyMap = policyMap;
/* 134 */       this.endpointClass = endpointClass;
/* 135 */       this.container = container;
/* 136 */       this.hasWsdl = hasWsdl;
/* 137 */       this.mutators = Arrays.asList(mutators);
/*     */     }
/*     */     @Nullable
/*     */     public PolicyMap getPolicyMap() {
/* 141 */       return this.policyMap;
/*     */     }
/*     */     @Nullable
/*     */     public Class getEndpointClass() {
/* 145 */       return this.endpointClass;
/*     */     }
/*     */     
/*     */     public Container getContainer() {
/* 149 */       return this.container;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasWsdl() {
/* 157 */       return this.hasWsdl;
/*     */     }
/*     */     
/*     */     public Collection<PolicyMapMutator> getMutators() {
/* 161 */       return this.mutators;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ClientContext
/*     */   {
/*     */     private PolicyMap policyMap;
/*     */ 
/*     */ 
/*     */     
/*     */     private Container container;
/*     */ 
/*     */ 
/*     */     
/*     */     public ClientContext(@Nullable PolicyMap policyMap, Container container) {
/* 179 */       this.policyMap = policyMap;
/* 180 */       this.container = container;
/*     */     }
/*     */     @Nullable
/*     */     public PolicyMap getPolicyMap() {
/* 184 */       return this.policyMap;
/*     */     }
/*     */     
/*     */     public Container getContainer() {
/* 188 */       return this.container;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\policy\PolicyResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */