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
/*    */ 
/*    */ public class RPCRequestUnorderedStructureType
/*    */   extends SOAPUnorderedStructureType
/*    */ {
/*    */   public RPCRequestUnorderedStructureType() {}
/*    */   
/*    */   public RPCRequestUnorderedStructureType(QName name) {
/* 43 */     this(name, SOAPVersion.SOAP_11);
/*    */   }
/*    */   
/*    */   public RPCRequestUnorderedStructureType(QName name, SOAPVersion version) {
/* 47 */     super(name, version);
/*    */   }
/*    */   
/*    */   public void accept(SOAPTypeVisitor visitor) throws Exception {
/* 51 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\RPCRequestUnorderedStructureType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */