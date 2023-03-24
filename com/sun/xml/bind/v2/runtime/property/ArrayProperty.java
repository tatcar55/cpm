/*    */ package com.sun.xml.bind.v2.runtime.property;
/*    */ 
/*    */ import com.sun.xml.bind.api.AccessorException;
/*    */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*    */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*    */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*    */ import com.sun.xml.bind.v2.runtime.reflect.Lister;
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
/*    */ 
/*    */ abstract class ArrayProperty<BeanT, ListT, ItemT>
/*    */   extends PropertyImpl<BeanT>
/*    */ {
/*    */   protected final Accessor<BeanT, ListT> acc;
/*    */   protected final Lister<BeanT, ListT, ItemT, Object> lister;
/*    */   
/*    */   protected ArrayProperty(JAXBContextImpl context, RuntimePropertyInfo prop) {
/* 63 */     super(context, prop);
/*    */     
/* 65 */     assert prop.isCollection();
/* 66 */     this.lister = Lister.create(Navigator.REFLECTION.erasure(prop.getRawType()), prop.id(), prop.getAdapter());
/*    */     
/* 68 */     assert this.lister != null;
/* 69 */     this.acc = prop.getAccessor().optimize(context);
/* 70 */     assert this.acc != null;
/*    */   }
/*    */   
/*    */   public void reset(BeanT o) throws AccessorException {
/* 74 */     this.lister.reset(o, this.acc);
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getIdValue(BeanT bean) {
/* 79 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\ArrayProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */