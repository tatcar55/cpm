/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*     */ import com.sun.xml.bind.marshaller.NamespaceSupport;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   implements NamespaceContext2
/*     */ {
/*  39 */   private int iota = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private final NamespaceSupport nss = new NamespaceSupport();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inCollectingMode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final NamespacePrefixMapper prefixMapper;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private final Map decls = new HashMap();
/*     */   
/*  62 */   private final Map reverseDecls = new HashMap();
/*     */ 
/*     */   
/*     */   public NamespaceContextImpl(NamespacePrefixMapper _prefixMapper) {
/*  66 */     this.prefixMapper = _prefixMapper;
/*     */ 
/*     */     
/*  69 */     this.nss.declarePrefix("", "");
/*  70 */     this.nss.declarePrefix("xmlns", "http://www.w3.org/2000/xmlns/");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final NamespacePrefixMapper getNamespacePrefixMapper() {
/*  76 */     return this.prefixMapper;
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
/*     */   public String declareNamespace(String namespaceUri, String preferedPrefix, boolean requirePrefix) {
/*  89 */     if (!this.inCollectingMode) {
/*  90 */       if (!requirePrefix && this.nss.getURI("").equals(namespaceUri)) {
/*  91 */         return "";
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       if (requirePrefix) {
/*  98 */         return this.nss.getPrefix2(namespaceUri);
/*     */       }
/* 100 */       return this.nss.getPrefix(namespaceUri);
/*     */     } 
/* 102 */     if (requirePrefix && namespaceUri.length() == 0) {
/* 103 */       return "";
/*     */     }
/*     */     
/* 106 */     String prefix = (String)this.reverseDecls.get(namespaceUri);
/* 107 */     if (prefix != null) {
/* 108 */       if (!requirePrefix || prefix.length() != 0)
/*     */       {
/*     */         
/* 111 */         return prefix;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       this.decls.remove(prefix);
/* 118 */       this.reverseDecls.remove(namespaceUri);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 123 */     if (namespaceUri.length() == 0) {
/*     */       
/* 125 */       prefix = "";
/*     */     } else {
/*     */       
/* 128 */       prefix = this.nss.getPrefix(namespaceUri);
/* 129 */       if (prefix == null) {
/* 130 */         prefix = (String)this.reverseDecls.get(namespaceUri);
/*     */       }
/* 132 */       if (prefix == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 138 */         if (this.prefixMapper != null) {
/* 139 */           prefix = this.prefixMapper.getPreferredPrefix(namespaceUri, preferedPrefix, requirePrefix);
/*     */         } else {
/*     */           
/* 142 */           prefix = preferedPrefix;
/*     */         } 
/* 144 */         if (prefix == null)
/*     */         {
/* 146 */           prefix = "ns" + this.iota++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 152 */     if (requirePrefix && prefix.length() == 0)
/*     */     {
/* 154 */       prefix = "ns" + this.iota++;
/*     */     }
/*     */     
/*     */     while (true) {
/* 158 */       String existingUri = (String)this.decls.get(prefix);
/*     */       
/* 160 */       if (existingUri == null) {
/*     */         
/* 162 */         this.decls.put(prefix, namespaceUri);
/* 163 */         this.reverseDecls.put(namespaceUri, prefix);
/* 164 */         return prefix;
/*     */       } 
/*     */       
/* 167 */       if (existingUri.length() != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 178 */         this.decls.put(prefix, namespaceUri);
/* 179 */         this.reverseDecls.put(namespaceUri, prefix);
/*     */         
/* 181 */         namespaceUri = existingUri;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 186 */       prefix = "ns" + this.iota++;
/*     */     } 
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
/*     */   public String getPrefix(String namespaceUri) {
/* 202 */     return declareNamespace(namespaceUri, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 210 */     String uri = (String)this.decls.get(prefix);
/* 211 */     if (uri != null) return uri;
/*     */     
/* 213 */     return this.nss.getURI(prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator getPrefixes(String namespaceUri) {
/* 218 */     Set s = new HashSet();
/*     */     
/* 220 */     String prefix = (String)this.reverseDecls.get(namespaceUri);
/* 221 */     if (prefix != null) s.add(prefix);
/*     */     
/* 223 */     if (this.nss.getURI("").equals(namespaceUri)) {
/* 224 */       s.add("");
/*     */     }
/* 226 */     for (Enumeration e = this.nss.getPrefixes(namespaceUri); e.hasMoreElements();) {
/* 227 */       s.add(e.nextElement());
/*     */     }
/* 229 */     return s.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement() {
/* 239 */     this.nss.pushContext();
/* 240 */     this.inCollectingMode = true;
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
/*     */   public void endNamespaceDecls() {
/* 252 */     if (!this.decls.isEmpty()) {
/*     */       
/* 254 */       for (Iterator itr = this.decls.entrySet().iterator(); itr.hasNext(); ) {
/* 255 */         Map.Entry e = itr.next();
/* 256 */         String prefix = (String)e.getKey();
/* 257 */         String uri = (String)e.getValue();
/* 258 */         if (!uri.equals(this.nss.getURI(prefix)))
/* 259 */           this.nss.declarePrefix(prefix, uri); 
/*     */       } 
/* 261 */       this.decls.clear();
/* 262 */       this.reverseDecls.clear();
/*     */     } 
/* 264 */     this.inCollectingMode = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement() {
/* 274 */     this.nss.popContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void iterateDeclaredPrefixes(PrefixCallback callback) throws SAXException {
/* 281 */     for (Enumeration e = this.nss.getDeclaredPrefixes(); e.hasMoreElements(); ) {
/* 282 */       String p = e.nextElement();
/* 283 */       String uri = this.nss.getURI(p);
/*     */       
/* 285 */       callback.onPrefixMapping(p, uri);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\NamespaceContextImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */