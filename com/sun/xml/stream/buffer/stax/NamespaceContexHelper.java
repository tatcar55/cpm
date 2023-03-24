/*     */ package com.sun.xml.stream.buffer.stax;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NamespaceContexHelper
/*     */   implements NamespaceContextEx
/*     */ {
/*  78 */   private static int DEFAULT_SIZE = 8;
/*     */ 
/*     */   
/*  81 */   private String[] prefixes = new String[DEFAULT_SIZE];
/*     */   
/*  83 */   private String[] namespaceURIs = new String[DEFAULT_SIZE];
/*     */ 
/*     */   
/*     */   private int namespacePosition;
/*     */   
/*  88 */   private int[] contexts = new int[DEFAULT_SIZE];
/*     */ 
/*     */ 
/*     */   
/*     */   private int contextPosition;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContexHelper() {
/*  98 */     this.prefixes[0] = "xml";
/*  99 */     this.namespaceURIs[0] = "http://www.w3.org/XML/1998/namespace";
/* 100 */     this.prefixes[1] = "xmlns";
/* 101 */     this.namespaceURIs[1] = "http://www.w3.org/2000/xmlns/";
/*     */     
/* 103 */     this.namespacePosition = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 110 */     if (prefix == null) throw new IllegalArgumentException();
/*     */     
/* 112 */     prefix = prefix.intern();
/*     */     
/* 114 */     for (int i = this.namespacePosition - 1; i >= 0; i--) {
/* 115 */       String declaredPrefix = this.prefixes[i];
/* 116 */       if (declaredPrefix == prefix) {
/* 117 */         return this.namespaceURIs[i];
/*     */       }
/*     */     } 
/*     */     
/* 121 */     return "";
/*     */   }
/*     */   
/*     */   public String getPrefix(String namespaceURI) {
/* 125 */     if (namespaceURI == null) throw new IllegalArgumentException();
/*     */     
/* 127 */     for (int i = this.namespacePosition - 1; i >= 0; i--) {
/* 128 */       String declaredNamespaceURI = this.namespaceURIs[i];
/* 129 */       if (declaredNamespaceURI == namespaceURI || declaredNamespaceURI.equals(namespaceURI)) {
/* 130 */         String declaredPrefix = this.prefixes[i];
/*     */ 
/*     */         
/* 133 */         for (; ++i < this.namespacePosition; i++) {
/* 134 */           if (declaredPrefix == this.prefixes[i])
/* 135 */             return null; 
/*     */         } 
/* 137 */         return declaredPrefix;
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     return null;
/*     */   }
/*     */   
/*     */   public Iterator getPrefixes(String namespaceURI) {
/* 145 */     if (namespaceURI == null) throw new IllegalArgumentException();
/*     */     
/* 147 */     List<String> l = new ArrayList<String>();
/*     */     
/* 149 */     for (int i = this.namespacePosition - 1; i >= 0; i--) {
/* 150 */       String declaredNamespaceURI = this.namespaceURIs[i];
/* 151 */       if (declaredNamespaceURI == namespaceURI || declaredNamespaceURI.equals(namespaceURI)) {
/* 152 */         String declaredPrefix = this.prefixes[i];
/*     */ 
/*     */         
/* 155 */         int j = i + 1; while (true) { if (j < this.namespacePosition) {
/* 156 */             if (declaredPrefix == this.prefixes[j])
/*     */               break;  j++; continue;
/*     */           } 
/* 159 */           l.add(declaredPrefix); break; }
/*     */       
/*     */       } 
/*     */     } 
/* 163 */     return l.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<NamespaceContextEx.Binding> iterator() {
/* 169 */     if (this.namespacePosition == 2) {
/* 170 */       return Collections.EMPTY_LIST.iterator();
/*     */     }
/* 172 */     List<NamespaceContextEx.Binding> namespaces = new ArrayList<NamespaceContextEx.Binding>(this.namespacePosition);
/*     */ 
/*     */     
/* 175 */     for (int i = this.namespacePosition - 1; i >= 2; i--) {
/* 176 */       String declaredPrefix = this.prefixes[i];
/*     */ 
/*     */       
/* 179 */       for (int j = i + 1; j < this.namespacePosition && 
/* 180 */         declaredPrefix != this.prefixes[j]; j++)
/*     */       {
/*     */         
/* 183 */         namespaces.add(new NamespaceBindingImpl(i));
/*     */       }
/*     */     } 
/*     */     
/* 187 */     return namespaces.iterator();
/*     */   }
/*     */   
/*     */   private final class NamespaceBindingImpl implements NamespaceContextEx.Binding {
/*     */     int index;
/*     */     
/*     */     NamespaceBindingImpl(int index) {
/* 194 */       this.index = index;
/*     */     }
/*     */     
/*     */     public String getPrefix() {
/* 198 */       return NamespaceContexHelper.this.prefixes[this.index];
/*     */     }
/*     */     
/*     */     public String getNamespaceURI() {
/* 202 */       return NamespaceContexHelper.this.namespaceURIs[this.index];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void declareDefaultNamespace(String namespaceURI) {
/* 212 */     declareNamespace("", namespaceURI);
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
/*     */   public void declareNamespace(String prefix, String namespaceURI) {
/* 234 */     if (prefix == null) throw new IllegalArgumentException();
/*     */     
/* 236 */     prefix = prefix.intern();
/*     */     
/* 238 */     if (prefix == "xml" || prefix == "xmlns") {
/*     */       return;
/*     */     }
/*     */     
/* 242 */     if (namespaceURI != null) {
/* 243 */       namespaceURI = namespaceURI.intern();
/*     */     }
/* 245 */     if (this.namespacePosition == this.namespaceURIs.length) {
/* 246 */       resizeNamespaces();
/*     */     }
/*     */     
/* 249 */     this.prefixes[this.namespacePosition] = prefix;
/* 250 */     this.namespaceURIs[this.namespacePosition++] = namespaceURI;
/*     */   }
/*     */   
/*     */   private void resizeNamespaces() {
/* 254 */     int newLength = this.namespaceURIs.length * 3 / 2 + 1;
/*     */     
/* 256 */     String[] newPrefixes = new String[newLength];
/* 257 */     System.arraycopy(this.prefixes, 0, newPrefixes, 0, this.prefixes.length);
/* 258 */     this.prefixes = newPrefixes;
/*     */     
/* 260 */     String[] newNamespaceURIs = new String[newLength];
/* 261 */     System.arraycopy(this.namespaceURIs, 0, newNamespaceURIs, 0, this.namespaceURIs.length);
/* 262 */     this.namespaceURIs = newNamespaceURIs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushContext() {
/* 269 */     if (this.contextPosition == this.contexts.length) {
/* 270 */       resizeContexts();
/*     */     }
/* 272 */     this.contexts[this.contextPosition++] = this.namespacePosition;
/*     */   }
/*     */   
/*     */   private void resizeContexts() {
/* 276 */     int[] newContexts = new int[this.contexts.length * 3 / 2 + 1];
/* 277 */     System.arraycopy(this.contexts, 0, newContexts, 0, this.contexts.length);
/* 278 */     this.contexts = newContexts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void popContext() {
/* 288 */     if (this.contextPosition > 0) {
/* 289 */       this.namespacePosition = this.contexts[--this.contextPosition];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetContexts() {
/* 299 */     this.namespacePosition = 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\stax\NamespaceContexHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */