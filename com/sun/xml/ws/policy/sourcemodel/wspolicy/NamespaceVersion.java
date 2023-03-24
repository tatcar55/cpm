/*     */ package com.sun.xml.ws.policy.sourcemodel.wspolicy;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ public enum NamespaceVersion
/*     */ {
/*  53 */   v1_2("http://schemas.xmlsoap.org/ws/2004/09/policy", "wsp1_2", new XmlToken[] { XmlToken.Policy, XmlToken.ExactlyOne, XmlToken.All, XmlToken.PolicyReference, XmlToken.UsingPolicy, XmlToken.Name, XmlToken.Optional, XmlToken.Ignorable, XmlToken.PolicyUris, XmlToken.Uri, XmlToken.Digest, XmlToken.DigestAlgorithm
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }),
/*  67 */   v1_5("http://www.w3.org/ns/ws-policy", "wsp", new XmlToken[] { XmlToken.Policy, XmlToken.ExactlyOne, XmlToken.All, XmlToken.PolicyReference, XmlToken.UsingPolicy, XmlToken.Name, XmlToken.Optional, XmlToken.Ignorable, XmlToken.PolicyUris, XmlToken.Uri, XmlToken.Digest, XmlToken.DigestAlgorithm });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String nsUri;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String defaultNsPrefix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<XmlToken, QName> tokenToQNameCache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NamespaceVersion resolveVersion(String uri) {
/*  92 */     for (NamespaceVersion namespaceVersion : values()) {
/*  93 */       if (namespaceVersion.toString().equalsIgnoreCase(uri)) {
/*  94 */         return namespaceVersion;
/*     */       }
/*     */     } 
/*     */     
/*  98 */     return null;
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
/*     */   public static NamespaceVersion resolveVersion(QName name) {
/* 111 */     return resolveVersion(name.getNamespaceURI());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NamespaceVersion getLatestVersion() {
/* 120 */     return v1_5;
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
/*     */   public static XmlToken resolveAsToken(QName name) {
/* 133 */     NamespaceVersion nsVersion = resolveVersion(name);
/* 134 */     if (nsVersion != null) {
/* 135 */       XmlToken token = XmlToken.resolveToken(name.getLocalPart());
/* 136 */       if (nsVersion.tokenToQNameCache.containsKey(token)) {
/* 137 */         return token;
/*     */       }
/*     */     } 
/* 140 */     return XmlToken.UNKNOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NamespaceVersion(String uri, String prefix, XmlToken... supportedTokens) {
/* 148 */     this.nsUri = uri;
/* 149 */     this.defaultNsPrefix = prefix;
/*     */     
/* 151 */     Map<XmlToken, QName> temp = new HashMap<XmlToken, QName>();
/* 152 */     for (XmlToken token : supportedTokens) {
/* 153 */       temp.put(token, new QName(this.nsUri, token.toString()));
/*     */     }
/* 155 */     this.tokenToQNameCache = Collections.unmodifiableMap(temp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultNamespacePrefix() {
/* 164 */     return this.defaultNsPrefix;
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
/*     */   public QName asQName(XmlToken token) throws IllegalArgumentException {
/* 177 */     return this.tokenToQNameCache.get(token);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 182 */     return this.nsUri;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\wspolicy\NamespaceVersion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */