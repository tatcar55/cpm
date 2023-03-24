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
/*    */ public class SOAPArrayType
/*    */   extends SOAPType
/*    */ {
/*    */   private QName elementName;
/*    */   private SOAPType elementType;
/*    */   private int rank;
/*    */   private int[] size;
/*    */   
/*    */   public SOAPArrayType() {}
/*    */   
/*    */   public SOAPArrayType(QName name) {
/* 43 */     this(name, SOAPVersion.SOAP_11);
/*    */   }
/*    */   
/*    */   public SOAPArrayType(QName name, SOAPVersion version) {
/* 47 */     this(name, null, null, null, version);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPArrayType(QName name, QName elementName, SOAPType elementType, JavaType javaType) {
/* 53 */     this(name, elementName, elementType, javaType, SOAPVersion.SOAP_11);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPArrayType(QName name, QName elementName, SOAPType elementType, JavaType javaType, SOAPVersion version) {
/* 59 */     super(name, javaType, version);
/* 60 */     this.elementName = elementName;
/* 61 */     this.elementType = elementType;
/*    */   }
/*    */   
/*    */   public QName getElementName() {
/* 65 */     return this.elementName;
/*    */   }
/*    */   
/*    */   public void setElementName(QName name) {
/* 69 */     this.elementName = name;
/*    */   }
/*    */   
/*    */   public SOAPType getElementType() {
/* 73 */     return this.elementType;
/*    */   }
/*    */   
/*    */   public void setElementType(SOAPType type) {
/* 77 */     this.elementType = type;
/*    */   }
/*    */   
/*    */   public int getRank() {
/* 81 */     return this.rank;
/*    */   }
/*    */   
/*    */   public void setRank(int i) {
/* 85 */     this.rank = i;
/*    */   }
/*    */   
/*    */   public int[] getSize() {
/* 89 */     return this.size;
/*    */   }
/*    */   
/*    */   public void setSize(int[] a) {
/* 93 */     this.size = a;
/*    */   }
/*    */   
/*    */   public void accept(SOAPTypeVisitor visitor) throws Exception {
/* 97 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPArrayType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */