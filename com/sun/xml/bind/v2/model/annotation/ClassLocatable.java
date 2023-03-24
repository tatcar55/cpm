/*    */ package com.sun.xml.bind.v2.model.annotation;
/*    */ 
/*    */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*    */ import com.sun.xml.bind.v2.runtime.Location;
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
/*    */ public class ClassLocatable<C>
/*    */   implements Locatable
/*    */ {
/*    */   private final Locatable upstream;
/*    */   private final C clazz;
/*    */   private final Navigator<?, C, ?, ?> nav;
/*    */   
/*    */   public ClassLocatable(Locatable upstream, C clazz, Navigator<?, C, ?, ?> nav) {
/* 57 */     this.upstream = upstream;
/* 58 */     this.clazz = clazz;
/* 59 */     this.nav = nav;
/*    */   }
/*    */   
/*    */   public Locatable getUpstream() {
/* 63 */     return this.upstream;
/*    */   }
/*    */   
/*    */   public Location getLocation() {
/* 67 */     return this.nav.getClassLocation(this.clazz);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\ClassLocatable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */