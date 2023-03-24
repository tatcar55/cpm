/*     */ package com.sun.xml.ws.client.sei;
/*     */ 
/*     */ import com.sun.xml.ws.model.ParameterImpl;
/*     */ import com.sun.xml.ws.spi.db.PropertyAccessor;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Holder;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ValueSetter
/*     */ {
/*     */   private ValueSetter() {}
/*     */   
/*  89 */   private static final ValueSetter RETURN_VALUE = new ReturnValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   private static final ValueSetter[] POOL = new ValueSetter[16];
/*     */   
/*     */   static {
/*  97 */     for (int i = 0; i < POOL.length; i++) {
/*  98 */       POOL[i] = new Param(i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static ValueSetter getSync(ParameterImpl p) {
/* 105 */     int idx = p.getIndex();
/*     */     
/* 107 */     if (idx == -1)
/* 108 */       return RETURN_VALUE; 
/* 109 */     if (idx < POOL.length) {
/* 110 */       return POOL[idx];
/*     */     }
/* 112 */     return new Param(idx);
/*     */   }
/*     */   
/*     */   private static final class ReturnValue
/*     */     extends ValueSetter {
/*     */     Object put(Object obj, Object[] args) {
/* 118 */       return obj;
/*     */     }
/*     */     
/*     */     private ReturnValue() {}
/*     */   }
/*     */   
/*     */   static final class Param
/*     */     extends ValueSetter {
/*     */     private final int idx;
/*     */     
/*     */     public Param(int idx) {
/* 129 */       this.idx = idx;
/*     */     }
/*     */     
/*     */     Object put(Object obj, Object[] args) {
/* 133 */       Object arg = args[this.idx];
/* 134 */       if (arg != null) {
/*     */         
/* 136 */         assert arg instanceof Holder;
/* 137 */         ((Holder)arg).value = (T)obj;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   static final ValueSetter SINGLE_VALUE = new SingleValue();
/*     */   
/*     */   abstract Object put(Object paramObject, Object[] paramArrayOfObject);
/*     */   
/*     */   private static final class SingleValue
/*     */     extends ValueSetter
/*     */   {
/*     */     private SingleValue() {}
/*     */     
/*     */     Object put(Object obj, Object[] args) {
/* 162 */       args[0] = obj;
/* 163 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class AsyncBeanValueSetter
/*     */     extends ValueSetter
/*     */   {
/*     */     private final PropertyAccessor accessor;
/*     */ 
/*     */     
/*     */     AsyncBeanValueSetter(ParameterImpl p, Class wrapper) {
/* 175 */       QName name = p.getName();
/*     */       try {
/* 177 */         this.accessor = p.getOwner().getBindingContext().getElementPropertyAccessor(wrapper, name.getNamespaceURI(), name.getLocalPart());
/*     */       }
/* 179 */       catch (JAXBException e) {
/* 180 */         throw new WebServiceException(wrapper + " do not have a property of the name " + name, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object put(Object obj, Object[] args) {
/* 193 */       assert args != null;
/* 194 */       assert args.length == 1;
/* 195 */       assert args[0] != null;
/*     */       
/* 197 */       Object bean = args[0];
/*     */       try {
/* 199 */         this.accessor.set(bean, obj);
/* 200 */       } catch (Exception e) {
/* 201 */         throw new WebServiceException(e);
/*     */       } 
/* 203 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\ValueSetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */