/*    */ package com.sun.xml.bind.v2;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.ResourceBundle;
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
/*    */ public enum Messages
/*    */ {
/*    */   private static final ResourceBundle rb;
/* 50 */   ILLEGAL_ENTRY,
/* 51 */   ERROR_LOADING_CLASS,
/* 52 */   INVALID_PROPERTY_VALUE,
/* 53 */   UNSUPPORTED_PROPERTY,
/* 54 */   BROKEN_CONTEXTPATH,
/* 55 */   NO_DEFAULT_CONSTRUCTOR_IN_INNER_CLASS,
/* 56 */   INVALID_TYPE_IN_MAP,
/* 57 */   INVALID_JAXP_IMPLEMENTATION;
/*    */   
/*    */   static {
/* 60 */     rb = ResourceBundle.getBundle(Messages.class.getName());
/*    */   }
/*    */   public String toString() {
/* 63 */     return format(new Object[0]);
/*    */   }
/*    */   
/*    */   public String format(Object... args) {
/* 67 */     return MessageFormat.format(rb.getString(name()), args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\Messages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */