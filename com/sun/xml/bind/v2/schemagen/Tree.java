/*     */ package com.sun.xml.bind.v2.schemagen;
/*     */ 
/*     */ import com.sun.xml.bind.v2.schemagen.xmlschema.ContentModelContainer;
/*     */ import com.sun.xml.bind.v2.schemagen.xmlschema.Occurs;
/*     */ import com.sun.xml.bind.v2.schemagen.xmlschema.Particle;
/*     */ import com.sun.xml.bind.v2.schemagen.xmlschema.TypeDefParticle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ abstract class Tree
/*     */ {
/*     */   Tree makeOptional(boolean really) {
/*  71 */     return really ? new Optional(this) : this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Tree makeRepeated(boolean really) {
/*  82 */     return really ? new Repeated(this) : this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Tree makeGroup(GroupKind kind, List<Tree> children) {
/*  90 */     if (children.size() == 1) {
/*  91 */       return children.get(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     List<Tree> normalizedChildren = new ArrayList<Tree>(children.size());
/*  98 */     for (Tree t : children) {
/*  99 */       if (t instanceof Group) {
/* 100 */         Group g = (Group)t;
/* 101 */         if (g.kind == kind) {
/* 102 */           normalizedChildren.addAll(Arrays.asList(g.children));
/*     */           continue;
/*     */         } 
/*     */       } 
/* 106 */       normalizedChildren.add(t);
/*     */     } 
/*     */     
/* 109 */     return new Group(kind, normalizedChildren.<Tree>toArray(new Tree[normalizedChildren.size()]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract boolean isNullable();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean canBeTopLevel() {
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void write(ContentModelContainer paramContentModelContainer, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void write(TypeDefParticle ct) {
/* 137 */     if (canBeTopLevel()) {
/* 138 */       write((ContentModelContainer)ct._cast(ContentModelContainer.class), false, false);
/*     */     } else {
/*     */       
/* 141 */       (new Group(GroupKind.SEQUENCE, new Tree[] { this })).write(ct);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void writeOccurs(Occurs o, boolean isOptional, boolean repeated) {
/* 148 */     if (isOptional)
/* 149 */       o.minOccurs(0); 
/* 150 */     if (repeated) {
/* 151 */       o.maxOccurs("unbounded");
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class Term
/*     */     extends Tree
/*     */   {
/*     */     boolean isNullable() {
/* 159 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Optional
/*     */     extends Tree
/*     */   {
/*     */     private final Tree body;
/*     */     
/*     */     private Optional(Tree body) {
/* 170 */       this.body = body;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isNullable() {
/* 175 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     Tree makeOptional(boolean really) {
/* 180 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 185 */       this.body.write(parent, true, repeated);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Repeated
/*     */     extends Tree
/*     */   {
/*     */     private final Tree body;
/*     */     
/*     */     private Repeated(Tree body) {
/* 196 */       this.body = body;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isNullable() {
/* 201 */       return this.body.isNullable();
/*     */     }
/*     */ 
/*     */     
/*     */     Tree makeRepeated(boolean really) {
/* 206 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 211 */       this.body.write(parent, isOptional, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Group
/*     */     extends Tree
/*     */   {
/*     */     private final GroupKind kind;
/*     */     private final Tree[] children;
/*     */     
/*     */     private Group(GroupKind kind, Tree... children) {
/* 223 */       this.kind = kind;
/* 224 */       this.children = children;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean canBeTopLevel() {
/* 229 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isNullable() {
/* 234 */       if (this.kind == GroupKind.CHOICE) {
/* 235 */         for (Tree t : this.children) {
/* 236 */           if (t.isNullable())
/* 237 */             return true; 
/*     */         } 
/* 239 */         return false;
/*     */       } 
/* 241 */       for (Tree t : this.children) {
/* 242 */         if (!t.isNullable())
/* 243 */           return false; 
/*     */       } 
/* 245 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 251 */       Particle c = this.kind.write(parent);
/* 252 */       writeOccurs((Occurs)c, isOptional, repeated);
/*     */       
/* 254 */       for (Tree child : this.children)
/* 255 */         child.write((ContentModelContainer)c, false, false); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\Tree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */