/*     */ package com.sun.xml.ws.wsdl.writer;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.addressing.W3CAddressingConstants;
/*     */ import com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants;
/*     */ import com.sun.xml.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*     */ import com.sun.xml.ws.wsdl.parser.WSDLConstants;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ public final class WSDLPatcher
/*     */   extends XMLStreamReaderToXMLStreamWriter
/*     */ {
/*     */   private static final String NS_XSD = "http://www.w3.org/2001/XMLSchema";
/*  65 */   private static final QName SCHEMA_INCLUDE_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "include");
/*  66 */   private static final QName SCHEMA_IMPORT_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "import");
/*  67 */   private static final QName SCHEMA_REDEFINE_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "redefine");
/*     */   
/*  69 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.wsdl.patcher");
/*     */ 
/*     */ 
/*     */   
/*     */   private final DocumentLocationResolver docResolver;
/*     */ 
/*     */   
/*     */   private final PortAddressResolver portAddressResolver;
/*     */ 
/*     */   
/*     */   private String targetNamespace;
/*     */ 
/*     */   
/*     */   private QName serviceName;
/*     */ 
/*     */   
/*     */   private QName portName;
/*     */ 
/*     */   
/*     */   private String portAddress;
/*     */ 
/*     */   
/*     */   private boolean inEpr;
/*     */ 
/*     */   
/*     */   private boolean inEprAddress;
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLPatcher(@NotNull PortAddressResolver portAddressResolver, @NotNull DocumentLocationResolver docResolver) {
/*  99 */     this.portAddressResolver = portAddressResolver;
/* 100 */     this.docResolver = docResolver;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleAttribute(int i) throws XMLStreamException {
/* 105 */     QName name = this.in.getName();
/* 106 */     String attLocalName = this.in.getAttributeLocalName(i);
/*     */     
/* 108 */     if ((name.equals(SCHEMA_INCLUDE_QNAME) && attLocalName.equals("schemaLocation")) || (name.equals(SCHEMA_IMPORT_QNAME) && attLocalName.equals("schemaLocation")) || (name.equals(SCHEMA_REDEFINE_QNAME) && attLocalName.equals("schemaLocation")) || (name.equals(WSDLConstants.QNAME_IMPORT) && attLocalName.equals("location"))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       String relPath = this.in.getAttributeValue(i);
/* 115 */       String actualPath = getPatchedImportLocation(relPath);
/* 116 */       if (actualPath == null) {
/*     */         return;
/*     */       }
/*     */       
/* 120 */       logger.fine("Fixing the relative location:" + relPath + " with absolute location:" + actualPath);
/*     */       
/* 122 */       writeAttribute(i, actualPath);
/*     */       
/*     */       return;
/*     */     } 
/* 126 */     if (name.equals(WSDLConstants.NS_SOAP_BINDING_ADDRESS) || name.equals(WSDLConstants.NS_SOAP12_BINDING_ADDRESS))
/*     */     {
/*     */       
/* 129 */       if (attLocalName.equals("location")) {
/* 130 */         this.portAddress = this.in.getAttributeValue(i);
/* 131 */         String value = getAddressLocation();
/* 132 */         if (value != null) {
/* 133 */           logger.fine("Service:" + this.serviceName + " port:" + this.portName + " current address " + this.portAddress + " Patching it with " + value);
/*     */           
/* 135 */           writeAttribute(i, value);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/* 141 */     super.handleAttribute(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeAttribute(int i, String value) throws XMLStreamException {
/* 151 */     String nsUri = this.in.getAttributeNamespace(i);
/* 152 */     if (nsUri != null) {
/* 153 */       this.out.writeAttribute(this.in.getAttributePrefix(i), nsUri, this.in.getAttributeLocalName(i), value);
/*     */     } else {
/* 155 */       this.out.writeAttribute(this.in.getAttributeLocalName(i), value);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleStartElement() throws XMLStreamException {
/* 160 */     QName name = this.in.getName();
/*     */     
/* 162 */     if (name.equals(WSDLConstants.QNAME_DEFINITIONS)) {
/* 163 */       String value = this.in.getAttributeValue(null, "targetNamespace");
/* 164 */       if (value != null) {
/* 165 */         this.targetNamespace = value;
/*     */       }
/* 167 */     } else if (name.equals(WSDLConstants.QNAME_SERVICE)) {
/* 168 */       String value = this.in.getAttributeValue(null, "name");
/* 169 */       if (value != null) {
/* 170 */         this.serviceName = new QName(this.targetNamespace, value);
/*     */       }
/* 172 */     } else if (name.equals(WSDLConstants.QNAME_PORT)) {
/* 173 */       String value = this.in.getAttributeValue(null, "name");
/* 174 */       if (value != null) {
/* 175 */         this.portName = new QName(this.targetNamespace, value);
/*     */       }
/* 177 */     } else if (name.equals(W3CAddressingConstants.WSA_EPR_QNAME) || name.equals(MemberSubmissionAddressingConstants.WSA_EPR_QNAME)) {
/*     */       
/* 179 */       if (this.serviceName != null && this.portName != null) {
/* 180 */         this.inEpr = true;
/*     */       }
/* 182 */     } else if (name.equals(W3CAddressingConstants.WSA_ADDRESS_QNAME) || name.equals(MemberSubmissionAddressingConstants.WSA_ADDRESS_QNAME)) {
/*     */       
/* 184 */       if (this.inEpr) {
/* 185 */         this.inEprAddress = true;
/*     */       }
/*     */     } 
/* 188 */     super.handleStartElement();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleEndElement() throws XMLStreamException {
/* 193 */     QName name = this.in.getName();
/* 194 */     if (name.equals(WSDLConstants.QNAME_SERVICE)) {
/* 195 */       this.serviceName = null;
/* 196 */     } else if (name.equals(WSDLConstants.QNAME_PORT)) {
/* 197 */       this.portName = null;
/* 198 */     } else if (name.equals(W3CAddressingConstants.WSA_EPR_QNAME) || name.equals(MemberSubmissionAddressingConstants.WSA_EPR_QNAME)) {
/*     */       
/* 200 */       if (this.inEpr) {
/* 201 */         this.inEpr = false;
/*     */       }
/* 203 */     } else if (name.equals(W3CAddressingConstants.WSA_ADDRESS_QNAME) || name.equals(MemberSubmissionAddressingConstants.WSA_ADDRESS_QNAME)) {
/*     */       
/* 205 */       if (this.inEprAddress) {
/* 206 */         String value = getAddressLocation();
/* 207 */         if (value != null) {
/* 208 */           logger.fine("Fixing EPR Address for service:" + this.serviceName + " port:" + this.portName + " address with " + value);
/*     */           
/* 210 */           this.out.writeCharacters(value);
/*     */         } 
/* 212 */         this.inEprAddress = false;
/*     */       } 
/*     */     } 
/* 215 */     super.handleEndElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleCharacters() throws XMLStreamException {
/* 221 */     if (this.inEprAddress) {
/* 222 */       String value = getAddressLocation();
/* 223 */       if (value != null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 228 */     super.handleCharacters();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String getPatchedImportLocation(String relPath) {
/* 239 */     return this.docResolver.getLocationFor(null, relPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getAddressLocation() {
/* 249 */     return (this.portAddressResolver == null || this.portName == null) ? null : this.portAddressResolver.getAddressFor(this.serviceName, this.portName.getLocalPart(), this.portAddress);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\WSDLPatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */