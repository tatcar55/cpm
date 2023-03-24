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
/*    */ public class MethodLocatable<M>
/*    */   implements Locatable
/*    */ {
/*    */   private final Locatable upstream;
/*    */   private final M method;
/*    */   private final Navigator<?, ?, ?, M> nav;
/*    */   
/*    */   public MethodLocatable(Locatable upstream, M method, Navigator<?, ?, ?, M> nav) {
/* 57 */     this.upstream = upstream;
/* 58 */     this.method = method;
/* 59 */     this.nav = nav;
/*    */   }
/*    */   
/*    */   public Locatable getUpstream() {
/* 63 */     return this.upstream;
/*    */   }
/*    */   
/*    */   public Location getLocation() {
/* 67 */     return this.nav.getMethodLocation(this.method);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\MethodLocatable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */