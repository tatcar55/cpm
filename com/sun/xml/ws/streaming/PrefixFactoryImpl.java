/*    */ package com.sun.xml.ws.streaming;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefixFactoryImpl
/*    */   implements PrefixFactory
/*    */ {
/*    */   private String _base;
/*    */   private int _next;
/*    */   private Map _cachedUriToPrefixMap;
/*    */   
/*    */   public PrefixFactoryImpl(String base) {
/* 54 */     this._base = base;
/* 55 */     this._next = 1;
/*    */   }
/*    */   
/*    */   public String getPrefix(String uri) {
/* 59 */     String prefix = null;
/*    */     
/* 61 */     if (this._cachedUriToPrefixMap == null) {
/* 62 */       this._cachedUriToPrefixMap = new HashMap<Object, Object>();
/*    */     } else {
/* 64 */       prefix = (String)this._cachedUriToPrefixMap.get(uri);
/*    */     } 
/*    */     
/* 67 */     if (prefix == null) {
/* 68 */       prefix = this._base + Integer.toString(this._next++);
/* 69 */       this._cachedUriToPrefixMap.put(uri, prefix);
/*    */     } 
/*    */     
/* 72 */     return prefix;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\streaming\PrefixFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */