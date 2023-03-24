/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.Selector;
/*     */ import java.util.EmptyStackException;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SelectorFactory
/*     */ {
/*  62 */   static long timeout = 5000L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   static int maxSelectors = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final Stack<Selector> selectors = new Stack<Selector>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  82 */       for (int i = 0; i < maxSelectors; i++)
/*  83 */         selectors.add(Selector.open()); 
/*  84 */     } catch (IOException ex) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Selector getSelector() {
/*  93 */     synchronized (selectors) {
/*  94 */       Selector s = null;
/*     */       try {
/*  96 */         if (selectors.size() != 0)
/*  97 */           s = selectors.pop(); 
/*  98 */       } catch (EmptyStackException ex) {}
/*     */       
/* 100 */       int attempts = 0;
/*     */       try {
/* 102 */         while (s == null && attempts < 2) {
/* 103 */           selectors.wait(timeout);
/*     */           try {
/* 105 */             if (selectors.size() != 0)
/* 106 */               s = selectors.pop(); 
/* 107 */           } catch (EmptyStackException ex) {
/*     */             break;
/*     */           } 
/* 110 */           attempts++;
/*     */         } 
/* 112 */       } catch (InterruptedException ex) {}
/* 113 */       return s;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void returnSelector(Selector s) {
/* 122 */     synchronized (selectors) {
/* 123 */       selectors.push(s);
/* 124 */       if (selectors.size() == 1)
/* 125 */         selectors.notify(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\SelectorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */