/*    */ package com.sun.xml.rpc.processor.model;
/*    */ 
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
/*    */ public class Block
/*    */   extends ModelObject
/*    */ {
/*    */   public static final int BODY = 1;
/*    */   public static final int HEADER = 2;
/*    */   public static final int ATTACHMENT = 3;
/*    */   private QName name;
/*    */   private AbstractType type;
/*    */   private int location;
/*    */   
/*    */   public Block() {}
/*    */   
/*    */   public Block(QName name) {
/* 44 */     this.name = name;
/*    */   }
/*    */   
/*    */   public Block(QName name, AbstractType type) {
/* 48 */     this.name = name;
/* 49 */     this.type = type;
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 53 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(QName n) {
/* 57 */     this.name = n;
/*    */   }
/*    */   
/*    */   public AbstractType getType() {
/* 61 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(AbstractType type) {
/* 65 */     this.type = type;
/*    */   }
/*    */   
/*    */   public int getLocation() {
/* 69 */     return this.location;
/*    */   }
/*    */   
/*    */   public void setLocation(int i) {
/* 73 */     this.location = i;
/*    */   }
/*    */   
/*    */   public void accept(ModelVisitor visitor) throws Exception {
/* 77 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\Block.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */