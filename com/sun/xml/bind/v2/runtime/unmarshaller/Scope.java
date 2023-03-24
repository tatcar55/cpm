/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Lister;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Scope<BeanT, PropT, ItemT, PackT>
/*     */ {
/*     */   public final UnmarshallingContext context;
/*     */   private BeanT bean;
/*     */   private Accessor<BeanT, PropT> acc;
/*     */   private PackT pack;
/*     */   private Lister<BeanT, PropT, ItemT, PackT> lister;
/*     */   
/*     */   Scope(UnmarshallingContext context) {
/*  67 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasStarted() {
/*  74 */     return (this.bean != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  81 */     if (this.bean == null) {
/*     */       
/*  83 */       assert clean();
/*     */       
/*     */       return;
/*     */     } 
/*  87 */     this.bean = null;
/*  88 */     this.acc = null;
/*  89 */     this.pack = null;
/*  90 */     this.lister = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish() throws AccessorException {
/*  98 */     if (hasStarted()) {
/*  99 */       this.lister.endPacking(this.pack, this.bean, this.acc);
/* 100 */       reset();
/*     */     } 
/* 102 */     assert clean();
/*     */   }
/*     */   
/*     */   private boolean clean() {
/* 106 */     return (this.bean == null && this.acc == null && this.pack == null && this.lister == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Accessor<BeanT, PropT> acc, Lister<BeanT, PropT, ItemT, PackT> lister, ItemT value) throws SAXException {
/*     */     try {
/* 114 */       if (!hasStarted()) {
/* 115 */         this.bean = (BeanT)(this.context.getCurrentState()).target;
/* 116 */         this.acc = acc;
/* 117 */         this.lister = lister;
/* 118 */         this.pack = (PackT)lister.startPacking(this.bean, acc);
/*     */       } 
/*     */       
/* 121 */       lister.addToPack(this.pack, value);
/* 122 */     } catch (AccessorException e) {
/* 123 */       Loader.handleGenericException((Exception)e, true);
/*     */       
/* 125 */       this.lister = Lister.getErrorInstance();
/* 126 */       this.acc = Accessor.getErrorInstance();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(Accessor<BeanT, PropT> acc, Lister<BeanT, PropT, ItemT, PackT> lister) throws SAXException {
/*     */     try {
/* 138 */       if (!hasStarted()) {
/* 139 */         this.bean = (BeanT)(this.context.getCurrentState()).target;
/* 140 */         this.acc = acc;
/* 141 */         this.lister = lister;
/* 142 */         this.pack = (PackT)lister.startPacking(this.bean, acc);
/*     */       } 
/* 144 */     } catch (AccessorException e) {
/* 145 */       Loader.handleGenericException((Exception)e, true);
/*     */       
/* 147 */       this.lister = Lister.getErrorInstance();
/* 148 */       this.acc = Accessor.getErrorInstance();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\Scope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */