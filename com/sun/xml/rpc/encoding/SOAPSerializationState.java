/*    */ package com.sun.xml.rpc.encoding;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SOAPSerializationState
/*    */ {
/*    */   private Object obj;
/*    */   private String id;
/*    */   private ReferenceableSerializer serializer;
/*    */   
/*    */   public SOAPSerializationState(Object obj, String id, ReferenceableSerializer serializer) {
/* 44 */     this.obj = obj;
/* 45 */     this.id = id;
/* 46 */     this.serializer = serializer;
/*    */   }
/*    */   
/*    */   public Object getObject() {
/* 50 */     return this.obj;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 54 */     return this.id;
/*    */   }
/*    */   
/*    */   public ReferenceableSerializer getSerializer() {
/* 58 */     return this.serializer;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SOAPSerializationState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */