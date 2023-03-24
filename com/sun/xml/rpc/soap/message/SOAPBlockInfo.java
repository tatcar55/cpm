/*    */ package com.sun.xml.rpc.soap.message;
/*    */ 
/*    */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SOAPBlockInfo
/*    */ {
/*    */   private QName _name;
/*    */   private Object _value;
/*    */   private JAXRPCSerializer _serializer;
/*    */   
/*    */   public SOAPBlockInfo(QName name) {
/* 39 */     this._name = name;
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 43 */     return this._name;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 47 */     return this._value;
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 51 */     this._value = value;
/*    */   }
/*    */   
/*    */   public JAXRPCSerializer getSerializer() {
/* 55 */     return this._serializer;
/*    */   }
/*    */   
/*    */   public void setSerializer(JAXRPCSerializer s) {
/* 59 */     this._serializer = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\message\SOAPBlockInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */