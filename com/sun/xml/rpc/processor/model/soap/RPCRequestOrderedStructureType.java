/*    */ package com.sun.xml.rpc.processor.model.soap;
/*    */ 
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
/*    */ public class RPCRequestOrderedStructureType
/*    */   extends SOAPOrderedStructureType
/*    */ {
/*    */   public RPCRequestOrderedStructureType() {}
/*    */   
/*    */   public RPCRequestOrderedStructureType(QName name) {
/* 42 */     this(name, SOAPVersion.SOAP_11);
/*    */   }
/*    */   
/*    */   public RPCRequestOrderedStructureType(QName name, SOAPVersion version) {
/* 46 */     super(name, version);
/*    */   }
/*    */   
/*    */   public void accept(SOAPTypeVisitor visitor) throws Exception {
/* 50 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\RPCRequestOrderedStructureType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */