/*    */ package com.sun.xml.ws.spi.db;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WrapperAccessor
/*    */ {
/*    */   protected Map<Object, PropertySetter> propertySetters;
/*    */   protected Map<Object, PropertyGetter> propertyGetters;
/*    */   protected boolean elementLocalNameCollision;
/*    */   
/*    */   protected PropertySetter getPropertySetter(QName name) {
/* 58 */     Object key = this.elementLocalNameCollision ? name : name.getLocalPart();
/* 59 */     return this.propertySetters.get(key);
/*    */   }
/*    */   protected PropertyGetter getPropertyGetter(QName name) {
/* 62 */     Object key = this.elementLocalNameCollision ? name : name.getLocalPart();
/* 63 */     return this.propertyGetters.get(key);
/*    */   }
/*    */   
/*    */   public PropertyAccessor getPropertyAccessor(String ns, String name) {
/* 67 */     QName n = new QName(ns, name);
/* 68 */     final PropertySetter setter = getPropertySetter(n);
/* 69 */     final PropertyGetter getter = getPropertyGetter(n);
/* 70 */     return new PropertyAccessor<Object, Object>() {
/*    */         public Object get(Object bean) throws DatabindingException {
/* 72 */           return getter.get(bean);
/*    */         }
/*    */         
/*    */         public void set(Object bean, Object value) throws DatabindingException {
/* 76 */           setter.set(bean, value);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\WrapperAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */