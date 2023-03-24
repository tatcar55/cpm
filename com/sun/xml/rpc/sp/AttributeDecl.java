/*    */ package com.sun.xml.rpc.sp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AttributeDecl
/*    */ {
/*    */   String name;
/*    */   String type;
/*    */   String[] values;
/*    */   String defaultValue;
/*    */   boolean isRequired;
/*    */   boolean isFixed;
/*    */   boolean isFromInternalSubset;
/*    */   static final String CDATA = "CDATA";
/*    */   static final String ID = "ID";
/*    */   static final String IDREF = "IDREF";
/*    */   static final String IDREFS = "IDREFS";
/*    */   static final String ENTITY = "ENTITY";
/*    */   static final String ENTITIES = "ENTITIES";
/*    */   static final String NMTOKEN = "NMTOKEN";
/*    */   static final String NMTOKENS = "NMTOKENS";
/*    */   static final String NOTATION = "NOTATION";
/*    */   static final String ENUMERATION = "ENUMERATION";
/*    */   
/*    */   AttributeDecl(String s) {
/* 61 */     this.name = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\AttributeDecl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */