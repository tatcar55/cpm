/*     */ package com.sun.xml.ws.security.secconv.impl;
/*     */ 
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*     */ import com.sun.xml.ws.security.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.impl.IssuedTokenContextImpl;
/*     */ import com.sun.xml.ws.security.secconv.WSSCVersion;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.str.DirectReference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.Reference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import java.net.URI;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityContextTokenInfoImpl
/*     */   implements SecurityContextTokenInfo
/*     */ {
/*  70 */   String identifier = null;
/*  71 */   String extId = null;
/*  72 */   String instance = null;
/*  73 */   byte[] secret = null;
/*  74 */   Map<String, byte[]> secretMap = (Map)new HashMap<String, byte>();
/*  75 */   Date creationTime = null;
/*  76 */   Date expirationTime = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIdentifier() {
/*  84 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public void setIdentifier(String identifier) {
/*  88 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExternalId() {
/*  95 */     return this.extId;
/*     */   }
/*     */   
/*     */   public void setExternalId(String externalId) {
/*  99 */     this.extId = externalId;
/*     */   }
/*     */   
/*     */   public String getInstance() {
/* 103 */     return this.instance;
/*     */   }
/*     */   
/*     */   public void setInstance(String instance) {
/* 107 */     this.instance = instance;
/*     */   }
/*     */   
/*     */   public byte[] getSecret() {
/* 111 */     byte[] newSecret = new byte[this.secret.length];
/* 112 */     System.arraycopy(this.secret, 0, newSecret, 0, this.secret.length);
/* 113 */     return newSecret;
/*     */   }
/*     */   
/*     */   public byte[] getInstanceSecret(String instance) {
/* 117 */     return this.secretMap.get(instance);
/*     */   }
/*     */   
/*     */   public void addInstance(String instance, byte[] key) {
/* 121 */     byte[] newKey = new byte[key.length];
/* 122 */     System.arraycopy(key, 0, newKey, 0, key.length);
/* 123 */     if (instance == null) {
/* 124 */       this.secret = newKey;
/*     */     } else {
/* 126 */       this.secretMap.put(instance, newKey);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Date getCreationTime() {
/* 131 */     return new Date(this.creationTime.getTime());
/*     */   }
/*     */   
/*     */   public void setCreationTime(Date creationTime) {
/* 135 */     this.creationTime = new Date(creationTime.getTime());
/*     */   }
/*     */   
/*     */   public Date getExpirationTime() {
/* 139 */     return new Date(this.expirationTime.getTime());
/*     */   }
/*     */   
/*     */   public void setExpirationTime(Date expirationTime) {
/* 143 */     this.expirationTime = new Date(expirationTime.getTime());
/*     */   }
/*     */   
/*     */   public Set getInstanceKeys() {
/* 147 */     return this.secretMap.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public IssuedTokenContext getIssuedTokenContext() {
/* 152 */     IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/* 153 */     issuedTokenContextImpl.setCreationTime(getCreationTime());
/* 154 */     issuedTokenContextImpl.setExpirationTime(getExpirationTime());
/* 155 */     issuedTokenContextImpl.setProofKey(getSecret());
/* 156 */     issuedTokenContextImpl.setSecurityContextTokenInfo(this);
/*     */ 
/*     */     
/* 159 */     URI uri = URI.create(getIdentifier());
/*     */     
/* 161 */     SecurityContextToken token = WSTrustElementFactory.newInstance(WSSCVersion.WSSC_10).createSecurityContextToken(uri, null, getExternalId());
/*     */     
/* 163 */     issuedTokenContextImpl.setSecurityToken((Token)token);
/*     */ 
/*     */     
/* 166 */     SecurityTokenReference attachedReference = createSecurityTokenReference(token.getWsuId(), false);
/*     */     
/* 168 */     SecurityTokenReference unattachedRef = createSecurityTokenReference(token.getIdentifier().toString(), true);
/*     */ 
/*     */     
/* 171 */     issuedTokenContextImpl.setAttachedSecurityTokenReference((Token)attachedReference);
/* 172 */     issuedTokenContextImpl.setUnAttachedSecurityTokenReference((Token)unattachedRef);
/*     */     
/* 174 */     return (IssuedTokenContext)issuedTokenContextImpl;
/*     */   }
/*     */   
/*     */   private SecurityTokenReference createSecurityTokenReference(String id, boolean unattached) {
/* 178 */     String uri = unattached ? id : ("#" + id);
/* 179 */     DirectReference directReference = WSTrustElementFactory.newInstance(WSSCVersion.WSSC_10).createDirectReference("http://schemas.xmlsoap.org/ws/2005/02/sc/sct", uri);
/* 180 */     return WSTrustElementFactory.newInstance(WSSCVersion.WSSC_10).createSecurityTokenReference((Reference)directReference);
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
/*     */   public IssuedTokenContext getIssuedTokenContext(SecurityTokenReference reference) {
/* 192 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\impl\SecurityContextTokenInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */