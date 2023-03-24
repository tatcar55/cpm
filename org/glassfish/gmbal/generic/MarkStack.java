/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.EmptyStackException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MarkStack<E>
/*     */ {
/*  58 */   private List<E> items = new ArrayList<E>();
/*  59 */   private List<Integer> marks = new ArrayList<Integer>();
/*     */ 
/*     */   
/*     */   public E push(E elem) {
/*  63 */     this.items.add(elem);
/*  64 */     return elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E pop() {
/*  73 */     if (isEmpty()) {
/*  74 */       throw new EmptyStackException();
/*     */     }
/*  76 */     if (this.marks.size() > 0) {
/*  77 */       int topMark = ((Integer)this.marks.get(this.marks.size() - 1)).intValue();
/*  78 */       if (topMark == this.items.size()) {
/*  79 */         throw new IllegalStateException("Cannot pop item past top mark");
/*     */       }
/*     */     } 
/*  82 */     E result = this.items.remove(this.items.size() - 1);
/*  83 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  89 */     if (this.marks.size() > 0) {
/*  90 */       int topMark = ((Integer)this.marks.get(this.marks.size() - 1)).intValue();
/*  91 */       return (topMark == this.items.size());
/*     */     } 
/*     */     
/*  94 */     return (this.items.size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E peek() {
/* 101 */     if (isEmpty()) {
/* 102 */       throw new EmptyStackException();
/*     */     }
/* 104 */     return this.items.get(this.items.size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark() {
/* 114 */     this.marks.add(Integer.valueOf(this.items.size()));
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
/*     */   public List<E> popMark() {
/* 127 */     LinkedList<E> result = new LinkedList<E>();
/* 128 */     int topMark = ((Integer)this.marks.remove(this.marks.size() - 1)).intValue();
/* 129 */     while (this.items.size() > topMark) {
/* 130 */       result.addFirst(this.items.remove(this.items.size() - 1));
/*     */     }
/* 132 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\MarkStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */