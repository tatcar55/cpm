/*     */ package com.sun.xml.rpc.processor.model;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.util.NullIterator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public abstract class AbstractType
/*     */ {
/*     */   private QName name;
/*     */   private JavaType javaType;
/*     */   
/*     */   protected AbstractType() {}
/*     */   
/*     */   protected AbstractType(QName name) {
/*  47 */     this(name, null, null);
/*     */   }
/*     */   
/*     */   protected AbstractType(QName name, String version) {
/*  51 */     this(name, null, version);
/*     */   }
/*     */   
/*     */   protected AbstractType(QName name, JavaType javaType) {
/*  55 */     this(name, javaType, null);
/*     */   }
/*     */   
/*     */   protected AbstractType(QName name, JavaType javaType, String version) {
/*  59 */     this.name = name;
/*  60 */     this.javaType = javaType;
/*  61 */     this.version = version;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  65 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(QName name) {
/*  69 */     this.name = name;
/*     */   }
/*     */   
/*     */   public JavaType getJavaType() {
/*  73 */     return this.javaType;
/*     */   }
/*     */   
/*     */   public void setJavaType(JavaType javaType) {
/*  77 */     this.javaType = javaType;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/*  81 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(String version) {
/*  85 */     this.version = version;
/*     */   }
/*     */   
/*     */   public boolean isNillable() {
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isSOAPType() {
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isLiteralType() {
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   public Object getProperty(String key) {
/* 101 */     if (this.properties == null) {
/* 102 */       return null;
/*     */     }
/* 104 */     return this.properties.get(key);
/*     */   }
/*     */   
/*     */   public void setProperty(String key, Object value) {
/* 108 */     if (value == null) {
/* 109 */       removeProperty(key);
/*     */       
/*     */       return;
/*     */     } 
/* 113 */     if (this.properties == null) {
/* 114 */       this.properties = new HashMap<Object, Object>();
/*     */     }
/* 116 */     this.properties.put(key, value);
/*     */   }
/*     */   
/*     */   public void removeProperty(String key) {
/* 120 */     if (this.properties != null) {
/* 121 */       this.properties.remove(key);
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator getProperties() {
/* 126 */     if (this.properties == null) {
/* 127 */       return (Iterator)NullIterator.getInstance();
/*     */     }
/* 129 */     return this.properties.keySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map getPropertiesMap() {
/* 135 */     return this.properties;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPropertiesMap(Map m) {
/* 140 */     this.properties = m;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 145 */   private String version = null;
/*     */   private Map properties;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\AbstractType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */