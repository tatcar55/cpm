/*    */ package com.sun.xml.rpc.streaming;
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
/*    */ public class PrefixFactoryImpl
/*    */   implements PrefixFactory
/*    */ {
/*    */   private String _base;
/*    */   private int _next;
/*    */   private Map _cachedUriToPrefixMap;
/*    */   
/*    */   public PrefixFactoryImpl(String base) {
/* 40 */     this._base = base;
/* 41 */     this._next = 1;
/*    */   }
/*    */   
/*    */   public String getPrefix(String uri) {
/* 45 */     String prefix = null;
/*    */     
/* 47 */     if (this._cachedUriToPrefixMap == null) {
/* 48 */       this._cachedUriToPrefixMap = new HashMap<Object, Object>();
/*    */     } else {
/* 50 */       prefix = (String)this._cachedUriToPrefixMap.get(uri);
/*    */     } 
/*    */     
/* 53 */     if (prefix == null) {
/* 54 */       prefix = this._base + Integer.toString(this._next++);
/* 55 */       this._cachedUriToPrefixMap.put(uri, prefix);
/*    */     } 
/*    */     
/* 58 */     return prefix;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\PrefixFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */