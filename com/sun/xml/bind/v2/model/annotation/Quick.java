/*    */ package com.sun.xml.bind.v2.model.annotation;
/*    */ 
/*    */ import com.sun.xml.bind.v2.runtime.Location;
/*    */ import java.lang.annotation.Annotation;
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
/*    */ 
/*    */ public abstract class Quick
/*    */   implements Annotation, Locatable, Location
/*    */ {
/*    */   private final Locatable upstream;
/*    */   
/*    */   protected Quick(Locatable upstream) {
/* 60 */     this.upstream = upstream;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Annotation getAnnotation();
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Quick newInstance(Locatable paramLocatable, Annotation paramAnnotation);
/*    */ 
/*    */ 
/*    */   
/*    */   public final Location getLocation() {
/* 75 */     return this;
/*    */   }
/*    */   
/*    */   public final Locatable getUpstream() {
/* 79 */     return this.upstream;
/*    */   }
/*    */   
/*    */   public final String toString() {
/* 83 */     return getAnnotation().toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\Quick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */