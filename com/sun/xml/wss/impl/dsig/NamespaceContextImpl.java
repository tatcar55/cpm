/*     */ package com.sun.xml.wss.impl.dsig;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamespaceContextImpl
/*     */   implements NamespaceContext
/*     */ {
/*  57 */   HashMap namespaceMap = null;
/*     */   
/*     */   public NamespaceContextImpl() {
/*  60 */     this.namespaceMap = new HashMap<Object, Object>(10);
/*  61 */     add("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
/*  62 */     add("env", "http://schemas.xmlsoap.org/soap/envelope/");
/*  63 */     add("ds", "http://www.w3.org/2000/09/xmldsig#");
/*  64 */     add("xenc", "http://www.w3.org/2001/04/xmlenc#");
/*  65 */     add("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/*  66 */     add("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*  67 */     add("saml", "urn:oasis:names:tc:SAML:1.0:assertion");
/*  68 */     add("S11", "http://schemas.xmlsoap.org/soap/envelope/");
/*  69 */     add("S12", "http://www.w3.org/2003/05/soap-envelope");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String prefix, String uri) {
/*  79 */     this.namespaceMap.put(prefix, uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/*  88 */     return (String)this.namespaceMap.get(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(String namespaceURI) {
/*  97 */     Iterator<String> iterator = this.namespaceMap.keySet().iterator();
/*  98 */     while (iterator.hasNext()) {
/*  99 */       String prefix = iterator.next();
/* 100 */       String uri = (String)this.namespaceMap.get(prefix);
/* 101 */       if (namespaceURI.equals(uri)) {
/* 102 */         return prefix;
/*     */       }
/*     */     } 
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getPrefixes(String namespaceURI) {
/* 116 */     ArrayList<String> prefixList = new ArrayList();
/* 117 */     Iterator<String> iterator = this.namespaceMap.keySet().iterator();
/* 118 */     while (iterator.hasNext()) {
/* 119 */       String prefix = iterator.next();
/*     */       
/* 121 */       String uri = (String)this.namespaceMap.get(prefix);
/*     */       
/* 123 */       if (namespaceURI.equals(uri)) {
/* 124 */         prefixList.add(prefix);
/*     */       }
/*     */     } 
/* 127 */     return prefixList.iterator();
/*     */   }
/*     */   
/*     */   public Map getMap() {
/* 131 */     return this.namespaceMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\dsig\NamespaceContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */