/*    */ package com.sun.xml.bind.v2.runtime;
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
/*    */ public final class NameList
/*    */ {
/*    */   public final String[] namespaceURIs;
/*    */   public final boolean[] nsUriCannotBeDefaulted;
/*    */   public final String[] localNames;
/*    */   public final int numberOfElementNames;
/*    */   public final int numberOfAttributeNames;
/*    */   
/*    */   public NameList(String[] namespaceURIs, boolean[] nsUriCannotBeDefaulted, String[] localNames, int numberElementNames, int numberAttributeNames) {
/* 82 */     this.namespaceURIs = namespaceURIs;
/* 83 */     this.nsUriCannotBeDefaulted = nsUriCannotBeDefaulted;
/* 84 */     this.localNames = localNames;
/* 85 */     this.numberOfElementNames = numberElementNames;
/* 86 */     this.numberOfAttributeNames = numberAttributeNames;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\NameList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */