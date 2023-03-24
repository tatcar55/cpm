/*     */ package com.sun.xml.ws.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFeaturedObject;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLBoundPortTypeImpl;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MemberSubmissionAddressingWSDLParserExtension
/*     */   extends W3CAddressingWSDLParserExtension
/*     */ {
/*     */   public boolean bindingElements(WSDLBoundPortType binding, XMLStreamReader reader) {
/*  65 */     return addressibleElement(reader, (WSDLFeaturedObject)binding);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean portElements(WSDLPort port, XMLStreamReader reader) {
/*  70 */     return addressibleElement(reader, (WSDLFeaturedObject)port);
/*     */   }
/*     */   
/*     */   private boolean addressibleElement(XMLStreamReader reader, WSDLFeaturedObject binding) {
/*  74 */     QName ua = reader.getName();
/*  75 */     if (ua.equals(AddressingVersion.MEMBER.wsdlExtensionTag)) {
/*  76 */       String required = reader.getAttributeValue("http://schemas.xmlsoap.org/wsdl/", "required");
/*  77 */       binding.addFeature((WebServiceFeature)new MemberSubmissionAddressingFeature(Boolean.parseBoolean(required)));
/*  78 */       XMLStreamReaderUtil.skipElement(reader);
/*  79 */       return true;
/*     */     } 
/*     */     
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bindingOperationElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void patchAnonymousDefault(WSDLBoundPortTypeImpl binding) {}
/*     */ 
/*     */   
/*     */   protected String getNamespaceURI() {
/*  96 */     return AddressingVersion.MEMBER.wsdlNsUri;
/*     */   }
/*     */ 
/*     */   
/*     */   protected QName getWsdlActionTag() {
/* 101 */     return AddressingVersion.MEMBER.wsdlActionTag;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\MemberSubmissionAddressingWSDLParserExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */