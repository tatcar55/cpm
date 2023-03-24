/*     */ package com.sun.xml.ws.api;
/*     */ 
/*     */ import com.oracle.webservices.api.message.BasePropertySet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PropertySet
/*     */   extends BasePropertySet
/*     */ {
/*     */   protected static class PropertyMap
/*     */     extends BasePropertySet.PropertyMap {}
/*     */   
/*     */   protected static PropertyMap parse(Class clazz) {
/*  68 */     BasePropertySet.PropertyMap pm = BasePropertySet.parse(clazz);
/*  69 */     PropertyMap map = new PropertyMap();
/*  70 */     map.putAll((Map)pm);
/*  71 */     return map;
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
/*     */   public Object get(Object key) {
/*  83 */     BasePropertySet.Accessor sp = (BasePropertySet.Accessor)getPropertyMap().get(key);
/*  84 */     if (sp != null)
/*  85 */       return sp.get((com.oracle.webservices.api.message.PropertySet)this); 
/*  86 */     throw new IllegalArgumentException("Undefined property " + key);
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
/*     */   public Object put(String key, Object value) {
/* 103 */     BasePropertySet.Accessor sp = (BasePropertySet.Accessor)getPropertyMap().get(key);
/* 104 */     if (sp != null) {
/* 105 */       Object old = sp.get((com.oracle.webservices.api.message.PropertySet)this);
/* 106 */       sp.set((com.oracle.webservices.api.message.PropertySet)this, value);
/* 107 */       return old;
/*     */     } 
/* 109 */     throw new IllegalArgumentException("Undefined property " + key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supports(Object key) {
/* 114 */     return getPropertyMap().containsKey(key);
/*     */   }
/*     */   
/*     */   public Object remove(Object key) {
/* 118 */     BasePropertySet.Accessor sp = (BasePropertySet.Accessor)getPropertyMap().get(key);
/* 119 */     if (sp != null) {
/* 120 */       Object old = sp.get((com.oracle.webservices.api.message.PropertySet)this);
/* 121 */       sp.set((com.oracle.webservices.api.message.PropertySet)this, null);
/* 122 */       return old;
/*     */     } 
/* 124 */     throw new IllegalArgumentException("Undefined property " + key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createEntrySet(Set<Map.Entry<String, Object>> core) {
/* 129 */     for (Map.Entry<String, BasePropertySet.Accessor> e : (Iterable<Map.Entry<String, BasePropertySet.Accessor>>)getPropertyMap().entrySet()) {
/* 130 */       core.add(new Map.Entry<String, Object>() {
/*     */             public String getKey() {
/* 132 */               return (String)e.getKey();
/*     */             }
/*     */             
/*     */             public Object getValue() {
/* 136 */               return ((BasePropertySet.Accessor)e.getValue()).get((com.oracle.webservices.api.message.PropertySet)PropertySet.this);
/*     */             }
/*     */             
/*     */             public Object setValue(Object value) {
/* 140 */               BasePropertySet.Accessor acc = (BasePropertySet.Accessor)e.getValue();
/* 141 */               Object old = acc.get((com.oracle.webservices.api.message.PropertySet)PropertySet.this);
/* 142 */               acc.set((com.oracle.webservices.api.message.PropertySet)PropertySet.this, value);
/* 143 */               return old;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract PropertyMap getPropertyMap();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\PropertySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */