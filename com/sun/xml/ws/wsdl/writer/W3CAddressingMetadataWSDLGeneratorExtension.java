/*     */ package com.sun.xml.ws.wsdl.writer;
/*     */ 
/*     */ import com.sun.xml.txw2.TypedXmlWriter;
/*     */ import com.sun.xml.ws.addressing.W3CAddressingMetadataConstants;
/*     */ import com.sun.xml.ws.addressing.WsaActionUtil;
/*     */ import com.sun.xml.ws.api.model.CheckedException;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.wsdl.writer.WSDLGenExtnContext;
/*     */ import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;
/*     */ import com.sun.xml.ws.model.CheckedExceptionImpl;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.logging.Logger;
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
/*     */ public class W3CAddressingMetadataWSDLGeneratorExtension
/*     */   extends WSDLGeneratorExtension
/*     */ {
/*     */   public void start(WSDLGenExtnContext ctxt) {
/*  66 */     TypedXmlWriter root = ctxt.getRoot();
/*  67 */     root._namespace("http://www.w3.org/2007/05/addressing/metadata", "wsam");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
/*  73 */     input._attribute(W3CAddressingMetadataConstants.WSAM_ACTION_QNAME, getInputAction(method));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
/*  79 */     output._attribute(W3CAddressingMetadataConstants.WSAM_ACTION_QNAME, getOutputAction(method));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {
/*  85 */     fault._attribute(W3CAddressingMetadataConstants.WSAM_ACTION_QNAME, getFaultAction(method, ce));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String getInputAction(JavaMethod method) {
/*  90 */     String inputaction = ((JavaMethodImpl)method).getInputAction();
/*  91 */     if (inputaction.equals(""))
/*     */     {
/*  93 */       inputaction = getDefaultInputAction(method);
/*     */     }
/*  95 */     return inputaction;
/*     */   }
/*     */   
/*     */   protected static final String getDefaultInputAction(JavaMethod method) {
/*  99 */     String tns = method.getOwner().getTargetNamespace();
/* 100 */     String delim = getDelimiter(tns);
/* 101 */     if (tns.endsWith(delim)) {
/* 102 */       tns = tns.substring(0, tns.length() - 1);
/*     */     }
/*     */ 
/*     */     
/* 106 */     String name = method.getMEP().isOneWay() ? method.getOperationName() : (method.getOperationName() + "Request");
/*     */ 
/*     */     
/* 109 */     return tns + delim + method.getOwner().getPortTypeName().getLocalPart() + delim + name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String getOutputAction(JavaMethod method) {
/* 115 */     String outputaction = ((JavaMethodImpl)method).getOutputAction();
/* 116 */     if (outputaction.equals(""))
/* 117 */       outputaction = getDefaultOutputAction(method); 
/* 118 */     return outputaction;
/*     */   }
/*     */   
/*     */   protected static final String getDefaultOutputAction(JavaMethod method) {
/* 122 */     String tns = method.getOwner().getTargetNamespace();
/* 123 */     String delim = getDelimiter(tns);
/* 124 */     if (tns.endsWith(delim)) {
/* 125 */       tns = tns.substring(0, tns.length() - 1);
/*     */     }
/*     */ 
/*     */     
/* 129 */     String name = method.getOperationName() + "Response";
/*     */     
/* 131 */     return tns + delim + method.getOwner().getPortTypeName().getLocalPart() + delim + name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String getDelimiter(String tns) {
/* 138 */     String delim = "/";
/*     */     
/*     */     try {
/* 141 */       URI uri = new URI(tns);
/* 142 */       if (uri.getScheme() != null && uri.getScheme().equalsIgnoreCase("urn"))
/* 143 */         delim = ":"; 
/* 144 */     } catch (URISyntaxException e) {
/* 145 */       LOGGER.warning("TargetNamespace of WebService is not a valid URI");
/*     */     } 
/* 147 */     return delim;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String getFaultAction(JavaMethod method, CheckedException ce) {
/* 153 */     String faultaction = ((CheckedExceptionImpl)ce).getFaultAction();
/* 154 */     if (faultaction.equals("")) {
/* 155 */       faultaction = getDefaultFaultAction(method, ce);
/*     */     }
/* 157 */     return faultaction;
/*     */   }
/*     */   
/*     */   protected static final String getDefaultFaultAction(JavaMethod method, CheckedException ce) {
/* 161 */     return WsaActionUtil.getDefaultFaultAction(method, ce);
/*     */   }
/*     */   
/* 164 */   private static final Logger LOGGER = Logger.getLogger(W3CAddressingMetadataWSDLGeneratorExtension.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\W3CAddressingMetadataWSDLGeneratorExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */