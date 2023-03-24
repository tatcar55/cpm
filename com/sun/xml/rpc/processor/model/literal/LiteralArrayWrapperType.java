/*     */ package com.sun.xml.rpc.processor.model.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaArrayType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LiteralArrayWrapperType
/*     */   extends LiteralSequenceType
/*     */ {
/*     */   public LiteralArrayWrapperType() {}
/*     */   
/*     */   public LiteralArrayWrapperType(QName name) {
/*  47 */     super(name, (JavaStructureType)null);
/*     */   }
/*     */   
/*     */   public LiteralArrayWrapperType(QName name, JavaStructureType javaType) {
/*  51 */     super(name, javaType);
/*     */   }
/*     */   
/*     */   public void add(LiteralElementMember m) {
/*  55 */     super.add(m);
/*  56 */     if (this.elementMember != null) {
/*  57 */       throw new ModelException("model.arraywrapper.member.already.set");
/*     */     }
/*  59 */     this.elementMember = m;
/*     */   }
/*     */   
/*     */   public void setElementMembersList(List<LiteralElementMember> l) {
/*  63 */     if (l.size() > 1) {
/*  64 */       throw new ModelException("model.arraywrapper.only.one.member");
/*     */     }
/*  66 */     super.setElementMembersList(l);
/*  67 */     this.elementMember = l.get(0);
/*     */   }
/*     */   
/*     */   public void addSubtype(LiteralStructuredType type) {
/*  71 */     throw new ModelException("model.arraywrapper.no.subtypes");
/*     */   }
/*     */   
/*     */   public void setSubtypesSet(Set s) {
/*  75 */     if (s != null && s.size() > 0) {
/*  76 */       throw new ModelException("model.arraywrapper.no.subtypes");
/*     */     }
/*  78 */     super.setSubtypesSet(s);
/*     */   }
/*     */   
/*     */   public void setParentType(LiteralStructuredType parent) {
/*  82 */     if (parent != null) {
/*  83 */       throw new ModelException("model.arraywrapper.no.parent");
/*     */     }
/*  85 */     super.setParentType(parent);
/*     */   }
/*     */   
/*     */   public void setContentMember(LiteralContentMember t) {
/*  89 */     if (t != null) {
/*  90 */       throw new ModelException("model.arraywrapper.no.content.member");
/*     */     }
/*  92 */     super.setContentMember(t);
/*     */   }
/*     */   
/*     */   public LiteralElementMember getElementMember() {
/*  96 */     return this.elementMember;
/*     */   }
/*     */   
/*     */   public JavaArrayType getJavaArrayType() {
/* 100 */     return this.javaArrayType;
/*     */   }
/*     */   
/*     */   public void setJavaArrayType(JavaArrayType javaArrayType) {
/* 104 */     this.javaArrayType = javaArrayType;
/*     */   }
/*     */   
/*     */   public void accept(LiteralTypeVisitor visitor) throws Exception {
/* 108 */     visitor.visit(this);
/*     */   }
/*     */   
/* 111 */   private LiteralElementMember elementMember = null;
/* 112 */   private JavaArrayType javaArrayType = null;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralArrayWrapperType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */