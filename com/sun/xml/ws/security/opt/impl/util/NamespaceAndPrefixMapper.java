/*    */ package com.sun.xml.ws.security.opt.impl.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.jvnet.staxex.NamespaceContextEx;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NamespaceAndPrefixMapper
/*    */ {
/*    */   public static final String NS_PREFIX_MAPPER = "NS_And_Prefix_Mapper";
/* 55 */   NamespaceContextEx ns = null;
/* 56 */   List<String> incList = null;
/*    */ 
/*    */   
/*    */   public NamespaceAndPrefixMapper(NamespaceContextEx ns, boolean disableIncPrefix) {
/* 60 */     this.ns = ns;
/* 61 */     this.incList = new ArrayList<String>();
/* 62 */     if (!disableIncPrefix) {
/* 63 */       this.incList.add("wsse");
/* 64 */       this.incList.add("S");
/*    */     } 
/*    */   }
/*    */   
/*    */   public NamespaceAndPrefixMapper(NamespaceContextEx ns, List<String> incList) {
/* 69 */     this.ns = ns;
/* 70 */     this.incList = incList;
/*    */   }
/*    */   
/*    */   public void setNamespaceContext(NamespaceContextEx ns) {
/* 74 */     this.ns = ns;
/*    */   }
/*    */   
/*    */   public NamespaceContextEx getNamespaceContext() {
/* 78 */     return this.ns;
/*    */   }
/*    */   
/*    */   public void addToInclusivePrefixList(String s) {
/* 82 */     if (this.incList == null)
/* 83 */       this.incList = new ArrayList<String>(); 
/* 84 */     this.incList.add(s);
/*    */   }
/*    */   
/*    */   public void removeFromInclusivePrefixList(String s) {
/* 88 */     if (this.incList == null)
/*    */       return; 
/* 90 */     this.incList.remove(s);
/*    */   }
/*    */   
/*    */   public List<String> getInlusivePrefixList() {
/* 94 */     return this.incList;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\NamespaceAndPrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */