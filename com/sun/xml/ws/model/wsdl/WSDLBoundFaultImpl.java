/*    */ package com.sun.xml.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundFault;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
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
/*    */ public class WSDLBoundFaultImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements WSDLBoundFault
/*    */ {
/*    */   private final String name;
/*    */   private WSDLFault fault;
/*    */   private WSDLBoundOperationImpl owner;
/*    */   
/*    */   public WSDLBoundFaultImpl(XMLStreamReader xsr, String name, WSDLBoundOperationImpl owner) {
/* 61 */     super(xsr);
/* 62 */     this.name = name;
/* 63 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getName() {
/* 69 */     return this.name;
/*    */   }
/*    */   
/*    */   public QName getQName() {
/* 73 */     if (this.owner.getOperation() != null) {
/* 74 */       return new QName(this.owner.getOperation().getName().getNamespaceURI(), this.name);
/*    */     }
/* 76 */     return null;
/*    */   }
/*    */   
/*    */   public WSDLFault getFault() {
/* 80 */     return this.fault;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public WSDLBoundOperation getBoundOperation() {
/* 85 */     return this.owner;
/*    */   }
/*    */   
/*    */   void freeze(WSDLBoundOperationImpl root) {
/* 89 */     assert root != null;
/* 90 */     WSDLOperation op = root.getOperation();
/* 91 */     if (op != null)
/* 92 */       for (WSDLFault f : op.getFaults()) {
/* 93 */         if (f.getName().equals(this.name)) {
/* 94 */           this.fault = f;
/*    */           break;
/*    */         } 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLBoundFaultImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */