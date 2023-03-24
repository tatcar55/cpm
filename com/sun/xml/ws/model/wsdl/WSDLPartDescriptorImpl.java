/*    */ package com.sun.xml.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLDescriptorKind;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPartDescriptor;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ public final class WSDLPartDescriptorImpl
/*    */   extends AbstractObjectImpl
/*    */   implements WSDLPartDescriptor
/*    */ {
/*    */   private QName name;
/*    */   private WSDLDescriptorKind type;
/*    */   
/*    */   public WSDLPartDescriptorImpl(XMLStreamReader xsr, QName name, WSDLDescriptorKind kind) {
/* 57 */     super(xsr);
/* 58 */     this.name = name;
/* 59 */     this.type = kind;
/*    */   }
/*    */   
/*    */   public QName name() {
/* 63 */     return this.name;
/*    */   }
/*    */   
/*    */   public WSDLDescriptorKind type() {
/* 67 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLPartDescriptorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */