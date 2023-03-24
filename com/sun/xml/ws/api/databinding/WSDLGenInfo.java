/*    */ package com.sun.xml.ws.api.databinding;
/*    */ 
/*    */ import com.oracle.webservices.api.databinding.WSDLResolver;
/*    */ import com.sun.xml.ws.api.server.Container;
/*    */ import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSDLGenInfo
/*    */ {
/*    */   WSDLResolver wsdlResolver;
/*    */   Container container;
/*    */   boolean inlineSchemas;
/*    */   WSDLGeneratorExtension[] extensions;
/*    */   
/*    */   public WSDLResolver getWsdlResolver() {
/* 59 */     return this.wsdlResolver;
/*    */   }
/*    */   public void setWsdlResolver(WSDLResolver wsdlResolver) {
/* 62 */     this.wsdlResolver = wsdlResolver;
/*    */   }
/*    */   public Container getContainer() {
/* 65 */     return this.container;
/*    */   }
/*    */   public void setContainer(Container container) {
/* 68 */     this.container = container;
/*    */   }
/*    */   public boolean isInlineSchemas() {
/* 71 */     return this.inlineSchemas;
/*    */   }
/*    */   public void setInlineSchemas(boolean inlineSchemas) {
/* 74 */     this.inlineSchemas = inlineSchemas;
/*    */   }
/*    */   public WSDLGeneratorExtension[] getExtensions() {
/* 77 */     if (this.extensions == null) return new WSDLGeneratorExtension[0]; 
/* 78 */     return this.extensions;
/*    */   }
/*    */   public void setExtensions(WSDLGeneratorExtension[] extensions) {
/* 81 */     this.extensions = extensions;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\databinding\WSDLGenInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */