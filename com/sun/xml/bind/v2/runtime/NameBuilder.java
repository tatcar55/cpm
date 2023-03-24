/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public final class NameBuilder
/*     */ {
/*  64 */   private Map<String, Integer> uriIndexMap = new HashMap<String, Integer>();
/*  65 */   private Set<String> nonDefaultableNsUris = new HashSet<String>();
/*  66 */   private Map<String, Integer> localNameIndexMap = new HashMap<String, Integer>();
/*  67 */   private QNameMap<Integer> elementQNameIndexMap = new QNameMap();
/*  68 */   private QNameMap<Integer> attributeQNameIndexMap = new QNameMap();
/*     */   
/*     */   public Name createElementName(QName name) {
/*  71 */     return createElementName(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */   
/*     */   public Name createElementName(String nsUri, String localName) {
/*  75 */     return createName(nsUri, localName, false, this.elementQNameIndexMap);
/*     */   }
/*     */   
/*     */   public Name createAttributeName(QName name) {
/*  79 */     return createAttributeName(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */   
/*     */   public Name createAttributeName(String nsUri, String localName) {
/*  83 */     assert nsUri.intern() == nsUri;
/*  84 */     assert localName.intern() == localName;
/*     */     
/*  86 */     if (nsUri.length() == 0) {
/*  87 */       return new Name(allocIndex(this.attributeQNameIndexMap, "", localName), -1, nsUri, allocIndex(this.localNameIndexMap, localName), localName, true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     this.nonDefaultableNsUris.add(nsUri);
/*  96 */     return createName(nsUri, localName, true, this.attributeQNameIndexMap);
/*     */   }
/*     */ 
/*     */   
/*     */   private Name createName(String nsUri, String localName, boolean isAttribute, QNameMap<Integer> map) {
/* 101 */     assert nsUri.intern() == nsUri;
/* 102 */     assert localName.intern() == localName;
/*     */     
/* 104 */     return new Name(allocIndex(map, nsUri, localName), allocIndex(this.uriIndexMap, nsUri), nsUri, allocIndex(this.localNameIndexMap, localName), localName, isAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int allocIndex(Map<String, Integer> map, String str) {
/* 114 */     Integer i = map.get(str);
/* 115 */     if (i == null) {
/* 116 */       i = Integer.valueOf(map.size());
/* 117 */       map.put(str, i);
/*     */     } 
/* 119 */     return i.intValue();
/*     */   }
/*     */   
/*     */   private int allocIndex(QNameMap<Integer> map, String nsUri, String localName) {
/* 123 */     Integer i = (Integer)map.get(nsUri, localName);
/* 124 */     if (i == null) {
/* 125 */       i = Integer.valueOf(map.size());
/* 126 */       map.put(nsUri, localName, i);
/*     */     } 
/* 128 */     return i.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NameList conclude() {
/* 135 */     boolean[] nsUriCannotBeDefaulted = new boolean[this.uriIndexMap.size()];
/* 136 */     for (Map.Entry<String, Integer> e : this.uriIndexMap.entrySet()) {
/* 137 */       nsUriCannotBeDefaulted[((Integer)e.getValue()).intValue()] = this.nonDefaultableNsUris.contains(e.getKey());
/*     */     }
/*     */     
/* 140 */     NameList r = new NameList(list(this.uriIndexMap), nsUriCannotBeDefaulted, list(this.localNameIndexMap), this.elementQNameIndexMap.size(), this.attributeQNameIndexMap.size());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     this.uriIndexMap = null;
/* 148 */     this.localNameIndexMap = null;
/* 149 */     return r;
/*     */   }
/*     */   
/*     */   private String[] list(Map<String, Integer> map) {
/* 153 */     String[] r = new String[map.size()];
/* 154 */     for (Map.Entry<String, Integer> e : map.entrySet())
/* 155 */       r[((Integer)e.getValue()).intValue()] = e.getKey(); 
/* 156 */     return r;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\NameBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */