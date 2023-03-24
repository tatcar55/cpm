/*     */ package com.sun.xml.ws.security.opt.crypto.jaxb;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBCryptoContext
/*     */   implements XMLCryptoContext
/*     */ {
/*  59 */   private String baseURI = null;
/*  60 */   private KeySelector keySelector = null;
/*  61 */   private URIDereferencer uriDereferencer = null;
/*  62 */   private HashMap namespacePrefix = null;
/*  63 */   private String defaultNamespacePrefix = null;
/*  64 */   private HashMap property = null;
/*  65 */   private HashMap context = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseURI() {
/*  78 */     return this.baseURI;
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
/*     */   public void setBaseURI(String baseURI) {
/*  91 */     this.baseURI = baseURI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeySelector getKeySelector() {
/* 101 */     return this.keySelector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeySelector(KeySelector keySelector) {
/* 112 */     this.keySelector = keySelector;
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
/*     */   public URIDereferencer getURIDereferencer() {
/* 124 */     return this.uriDereferencer;
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
/*     */   
/*     */   public void setURIDereferencer(URIDereferencer uriDereferencer) {
/* 138 */     this.uriDereferencer = uriDereferencer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespacePrefix(String namespaceURI, String defaultPrefix) {
/* 160 */     if (this.namespacePrefix == null) {
/* 161 */       return defaultPrefix;
/*     */     }
/* 163 */     Object prefix = this.namespacePrefix.get(namespaceURI);
/* 164 */     if (prefix == null)
/* 165 */       return defaultPrefix; 
/* 166 */     if (prefix.equals("")) {
/* 167 */       return defaultPrefix;
/*     */     }
/* 169 */     return prefix.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String putNamespacePrefix(String namespaceURI, String prefix) {
/* 190 */     if (namespaceURI == null) {
/* 191 */       return null;
/*     */     }
/*     */     
/* 194 */     if (this.namespacePrefix == null) {
/* 195 */       this.namespacePrefix = new HashMap<Object, Object>();
/*     */     }
/*     */ 
/*     */     
/* 199 */     Object oldPrefix = this.namespacePrefix.get(namespaceURI);
/*     */     
/* 201 */     if (prefix == null && oldPrefix != null)
/*     */     {
/* 203 */       return this.namespacePrefix.remove(namespaceURI).toString();
/*     */     }
/*     */     
/* 206 */     if (prefix != "") {
/* 207 */       this.namespacePrefix.put(namespaceURI, prefix);
/*     */     }
/* 209 */     if (oldPrefix != null)
/* 210 */       return oldPrefix.toString(); 
/* 211 */     return null;
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
/*     */   public String getDefaultNamespacePrefix() {
/* 224 */     return this.defaultNamespacePrefix;
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
/*     */   
/*     */   public void setDefaultNamespacePrefix(String defaultNamespacePrefix) {
/* 238 */     this.defaultNamespacePrefix = defaultNamespacePrefix;
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
/*     */ 
/*     */   
/*     */   public Object setProperty(String name, Object value) {
/* 253 */     if (this.property == null) {
/* 254 */       this.property = new HashMap<Object, Object>();
/*     */     }
/* 256 */     return this.property.put(name, value);
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
/*     */   public Object getProperty(String name) {
/* 269 */     if (this.property != null) {
/* 270 */       return this.property.get(name);
/*     */     }
/* 272 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/* 294 */     if (this.context != null) {
/* 295 */       return this.context.get(key);
/*     */     }
/* 297 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(Object key, Object value) {
/* 319 */     if (this.context == null) {
/* 320 */       this.context = new HashMap<Object, Object>();
/*     */     }
/*     */     
/* 323 */     return this.context.put(key, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\jaxb\JAXBCryptoContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */