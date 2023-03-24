/*    */ package com.sun.xml.bind.v2.runtime;
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
/*    */ 
/*    */ enum Messages
/*    */ {
/* 50 */   ILLEGAL_PARAMETER,
/* 51 */   UNABLE_TO_FIND_CONVERSION_METHOD,
/* 52 */   MISSING_ID,
/* 53 */   NOT_IMPLEMENTED_IN_2_0,
/* 54 */   UNRECOGNIZED_ELEMENT_NAME,
/* 55 */   TYPE_MISMATCH,
/* 56 */   MISSING_OBJECT,
/* 57 */   NOT_IDENTIFIABLE,
/* 58 */   DANGLING_IDREF,
/* 59 */   NULL_OUTPUT_RESOLVER,
/* 60 */   UNABLE_TO_MARSHAL_NON_ELEMENT,
/* 61 */   UNABLE_TO_MARSHAL_UNBOUND_CLASS,
/* 62 */   UNSUPPORTED_PROPERTY,
/* 63 */   NULL_PROPERTY_NAME,
/* 64 */   MUST_BE_X,
/* 65 */   NOT_MARSHALLABLE,
/* 66 */   UNSUPPORTED_RESULT,
/* 67 */   UNSUPPORTED_ENCODING,
/* 68 */   SUBSTITUTED_BY_ANONYMOUS_TYPE,
/* 69 */   CYCLE_IN_MARSHALLER,
/* 70 */   UNABLE_TO_DISCOVER_EVENTHANDLER,
/* 71 */   ELEMENT_NEEDED_BUT_FOUND_DOCUMENT,
/* 72 */   UNKNOWN_CLASS,
/* 73 */   FAILED_TO_GENERATE_SCHEMA,
/* 74 */   ERROR_PROCESSING_SCHEMA,
/* 75 */   ILLEGAL_CONTENT;
/*    */   
/*    */   static {
/* 78 */     rb = ResourceBundle.getBundle(Messages.class.getName());
/*    */   }
/*    */   private static final ResourceBundle rb;
/*    */   public String toString() {
/* 82 */     return format(new Object[0]);
/*    */   }
/*    */   
/*    */   public String format(Object... args) {
/* 86 */     return MessageFormat.format(rb.getString(name()), args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\Messages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */