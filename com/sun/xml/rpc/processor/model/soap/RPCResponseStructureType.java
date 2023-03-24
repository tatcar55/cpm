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
/*    */ public class RPCResponseStructureType
/*    */   extends SOAPStructureType
/*    */ {
/*    */   public RPCResponseStructureType() {}
/*    */   
/*    */   public RPCResponseStructureType(QName name) {
/* 42 */     this(name, SOAPVersion.SOAP_11);
/*    */   }
/*    */   
/*    */   public RPCResponseStructureType(QName name, SOAPVersion version) {
/* 46 */     super(name, version);
/*    */   }
/*    */   
/*    */   public void accept(SOAPTypeVisitor visitor) throws Exception {
/* 50 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\RPCResponseStructureType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */