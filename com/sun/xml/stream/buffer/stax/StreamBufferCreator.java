/*     */ package com.sun.xml.stream.buffer.stax;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.AbstractCreator;
/*     */ import java.util.ArrayList;
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
/*     */ abstract class StreamBufferCreator
/*     */   extends AbstractCreator
/*     */ {
/*     */   private boolean checkAttributeValue = false;
/*  58 */   protected List<String> attributeValuePrefixes = new ArrayList<String>();
/*     */   
/*     */   protected void storeQualifiedName(int item, String prefix, String uri, String localName) {
/*  61 */     if (uri != null && uri.length() > 0) {
/*  62 */       if (prefix != null && prefix.length() > 0) {
/*  63 */         item |= 0x1;
/*  64 */         storeStructureString(prefix);
/*     */       } 
/*     */       
/*  67 */       item |= 0x2;
/*  68 */       storeStructureString(uri);
/*     */     } 
/*     */     
/*  71 */     storeStructureString(localName);
/*     */     
/*  73 */     storeStructure(item);
/*     */   }
/*     */   
/*     */   protected final void storeNamespaceAttribute(String prefix, String uri) {
/*  77 */     int item = 64;
/*     */     
/*  79 */     if (prefix != null && prefix.length() > 0) {
/*  80 */       item |= 0x1;
/*  81 */       storeStructureString(prefix);
/*     */     } 
/*     */     
/*  84 */     if (uri != null && uri.length() > 0) {
/*  85 */       item |= 0x2;
/*  86 */       storeStructureString(uri);
/*     */     } 
/*     */     
/*  89 */     storeStructure(item);
/*     */   }
/*     */   
/*     */   protected final void storeAttribute(String prefix, String uri, String localName, String type, String value) {
/*  93 */     storeQualifiedName(48, prefix, uri, localName);
/*     */     
/*  95 */     storeStructureString(type);
/*  96 */     storeContentString(value);
/*  97 */     if (this.checkAttributeValue && value.indexOf("://") == -1) {
/*  98 */       int firstIndex = value.indexOf(":");
/*  99 */       int lastIndex = value.lastIndexOf(":");
/* 100 */       if (firstIndex != -1 && lastIndex == firstIndex) {
/* 101 */         String valuePrefix = value.substring(0, firstIndex);
/* 102 */         if (!this.attributeValuePrefixes.contains(valuePrefix)) {
/* 103 */           this.attributeValuePrefixes.add(valuePrefix);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final List getAttributeValuePrefixes() {
/* 110 */     return this.attributeValuePrefixes;
/*     */   }
/*     */   
/*     */   protected final void storeProcessingInstruction(String target, String data) {
/* 114 */     storeStructure(112);
/* 115 */     storeStructureString(target);
/* 116 */     storeStructureString(data);
/*     */   }
/*     */   
/*     */   public final boolean isCheckAttributeValue() {
/* 120 */     return this.checkAttributeValue;
/*     */   }
/*     */   
/*     */   public final void setCheckAttributeValue(boolean value) {
/* 124 */     this.checkAttributeValue = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\stax\StreamBufferCreator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */