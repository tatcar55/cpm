/*    */ package com.sun.xml.bind.v2.schemagen;
/*    */ 
/*    */ import com.sun.xml.bind.v2.schemagen.xmlschema.ContentModelContainer;
/*    */ import com.sun.xml.bind.v2.schemagen.xmlschema.Particle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum GroupKind
/*    */ {
/* 52 */   ALL("all"), SEQUENCE("sequence"), CHOICE("choice");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   GroupKind(String name) {
/* 57 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Particle write(ContentModelContainer parent) {
/* 64 */     return (Particle)parent._element(this.name, Particle.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\GroupKind.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */