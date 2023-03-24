/*     */ package com.sun.xml.ws.protocol.soap;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.binding.SOAPBindingImpl;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.message.DOMHeader;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
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
/*     */ 
/*     */ 
/*     */ abstract class MUTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   private static final String MU_FAULT_DETAIL_LOCALPART = "NotUnderstood";
/*  75 */   private static final QName MU_HEADER_DETAIL = new QName(SOAPVersion.SOAP_12.nsUri, "NotUnderstood");
/*     */   
/*  77 */   protected static final Logger logger = Logger.getLogger("com.sun.xml.ws.soap.decoder");
/*     */   
/*     */   private static final String MUST_UNDERSTAND_FAULT_MESSAGE_STRING = "One or more mandatory SOAP header blocks not understood";
/*     */   
/*     */   protected final SOAPVersion soapVersion;
/*     */   
/*     */   protected SOAPBindingImpl binding;
/*     */   
/*     */   protected MUTube(WSBinding binding, Tube next) {
/*  86 */     super(next);
/*     */     
/*  88 */     if (!(binding instanceof javax.xml.ws.soap.SOAPBinding)) {
/*  89 */       throw new WebServiceException("MUPipe should n't be used for bindings other than SOAP.");
/*     */     }
/*     */     
/*  92 */     this.binding = (SOAPBindingImpl)binding;
/*  93 */     this.soapVersion = binding.getSOAPVersion();
/*     */   }
/*     */   
/*     */   protected MUTube(MUTube that, TubeCloner cloner) {
/*  97 */     super(that, cloner);
/*  98 */     this.binding = that.binding;
/*  99 */     this.soapVersion = that.soapVersion;
/*     */   }
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
/*     */   public final Set<QName> getMisUnderstoodHeaders(MessageHeaders headers, Set<String> roles, Set<QName> handlerKnownHeaders) {
/* 112 */     return headers.getNotUnderstoodHeaders(roles, handlerKnownHeaders, (WSBinding)this.binding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final SOAPFaultException createMUSOAPFaultException(Set<QName> notUnderstoodHeaders) {
/*     */     try {
/* 123 */       SOAPFault fault = this.soapVersion.getSOAPFactory().createFault("One or more mandatory SOAP header blocks not understood", this.soapVersion.faultCodeMustUnderstand);
/*     */ 
/*     */       
/* 126 */       fault.setFaultString("MustUnderstand headers:" + notUnderstoodHeaders + " are not understood");
/*     */       
/* 128 */       return new SOAPFaultException(fault);
/* 129 */     } catch (SOAPException e) {
/* 130 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
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
/*     */   final Message createMUSOAPFaultMessage(Set<QName> notUnderstoodHeaders) {
/*     */     try {
/* 145 */       String faultString = "One or more mandatory SOAP header blocks not understood";
/* 146 */       if (this.soapVersion == SOAPVersion.SOAP_11) {
/* 147 */         faultString = "MustUnderstand headers:" + notUnderstoodHeaders + " are not understood";
/*     */       }
/* 149 */       Message muFaultMessage = SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, faultString, this.soapVersion.faultCodeMustUnderstand);
/*     */ 
/*     */       
/* 152 */       if (this.soapVersion == SOAPVersion.SOAP_12) {
/* 153 */         addHeader(muFaultMessage, notUnderstoodHeaders);
/*     */       }
/* 155 */       return muFaultMessage;
/* 156 */     } catch (SOAPException e) {
/* 157 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void addHeader(Message m, Set<QName> notUnderstoodHeaders) throws SOAPException {
/* 162 */     for (QName qname : notUnderstoodHeaders) {
/* 163 */       SOAPElement soapEl = SOAPVersion.SOAP_12.getSOAPFactory().createElement(MU_HEADER_DETAIL);
/* 164 */       soapEl.addNamespaceDeclaration("abc", qname.getNamespaceURI());
/* 165 */       soapEl.setAttribute("qname", "abc:" + qname.getLocalPart());
/* 166 */       DOMHeader dOMHeader = new DOMHeader(soapEl);
/* 167 */       m.getMessageHeaders().add((Header)dOMHeader);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\protocol\soap\MUTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */