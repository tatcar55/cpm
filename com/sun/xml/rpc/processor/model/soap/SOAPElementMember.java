/*     */ package com.sun.xml.rpc.processor.model.soap;
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
/*     */ public class SOAPElementMember
/*     */ {
/*     */   private QName _name;
/*     */   private SOAPType _type;
/*     */   private JavaStructureMember _javaStructureMember;
/*     */   private boolean _nillable;
/*     */   private boolean _required;
/*     */   private boolean _repeated;
/*     */   
/*     */   public SOAPElementMember() {}
/*     */   
/*     */   public SOAPElementMember(QName name, SOAPType type) {
/*  41 */     this(name, type, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElementMember(QName name, SOAPType type, JavaStructureMember javaStructureMember) {
/*  47 */     this._name = name;
/*  48 */     this._type = type;
/*  49 */     this._javaStructureMember = javaStructureMember;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  53 */     return this._name;
/*     */   }
/*     */   
/*     */   public void setName(QName n) {
/*  57 */     this._name = n;
/*     */   }
/*     */   
/*     */   public SOAPType getType() {
/*  61 */     return this._type;
/*     */   }
/*     */   
/*     */   public void setType(SOAPType t) {
/*  65 */     this._type = t;
/*     */   }
/*     */   
/*     */   public boolean isNillable() {
/*  69 */     return this._nillable;
/*     */   }
/*     */   
/*     */   public void setNillable(boolean b) {
/*  73 */     this._nillable = b;
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/*  77 */     return this._required;
/*     */   }
/*     */   
/*     */   public void setRequired(boolean b) {
/*  81 */     this._required = b;
/*     */   }
/*     */   
/*     */   public boolean isRepeated() {
/*  85 */     return this._repeated;
/*     */   }
/*     */   
/*     */   public void setRepeated(boolean b) {
/*  89 */     this._repeated = b;
/*     */   }
/*     */   
/*     */   public JavaStructureMember getJavaStructureMember() {
/*  93 */     return this._javaStructureMember;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJavaStructureMember(JavaStructureMember javaStructureMember) {
/*  99 */     this._javaStructureMember = javaStructureMember;
/*     */   }
/*     */   
/*     */   public boolean isWildcard() {
/* 103 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isInherited() {
/* 107 */     return this.isInherited;
/*     */   }
/*     */   
/*     */   public void setInherited(boolean b) {
/* 111 */     this.isInherited = b;
/*     */   }
/*     */   
/*     */   private boolean isInherited = false;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPElementMember.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */