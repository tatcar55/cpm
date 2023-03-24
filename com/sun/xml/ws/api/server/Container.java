/*     */ package com.sun.xml.ws.api.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.api.ComponentEx;
/*     */ import com.sun.xml.ws.api.ComponentRegistry;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Container
/*     */   implements ComponentRegistry, ComponentEx
/*     */ {
/*  91 */   private final Set<Component> components = new CopyOnWriteArraySet<Component>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static final Container NONE = new NoneContainer();
/*     */   
/*     */   private static final class NoneContainer extends Container {
/*     */     private NoneContainer() {} }
/*     */   
/*     */   public <S> S getSPI(Class<S> spiType) {
/* 109 */     for (Component c : this.components) {
/* 110 */       S s = (S)c.getSPI(spiType);
/* 111 */       if (s != null)
/* 112 */         return s; 
/*     */     } 
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   public Set<Component> getComponents() {
/* 118 */     return this.components;
/*     */   }
/*     */   @NotNull
/*     */   public <E> Iterable<E> getIterableSPI(Class<E> spiType) {
/* 122 */     E item = getSPI(spiType);
/* 123 */     if (item != null) {
/* 124 */       Collection<E> c = Collections.singletonList(item);
/* 125 */       return c;
/*     */     } 
/* 127 */     return Collections.emptySet();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\Container.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */