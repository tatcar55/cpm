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
/*    */ public class SOAPEnumerationType
/*    */   extends SOAPType
/*    */ {
/*    */   private SOAPType baseType;
/*    */   
/*    */   public SOAPEnumerationType() {}
/*    */   
/*    */   public SOAPEnumerationType(QName name, SOAPType baseType, JavaType javaType) {
/* 45 */     this(name, baseType, javaType, SOAPVersion.SOAP_11);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPEnumerationType(QName name, SOAPType baseType, JavaType javaType, SOAPVersion version) {
/* 51 */     super(name, javaType, version);
/* 52 */     this.baseType = baseType;
/*    */   }
/*    */   
/*    */   public SOAPType getBaseType() {
/* 56 */     return this.baseType;
/*    */   }
/*    */   
/*    */   public void setBaseType(SOAPType t) {
/* 60 */     this.baseType = t;
/*    */   }
/*    */   
/*    */   public void accept(SOAPTypeVisitor visitor) throws Exception {
/* 64 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPEnumerationType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */