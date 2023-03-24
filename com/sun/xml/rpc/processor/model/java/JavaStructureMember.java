/*     */ package com.sun.xml.rpc.processor.model.java;
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
/*     */ public class JavaStructureMember
/*     */ {
/*     */   private String name;
/*     */   private JavaType type;
/*     */   
/*     */   public JavaStructureMember() {}
/*     */   
/*     */   public JavaStructureMember(String name, JavaType type, Object owner) {
/*  38 */     this(name, type, owner, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaStructureMember(String name, JavaType type, Object owner, boolean isPublic) {
/*  43 */     this.name = name;
/*  44 */     this.type = type;
/*  45 */     this.owner = owner;
/*  46 */     this.isPublic = isPublic;
/*  47 */     this.constructorPos = -1;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  51 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String s) {
/*  55 */     this.name = s;
/*     */   }
/*     */   
/*     */   public JavaType getType() {
/*  59 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(JavaType t) {
/*  63 */     this.type = t;
/*     */   }
/*     */   
/*     */   public boolean isPublic() {
/*  67 */     return this.isPublic;
/*     */   }
/*     */   
/*     */   public void setPublic(boolean b) {
/*  71 */     this.isPublic = b;
/*     */   }
/*     */   
/*     */   public boolean isInherited() {
/*  75 */     return this.isInherited;
/*     */   }
/*     */   
/*     */   public void setInherited(boolean b) {
/*  79 */     this.isInherited = b;
/*     */   }
/*     */   
/*     */   public String getReadMethod() {
/*  83 */     return this.readMethod;
/*     */   }
/*     */   
/*     */   public void setReadMethod(String readMethod) {
/*  87 */     this.readMethod = readMethod;
/*     */   }
/*     */   
/*     */   public String getWriteMethod() {
/*  91 */     return this.writeMethod;
/*     */   }
/*     */   
/*     */   public void setWriteMethod(String writeMethod) {
/*  95 */     this.writeMethod = writeMethod;
/*     */   }
/*     */   
/*     */   public String getDeclaringClass() {
/*  99 */     return this.declaringClass;
/*     */   }
/*     */   public void setDeclaringClass(String declaringClass) {
/* 102 */     this.declaringClass = declaringClass;
/*     */   }
/*     */   
/*     */   public Object getOwner() {
/* 106 */     return this.owner;
/*     */   }
/*     */   
/*     */   public void setOwner(Object owner) {
/* 110 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public int getConstructorPos() {
/* 114 */     return this.constructorPos;
/*     */   }
/*     */   
/*     */   public void setConstructorPos(int idx) {
/* 118 */     this.constructorPos = idx;
/*     */   }
/*     */   
/*     */   private boolean isPublic = false;
/*     */   private boolean isInherited = false;
/*     */   private String readMethod;
/*     */   private String writeMethod;
/*     */   private String declaringClass;
/*     */   private Object owner;
/*     */   private int constructorPos;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\java\JavaStructureMember.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */