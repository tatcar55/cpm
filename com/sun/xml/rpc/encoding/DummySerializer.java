/*    */ package com.sun.xml.rpc.encoding;
/*    */ 
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import com.sun.xml.rpc.streaming.XMLWriter;
/*    */ import javax.activation.DataHandler;
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
/*    */ 
/*    */ public class DummySerializer
/*    */   implements CombinedSerializer
/*    */ {
/* 38 */   private static final DummySerializer _instance = new DummySerializer();
/*    */   
/*    */   public static DummySerializer getInstance() {
/* 41 */     return _instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object deserialize(QName name, XMLReader element, SOAPDeserializationContext context) {
/* 60 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object deserialize(DataHandler dataHandler, SOAPDeserializationContext context) {
/* 67 */     return null;
/*    */   }
/*    */   
/*    */   public QName getXmlType() {
/* 71 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean getEncodeType() {
/* 75 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean isNullable() {
/* 79 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public String getEncodingStyle() {
/* 83 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public CombinedSerializer getInnermostSerializer() {
/* 87 */     return this;
/*    */   }
/*    */   
/*    */   public String getMechanismType() {
/* 91 */     return "http://java.sun.com/jax-rpc-ri/1.0/streaming/";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\DummySerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */