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
/*    */ public class FieldLocatable<F>
/*    */   implements Locatable
/*    */ {
/*    */   private final Locatable upstream;
/*    */   private final F field;
/*    */   private final Navigator<?, ?, F, ?> nav;
/*    */   
/*    */   public FieldLocatable(Locatable upstream, F field, Navigator<?, ?, F, ?> nav) {
/* 57 */     this.upstream = upstream;
/* 58 */     this.field = field;
/* 59 */     this.nav = nav;
/*    */   }
/*    */   
/*    */   public Locatable getUpstream() {
/* 63 */     return this.upstream;
/*    */   }
/*    */   
/*    */   public Location getLocation() {
/* 67 */     return this.nav.getFieldLocation(this.field);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\FieldLocatable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */