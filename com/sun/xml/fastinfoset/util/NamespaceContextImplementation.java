/*     */ package com.sun.xml.fastinfoset.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public final class NamespaceContextImplementation
/*     */   implements NamespaceContext
/*     */ {
/*  30 */   private static int DEFAULT_SIZE = 8;
/*     */   
/*  32 */   private String[] prefixes = new String[DEFAULT_SIZE];
/*  33 */   private String[] namespaceURIs = new String[DEFAULT_SIZE];
/*     */   
/*     */   private int namespacePosition;
/*  36 */   private int[] contexts = new int[DEFAULT_SIZE];
/*     */   
/*     */   private int contextPosition;
/*     */   private int currentContext;
/*     */   
/*     */   public NamespaceContextImplementation() {
/*  42 */     this.prefixes[0] = "xml";
/*  43 */     this.namespaceURIs[0] = "http://www.w3.org/XML/1998/namespace";
/*  44 */     this.prefixes[1] = "xmlns";
/*  45 */     this.namespaceURIs[1] = "http://www.w3.org/2000/xmlns/";
/*     */     
/*  47 */     this.currentContext = this.namespacePosition = 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/*  52 */     if (prefix == null) throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */     
/*  56 */     for (int i = this.namespacePosition - 1; i >= 0; i--) {
/*  57 */       String declaredPrefix = this.prefixes[i];
/*  58 */       if (declaredPrefix.equals(prefix)) {
/*  59 */         return this.namespaceURIs[i];
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return "";
/*     */   }
/*     */   
/*     */   public String getPrefix(String namespaceURI) {
/*  67 */     if (namespaceURI == null) throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */     
/*  71 */     for (int i = this.namespacePosition - 1; i >= 0; i--) {
/*  72 */       String declaredNamespaceURI = this.namespaceURIs[i];
/*  73 */       if (declaredNamespaceURI.equals(namespaceURI)) {
/*  74 */         String declaredPrefix = this.prefixes[i];
/*     */ 
/*     */         
/*  77 */         boolean isOutOfScope = false;
/*  78 */         for (int j = i + 1; j < this.namespacePosition; j++) {
/*  79 */           if (declaredPrefix.equals(this.prefixes[j])) {
/*  80 */             isOutOfScope = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*  84 */         if (!isOutOfScope) {
/*  85 */           return declaredPrefix;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     return null;
/*     */   }
/*     */   
/*     */   public String getNonDefaultPrefix(String namespaceURI) {
/*  94 */     if (namespaceURI == null) throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */     
/*  98 */     for (int i = this.namespacePosition - 1; i >= 0; i--) {
/*  99 */       String declaredNamespaceURI = this.namespaceURIs[i];
/* 100 */       if (declaredNamespaceURI.equals(namespaceURI) && this.prefixes[i].length() > 0) {
/*     */         
/* 102 */         String declaredPrefix = this.prefixes[i];
/*     */ 
/*     */         
/* 105 */         for (; ++i < this.namespacePosition; i++) {
/* 106 */           if (declaredPrefix.equals(this.prefixes[i]))
/* 107 */             return null; 
/*     */         } 
/* 109 */         return declaredPrefix;
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     return null;
/*     */   }
/*     */   
/*     */   public Iterator getPrefixes(String namespaceURI) {
/* 117 */     if (namespaceURI == null) throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */     
/* 121 */     List<String> l = new ArrayList();
/*     */     
/* 123 */     for (int i = this.namespacePosition - 1; i >= 0; i--) {
/* 124 */       String declaredNamespaceURI = this.namespaceURIs[i];
/* 125 */       if (declaredNamespaceURI.equals(namespaceURI)) {
/* 126 */         String declaredPrefix = this.prefixes[i];
/*     */ 
/*     */         
/* 129 */         int j = i + 1; while (true) { if (j < this.namespacePosition) {
/* 130 */             if (declaredPrefix.equals(this.prefixes[j]))
/*     */               break;  j++; continue;
/*     */           } 
/* 133 */           l.add(declaredPrefix); break; }
/*     */       
/*     */       } 
/*     */     } 
/* 137 */     return l.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix(int index) {
/* 142 */     return this.prefixes[index];
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(int index) {
/* 146 */     return this.namespaceURIs[index];
/*     */   }
/*     */   
/*     */   public int getCurrentContextStartIndex() {
/* 150 */     return this.currentContext;
/*     */   }
/*     */   
/*     */   public int getCurrentContextEndIndex() {
/* 154 */     return this.namespacePosition;
/*     */   }
/*     */   
/*     */   public boolean isCurrentContextEmpty() {
/* 158 */     return (this.currentContext == this.namespacePosition);
/*     */   }
/*     */   
/*     */   public void declarePrefix(String prefix, String namespaceURI) {
/* 162 */     prefix = prefix.intern();
/* 163 */     namespaceURI = namespaceURI.intern();
/*     */ 
/*     */     
/* 166 */     if (prefix == "xml" || prefix == "xmlns") {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     for (int i = this.currentContext; i < this.namespacePosition; i++) {
/* 171 */       String declaredPrefix = this.prefixes[i];
/* 172 */       if (declaredPrefix == prefix) {
/* 173 */         this.prefixes[i] = prefix;
/* 174 */         this.namespaceURIs[i] = namespaceURI;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 179 */     if (this.namespacePosition == this.namespaceURIs.length) {
/* 180 */       resizeNamespaces();
/*     */     }
/*     */     
/* 183 */     this.prefixes[this.namespacePosition] = prefix;
/* 184 */     this.namespaceURIs[this.namespacePosition++] = namespaceURI;
/*     */   }
/*     */   
/*     */   private void resizeNamespaces() {
/* 188 */     int newLength = this.namespaceURIs.length * 3 / 2 + 1;
/*     */     
/* 190 */     String[] newPrefixes = new String[newLength];
/* 191 */     System.arraycopy(this.prefixes, 0, newPrefixes, 0, this.prefixes.length);
/* 192 */     this.prefixes = newPrefixes;
/*     */     
/* 194 */     String[] newNamespaceURIs = new String[newLength];
/* 195 */     System.arraycopy(this.namespaceURIs, 0, newNamespaceURIs, 0, this.namespaceURIs.length);
/* 196 */     this.namespaceURIs = newNamespaceURIs;
/*     */   }
/*     */   
/*     */   public void pushContext() {
/* 200 */     if (this.contextPosition == this.contexts.length) {
/* 201 */       resizeContexts();
/*     */     }
/* 203 */     this.contexts[this.contextPosition++] = this.currentContext = this.namespacePosition;
/*     */   }
/*     */   
/*     */   private void resizeContexts() {
/* 207 */     int[] newContexts = new int[this.contexts.length * 3 / 2 + 1];
/* 208 */     System.arraycopy(this.contexts, 0, newContexts, 0, this.contexts.length);
/* 209 */     this.contexts = newContexts;
/*     */   }
/*     */   
/*     */   public void popContext() {
/* 213 */     if (this.contextPosition > 0) {
/* 214 */       this.namespacePosition = this.currentContext = this.contexts[--this.contextPosition];
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/* 219 */     this.currentContext = this.namespacePosition = 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\NamespaceContextImplementation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */