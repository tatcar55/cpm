/*     */ package org.glassfish.gmbal.typelib;
/*     */ 
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.glassfish.gmbal.generic.ObjectSet;
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
/*     */ public abstract class EvaluatedTypeBase
/*     */   implements EvaluatedType
/*     */ {
/*     */   public <R> R accept(Visitor<R> visitor) {
/*  56 */     return visitor.visitEvaluatedType(this);
/*     */   }
/*     */   
/*  59 */   private String rep = null;
/*     */   
/*     */   public static void handleModifier(StringBuilder sb, int modifiers) {
/*  62 */     if (Modifier.isPublic(modifiers)) {
/*  63 */       sb.append("public ");
/*  64 */     } else if (Modifier.isPrivate(modifiers)) {
/*  65 */       sb.append("private ");
/*  66 */     } else if (Modifier.isProtected(modifiers)) {
/*  67 */       sb.append("protected ");
/*  68 */     } else if (Modifier.isAbstract(modifiers)) {
/*  69 */       sb.append("abstract ");
/*  70 */     } else if (Modifier.isNative(modifiers)) {
/*  71 */       sb.append("native ");
/*  72 */     } else if (Modifier.isStatic(modifiers)) {
/*  73 */       sb.append("static ");
/*  74 */     } else if (Modifier.isStrict(modifiers)) {
/*  75 */       sb.append("strictfp ");
/*  76 */     } else if (Modifier.isSynchronized(modifiers)) {
/*  77 */       sb.append("synchronized ");
/*  78 */     } else if (Modifier.isTransient(modifiers)) {
/*  79 */       sb.append("transient ");
/*  80 */     } else if (Modifier.isVolatile(modifiers)) {
/*  81 */       sb.append("volatile ");
/*  82 */     } else if (Modifier.isFinal(modifiers)) {
/*  83 */       sb.append("Final ");
/*     */     } 
/*     */   }
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
/*     */   <S, T extends S> List<T> castList(List<S> list, Class<T> cls) {
/*  99 */     List<T> result = new ArrayList<T>();
/* 100 */     for (S s : list) {
/* 101 */       result.add(cls.cast(s));
/*     */     }
/* 103 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends EvaluatedTypeBase> void handleList(StringBuilder sb, String start, List<T> list, String sep, String end, ObjectSet set) {
/* 110 */     if (list.size() > 0) {
/* 111 */       if (start != null) {
/* 112 */         sb.append(start);
/*     */       }
/*     */       
/* 115 */       boolean first = true;
/* 116 */       for (EvaluatedTypeBase evaluatedTypeBase : list) {
/* 117 */         if (first) {
/* 118 */           first = false;
/*     */         } else {
/* 120 */           sb.append(sep);
/*     */         } 
/* 122 */         evaluatedTypeBase.makeRepresentation(sb, set);
/*     */       } 
/*     */       
/* 125 */       if (end != null) {
/* 126 */         sb.append(end);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 133 */     if (this.rep == null) {
/* 134 */       ObjectSet set = new ObjectSet();
/* 135 */       StringBuilder sb = new StringBuilder();
/* 136 */       makeRepresentation(sb, set);
/* 137 */       this.rep = sb.toString();
/*     */     } 
/*     */     
/* 140 */     return this.rep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void makeRepresentation(StringBuilder paramStringBuilder, ObjectSet paramObjectSet);
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 151 */     ObjectSet set = new ObjectSet();
/* 152 */     return equals(obj, set);
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj, ObjectSet set) {
/* 156 */     if (this == obj) {
/* 157 */       return true;
/*     */     }
/*     */     
/* 160 */     if (getClass().isAssignableFrom(obj.getClass())) {
/* 161 */       if (set.contains(obj)) {
/* 162 */         return true;
/*     */       }
/* 164 */       set.add(obj);
/* 165 */       return myEquals(obj, set);
/*     */     } 
/*     */     
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean equalList(List<EvaluatedType> list1, List<EvaluatedType> list2, ObjectSet set) {
/* 174 */     if (list1 == null) {
/* 175 */       return (list2 == null);
/*     */     }
/* 177 */     Iterator<EvaluatedType> iter1 = list1.iterator();
/* 178 */     Iterator<EvaluatedType> iter2 = list2.iterator();
/* 179 */     while (iter1.hasNext() && iter2.hasNext()) {
/* 180 */       EvaluatedTypeBase obj1 = (EvaluatedTypeBase)iter1.next();
/* 181 */       EvaluatedTypeBase obj2 = (EvaluatedTypeBase)iter2.next();
/* 182 */       if (!set.contains(obj1) && 
/* 183 */         !obj1.equals(obj2, set)) {
/* 184 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 189 */     if (iter1.hasNext() != iter2.hasNext()) {
/* 190 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   abstract boolean myEquals(Object paramObject, ObjectSet paramObjectSet);
/*     */   
/*     */   public int hashCode() {
/* 201 */     ObjectSet set = new ObjectSet();
/* 202 */     return hashCode(set);
/*     */   }
/*     */   
/*     */   abstract int hashCode(ObjectSet paramObjectSet);
/*     */   
/*     */   public boolean isImmutable() {
/* 208 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedTypeBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */