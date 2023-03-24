/*     */ package org.glassfish.ha.store.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.lang.reflect.Array;
/*     */ import org.jvnet.hk2.annotations.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class ObjectInputStreamWithLoader
/*     */   extends ObjectInputStream
/*     */ {
/*     */   protected ClassLoader loader;
/*     */   
/*     */   public ObjectInputStreamWithLoader(InputStream in, ClassLoader loader) throws IOException, StreamCorruptedException {
/*  67 */     super(in);
/*  68 */     if (loader == null) {
/*  69 */       throw new IllegalArgumentException("Illegal null argument to ObjectInputStreamWithLoader");
/*     */     }
/*  71 */     this.loader = loader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class primitiveType(char type) {
/*  79 */     switch (type) {
/*     */       case 'B':
/*  81 */         return byte.class;
/*     */       case 'C':
/*  83 */         return char.class;
/*     */       case 'D':
/*  85 */         return double.class;
/*     */       case 'F':
/*  87 */         return float.class;
/*     */       case 'I':
/*  89 */         return int.class;
/*     */       case 'J':
/*  91 */         return long.class;
/*     */       case 'S':
/*  93 */         return short.class;
/*     */       case 'Z':
/*  95 */         return boolean.class;
/*     */     } 
/*  97 */     return null;
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
/*     */   protected Class resolveClass(ObjectStreamClass classDesc) throws IOException, ClassNotFoundException {
/*     */     try {
/* 110 */       String cname = classDesc.getName();
/* 111 */       if (cname.startsWith("[")) {
/*     */         Class<?> component;
/*     */         
/*     */         int dcount;
/* 115 */         for (dcount = 1; cname.charAt(dcount) == '['; dcount++);
/* 116 */         if (cname.charAt(dcount) == 'L') {
/* 117 */           component = this.loader.loadClass(cname.substring(dcount + 1, cname.length() - 1));
/*     */         } else {
/*     */           
/* 120 */           if (cname.length() != dcount + 1) {
/* 121 */             throw new ClassNotFoundException(cname);
/*     */           }
/* 123 */           component = primitiveType(cname.charAt(dcount));
/*     */         } 
/* 125 */         int[] dim = new int[dcount];
/* 126 */         for (int i = 0; i < dcount; i++) {
/* 127 */           dim[i] = 0;
/*     */         }
/* 129 */         return Array.newInstance(component, dim).getClass();
/*     */       } 
/* 131 */       return this.loader.loadClass(cname);
/*     */     }
/* 133 */     catch (ClassNotFoundException e) {
/*     */       
/* 135 */       return super.resolveClass(classDesc);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\spi\ObjectInputStreamWithLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */