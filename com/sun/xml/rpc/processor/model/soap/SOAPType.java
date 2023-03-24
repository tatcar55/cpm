/*    */ package com.sun.xml.rpc.processor.model.soap;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.AbstractType;
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
/*    */ public abstract class SOAPType
/*    */   extends AbstractType
/*    */ {
/*    */   protected SOAPType() {
/* 42 */     setVersion(SOAPVersion.SOAP_11.toString());
/*    */   }
/*    */   
/*    */   protected SOAPType(QName name) {
/* 46 */     this(name, SOAPVersion.SOAP_11);
/*    */   }
/*    */   
/*    */   protected SOAPType(QName name, SOAPVersion version) {
/* 50 */     this(name, (JavaType)null, version);
/*    */   }
/*    */   
/*    */   protected SOAPType(QName name, JavaType javaType) {
/* 54 */     this(name, javaType, SOAPVersion.SOAP_11);
/*    */   }
/*    */   
/*    */   protected SOAPType(QName name, JavaType javaType, SOAPVersion version) {
/* 58 */     super(name, javaType, (version != null) ? version.toString() : null);
/*    */   }
/*    */   
/*    */   public boolean isNillable() {
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isReferenceable() {
/* 66 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isSOAPType() {
/* 70 */     return true;
/*    */   }
/*    */   
/*    */   public abstract void accept(SOAPTypeVisitor paramSOAPTypeVisitor) throws Exception;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */