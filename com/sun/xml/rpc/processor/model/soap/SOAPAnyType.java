/*    */ package com.sun.xml.rpc.processor.model.soap;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*    */ import com.sun.xml.rpc.soap.SOAPVersion;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SOAPAnyType
/*    */   extends SOAPType
/*    */ {
/*    */   public SOAPAnyType() {}
/*    */   
/*    */   public SOAPAnyType(QName name) {
/* 43 */     super(name);
/*    */   }
/*    */   
/*    */   public SOAPAnyType(QName name, JavaType javaType) {
/* 47 */     this(name, javaType, SOAPVersion.SOAP_11);
/*    */   }
/*    */   
/*    */   public SOAPAnyType(QName name, JavaType javaType, SOAPVersion version) {
/* 51 */     super(name, javaType, version);
/*    */   }
/*    */   
/*    */   public void accept(SOAPTypeVisitor visitor) throws Exception {
/* 55 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPAnyType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */