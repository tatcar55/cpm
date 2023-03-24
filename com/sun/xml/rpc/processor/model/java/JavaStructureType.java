/*     */ package com.sun.xml.rpc.processor.model.java;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class JavaStructureType
/*     */   extends JavaType
/*     */ {
/*     */   public JavaStructureType() {}
/*     */   
/*     */   public JavaStructureType(String name, boolean present, Object owner) {
/*  48 */     super(name, present, "null");
/*  49 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public void add(JavaStructureMember m) {
/*  53 */     if (this.membersByName.containsKey(m.getName())) {
/*  54 */       throw new ModelException("model.uniqueness.javastructuretype", new Object[] { m.getName(), getRealName() });
/*     */     }
/*     */     
/*  57 */     this.members.add(m);
/*  58 */     this.membersByName.put(m.getName(), m);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaStructureMember getMemberByName(String name) {
/*  63 */     if (this.membersByName.size() != this.members.size()) {
/*  64 */       initializeMembersByName();
/*     */     }
/*  66 */     return (JavaStructureMember)this.membersByName.get(name);
/*     */   }
/*     */   
/*     */   public Iterator getMembers() {
/*  70 */     return this.members.iterator();
/*     */   }
/*     */   
/*     */   public int getMembersCount() {
/*  74 */     return this.members.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List getMembersList() {
/*  79 */     return this.members;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMembersList(List l) {
/*  84 */     this.members = l;
/*     */   }
/*     */   
/*     */   private void initializeMembersByName() {
/*  88 */     this.membersByName = new HashMap<Object, Object>();
/*  89 */     if (this.members != null) {
/*  90 */       for (Iterator<JavaStructureMember> iter = this.members.iterator(); iter.hasNext(); ) {
/*  91 */         JavaStructureMember m = iter.next();
/*  92 */         if (m.getName() != null && this.membersByName.containsKey(m.getName()))
/*     */         {
/*     */           
/*  95 */           throw new ModelException("model.uniqueness");
/*     */         }
/*  97 */         this.membersByName.put(m.getName(), m);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isAbstract() {
/* 103 */     return this.isAbstract;
/*     */   }
/*     */   
/*     */   public void setAbstract(boolean isAbstract) {
/* 107 */     this.isAbstract = isAbstract;
/*     */   }
/*     */   
/*     */   public JavaStructureType getSuperclass() {
/* 111 */     return this.superclass;
/*     */   }
/*     */   
/*     */   public void setSuperclass(JavaStructureType superclassType) {
/* 115 */     this.superclass = superclassType;
/*     */   }
/*     */   
/*     */   public void addSubclass(JavaStructureType subclassType) {
/* 119 */     this.subclasses.add(subclassType);
/* 120 */     subclassType.setSuperclass(this);
/*     */   }
/*     */   
/*     */   public Iterator getSubclasses() {
/* 124 */     if (this.subclasses == null || this.subclasses.size() == 0) {
/* 125 */       return null;
/*     */     }
/* 127 */     return this.subclasses.iterator();
/*     */   }
/*     */   
/*     */   public Set getSubclassesSet() {
/* 131 */     return this.subclasses;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSubclassesSet(Set s) {
/* 136 */     this.subclasses = s;
/* 137 */     for (Iterator<JavaStructureType> iter = s.iterator(); iter.hasNext();) {
/* 138 */       ((JavaStructureType)iter.next()).setSuperclass(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator getAllSubclasses() {
/* 143 */     Set subs = getAllSubclassesSet();
/* 144 */     if (subs.size() == 0) {
/* 145 */       return null;
/*     */     }
/* 147 */     return subs.iterator();
/*     */   }
/*     */   
/*     */   public Set getAllSubclassesSet() {
/* 151 */     Set transitiveSet = new HashSet();
/* 152 */     Iterator<JavaStructureType> subs = this.subclasses.iterator();
/* 153 */     while (subs.hasNext()) {
/* 154 */       transitiveSet.addAll(((JavaStructureType)subs.next()).getAllSubclassesSet());
/*     */     }
/*     */     
/* 157 */     transitiveSet.addAll(this.subclasses);
/* 158 */     return transitiveSet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getOwner() {
/* 164 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwner(Object owner) {
/* 170 */     this.owner = owner;
/*     */   }
/*     */   
/* 173 */   private List members = new ArrayList();
/* 174 */   private Map membersByName = new HashMap<Object, Object>();
/*     */ 
/*     */   
/* 177 */   private Set subclasses = new HashSet();
/*     */   private JavaStructureType superclass;
/*     */   private Object owner;
/*     */   private boolean isAbstract = false;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\java\JavaStructureType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */