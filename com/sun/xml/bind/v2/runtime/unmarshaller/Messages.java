/*    */ package com.sun.xml.bind.v2.runtime.unmarshaller;
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
/*    */ enum Messages
/*    */ {
/*    */   private static final ResourceBundle rb;
/* 50 */   UNRESOLVED_IDREF,
/* 51 */   UNEXPECTED_ELEMENT,
/* 52 */   UNEXPECTED_TEXT,
/* 53 */   NOT_A_QNAME,
/* 54 */   UNRECOGNIZED_TYPE_NAME,
/* 55 */   UNRECOGNIZED_TYPE_NAME_MAYBE,
/* 56 */   UNABLE_TO_CREATE_MAP,
/* 57 */   UNINTERNED_STRINGS;
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\Messages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */