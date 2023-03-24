/*     */ package com.sun.xml.wss.impl.dsig;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.xml.crypto.NodeSetData;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMSubTreeData
/*     */   implements NodeSetData
/*     */ {
/*     */   private boolean excludeComments;
/*     */   private Iterator ni;
/*     */   private Node root;
/*     */   
/*     */   public DOMSubTreeData(Node root, boolean excludeComments) {
/*  66 */     this.root = root;
/*  67 */     this.ni = new DelayedNodeIterator(root, excludeComments);
/*  68 */     this.excludeComments = excludeComments;
/*     */   }
/*     */   
/*     */   public Iterator iterator() {
/*  72 */     return this.ni;
/*     */   }
/*     */   
/*     */   public Node getRoot() {
/*  76 */     return this.root;
/*     */   }
/*     */   
/*     */   public boolean excludeComments() {
/*  80 */     return this.excludeComments;
/*     */   }
/*     */ 
/*     */   
/*     */   static class DelayedNodeIterator
/*     */     implements Iterator
/*     */   {
/*     */     private Node root;
/*     */     
/*     */     private List nodeSet;
/*     */     private ListIterator li;
/*     */     private boolean withComments;
/*     */     
/*     */     DelayedNodeIterator(Node root, boolean excludeComments) {
/*  94 */       this.root = root;
/*  95 */       this.withComments = !excludeComments;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/*  99 */       if (this.nodeSet == null) {
/* 100 */         this.nodeSet = dereferenceSameDocumentURI(this.root);
/* 101 */         this.li = this.nodeSet.listIterator();
/*     */       } 
/* 103 */       return this.li.hasNext();
/*     */     }
/*     */     
/*     */     public Object next() {
/* 107 */       if (this.nodeSet == null) {
/* 108 */         this.nodeSet = dereferenceSameDocumentURI(this.root);
/* 109 */         this.li = this.nodeSet.listIterator();
/*     */       } 
/* 111 */       if (this.li.hasNext()) {
/* 112 */         return this.li.next();
/*     */       }
/* 114 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 119 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private List dereferenceSameDocumentURI(Node node) {
/* 130 */       List nodeSet = new ArrayList();
/* 131 */       if (node != null) {
/* 132 */         nodeSetMinusCommentNodes(node, nodeSet, null);
/*     */       }
/* 134 */       return nodeSet;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void nodeSetMinusCommentNodes(Node node, List<Node> nodeSet, Node prevSibling) {
/*     */       NamedNodeMap attrs;
/*     */       Node pSibling;
/*     */       Node child;
/* 148 */       switch (node.getNodeType()) {
/*     */         case 1:
/* 150 */           attrs = node.getAttributes();
/* 151 */           if (attrs != null) {
/* 152 */             for (int i = 0, len = attrs.getLength(); i < len; i++) {
/* 153 */               nodeSet.add(attrs.item(i));
/*     */             }
/*     */           }
/* 156 */           nodeSet.add(node);
/*     */         case 9:
/* 158 */           pSibling = null;
/* 159 */           for (child = node.getFirstChild(); child != null; 
/* 160 */             child = child.getNextSibling()) {
/* 161 */             nodeSetMinusCommentNodes(child, nodeSet, pSibling);
/* 162 */             pSibling = child;
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/*     */         case 4:
/* 169 */           if (prevSibling != null && (prevSibling.getNodeType() == 3 || prevSibling.getNodeType() == 4)) {
/*     */             return;
/*     */           }
/*     */ 
/*     */         
/*     */         case 7:
/* 175 */           nodeSet.add(node);
/*     */           break;
/*     */         case 8:
/* 178 */           if (this.withComments)
/* 179 */             nodeSet.add(node); 
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\dsig\DOMSubTreeData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */