/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
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
/*     */ public class Graph<E>
/*     */ {
/*     */   private Set<E> roots;
/*  55 */   private List<E> preorderTraversal = null;
/*  56 */   private List<E> postorderTraversal = null;
/*     */   
/*     */   private void traverse(E node, Set<E> visited, Finder<E> finder) {
/*  59 */     if (!visited.contains(node)) {
/*  60 */       visited.add(node);
/*     */       
/*  62 */       this.preorderTraversal.add(node);
/*     */       
/*  64 */       for (E child : finder.evaluate(node)) {
/*  65 */         traverse(child, visited, finder);
/*     */       }
/*     */       
/*  68 */       this.postorderTraversal.add(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init(Collection<E> roots, Finder<E> finder) {
/*  73 */     this.roots = new HashSet<E>(roots);
/*  74 */     this.roots = Collections.unmodifiableSet(this.roots);
/*  75 */     this.preorderTraversal = new ArrayList<E>();
/*  76 */     this.postorderTraversal = new ArrayList<E>();
/*  77 */     Set<E> visited = new HashSet<E>();
/*  78 */     for (E node : this.roots) {
/*  79 */       traverse(node, visited, finder);
/*     */     }
/*  81 */     this.preorderTraversal = Collections.unmodifiableList(this.preorderTraversal);
/*  82 */     this.postorderTraversal = Collections.unmodifiableList(this.postorderTraversal);
/*     */   }
/*     */   
/*     */   public Graph(Collection<E> roots, Finder<E> finder) {
/*  86 */     init(roots, finder);
/*     */   }
/*     */   
/*     */   public Graph(E root, Finder<E> finder) {
/*  90 */     Set<E> roots = new HashSet<E>();
/*  91 */     roots.add(root);
/*  92 */     init(roots, finder);
/*     */   }
/*     */   
/*     */   public Set<E> getRoots() {
/*  96 */     return this.roots;
/*     */   }
/*     */   
/*     */   public List<E> getPreorderList() {
/* 100 */     return this.preorderTraversal;
/*     */   }
/*     */   
/*     */   public List<E> getPostorderList() {
/* 104 */     return this.postorderTraversal;
/*     */   }
/*     */   
/*     */   public static interface Finder<E> extends UnaryFunction<E, List<E>> {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\Graph.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */