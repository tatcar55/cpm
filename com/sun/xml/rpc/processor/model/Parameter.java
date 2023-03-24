/*    */ package com.sun.xml.rpc.processor.model;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.java.JavaParameter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Parameter
/*    */   extends ModelObject
/*    */ {
/*    */   private String name;
/*    */   private JavaParameter javaParameter;
/*    */   private AbstractType type;
/*    */   private Block block;
/*    */   private Parameter link;
/*    */   private boolean embedded;
/*    */   
/*    */   public Parameter() {}
/*    */   
/*    */   public Parameter(String name) {
/* 40 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 44 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String s) {
/* 48 */     this.name = s;
/*    */   }
/*    */   
/*    */   public JavaParameter getJavaParameter() {
/* 52 */     return this.javaParameter;
/*    */   }
/*    */   
/*    */   public void setJavaParameter(JavaParameter p) {
/* 56 */     this.javaParameter = p;
/*    */   }
/*    */   
/*    */   public AbstractType getType() {
/* 60 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(AbstractType t) {
/* 64 */     this.type = t;
/*    */   }
/*    */   
/*    */   public Block getBlock() {
/* 68 */     return this.block;
/*    */   }
/*    */   
/*    */   public void setBlock(Block d) {
/* 72 */     this.block = d;
/*    */   }
/*    */   
/*    */   public Parameter getLinkedParameter() {
/* 76 */     return this.link;
/*    */   }
/*    */   
/*    */   public void setLinkedParameter(Parameter p) {
/* 80 */     this.link = p;
/*    */   }
/*    */   
/*    */   public boolean isEmbedded() {
/* 84 */     return this.embedded;
/*    */   }
/*    */   
/*    */   public void setEmbedded(boolean b) {
/* 88 */     this.embedded = b;
/*    */   }
/*    */   
/*    */   public void accept(ModelVisitor visitor) throws Exception {
/* 92 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\Parameter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */