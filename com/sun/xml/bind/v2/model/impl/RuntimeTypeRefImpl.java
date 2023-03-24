/*    */ package com.sun.xml.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.bind.v2.model.core.NonElement;
/*    */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElement;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElementRef;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeRef;
/*    */ import com.sun.xml.bind.v2.runtime.Transducer;
/*    */ import java.lang.reflect.Type;
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
/*    */ final class RuntimeTypeRefImpl
/*    */   extends TypeRefImpl<Type, Class>
/*    */   implements RuntimeTypeRef
/*    */ {
/*    */   public RuntimeTypeRefImpl(RuntimeElementPropertyInfoImpl elementPropertyInfo, QName elementName, Type type, boolean isNillable, String defaultValue) {
/* 58 */     super(elementPropertyInfo, elementName, type, isNillable, defaultValue);
/*    */   }
/*    */   
/*    */   public RuntimeNonElement getTarget() {
/* 62 */     return (RuntimeNonElement)super.getTarget();
/*    */   }
/*    */   
/*    */   public Transducer getTransducer() {
/* 66 */     return RuntimeModelBuilder.createTransducer((RuntimeNonElementRef)this);
/*    */   }
/*    */   
/*    */   public RuntimePropertyInfo getSource() {
/* 70 */     return (RuntimePropertyInfo)this.owner;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeTypeRefImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */