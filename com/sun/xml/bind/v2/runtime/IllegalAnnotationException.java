/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ public class IllegalAnnotationException
/*     */   extends JAXBException
/*     */ {
/*     */   private final List<List<Location>> pos;
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public IllegalAnnotationException(String message, Locatable src) {
/*  69 */     super(message);
/*  70 */     this.pos = build(new Locatable[] { src });
/*     */   }
/*     */   
/*     */   public IllegalAnnotationException(String message, Annotation src) {
/*  74 */     this(message, cast(src));
/*     */   }
/*     */   
/*     */   public IllegalAnnotationException(String message, Locatable src1, Locatable src2) {
/*  78 */     super(message);
/*  79 */     this.pos = build(new Locatable[] { src1, src2 });
/*     */   }
/*     */   
/*     */   public IllegalAnnotationException(String message, Annotation src1, Annotation src2) {
/*  83 */     this(message, cast(src1), cast(src2));
/*     */   }
/*     */   
/*     */   public IllegalAnnotationException(String message, Annotation src1, Locatable src2) {
/*  87 */     this(message, cast(src1), src2);
/*     */   }
/*     */   
/*     */   public IllegalAnnotationException(String message, Throwable cause, Locatable src) {
/*  91 */     super(message, cause);
/*  92 */     this.pos = build(new Locatable[] { src });
/*     */   }
/*     */   
/*     */   private static Locatable cast(Annotation a) {
/*  96 */     if (a instanceof Locatable) {
/*  97 */       return (Locatable)a;
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   private List<List<Location>> build(Locatable... srcs) {
/* 103 */     List<List<Location>> r = new ArrayList<List<Location>>();
/* 104 */     for (Locatable l : srcs) {
/* 105 */       if (l != null) {
/* 106 */         List<Location> ll = convert(l);
/* 107 */         if (ll != null && !ll.isEmpty())
/* 108 */           r.add(ll); 
/*     */       } 
/*     */     } 
/* 111 */     return Collections.unmodifiableList(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Location> convert(Locatable src) {
/* 118 */     if (src == null) return null;
/*     */     
/* 120 */     List<Location> r = new ArrayList<Location>();
/* 121 */     for (; src != null; src = src.getUpstream())
/* 122 */       r.add(src.getLocation()); 
/* 123 */     return Collections.unmodifiableList(r);
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
/*     */   public List<List<Location>> getSourcePos() {
/* 179 */     return this.pos;
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
/*     */   public String toString() {
/* 192 */     StringBuilder sb = new StringBuilder(getMessage());
/*     */     
/* 194 */     for (List<Location> locs : this.pos) {
/* 195 */       sb.append("\n\tthis problem is related to the following location:");
/* 196 */       for (Location loc : locs) {
/* 197 */         sb.append("\n\t\tat ").append(loc.toString());
/*     */       }
/*     */     } 
/* 200 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\IllegalAnnotationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */