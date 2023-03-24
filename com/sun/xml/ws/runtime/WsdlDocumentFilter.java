/*     */ package com.sun.xml.ws.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.config.management.policy.ManagedClientAssertion;
/*     */ import com.sun.xml.ws.api.config.management.policy.ManagedServiceAssertion;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.api.server.SDDocumentFilter;
/*     */ import com.sun.xml.ws.transport.tcp.wsit.PortAttributeInvocationTransformer;
/*     */ import com.sun.xml.ws.xmlfilter.EnhancedXmlStreamWriterProxy;
/*     */ import com.sun.xml.ws.xmlfilter.FilteringInvocationProcessor;
/*     */ import com.sun.xml.ws.xmlfilter.FilteringStateMachine;
/*     */ import com.sun.xml.ws.xmlfilter.InvocationProcessor;
/*     */ import com.sun.xml.ws.xmlfilter.InvocationProcessorFactory;
/*     */ import com.sun.xml.ws.xmlfilter.InvocationTransformer;
/*     */ import com.sun.xml.ws.xmlfilter.MexImportFilteringStateMachine;
/*     */ import com.sun.xml.ws.xmlfilter.PrivateAttributeFilteringStateMachine;
/*     */ import com.sun.xml.ws.xmlfilter.PrivateElementFilteringStateMachine;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class WsdlDocumentFilter
/*     */   implements SDDocumentFilter
/*     */ {
/*  68 */   private static final Logger LOGGER = Logger.getLogger(WsdlDocumentFilter.class);
/*     */   
/*  70 */   private static final InvocationProcessorFactory FILTERING_FACTORY = new InvocationProcessorFactory() {
/*     */       public InvocationProcessor createInvocationProcessor(XMLStreamWriter writer) throws XMLStreamException {
/*  72 */         return (InvocationProcessor)new FilteringInvocationProcessor(writer, (InvocationTransformer)new PortAttributeInvocationTransformer(), new FilteringStateMachine[] { (FilteringStateMachine)new MexImportFilteringStateMachine(), (FilteringStateMachine)new PrivateAttributeFilteringStateMachine(), (FilteringStateMachine)new PrivateElementFilteringStateMachine(new QName[] { new QName("http://schemas.sun.com/2006/03/wss/server", "KeyStore"), new QName("http://schemas.sun.com/2006/03/wss/server", "TrustStore"), new QName("http://schemas.sun.com/2006/03/wss/server", "CallbackHandlerConfiguration"), new QName("http://schemas.sun.com/2006/03/wss/server", "ValidatorConfiguration"), new QName("http://schemas.sun.com/2006/03/wss/server", "DisablePayloadBuffering"), new QName("http://schemas.sun.com/2006/03/wss/server", "KerberosConfig"), new QName("http://schemas.sun.com/2006/03/wss/client", "KeyStore"), new QName("http://schemas.sun.com/2006/03/wss/client", "TrustStore"), new QName("http://schemas.sun.com/2006/03/wss/client", "CallbackHandlerConfiguration"), new QName("http://schemas.sun.com/2006/03/wss/client", "ValidatorConfiguration"), new QName("http://schemas.sun.com/2006/03/wss/client", "DisablePayloadBuffering"), new QName("http://schemas.sun.com/2006/03/wss/client", "KerberosConfig"), new QName("http://schemas.sun.com/ws/2006/05/sc/server", "SCConfiguration"), new QName("http://schemas.sun.com/ws/2006/05/sc/client", "SCClientConfiguration"), new QName("http://schemas.sun.com/ws/2006/05/trust/server", "STSConfiguration"), new QName("http://schemas.sun.com/ws/2006/05/trust/client", "PreconfiguredSTS"), ManagedServiceAssertion.MANAGED_SERVICE_QNAME, ManagedClientAssertion.MANAGED_CLIENT_QNAME }) });
/*     */       }
/*     */     };
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
/*     */   public XMLStreamWriter filter(SDDocument sdDocument, XMLStreamWriter xmlStreamWriter) throws XMLStreamException {
/* 108 */     if (LOGGER.isMethodCallLoggable()) {
/* 109 */       LOGGER.entering(new Object[] { sdDocument, xmlStreamWriter });
/*     */     }
/* 111 */     XMLStreamWriter result = null;
/*     */     try {
/* 113 */       result = EnhancedXmlStreamWriterProxy.createProxy(xmlStreamWriter, FILTERING_FACTORY);
/* 114 */       return result;
/*     */     } finally {
/* 116 */       LOGGER.exiting(result);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\runtime\WsdlDocumentFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */