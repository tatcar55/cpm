/*    */ package com.sun.xml.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPortType;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.xml.sax.Locator;
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
/*    */ public final class WSDLPortTypeImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements WSDLPortType
/*    */ {
/*    */   private QName name;
/*    */   private final Map<String, WSDLOperationImpl> portTypeOperations;
/*    */   private WSDLModelImpl owner;
/*    */   
/*    */   public WSDLPortTypeImpl(XMLStreamReader xsr, WSDLModelImpl owner, QName name) {
/* 62 */     super(xsr);
/* 63 */     this.name = name;
/* 64 */     this.owner = owner;
/* 65 */     this.portTypeOperations = new Hashtable<String, WSDLOperationImpl>();
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 69 */     return this.name;
/*    */   }
/*    */   
/*    */   public WSDLOperationImpl get(String operationName) {
/* 73 */     return this.portTypeOperations.get(operationName);
/*    */   }
/*    */   
/*    */   public Iterable<WSDLOperationImpl> getOperations() {
/* 77 */     return this.portTypeOperations.values();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void put(String opName, WSDLOperationImpl ptOp) {
/* 87 */     this.portTypeOperations.put(opName, ptOp);
/*    */   }
/*    */   
/*    */   WSDLModelImpl getOwner() {
/* 91 */     return this.owner;
/*    */   }
/*    */   
/*    */   void freeze() {
/* 95 */     for (WSDLOperationImpl op : this.portTypeOperations.values())
/* 96 */       op.freez(this.owner); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLPortTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */