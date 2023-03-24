/*    */ package com.sun.xml.rpc.processor.model.literal;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LiteralContentMember
/*    */ {
/*    */   private LiteralType _type;
/*    */   private JavaStructureMember _javaStructureMember;
/*    */   
/*    */   public LiteralContentMember() {}
/*    */   
/*    */   public LiteralContentMember(LiteralType type) {
/* 40 */     this(type, null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LiteralContentMember(LiteralType type, JavaStructureMember javaStructureMember) {
/* 46 */     this._type = type;
/* 47 */     this._javaStructureMember = javaStructureMember;
/*    */   }
/*    */   
/*    */   public LiteralType getType() {
/* 51 */     return this._type;
/*    */   }
/*    */   
/*    */   public void setType(LiteralType t) {
/* 55 */     this._type = t;
/*    */   }
/*    */   
/*    */   public JavaStructureMember getJavaStructureMember() {
/* 59 */     return this._javaStructureMember;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setJavaStructureMember(JavaStructureMember javaStructureMember) {
/* 65 */     this._javaStructureMember = javaStructureMember;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralContentMember.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */