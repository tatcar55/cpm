/*    */ package com.sun.xml.rpc.processor.model.literal;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
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
/*    */ public class LiteralAttributeMember
/*    */ {
/*    */   private QName _name;
/*    */   private LiteralType _type;
/*    */   private JavaStructureMember _javaStructureMember;
/*    */   private boolean _required;
/*    */   
/*    */   public LiteralAttributeMember() {}
/*    */   
/*    */   public LiteralAttributeMember(QName name, LiteralType type) {
/* 42 */     this(name, type, null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LiteralAttributeMember(QName name, LiteralType type, JavaStructureMember javaStructureMember) {
/* 48 */     this._name = name;
/* 49 */     this._type = type;
/* 50 */     this._javaStructureMember = javaStructureMember;
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 54 */     return this._name;
/*    */   }
/*    */   
/*    */   public void setName(QName n) {
/* 58 */     this._name = n;
/*    */   }
/*    */   
/*    */   public LiteralType getType() {
/* 62 */     return this._type;
/*    */   }
/*    */   
/*    */   public void setType(LiteralType t) {
/* 66 */     this._type = t;
/*    */   }
/*    */   
/*    */   public JavaStructureMember getJavaStructureMember() {
/* 70 */     return this._javaStructureMember;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setJavaStructureMember(JavaStructureMember javaStructureMember) {
/* 76 */     this._javaStructureMember = javaStructureMember;
/*    */   }
/*    */   
/*    */   public boolean isRequired() {
/* 80 */     return this._required;
/*    */   }
/*    */   
/*    */   public void setRequired(boolean b) {
/* 84 */     this._required = b;
/*    */   }
/*    */   
/*    */   public boolean isInherited() {
/* 88 */     return this.isInherited;
/*    */   }
/*    */   
/*    */   public void setInherited(boolean b) {
/* 92 */     this.isInherited = b;
/*    */   }
/*    */   
/*    */   private boolean isInherited = false;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralAttributeMember.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */