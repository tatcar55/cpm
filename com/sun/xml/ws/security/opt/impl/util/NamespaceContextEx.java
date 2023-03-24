/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamespaceContextEx
/*     */   implements NamespaceContextEx
/*     */ {
/*     */   private boolean addedWSSNS = false;
/*     */   private boolean samlNS = false;
/*     */   private boolean dsNS = false;
/*     */   private boolean encNS = false;
/*     */   private boolean scNS = false;
/*     */   private boolean exc14NS = false;
/*     */   private boolean addedWSS11NS = false;
/*  68 */   private ArrayList<NamespaceContextEx.Binding> list = new ArrayList<NamespaceContextEx.Binding>();
/*     */   
/*     */   private boolean addedXSDNS = false;
/*     */   
/*     */   public NamespaceContextEx() {
/*  73 */     add("S", "http://schemas.xmlsoap.org/soap/envelope/");
/*  74 */     addDefaultNSDecl();
/*     */   }
/*     */ 
/*     */   
/*     */   public NamespaceContextEx(boolean soap12Version) {
/*  79 */     if (soap12Version) {
/*  80 */       add("S", "http://www.w3.org/2003/05/soap-envelope");
/*     */     } else {
/*  82 */       add("S", "http://schemas.xmlsoap.org/soap/envelope/");
/*     */     } 
/*  84 */     addDefaultNSDecl();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addDefaultNSDecl() {}
/*     */ 
/*     */   
/*     */   public void addWSSNS() {
/*  92 */     if (!this.addedWSSNS) {
/*  93 */       add("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/*  94 */       add("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*  95 */       this.addedWSSNS = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addWSS11NS() {
/* 100 */     if (!this.addedWSS11NS) {
/* 101 */       add("wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
/* 102 */       this.addedWSS11NS = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addXSDNS() {
/* 107 */     if (!this.addedXSDNS) {
/* 108 */       add("xs", "http://www.w3.org/2001/XMLSchema");
/* 109 */       this.addedXSDNS = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addSignatureNS() {
/* 114 */     addWSSNS();
/* 115 */     if (!this.dsNS) {
/* 116 */       add("ds", "http://www.w3.org/2000/09/xmldsig#");
/* 117 */       this.dsNS = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addEncryptionNS() {
/* 122 */     addWSSNS();
/* 123 */     if (!this.encNS) {
/* 124 */       add("xenc", "http://www.w3.org/2001/04/xmlenc#");
/* 125 */       this.encNS = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addSAMLNS() {
/* 130 */     if (!this.samlNS) {
/* 131 */       add("saml", "urn:oasis:names:tc:SAML:1.0:assertion");
/* 132 */       this.samlNS = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addSCNS() {
/* 137 */     if (!this.scNS) {
/* 138 */       add("wsc", "http://schemas.xmlsoap.org/ws/2005/02/sc");
/* 139 */       this.scNS = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addExc14NS() {
/* 144 */     if (!this.exc14NS) {
/* 145 */       add("exc14n", "http://www.w3.org/2001/10/xml-exc-c14n#");
/* 146 */       this.exc14NS = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void add(String prefix, String uri) {
/* 151 */     this.list.add(new BindingImpl(prefix, uri));
/*     */   }
/*     */   
/*     */   public Iterator<NamespaceContextEx.Binding> iterator() {
/* 155 */     return this.list.iterator();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 159 */     for (NamespaceContextEx.Binding binding : this.list) {
/* 160 */       if (prefix.equals(binding.getPrefix())) {
/* 161 */         return binding.getNamespaceURI();
/*     */       }
/*     */     } 
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   public String getPrefix(String namespaceURI) {
/* 168 */     for (NamespaceContextEx.Binding binding : this.list) {
/* 169 */       if (namespaceURI.equals(binding.getNamespaceURI())) {
/* 170 */         return binding.getPrefix();
/*     */       }
/*     */     } 
/* 173 */     return null;
/*     */   }
/*     */   
/*     */   public Iterator getPrefixes(final String namespaceURI) {
/* 177 */     return new Iterator()
/*     */       {
/* 179 */         int index = 0;
/*     */         
/*     */         public boolean hasNext() {
/* 182 */           if (++this.index < NamespaceContextEx.this.list.size() && move()) {
/* 183 */             return true;
/*     */           }
/* 185 */           return false;
/*     */         }
/*     */         
/*     */         public Object next() {
/* 189 */           return ((NamespaceContextEx.Binding)NamespaceContextEx.this.list.get(this.index)).getPrefix();
/*     */         }
/*     */         
/*     */         public void remove() {
/* 193 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         private boolean move() {
/* 197 */           boolean found = false;
/*     */           do {
/* 199 */             if (namespaceURI.equals(((NamespaceContextEx.Binding)NamespaceContextEx.this.list.get(this.index)).getNamespaceURI())) {
/* 200 */               found = true;
/*     */               break;
/*     */             } 
/* 203 */             this.index++;
/*     */           }
/* 205 */           while (this.index < NamespaceContextEx.this.list.size());
/* 206 */           return found;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static class BindingImpl
/*     */     implements NamespaceContextEx.Binding {
/* 213 */     private String prefix = "";
/* 214 */     private String uri = "";
/*     */     public BindingImpl(String prefix, String uri) {
/* 216 */       this.prefix = prefix;
/* 217 */       this.uri = uri;
/*     */     }
/*     */     
/*     */     public String getPrefix() {
/* 221 */       return this.prefix;
/*     */     }
/*     */     
/*     */     public String getNamespaceURI() {
/* 225 */       return this.uri;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\NamespaceContextEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */