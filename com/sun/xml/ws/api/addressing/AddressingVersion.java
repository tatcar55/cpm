/*     */ package com.sun.xml.ws.api.addressing;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.addressing.WsaTubeHelper;
/*     */ import com.sun.xml.ws.addressing.WsaTubeHelperImpl;
/*     */ import com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants;
/*     */ import com.sun.xml.ws.addressing.v200408.WsaTubeHelperImpl;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
/*     */ import com.sun.xml.ws.message.stream.OutboundStreamHeader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
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
/*     */ public enum AddressingVersion
/*     */ {
/*  74 */   W3C("http://www.w3.org/2005/08/addressing", "wsa", "<EndpointReference xmlns=\"http://www.w3.org/2005/08/addressing\">\n    <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>\n</EndpointReference>", "http://www.w3.org/2006/05/addressing/wsdl", "http://www.w3.org/2006/05/addressing/wsdl", "http://www.w3.org/2005/08/addressing/anonymous", "http://www.w3.org/2005/08/addressing/none", new EPR((Class)W3CEndpointReference.class, "Address", "ServiceName", "EndpointName", "InterfaceName", new QName("http://www.w3.org/2005/08/addressing", "Metadata", "wsa"), "ReferenceParameters", null))
/*     */   {
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
/*     */     String getActionMismatchLocalName()
/*     */     {
/*  91 */       return "ActionMismatch";
/*     */     }
/*     */     
/*     */     public boolean isReferenceParameter(String localName) {
/*  95 */       return localName.equals("ReferenceParameters");
/*     */     }
/*     */ 
/*     */     
/*     */     public WsaTubeHelper getWsaHelper(WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/* 100 */       return (WsaTubeHelper)new WsaTubeHelperImpl(wsdlPort, seiModel, binding);
/*     */     }
/*     */ 
/*     */     
/*     */     String getMapRequiredLocalName() {
/* 105 */       return "MessageAddressingHeaderRequired";
/*     */     }
/*     */ 
/*     */     
/*     */     public String getMapRequiredText() {
/* 110 */       return "A required header representing a Message Addressing Property is not present";
/*     */     }
/*     */     
/*     */     String getInvalidAddressLocalName() {
/* 114 */       return "InvalidAddress";
/*     */     }
/*     */ 
/*     */     
/*     */     String getInvalidMapLocalName() {
/* 119 */       return "InvalidAddressingHeader";
/*     */     }
/*     */ 
/*     */     
/*     */     public String getInvalidMapText() {
/* 124 */       return "A header representing a Message Addressing Property is not valid and the message cannot be processed";
/*     */     }
/*     */ 
/*     */     
/*     */     String getInvalidCardinalityLocalName() {
/* 129 */       return "InvalidCardinality";
/*     */     }
/*     */     
/*     */     Header createReferenceParameterHeader(XMLStreamBuffer mark, String nsUri, String localName) {
/* 133 */       return (Header)new OutboundReferenceParameterHeader(mark, nsUri, localName);
/*     */     }
/*     */     
/*     */     String getIsReferenceParameterLocalName() {
/* 137 */       return "IsReferenceParameter";
/*     */     }
/*     */     
/*     */     String getWsdlAnonymousLocalName() {
/* 141 */       return "Anonymous";
/*     */     }
/*     */     
/*     */     public String getPrefix() {
/* 145 */       return "wsa";
/*     */     }
/*     */     
/*     */     public String getWsdlPrefix() {
/* 149 */       return "wsaw";
/*     */     }
/*     */     
/*     */     public Class<? extends WebServiceFeature> getFeatureClass() {
/* 153 */       return (Class)AddressingFeature.class;
/*     */     }
/*     */   },
/* 156 */   MEMBER("http://schemas.xmlsoap.org/ws/2004/08/addressing", "wsa", "<EndpointReference xmlns=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\">\n    <Address>http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous</Address>\n</EndpointReference>", "http://schemas.xmlsoap.org/ws/2004/08/addressing", "http://schemas.xmlsoap.org/ws/2004/08/addressing/policy", "http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous", "", new EPR((Class)MemberSubmissionEndpointReference.class, "Address", "ServiceName", "PortName", "PortType", MemberSubmissionAddressingConstants.MEX_METADATA, "ReferenceParameters", "ReferenceProperties"))
/*     */   {
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
/*     */     String getActionMismatchLocalName()
/*     */     {
/* 172 */       return "InvalidMessageInformationHeader";
/*     */     }
/*     */     
/*     */     public boolean isReferenceParameter(String localName) {
/* 176 */       return (localName.equals("ReferenceParameters") || localName.equals("ReferenceProperties"));
/*     */     }
/*     */ 
/*     */     
/*     */     public WsaTubeHelper getWsaHelper(WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/* 181 */       return (WsaTubeHelper)new WsaTubeHelperImpl(wsdlPort, seiModel, binding);
/*     */     }
/*     */ 
/*     */     
/*     */     String getMapRequiredLocalName() {
/* 186 */       return "MessageInformationHeaderRequired";
/*     */     }
/*     */ 
/*     */     
/*     */     public String getMapRequiredText() {
/* 191 */       return "A required message information header, To, MessageID, or Action, is not present.";
/*     */     }
/*     */     
/*     */     String getInvalidAddressLocalName() {
/* 195 */       return getInvalidMapLocalName();
/*     */     }
/*     */ 
/*     */     
/*     */     String getInvalidMapLocalName() {
/* 200 */       return "InvalidMessageInformationHeader";
/*     */     }
/*     */ 
/*     */     
/*     */     public String getInvalidMapText() {
/* 205 */       return "A message information header is not valid and the message cannot be processed.";
/*     */     }
/*     */ 
/*     */     
/*     */     String getInvalidCardinalityLocalName() {
/* 210 */       return getInvalidMapLocalName();
/*     */     }
/*     */     
/*     */     Header createReferenceParameterHeader(XMLStreamBuffer mark, String nsUri, String localName) {
/* 214 */       return (Header)new OutboundStreamHeader(mark, nsUri, localName);
/*     */     }
/*     */     
/*     */     String getIsReferenceParameterLocalName() {
/* 218 */       return "";
/*     */     }
/*     */     
/*     */     String getWsdlAnonymousLocalName() {
/* 222 */       return "";
/*     */     }
/*     */     
/*     */     public String getPrefix() {
/* 226 */       return "wsa";
/*     */     }
/*     */     
/*     */     public String getWsdlPrefix() {
/* 230 */       return "wsaw";
/*     */     }
/*     */     
/*     */     public Class<? extends WebServiceFeature> getFeatureClass() {
/* 234 */       return (Class)MemberSubmissionAddressingFeature.class;
/*     */     }
/*     */   };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String nsUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String wsdlNsUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final EPR eprType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String policyNsUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final String anonymousUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final String noneUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final WSEndpointReference anonymousEpr;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName toTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName fromTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName replyToTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName faultToTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName actionTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName messageIDTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName relatesToTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName mapRequiredTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName actionMismatchTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName actionNotSupportedTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String actionNotSupportedText;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName invalidMapTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName invalidCardinalityTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName invalidAddressTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName problemHeaderQNameTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName problemActionTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName faultDetailTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName fault_missingAddressInEpr;
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName wsdlActionTag;
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName wsdlExtensionTag;
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName wsdlAnonymousTag;
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName isReferenceParameterTag;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String EXTENDED_FAULT_NAMESPACE = "http://jax-ws.dev.java.net/addressing/fault";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String UNSET_OUTPUT_ACTION = "http://jax-ws.dev.java.net/addressing/output-action-not-set";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String UNSET_INPUT_ACTION = "http://jax-ws.dev.java.net/addressing/input-action-not-set";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final QName fault_duplicateAddressInEpr;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 402 */     fault_duplicateAddressInEpr = new QName("http://jax-ws.dev.java.net/addressing/fault", "DuplicateAddressInEpr", "wsa");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AddressingVersion(String nsUri, String prefix, String anonymousEprString, String wsdlNsUri, String policyNsUri, String anonymousUri, String noneUri, EPR eprType) {
/* 409 */     this.nsUri = nsUri;
/* 410 */     this.wsdlNsUri = wsdlNsUri;
/* 411 */     this.policyNsUri = policyNsUri;
/* 412 */     this.anonymousUri = anonymousUri;
/* 413 */     this.noneUri = noneUri;
/* 414 */     this.toTag = new QName(nsUri, "To", prefix);
/* 415 */     this.fromTag = new QName(nsUri, "From", prefix);
/* 416 */     this.replyToTag = new QName(nsUri, "ReplyTo", prefix);
/* 417 */     this.faultToTag = new QName(nsUri, "FaultTo", prefix);
/* 418 */     this.actionTag = new QName(nsUri, "Action", prefix);
/* 419 */     this.messageIDTag = new QName(nsUri, "MessageID", prefix);
/* 420 */     this.relatesToTag = new QName(nsUri, "RelatesTo", prefix);
/*     */     
/* 422 */     this.mapRequiredTag = new QName(nsUri, getMapRequiredLocalName(), prefix);
/* 423 */     this.actionMismatchTag = new QName(nsUri, getActionMismatchLocalName(), prefix);
/* 424 */     this.actionNotSupportedTag = new QName(nsUri, "ActionNotSupported", prefix);
/* 425 */     this.actionNotSupportedText = "The \"%s\" cannot be processed at the receiver";
/* 426 */     this.invalidMapTag = new QName(nsUri, getInvalidMapLocalName(), prefix);
/* 427 */     this.invalidAddressTag = new QName(nsUri, getInvalidAddressLocalName(), prefix);
/* 428 */     this.invalidCardinalityTag = new QName(nsUri, getInvalidCardinalityLocalName(), prefix);
/* 429 */     this.faultDetailTag = new QName(nsUri, "FaultDetail", prefix);
/*     */     
/* 431 */     this.problemHeaderQNameTag = new QName(nsUri, "ProblemHeaderQName", prefix);
/* 432 */     this.problemActionTag = new QName(nsUri, "ProblemAction", prefix);
/*     */     
/* 434 */     this.fault_missingAddressInEpr = new QName(nsUri, "MissingAddressInEPR", prefix);
/* 435 */     this.isReferenceParameterTag = new QName(nsUri, getIsReferenceParameterLocalName(), prefix);
/*     */     
/* 437 */     this.wsdlActionTag = new QName(wsdlNsUri, "Action", prefix);
/* 438 */     this.wsdlExtensionTag = new QName(wsdlNsUri, "UsingAddressing", prefix);
/* 439 */     this.wsdlAnonymousTag = new QName(wsdlNsUri, getWsdlAnonymousLocalName(), prefix);
/*     */ 
/*     */     
/*     */     try {
/* 443 */       this.anonymousEpr = new WSEndpointReference(new ByteArrayInputStream(anonymousEprString.getBytes("UTF-8")), this);
/* 444 */     } catch (XMLStreamException e) {
/* 445 */       throw new Error(e);
/* 446 */     } catch (UnsupportedEncodingException e) {
/* 447 */       throw new Error(e);
/*     */     } 
/* 449 */     this.eprType = eprType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AddressingVersion fromNsUri(String nsUri) {
/* 470 */     if (nsUri.equals(W3C.nsUri)) {
/* 471 */       return W3C;
/*     */     }
/* 473 */     if (nsUri.equals(MEMBER.nsUri)) {
/* 474 */       return MEMBER;
/*     */     }
/* 476 */     return null;
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
/*     */   @Nullable
/*     */   public static AddressingVersion fromBinding(WSBinding binding) {
/* 490 */     if (binding.isFeatureEnabled(AddressingFeature.class)) {
/* 491 */       return W3C;
/*     */     }
/* 493 */     if (binding.isFeatureEnabled(MemberSubmissionAddressingFeature.class)) {
/* 494 */       return MEMBER;
/*     */     }
/* 496 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AddressingVersion fromPort(WSDLPort port) {
/* 506 */     if (port == null) {
/* 507 */       return null;
/*     */     }
/* 509 */     WebServiceFeature wsf = port.getFeature(AddressingFeature.class);
/* 510 */     if (wsf == null) {
/* 511 */       wsf = port.getFeature(MemberSubmissionAddressingFeature.class);
/*     */     }
/* 513 */     if (wsf == null) {
/* 514 */       return null;
/*     */     }
/* 516 */     return fromFeature(wsf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNsUri() {
/* 527 */     return this.nsUri;
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
/*     */   public final String getNoneUri() {
/* 560 */     return this.noneUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getAnonymousUri() {
/* 570 */     return this.anonymousUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultFaultAction() {
/* 579 */     return this.nsUri + "/fault";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AddressingVersion fromFeature(WebServiceFeature af) {
/* 649 */     if (af.getID().equals("http://www.w3.org/2005/08/addressing/module"))
/* 650 */       return W3C; 
/* 651 */     if (af.getID().equals("http://java.sun.com/xml/ns/jaxws/2004/08/addressing")) {
/* 652 */       return MEMBER;
/*     */     }
/* 654 */     return null;
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
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static WebServiceFeature getFeature(String nsUri, boolean enabled, boolean required) {
/* 670 */     if (nsUri.equals(W3C.policyNsUri))
/* 671 */       return new AddressingFeature(enabled, required); 
/* 672 */     if (nsUri.equals(MEMBER.policyNsUri)) {
/* 673 */       return (WebServiceFeature)new MemberSubmissionAddressingFeature(enabled, required);
/*     */     }
/* 675 */     throw new WebServiceException("Unsupported namespace URI: " + nsUri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static AddressingVersion fromSpecClass(Class<? extends EndpointReference> eprClass) {
/* 683 */     if (eprClass == W3CEndpointReference.class)
/* 684 */       return W3C; 
/* 685 */     if (eprClass == MemberSubmissionEndpointReference.class)
/* 686 */       return MEMBER; 
/* 687 */     throw new WebServiceException("Unsupported EPR type: " + eprClass);
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
/*     */   public static boolean isRequired(WebServiceFeature wsf) {
/* 700 */     if (wsf.getID().equals("http://www.w3.org/2005/08/addressing/module"))
/* 701 */       return ((AddressingFeature)wsf).isRequired(); 
/* 702 */     if (wsf.getID().equals("http://java.sun.com/xml/ns/jaxws/2004/08/addressing")) {
/* 703 */       return ((MemberSubmissionAddressingFeature)wsf).isRequired();
/*     */     }
/* 705 */     throw new WebServiceException("WebServiceFeature not an Addressing feature: " + wsf.getID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRequired(WSBinding binding) {
/* 716 */     AddressingFeature af = (AddressingFeature)binding.getFeature(AddressingFeature.class);
/* 717 */     if (af != null)
/* 718 */       return af.isRequired(); 
/* 719 */     MemberSubmissionAddressingFeature msaf = (MemberSubmissionAddressingFeature)binding.getFeature(MemberSubmissionAddressingFeature.class);
/* 720 */     if (msaf != null) {
/* 721 */       return msaf.isRequired();
/*     */     }
/* 723 */     return false;
/*     */   } abstract String getActionMismatchLocalName(); public abstract boolean isReferenceParameter(String paramString);
/*     */   public abstract WsaTubeHelper getWsaHelper(WSDLPort paramWSDLPort, SEIModel paramSEIModel, WSBinding paramWSBinding);
/*     */   abstract String getMapRequiredLocalName();
/*     */   public abstract String getMapRequiredText();
/*     */   abstract String getInvalidAddressLocalName();
/*     */   abstract String getInvalidMapLocalName();
/*     */   public abstract String getInvalidMapText();
/*     */   abstract String getInvalidCardinalityLocalName();
/*     */   abstract String getWsdlAnonymousLocalName();
/*     */   public static boolean isEnabled(WSBinding binding) {
/* 734 */     return (binding.isFeatureEnabled(MemberSubmissionAddressingFeature.class) || binding.isFeatureEnabled(AddressingFeature.class));
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getPrefix();
/*     */ 
/*     */   
/*     */   public abstract String getWsdlPrefix();
/*     */ 
/*     */   
/*     */   public abstract Class<? extends WebServiceFeature> getFeatureClass();
/*     */ 
/*     */   
/*     */   abstract Header createReferenceParameterHeader(XMLStreamBuffer paramXMLStreamBuffer, String paramString1, String paramString2);
/*     */ 
/*     */   
/*     */   abstract String getIsReferenceParameterLocalName();
/*     */ 
/*     */   
/*     */   public static final class EPR
/*     */   {
/*     */     public EPR(Class<? extends EndpointReference> eprClass, String address, String serviceName, String portName, String portTypeName, QName wsdlMetadata, String referenceParameters, String referenceProperties) {
/* 756 */       this.eprClass = eprClass;
/* 757 */       this.address = address;
/* 758 */       this.serviceName = serviceName;
/* 759 */       this.portName = portName;
/* 760 */       this.portTypeName = portTypeName;
/* 761 */       this.referenceParameters = referenceParameters;
/* 762 */       this.referenceProperties = referenceProperties;
/* 763 */       this.wsdlMetadata = wsdlMetadata;
/*     */     }
/*     */     
/*     */     public final Class<? extends EndpointReference> eprClass;
/*     */     public final String address;
/*     */     public final String serviceName;
/*     */     public final String portName;
/*     */     public final String portTypeName;
/*     */     public final String referenceParameters;
/*     */     public final QName wsdlMetadata;
/*     */     public final String referenceProperties;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\addressing\AddressingVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */