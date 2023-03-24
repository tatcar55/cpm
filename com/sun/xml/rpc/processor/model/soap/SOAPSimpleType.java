/*    */ package com.sun.xml.rpc.processor.model.soap;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
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
/*    */ public class SOAPSimpleType
/*    */   extends SOAPType
/*    */ {
/*    */   private QName schemaTypeRef;
/*    */   private boolean referenceable;
/*    */   
/*    */   public SOAPSimpleType() {}
/*    */   
/*    */   public SOAPSimpleType(QName name) {
/* 43 */     this(name, null);
/*    */   }
/*    */   
/*    */   public SOAPSimpleType(QName name, JavaSimpleType javaType) {
/* 47 */     this(name, javaType, true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPSimpleType(QName name, JavaSimpleType javaType, SOAPVersion version) {
/* 53 */     this(name, javaType, true, version);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPSimpleType(QName name, JavaSimpleType javaType, boolean referenceable) {
/* 59 */     this(name, javaType, referenceable, SOAPVersion.SOAP_11);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPSimpleType(QName name, JavaSimpleType javaType, boolean referenceable, SOAPVersion version) {
/* 65 */     super(name, (JavaType)javaType, version);
/* 66 */     this.referenceable = referenceable;
/*    */   }
/*    */   
/*    */   public QName getSchemaTypeRef() {
/* 70 */     return this.schemaTypeRef;
/*    */   }
/*    */   
/*    */   public void setSchemaTypeRef(QName n) {
/* 74 */     this.schemaTypeRef = n;
/*    */   }
/*    */   
/*    */   public boolean isReferenceable() {
/* 78 */     return this.referenceable;
/*    */   }
/*    */   
/*    */   public void setReferenceable(boolean b) {
/* 82 */     this.referenceable = b;
/*    */   }
/*    */   
/*    */   public void accept(SOAPTypeVisitor visitor) throws Exception {
/* 86 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPSimpleType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */