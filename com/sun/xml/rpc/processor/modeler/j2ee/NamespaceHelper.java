/*     */ package com.sun.xml.rpc.processor.modeler.j2ee;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.ComplexType;
/*     */ import java.util.HashMap;
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
/*     */ public class NamespaceHelper
/*     */ {
/*     */   private HashMap map;
/*     */   private NamespaceHelper prev;
/*     */   
/*     */   public NamespaceHelper() {
/*  59 */     this.map = null;
/*  60 */     this.prev = null;
/*     */   }
/*     */   
/*     */   private NamespaceHelper(NamespaceHelper prev, ComplexType ct) {
/*  64 */     this.map = new HashMap<Object, Object>();
/*  65 */     this.prev = prev;
/*  66 */     String[] attrNames = ct.getAttributeNames();
/*  67 */     for (int i = 0; i < attrNames.length; i++) {
/*  68 */       String attrName = attrNames[i];
/*  69 */       if (attrName.equals("xmlns") || attrName.startsWith("xmlns:")) {
/*  70 */         this.map.put(attrName, ct.getAttributeValue(attrName));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceHelper push(ComplexType ct) {
/*  82 */     return new NamespaceHelper(this, ct);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceHelper pop() {
/*  93 */     return this.prev;
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
/*     */   public QName getQName(String nsString) {
/*     */     String attrName, local;
/* 109 */     int idx = nsString.indexOf(':');
/* 110 */     if (idx < nsString.length() - 1) {
/*     */       
/* 112 */       String prefix = nsString.substring(0, idx);
/* 113 */       attrName = "xmlns:" + prefix;
/* 114 */       local = nsString.substring(idx + 1);
/*     */     } else {
/* 116 */       attrName = "xmlns";
/* 117 */       local = nsString;
/*     */     } 
/* 119 */     return getQNameInternal(attrName, local);
/*     */   }
/*     */   
/*     */   private QName getQNameInternal(String attrName, String local) {
/* 123 */     if (this.map == null)
/* 124 */       return null; 
/* 125 */     String namespace = (String)this.map.get(attrName);
/* 126 */     if (namespace != null)
/* 127 */       return new QName(namespace, local); 
/* 128 */     if (this.prev != null) {
/* 129 */       return this.prev.getQNameInternal(attrName, local);
/*     */     }
/* 131 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\NamespaceHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */