/*    */ package com.sun.xml.rpc.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.document.WSDLConstants;
/*    */ import com.sun.xml.rpc.wsdl.document.mime.MIMEConstants;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*    */ import com.sun.xml.rpc.wsdl.framework.ParserContext;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ExtensionHandlerBase
/*    */   extends ExtensionHandler
/*    */ {
/*    */   public boolean doHandleExtension(ParserContext context, Extensible parent, Element e) {
/* 49 */     if (parent.getElementName().equals(WSDLConstants.QNAME_DEFINITIONS))
/* 50 */       return handleDefinitionsExtension(context, parent, e); 
/* 51 */     if (parent.getElementName().equals(WSDLConstants.QNAME_TYPES))
/* 52 */       return handleTypesExtension(context, parent, e); 
/* 53 */     if (parent.getElementName().equals(WSDLConstants.QNAME_BINDING))
/*    */     {
/* 55 */       return handleBindingExtension(context, parent, e); } 
/* 56 */     if (parent.getElementName().equals(WSDLConstants.QNAME_OPERATION))
/*    */     {
/* 58 */       return handleOperationExtension(context, parent, e); } 
/* 59 */     if (parent.getElementName().equals(WSDLConstants.QNAME_INPUT))
/* 60 */       return handleInputExtension(context, parent, e); 
/* 61 */     if (parent.getElementName().equals(WSDLConstants.QNAME_OUTPUT))
/*    */     {
/* 63 */       return handleOutputExtension(context, parent, e); } 
/* 64 */     if (parent.getElementName().equals(WSDLConstants.QNAME_FAULT))
/* 65 */       return handleFaultExtension(context, parent, e); 
/* 66 */     if (parent.getElementName().equals(WSDLConstants.QNAME_SERVICE))
/*    */     {
/* 68 */       return handleServiceExtension(context, parent, e); } 
/* 69 */     if (parent.getElementName().equals(WSDLConstants.QNAME_PORT))
/* 70 */       return handlePortExtension(context, parent, e); 
/* 71 */     if (parent.getElementName().equals(MIMEConstants.QNAME_PART)) {
/* 72 */       return handleMIMEPartExtension(context, parent, e);
/*    */     }
/* 74 */     return false;
/*    */   }
/*    */   
/*    */   protected abstract boolean handleDefinitionsExtension(ParserContext paramParserContext, Extensible paramExtensible, Element paramElement);
/*    */   
/*    */   protected abstract boolean handleTypesExtension(ParserContext paramParserContext, Extensible paramExtensible, Element paramElement);
/*    */   
/*    */   protected abstract boolean handleBindingExtension(ParserContext paramParserContext, Extensible paramExtensible, Element paramElement);
/*    */   
/*    */   protected abstract boolean handleOperationExtension(ParserContext paramParserContext, Extensible paramExtensible, Element paramElement);
/*    */   
/*    */   protected abstract boolean handleInputExtension(ParserContext paramParserContext, Extensible paramExtensible, Element paramElement);
/*    */   
/*    */   protected abstract boolean handleOutputExtension(ParserContext paramParserContext, Extensible paramExtensible, Element paramElement);
/*    */   
/*    */   protected abstract boolean handleFaultExtension(ParserContext paramParserContext, Extensible paramExtensible, Element paramElement);
/*    */   
/*    */   protected abstract boolean handleServiceExtension(ParserContext paramParserContext, Extensible paramExtensible, Element paramElement);
/*    */   
/*    */   protected abstract boolean handlePortExtension(ParserContext paramParserContext, Extensible paramExtensible, Element paramElement);
/*    */   
/*    */   protected abstract boolean handleMIMEPartExtension(ParserContext paramParserContext, Extensible paramExtensible, Element paramElement);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\ExtensionHandlerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */