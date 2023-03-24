/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class StAXPostInitAction
/*     */   implements Runnable
/*     */ {
/*     */   private final XMLStreamWriter xsw;
/*     */   private final XMLEventWriter xew;
/*     */   private final NamespaceContext nsc;
/*     */   private final XMLSerializer serializer;
/*     */   
/*     */   StAXPostInitAction(XMLStreamWriter xsw, XMLSerializer serializer) {
/*  65 */     this.xsw = xsw;
/*  66 */     this.xew = null;
/*  67 */     this.nsc = null;
/*  68 */     this.serializer = serializer;
/*     */   }
/*     */   
/*     */   StAXPostInitAction(XMLEventWriter xew, XMLSerializer serializer) {
/*  72 */     this.xsw = null;
/*  73 */     this.xew = xew;
/*  74 */     this.nsc = null;
/*  75 */     this.serializer = serializer;
/*     */   }
/*     */   
/*     */   StAXPostInitAction(NamespaceContext nsc, XMLSerializer serializer) {
/*  79 */     this.xsw = null;
/*  80 */     this.xew = null;
/*  81 */     this.nsc = nsc;
/*  82 */     this.serializer = serializer;
/*     */   }
/*     */   
/*     */   public void run() {
/*  86 */     NamespaceContext ns = this.nsc;
/*  87 */     if (this.xsw != null) ns = this.xsw.getNamespaceContext(); 
/*  88 */     if (this.xew != null) ns = this.xew.getNamespaceContext();
/*     */ 
/*     */ 
/*     */     
/*  92 */     if (ns == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  98 */     for (String nsUri : this.serializer.grammar.nameList.namespaceURIs) {
/*  99 */       String p = ns.getPrefix(nsUri);
/* 100 */       if (p != null)
/* 101 */         this.serializer.addInscopeBinding(nsUri, p); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\StAXPostInitAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */