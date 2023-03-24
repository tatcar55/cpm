/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AssociationMap<XmlNode>
/*     */ {
/*     */   static final class Entry<XmlNode>
/*     */   {
/*     */     private XmlNode element;
/*     */     private Object inner;
/*     */     private Object outer;
/*     */     
/*     */     public XmlNode element() {
/*  70 */       return this.element;
/*     */     }
/*     */     public Object inner() {
/*  73 */       return this.inner;
/*     */     }
/*     */     public Object outer() {
/*  76 */       return this.outer;
/*     */     }
/*     */   }
/*     */   
/*  80 */   private final Map<XmlNode, Entry<XmlNode>> byElement = new IdentityHashMap<XmlNode, Entry<XmlNode>>();
/*  81 */   private final Map<Object, Entry<XmlNode>> byPeer = new IdentityHashMap<Object, Entry<XmlNode>>();
/*  82 */   private final Set<XmlNode> usedNodes = new HashSet<XmlNode>();
/*     */ 
/*     */   
/*     */   public void addInner(XmlNode element, Object inner) {
/*  86 */     Entry<XmlNode> e = this.byElement.get(element);
/*  87 */     if (e != null) {
/*  88 */       if (e.inner != null)
/*  89 */         this.byPeer.remove(e.inner); 
/*  90 */       e.inner = inner;
/*     */     } else {
/*  92 */       e = new Entry<XmlNode>();
/*  93 */       e.element = element;
/*  94 */       e.inner = inner;
/*     */     } 
/*     */     
/*  97 */     this.byElement.put(element, e);
/*     */     
/*  99 */     Entry<XmlNode> old = this.byPeer.put(inner, e);
/* 100 */     if (old != null) {
/* 101 */       if (old.outer != null)
/* 102 */         this.byPeer.remove(old.outer); 
/* 103 */       if (old.element != null) {
/* 104 */         this.byElement.remove(old.element);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addOuter(XmlNode element, Object outer) {
/* 110 */     Entry<XmlNode> e = this.byElement.get(element);
/* 111 */     if (e != null) {
/* 112 */       if (e.outer != null)
/* 113 */         this.byPeer.remove(e.outer); 
/* 114 */       e.outer = outer;
/*     */     } else {
/* 116 */       e = new Entry<XmlNode>();
/* 117 */       e.element = element;
/* 118 */       e.outer = outer;
/*     */     } 
/*     */     
/* 121 */     this.byElement.put(element, e);
/*     */     
/* 123 */     Entry<XmlNode> old = this.byPeer.put(outer, e);
/* 124 */     if (old != null) {
/* 125 */       old.outer = null;
/*     */       
/* 127 */       if (old.inner == null)
/*     */       {
/* 129 */         this.byElement.remove(old.element); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addUsed(XmlNode n) {
/* 134 */     this.usedNodes.add(n);
/*     */   }
/*     */   
/*     */   public Entry<XmlNode> byElement(Object e) {
/* 138 */     return this.byElement.get(e);
/*     */   }
/*     */   
/*     */   public Entry<XmlNode> byPeer(Object o) {
/* 142 */     return this.byPeer.get(o);
/*     */   }
/*     */   
/*     */   public Object getInnerPeer(XmlNode element) {
/* 146 */     Entry<XmlNode> e = byElement(element);
/* 147 */     if (e == null) return null; 
/* 148 */     return e.inner;
/*     */   }
/*     */   
/*     */   public Object getOuterPeer(XmlNode element) {
/* 152 */     Entry<XmlNode> e = byElement(element);
/* 153 */     if (e == null) return null; 
/* 154 */     return e.outer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\AssociationMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */