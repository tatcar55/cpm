/*     */ package com.sun.xml.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.ClassFactory;
/*     */ import com.sun.xml.bind.v2.runtime.Coordinator;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class AdaptedAccessor<BeanT, InMemValueT, OnWireValueT>
/*     */   extends Accessor<BeanT, OnWireValueT>
/*     */ {
/*     */   private final Accessor<BeanT, InMemValueT> core;
/*     */   private final Class<? extends XmlAdapter<OnWireValueT, InMemValueT>> adapter;
/*     */   private XmlAdapter<OnWireValueT, InMemValueT> staticAdapter;
/*     */   
/*     */   AdaptedAccessor(Class<OnWireValueT> targetType, Accessor<BeanT, InMemValueT> extThis, Class<? extends XmlAdapter<OnWireValueT, InMemValueT>> adapter) {
/*  61 */     super(targetType);
/*  62 */     this.core = extThis;
/*  63 */     this.adapter = adapter;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAdapted() {
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   public OnWireValueT get(BeanT bean) throws AccessorException {
/*  72 */     InMemValueT v = this.core.get(bean);
/*     */     
/*  74 */     XmlAdapter<OnWireValueT, InMemValueT> a = getAdapter();
/*     */     try {
/*  76 */       return a.marshal(v);
/*  77 */     } catch (Exception e) {
/*  78 */       throw new AccessorException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void set(BeanT bean, OnWireValueT o) throws AccessorException {
/*  83 */     XmlAdapter<OnWireValueT, InMemValueT> a = getAdapter();
/*     */     try {
/*  85 */       this.core.set(bean, (o == null) ? null : a.unmarshal(o));
/*  86 */     } catch (Exception e) {
/*  87 */       throw new AccessorException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getUnadapted(BeanT bean) throws AccessorException {
/*  92 */     return this.core.getUnadapted(bean);
/*     */   }
/*     */   
/*     */   public void setUnadapted(BeanT bean, Object value) throws AccessorException {
/*  96 */     this.core.setUnadapted(bean, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XmlAdapter<OnWireValueT, InMemValueT> getAdapter() {
/* 107 */     Coordinator coordinator = Coordinator._getInstance();
/* 108 */     if (coordinator != null) {
/* 109 */       return coordinator.getAdapter(this.adapter);
/*     */     }
/* 111 */     synchronized (this) {
/* 112 */       if (this.staticAdapter == null)
/* 113 */         this.staticAdapter = (XmlAdapter<OnWireValueT, InMemValueT>)ClassFactory.create(this.adapter); 
/*     */     } 
/* 115 */     return this.staticAdapter;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\AdaptedAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */