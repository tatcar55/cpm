/*     */ package com.sun.xml.rpc.processor.model.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LiteralElementMember
/*     */ {
/*     */   private QName _name;
/*     */   private LiteralType _type;
/*     */   private JavaStructureMember _javaStructureMember;
/*     */   private boolean _nillable;
/*     */   private boolean _required;
/*     */   private boolean _repeated;
/*     */   
/*     */   public LiteralElementMember() {}
/*     */   
/*     */   public LiteralElementMember(QName name, LiteralType type) {
/*  42 */     this(name, type, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralElementMember(QName name, LiteralType type, JavaStructureMember javaStructureMember) {
/*  48 */     this._name = name;
/*  49 */     this._type = type;
/*  50 */     this._javaStructureMember = javaStructureMember;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  54 */     return this._name;
/*     */   }
/*     */   
/*     */   public void setName(QName n) {
/*  58 */     this._name = n;
/*     */   }
/*     */   
/*     */   public LiteralType getType() {
/*  62 */     return this._type;
/*     */   }
/*     */   
/*     */   public void setType(LiteralType t) {
/*  66 */     this._type = t;
/*     */   }
/*     */   
/*     */   public boolean isNillable() {
/*  70 */     return this._nillable;
/*     */   }
/*     */   
/*     */   public void setNillable(boolean b) {
/*  74 */     this._nillable = b;
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/*  78 */     return this._required;
/*     */   }
/*     */   
/*     */   public void setRequired(boolean b) {
/*  82 */     this._required = b;
/*     */   }
/*     */   
/*     */   public boolean isRepeated() {
/*  86 */     return this._repeated;
/*     */   }
/*     */   
/*     */   public void setRepeated(boolean b) {
/*  90 */     this._repeated = b;
/*     */   }
/*     */   
/*     */   public JavaStructureMember getJavaStructureMember() {
/*  94 */     return this._javaStructureMember;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJavaStructureMember(JavaStructureMember javaStructureMember) {
/* 100 */     this._javaStructureMember = javaStructureMember;
/*     */   }
/*     */   
/*     */   public boolean isWildcard() {
/* 104 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isInherited() {
/* 108 */     return this.isInherited;
/*     */   }
/*     */   
/*     */   public void setInherited(boolean b) {
/* 112 */     this.isInherited = b;
/*     */   }
/*     */   
/*     */   private boolean isInherited = false;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralElementMember.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */