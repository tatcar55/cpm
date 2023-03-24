/*     */ package com.sun.xml.messaging.saaj.util;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamespaceContextIterator
/*     */   implements Iterator
/*     */ {
/*     */   Node context;
/*  54 */   NamedNodeMap attributes = null;
/*     */   int attributesLength;
/*     */   int attributeIndex;
/*  57 */   Attr next = null;
/*  58 */   Attr last = null;
/*     */   boolean traverseStack = true;
/*     */   
/*     */   public NamespaceContextIterator(Node context) {
/*  62 */     this.context = context;
/*  63 */     findContextAttributes();
/*     */   }
/*     */   
/*     */   public NamespaceContextIterator(Node context, boolean traverseStack) {
/*  67 */     this(context);
/*  68 */     this.traverseStack = traverseStack;
/*     */   }
/*     */   
/*     */   protected void findContextAttributes() {
/*  72 */     while (this.context != null) {
/*  73 */       int type = this.context.getNodeType();
/*  74 */       if (type == 1) {
/*  75 */         this.attributes = this.context.getAttributes();
/*  76 */         this.attributesLength = this.attributes.getLength();
/*  77 */         this.attributeIndex = 0;
/*     */         return;
/*     */       } 
/*  80 */       this.context = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void findNext() {
/*  86 */     while (this.next == null && this.context != null) {
/*  87 */       for (; this.attributeIndex < this.attributesLength; this.attributeIndex++) {
/*  88 */         Node currentAttribute = this.attributes.item(this.attributeIndex);
/*  89 */         String attributeName = currentAttribute.getNodeName();
/*  90 */         if (attributeName.startsWith("xmlns") && (attributeName.length() == 5 || attributeName.charAt(5) == ':')) {
/*     */ 
/*     */           
/*  93 */           this.next = (Attr)currentAttribute;
/*  94 */           this.attributeIndex++;
/*     */           return;
/*     */         } 
/*     */       } 
/*  98 */       if (this.traverseStack) {
/*  99 */         this.context = this.context.getParentNode();
/* 100 */         findContextAttributes(); continue;
/*     */       } 
/* 102 */       this.context = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/* 108 */     findNext();
/* 109 */     return (this.next != null);
/*     */   }
/*     */   
/*     */   public Object next() {
/* 113 */     return getNext();
/*     */   }
/*     */   
/*     */   public Attr nextNamespaceAttr() {
/* 117 */     return getNext();
/*     */   }
/*     */   
/*     */   protected Attr getNext() {
/* 121 */     findNext();
/* 122 */     if (this.next == null) {
/* 123 */       throw new NoSuchElementException();
/*     */     }
/* 125 */     this.last = this.next;
/* 126 */     this.next = null;
/* 127 */     return this.last;
/*     */   }
/*     */   
/*     */   public void remove() {
/* 131 */     if (this.last == null) {
/* 132 */       throw new IllegalStateException();
/*     */     }
/* 134 */     ((Element)this.context).removeAttributeNode(this.last);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\NamespaceContextIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */