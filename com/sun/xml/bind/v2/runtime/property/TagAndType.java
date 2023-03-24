/*    */ package com.sun.xml.bind.v2.runtime.property;
/*    */ 
/*    */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*    */ import com.sun.xml.bind.v2.runtime.Name;
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
/*    */ class TagAndType
/*    */ {
/*    */   final Name tagName;
/*    */   final JaxBeanInfo beanInfo;
/*    */   
/*    */   TagAndType(Name tagName, JaxBeanInfo beanInfo) {
/* 57 */     this.tagName = tagName;
/* 58 */     this.beanInfo = beanInfo;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\TagAndType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */