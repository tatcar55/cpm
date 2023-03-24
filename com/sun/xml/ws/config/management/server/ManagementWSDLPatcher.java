/*     */ package com.sun.xml.ws.config.management.server;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.txw2.output.StaxSerializer;
/*     */ import com.sun.xml.ws.api.policy.ModelGenerator;
/*     */ import com.sun.xml.ws.config.management.ManagementMessages;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicyModelGenerator;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicyModelMarshaller;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*     */ import com.sun.xml.ws.policy.sourcemodel.attach.ExternalAttachmentsUnmarshaller;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*     */ import com.sun.xml.ws.wsdl.parser.WSDLConstants;
/*     */ import java.net.URI;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class ManagementWSDLPatcher
/*     */   extends XMLStreamReaderToXMLStreamWriter
/*     */ {
/*  73 */   private static final Logger LOGGER = Logger.getLogger(ManagementWSDLPatcher.class);
/*  74 */   private static final PolicyModelMarshaller POLICY_MARSHALLER = PolicyModelMarshaller.getXmlMarshaller(true);
/*  75 */   private static final PolicyModelGenerator POLICY_GENERATOR = ModelGenerator.getGenerator();
/*     */   
/*     */   private final Map<URI, Policy> urnToPolicy;
/*  78 */   private long skipDepth = -1L;
/*     */   private boolean inBinding = false;
/*     */   
/*     */   public ManagementWSDLPatcher(Map<URI, Policy> urnToPolicy) {
/*  82 */     this.urnToPolicy = urnToPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleStartElement() throws XMLStreamException {
/*  93 */     if (this.skipDepth >= 0L) {
/*  94 */       this.skipDepth++;
/*     */       return;
/*     */     } 
/*  97 */     QName elementName = this.in.getName();
/*  98 */     XmlToken policyToken = NamespaceVersion.resolveAsToken(elementName);
/*  99 */     if (policyToken != XmlToken.UNKNOWN) {
/* 100 */       this.skipDepth++;
/*     */       return;
/*     */     } 
/* 103 */     if (elementName.equals(WSDLConstants.QNAME_BINDING)) {
/* 104 */       this.inBinding = true;
/* 105 */       super.handleStartElement();
/* 106 */       Policy bindingPolicy = this.urnToPolicy.get(ExternalAttachmentsUnmarshaller.BINDING_ID);
/* 107 */       if (bindingPolicy != null) {
/* 108 */         writePolicy(bindingPolicy);
/*     */       }
/*     */     }
/* 111 */     else if (this.inBinding && elementName.equals(WSDLConstants.QNAME_OPERATION)) {
/* 112 */       super.handleStartElement();
/* 113 */       Policy operationPolicy = this.urnToPolicy.get(ExternalAttachmentsUnmarshaller.BINDING_OPERATION_ID);
/* 114 */       if (operationPolicy != null) {
/* 115 */         writePolicy(operationPolicy);
/*     */       }
/*     */     }
/* 118 */     else if (this.inBinding && elementName.equals(WSDLConstants.QNAME_INPUT)) {
/* 119 */       super.handleStartElement();
/* 120 */       Policy inputPolicy = this.urnToPolicy.get(ExternalAttachmentsUnmarshaller.BINDING_OPERATION_INPUT_ID);
/* 121 */       if (inputPolicy != null) {
/* 122 */         writePolicy(inputPolicy);
/*     */       }
/*     */     }
/* 125 */     else if (this.inBinding && elementName.equals(WSDLConstants.QNAME_OUTPUT)) {
/* 126 */       super.handleStartElement();
/* 127 */       Policy outputPolicy = this.urnToPolicy.get(ExternalAttachmentsUnmarshaller.BINDING_OPERATION_OUTPUT_ID);
/* 128 */       if (outputPolicy != null) {
/* 129 */         writePolicy(outputPolicy);
/*     */       }
/*     */     }
/* 132 */     else if (this.inBinding && elementName.equals(WSDLConstants.QNAME_FAULT)) {
/* 133 */       super.handleStartElement();
/* 134 */       Policy faultPolicy = this.urnToPolicy.get(ExternalAttachmentsUnmarshaller.BINDING_OPERATION_FAULT_ID);
/* 135 */       if (faultPolicy != null) {
/* 136 */         writePolicy(faultPolicy);
/*     */       }
/*     */     } else {
/*     */       
/* 140 */       super.handleStartElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleEndElement() throws XMLStreamException {
/* 151 */     QName elementName = this.in.getName();
/* 152 */     if (this.inBinding) {
/* 153 */       this.inBinding = !elementName.equals(WSDLConstants.QNAME_BINDING);
/*     */     }
/* 155 */     if (this.skipDepth < 0L) {
/* 156 */       super.handleEndElement();
/*     */       
/*     */       return;
/*     */     } 
/* 160 */     this.skipDepth--;
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
/*     */   protected void handleAttribute(int i) throws XMLStreamException {
/* 173 */     QName attributeName = this.in.getAttributeName(i);
/* 174 */     XmlToken policyToken = NamespaceVersion.resolveAsToken(attributeName);
/* 175 */     switch (policyToken) {
/*     */       case PolicyUris:
/*     */         return;
/*     */     } 
/* 179 */     super.handleAttribute(i);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleCharacters() throws XMLStreamException {
/* 184 */     if (this.skipDepth < 0L) {
/* 185 */       super.handleCharacters();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleComment() throws XMLStreamException {
/* 191 */     if (this.skipDepth < 0L) {
/* 192 */       super.handleComment();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handlePI() throws XMLStreamException {
/* 198 */     if (this.skipDepth < 0L) {
/* 199 */       super.handlePI();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleDTD() throws XMLStreamException {
/* 205 */     if (this.skipDepth < 0L) {
/* 206 */       super.handleDTD();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleEntityReference() throws XMLStreamException {
/* 212 */     if (this.skipDepth < 0L) {
/* 213 */       super.handleEntityReference();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleSpace() throws XMLStreamException {
/* 219 */     if (this.skipDepth < 0L) {
/* 220 */       super.handleSpace();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleCDATA() throws XMLStreamException {
/* 226 */     if (this.skipDepth < 0L) {
/* 227 */       super.handleCDATA();
/*     */     }
/*     */   }
/*     */   
/*     */   private void writePolicy(Policy policy) {
/*     */     try {
/* 233 */       PolicySourceModel policyModel = POLICY_GENERATOR.translate(policy);
/* 234 */       this.out.writeCharacters("\n");
/* 235 */       StaxSerializer serializer = new FragmentSerializer(this.out);
/* 236 */       POLICY_MARSHALLER.marshal(policyModel, serializer);
/* 237 */     } catch (PolicyException e) {
/* 238 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(ManagementMessages.WSM_5096_CANNOT_MARSHAL(this.out)), e);
/*     */     }
/* 240 */     catch (XMLStreamException e) {
/* 241 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(ManagementMessages.WSM_5096_CANNOT_MARSHAL(this.out)), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   class FragmentSerializer
/*     */     extends StaxSerializer
/*     */   {
/*     */     public FragmentSerializer(XMLStreamWriter writer) {
/* 249 */       super(writer);
/*     */     }
/*     */     
/*     */     public void endDocument() {}
/*     */     
/*     */     public void startDocument() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\management\server\ManagementWSDLPatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */