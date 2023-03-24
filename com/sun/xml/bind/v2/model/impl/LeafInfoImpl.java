/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.model.core.LeafInfo;
/*     */ import com.sun.xml.bind.v2.runtime.Location;
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
/*     */ abstract class LeafInfoImpl<TypeT, ClassDeclT>
/*     */   implements LeafInfo<TypeT, ClassDeclT>, Location
/*     */ {
/*     */   private final TypeT type;
/*     */   private final QName typeName;
/*     */   
/*     */   protected LeafInfoImpl(TypeT type, QName typeName) {
/*  60 */     assert type != null;
/*     */     
/*  62 */     this.type = type;
/*  63 */     this.typeName = typeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeT getType() {
/*  70 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean canBeReferencedByIDREF() {
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   public QName getTypeName() {
/*  84 */     return this.typeName;
/*     */   }
/*     */   
/*     */   public Locatable getUpstream() {
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location getLocation() {
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isSimpleType() {
/*  99 */     return true;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 103 */     return this.type.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\LeafInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */