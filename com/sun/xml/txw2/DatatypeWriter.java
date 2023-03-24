/*     */ package com.sun.xml.txw2;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface DatatypeWriter<DT>
/*     */ {
/*  74 */   public static final List<DatatypeWriter<?>> BUILTIN = Collections.unmodifiableList(new AbstractList<DatatypeWriter<?>>()
/*     */       {
/*  76 */         private DatatypeWriter<?>[] BUILTIN_ARRAY = new DatatypeWriter[] { new DatatypeWriter<String>()
/*     */             {
/*     */               public Class<String> getType() {
/*  79 */                 return String.class;
/*     */               }
/*     */               public void print(String s, NamespaceResolver resolver, StringBuilder buf) {
/*  82 */                 buf.append(s);
/*     */               }
/*     */             }, new DatatypeWriter<Integer>()
/*     */             {
/*     */               public Class<Integer> getType() {
/*  87 */                 return Integer.class;
/*     */               }
/*     */               public void print(Integer i, NamespaceResolver resolver, StringBuilder buf) {
/*  90 */                 buf.append(i);
/*     */               }
/*     */             }, new DatatypeWriter<Float>()
/*     */             {
/*     */               public Class<Float> getType() {
/*  95 */                 return Float.class;
/*     */               }
/*     */               public void print(Float f, NamespaceResolver resolver, StringBuilder buf) {
/*  98 */                 buf.append(f);
/*     */               }
/*     */             }, new DatatypeWriter<Double>()
/*     */             {
/*     */               public Class<Double> getType() {
/* 103 */                 return Double.class;
/*     */               }
/*     */               public void print(Double d, NamespaceResolver resolver, StringBuilder buf) {
/* 106 */                 buf.append(d);
/*     */               }
/*     */             }, new DatatypeWriter<QName>()
/*     */             {
/*     */               public Class<QName> getType() {
/* 111 */                 return QName.class;
/*     */               }
/*     */               public void print(QName qn, NamespaceResolver resolver, StringBuilder buf) {
/* 114 */                 String p = resolver.getPrefix(qn.getNamespaceURI());
/* 115 */                 if (p.length() != 0)
/* 116 */                   buf.append(p).append(':'); 
/* 117 */                 buf.append(qn.getLocalPart());
/*     */               }
/*     */             } };
/*     */ 
/*     */         
/*     */         public DatatypeWriter<?> get(int n) {
/* 123 */           return this.BUILTIN_ARRAY[n];
/*     */         }
/*     */         
/*     */         public int size() {
/* 127 */           return this.BUILTIN_ARRAY.length;
/*     */         }
/*     */       });
/*     */   
/*     */   Class<DT> getType();
/*     */   
/*     */   void print(DT paramDT, NamespaceResolver paramNamespaceResolver, StringBuilder paramStringBuilder);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\DatatypeWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */