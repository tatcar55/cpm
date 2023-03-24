/*    */ package com.ctc.wstx.dtd;
/*    */ 
/*    */ import com.ctc.wstx.util.PrefixedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmptyValidator
/*    */   extends StructValidator
/*    */ {
/* 14 */   static final EmptyValidator sPcdataInstance = new EmptyValidator("No elements allowed in pure #PCDATA content model");
/*    */   
/* 16 */   static final EmptyValidator sEmptyInstance = new EmptyValidator("No elements allowed in EMPTY content model");
/*    */   
/*    */   final String mErrorMsg;
/*    */   
/*    */   private EmptyValidator(String errorMsg) {
/* 21 */     this.mErrorMsg = errorMsg;
/*    */   }
/*    */   
/* 24 */   public static EmptyValidator getPcdataInstance() { return sPcdataInstance; } public static EmptyValidator getEmptyInstance() {
/* 25 */     return sPcdataInstance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StructValidator newInstance() {
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String tryToValidate(PrefixedName elemName) {
/* 37 */     return this.mErrorMsg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String fullyValid() {
/* 46 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\dtd\EmptyValidator.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */