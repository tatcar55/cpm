/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.rpc.encoding.TypeMapping;
/*     */ import javax.xml.rpc.encoding.TypeMappingRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeMappingRegistryImpl
/*     */   implements TypeMappingRegistry, SerializerConstants
/*     */ {
/*     */   protected Map mappings;
/*     */   protected TypeMapping defaultMapping;
/*     */   
/*     */   public TypeMappingRegistryImpl() {
/*  49 */     init();
/*     */   }
/*     */   
/*     */   protected void init() {
/*  53 */     this.mappings = new HashMap<Object, Object>();
/*  54 */     this.defaultMapping = null;
/*     */   }
/*     */   
/*     */   public TypeMapping register(String namespaceURI, TypeMapping mapping) {
/*  58 */     if (mapping == null || namespaceURI == null) {
/*  59 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  62 */     if (!mappingSupportsEncoding(mapping, namespaceURI)) {
/*  63 */       throw new TypeMappingException("typemapping.mappingDoesNotSupportEncoding", namespaceURI);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  68 */     TypeMapping oldMapping = (TypeMapping)this.mappings.get(namespaceURI);
/*  69 */     this.mappings.put(namespaceURI, mapping);
/*  70 */     return oldMapping;
/*     */   }
/*     */   
/*     */   public void registerDefault(TypeMapping mapping) {
/*  74 */     this.defaultMapping = mapping;
/*     */   }
/*     */   
/*     */   public TypeMapping getDefaultTypeMapping() {
/*  78 */     return this.defaultMapping;
/*     */   }
/*     */   
/*     */   public String[] getRegisteredEncodingStyleURIs() {
/*  82 */     Set namespaceSet = this.mappings.keySet();
/*  83 */     return (String[])namespaceSet.toArray((Object[])new String[namespaceSet.size()]);
/*     */   }
/*     */   
/*     */   public TypeMapping getTypeMapping(String namespaceURI) {
/*  87 */     if (namespaceURI == null) {
/*  88 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  91 */     TypeMapping mapping = (TypeMapping)this.mappings.get(namespaceURI);
/*  92 */     if (mapping == null) {
/*  93 */       mapping = this.defaultMapping;
/*     */     }
/*  95 */     return mapping;
/*     */   }
/*     */   
/*     */   public TypeMapping createTypeMapping() {
/*  99 */     return new TypeMappingImpl();
/*     */   }
/*     */   
/*     */   public TypeMapping unregisterTypeMapping(String namespaceURI) {
/* 103 */     return (TypeMapping)this.mappings.remove(namespaceURI);
/*     */   }
/*     */   
/*     */   public boolean removeTypeMapping(TypeMapping mapping) {
/* 107 */     if (mapping == null) {
/* 108 */       throw new IllegalArgumentException("mapping cannot be null");
/*     */     }
/* 110 */     Set typeEntries = this.mappings.entrySet();
/* 111 */     Iterator<Map.Entry> eachEntry = typeEntries.iterator();
/* 112 */     boolean typeMappingFound = false;
/*     */     
/* 114 */     while (eachEntry.hasNext()) {
/* 115 */       Map.Entry currentEntry = eachEntry.next();
/* 116 */       if (mapping.equals(currentEntry.getValue())) {
/* 117 */         eachEntry.remove();
/* 118 */         typeMappingFound = true;
/*     */       } 
/*     */     } 
/* 121 */     return typeMappingFound;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 125 */     this.mappings.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean mappingSupportsEncoding(TypeMapping mapping, String namespaceURI) {
/* 131 */     String[] encodings = ((TypeMappingImpl)mapping).getSupportedEncodings();
/*     */     
/* 133 */     for (int i = 0; i < encodings.length; i++) {
/* 134 */       if (encodings[i].equals(namespaceURI)) {
/* 135 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\TypeMappingRegistryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */