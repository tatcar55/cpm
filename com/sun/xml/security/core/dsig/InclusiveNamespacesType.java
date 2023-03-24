/*    */ package com.sun.xml.security.core.dsig;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAttribute;
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
/*    */ public class InclusiveNamespacesType
/*    */ {
/*    */   @XmlAttribute(name = "PrefixList", required = true)
/*    */   protected String prefixList;
/*    */   
/*    */   public String getPrefixList() {
/* 70 */     return this.prefixList;
/*    */   }
/*    */   
/*    */   public void addToPrefixList(String prefix) {
/* 74 */     if (this.prefixList == null) {
/* 75 */       this.prefixList = prefix;
/*    */     } else {
/* 77 */       this.prefixList = this.prefixList.concat(" ").concat(prefix);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\security\core\dsig\InclusiveNamespacesType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */