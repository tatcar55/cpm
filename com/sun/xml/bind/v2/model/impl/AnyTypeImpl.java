/*    */ package com.sun.xml.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*    */ import com.sun.xml.bind.v2.model.core.NonElement;
/*    */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*    */ import com.sun.xml.bind.v2.runtime.Location;
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
/*    */ 
/*    */ 
/*    */ class AnyTypeImpl<T, C>
/*    */   implements NonElement<T, C>
/*    */ {
/*    */   private final T type;
/*    */   private final Navigator<T, C, ?, ?> nav;
/*    */   
/*    */   public AnyTypeImpl(Navigator<T, C, ?, ?> nav) {
/* 63 */     this.type = (T)nav.ref(Object.class);
/* 64 */     this.nav = nav;
/*    */   }
/*    */   
/*    */   public QName getTypeName() {
/* 68 */     return ANYTYPE_NAME;
/*    */   }
/*    */   
/*    */   public T getType() {
/* 72 */     return this.type;
/*    */   }
/*    */   
/*    */   public Locatable getUpstream() {
/* 76 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isSimpleType() {
/* 80 */     return false;
/*    */   }
/*    */   
/*    */   public Location getLocation() {
/* 84 */     return this.nav.getClassLocation(this.nav.asDecl(Object.class));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean canBeReferencedByIDREF() {
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\AnyTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */