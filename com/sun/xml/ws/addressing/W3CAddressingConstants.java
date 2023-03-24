/*     */ package com.sun.xml.ws.addressing;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface W3CAddressingConstants
/*     */ {
/*     */   public static final String WSA_NAMESPACE_NAME = "http://www.w3.org/2005/08/addressing";
/*     */   public static final String WSA_NAMESPACE_WSDL_NAME = "http://www.w3.org/2006/05/addressing/wsdl";
/*     */   public static final String WSAW_SERVICENAME_NAME = "ServiceName";
/*     */   public static final String WSAW_INTERFACENAME_NAME = "InterfaceName";
/*     */   public static final String WSAW_ENDPOINTNAME_NAME = "EndpointName";
/*     */   public static final String WSA_REFERENCEPROPERTIES_NAME = "ReferenceParameters";
/*  61 */   public static final QName WSA_REFERENCEPROPERTIES_QNAME = new QName("http://www.w3.org/2005/08/addressing", "ReferenceParameters");
/*     */   
/*     */   public static final String WSA_REFERENCEPARAMETERS_NAME = "ReferenceParameters";
/*  64 */   public static final QName WSA_REFERENCEPARAMETERS_QNAME = new QName("http://www.w3.org/2005/08/addressing", "ReferenceParameters");
/*     */   
/*     */   public static final String WSA_METADATA_NAME = "Metadata";
/*  67 */   public static final QName WSA_METADATA_QNAME = new QName("http://www.w3.org/2005/08/addressing", "Metadata");
/*     */   
/*     */   public static final String WSA_ADDRESS_NAME = "Address";
/*  70 */   public static final QName WSA_ADDRESS_QNAME = new QName("http://www.w3.org/2005/08/addressing", "Address");
/*     */   
/*     */   public static final String WSA_ANONYMOUS_ADDRESS = "http://www.w3.org/2005/08/addressing/anonymous";
/*     */   
/*     */   public static final String WSA_NONE_ADDRESS = "http://www.w3.org/2005/08/addressing/none";
/*     */   
/*     */   public static final String WSA_DEFAULT_FAULT_ACTION = "http://www.w3.org/2005/08/addressing/fault";
/*     */   public static final String WSA_EPR_NAME = "EndpointReference";
/*  78 */   public static final QName WSA_EPR_QNAME = new QName("http://www.w3.org/2005/08/addressing", "EndpointReference");
/*     */   
/*     */   public static final String WSAW_USING_ADDRESSING_NAME = "UsingAddressing";
/*     */   
/*  82 */   public static final QName WSAW_USING_ADDRESSING_QNAME = new QName("http://www.w3.org/2006/05/addressing/wsdl", "UsingAddressing");
/*     */   
/*  84 */   public static final QName INVALID_MAP_QNAME = new QName("http://www.w3.org/2005/08/addressing", "InvalidAddressingHeader");
/*  85 */   public static final QName MAP_REQUIRED_QNAME = new QName("http://www.w3.org/2005/08/addressing", "MessageAddressingHeaderRequired");
/*  86 */   public static final QName DESTINATION_UNREACHABLE_QNAME = new QName("http://www.w3.org/2005/08/addressing", "DestinationUnreachable");
/*  87 */   public static final QName ACTION_NOT_SUPPORTED_QNAME = new QName("http://www.w3.org/2005/08/addressing", "ActionNotSupported");
/*  88 */   public static final QName ENDPOINT_UNAVAILABLE_QNAME = new QName("http://www.w3.org/2005/08/addressing", "EndpointUnavailable");
/*     */   
/*     */   public static final String ACTION_NOT_SUPPORTED_TEXT = "The \"%s\" cannot be processed at the receiver";
/*     */   
/*     */   public static final String DESTINATION_UNREACHABLE_TEXT = "No route can be determined to reach %s";
/*     */   public static final String ENDPOINT_UNAVAILABLE_TEXT = "The endpoint is unable to process the message at this time";
/*     */   public static final String INVALID_MAP_TEXT = "A header representing a Message Addressing Property is not valid and the message cannot be processed";
/*     */   public static final String MAP_REQUIRED_TEXT = "A required header representing a Message Addressing Property is not present";
/*  96 */   public static final QName PROBLEM_ACTION_QNAME = new QName("http://www.w3.org/2005/08/addressing", "ProblemAction");
/*  97 */   public static final QName PROBLEM_HEADER_QNAME_QNAME = new QName("http://www.w3.org/2005/08/addressing", "ProblemHeaderQName");
/*  98 */   public static final QName FAULT_DETAIL_QNAME = new QName("http://www.w3.org/2005/08/addressing", "FaultDetail");
/*     */ 
/*     */   
/* 101 */   public static final QName INVALID_ADDRESS_SUBCODE = new QName("http://www.w3.org/2005/08/addressing", "InvalidAddress", AddressingVersion.W3C.getPrefix());
/*     */ 
/*     */ 
/*     */   
/* 105 */   public static final QName INVALID_EPR = new QName("http://www.w3.org/2005/08/addressing", "InvalidEPR", AddressingVersion.W3C.getPrefix());
/*     */ 
/*     */   
/* 108 */   public static final QName INVALID_CARDINALITY = new QName("http://www.w3.org/2005/08/addressing", "InvalidCardinality", AddressingVersion.W3C.getPrefix());
/*     */ 
/*     */ 
/*     */   
/* 112 */   public static final QName MISSING_ADDRESS_IN_EPR = new QName("http://www.w3.org/2005/08/addressing", "MissingAddressInEPR", AddressingVersion.W3C.getPrefix());
/*     */ 
/*     */ 
/*     */   
/* 116 */   public static final QName DUPLICATE_MESSAGEID = new QName("http://www.w3.org/2005/08/addressing", "DuplicateMessageID", AddressingVersion.W3C.getPrefix());
/*     */ 
/*     */ 
/*     */   
/* 120 */   public static final QName ACTION_MISMATCH = new QName("http://www.w3.org/2005/08/addressing", "ActionMismatch", AddressingVersion.W3C.getPrefix());
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static final QName ONLY_ANONYMOUS_ADDRESS_SUPPORTED = new QName("http://www.w3.org/2005/08/addressing", "OnlyAnonymousAddressSupported", AddressingVersion.W3C.getPrefix());
/*     */ 
/*     */ 
/*     */   
/* 128 */   public static final QName ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED = new QName("http://www.w3.org/2005/08/addressing", "OnlyNonAnonymousAddressSupported", AddressingVersion.W3C.getPrefix());
/*     */   public static final String ANONYMOUS_EPR = "<EndpointReference xmlns=\"http://www.w3.org/2005/08/addressing\">\n    <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>\n</EndpointReference>";
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\W3CAddressingConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */