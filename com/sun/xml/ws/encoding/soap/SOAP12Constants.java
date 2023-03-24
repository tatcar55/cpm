/*    */ package com.sun.xml.ws.encoding.soap;
/*    */ 
/*    */ import javax.xml.namespace.QName;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SOAP12Constants
/*    */ {
/*    */   public static final String URI_ENVELOPE = "http://www.w3.org/2003/05/soap-envelope";
/*    */   public static final String URI_ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";
/*    */   public static final String URI_HTTP = "http://www.w3.org/2003/05/soap/bindings/HTTP/";
/*    */   public static final String URI_SOAP_RPC = "http://www.w3.org/2002/06/soap-rpc";
/* 59 */   public static final QName QNAME_SOAP_RPC = new QName("http://www.w3.org/2002/06/soap-rpc", "rpc");
/* 60 */   public static final QName QNAME_SOAP_RESULT = new QName("http://www.w3.org/2002/06/soap-rpc", "result");
/*    */   
/* 62 */   public static final QName QNAME_SOAP_ENVELOPE = new QName("http://www.w3.org/2003/05/soap-envelope", "Envelope");
/* 63 */   public static final QName QNAME_SOAP_BODY = new QName("http://www.w3.org/2003/05/soap-envelope", "Body");
/* 64 */   public static final QName QNAME_SOAP_HEADER = new QName("http://www.w3.org/2003/05/soap-envelope", "Header");
/* 65 */   public static final QName QNAME_ENVELOPE_ENCODINGSTYLE = new QName("http://www.w3.org/2003/05/soap-envelope", "encodingStyle");
/* 66 */   public static final QName QNAME_SOAP_FAULT = new QName("http://www.w3.org/2003/05/soap-envelope", "Fault");
/* 67 */   public static final QName QNAME_MUSTUNDERSTAND = new QName("http://www.w3.org/2003/05/soap-envelope", "mustUnderstand");
/* 68 */   public static final QName QNAME_ROLE = new QName("http://www.w3.org/2003/05/soap-envelope", "role");
/*    */   
/* 70 */   public static final QName QNAME_NOT_UNDERSTOOD = new QName("http://www.w3.org/2003/05/soap-envelope", "NotUnderstood");
/*    */ 
/*    */   
/* 73 */   public static final QName QNAME_FAULT_CODE = new QName("http://www.w3.org/2003/05/soap-envelope", "Code");
/* 74 */   public static final QName QNAME_FAULT_SUBCODE = new QName("http://www.w3.org/2003/05/soap-envelope", "Subcode");
/* 75 */   public static final QName QNAME_FAULT_VALUE = new QName("http://www.w3.org/2003/05/soap-envelope", "Value");
/* 76 */   public static final QName QNAME_FAULT_REASON = new QName("http://www.w3.org/2003/05/soap-envelope", "Reason");
/* 77 */   public static final QName QNAME_FAULT_NODE = new QName("http://www.w3.org/2003/05/soap-envelope", "Node");
/* 78 */   public static final QName QNAME_FAULT_ROLE = new QName("http://www.w3.org/2003/05/soap-envelope", "Role");
/* 79 */   public static final QName QNAME_FAULT_DETAIL = new QName("http://www.w3.org/2003/05/soap-envelope", "Detail");
/* 80 */   public static final QName QNAME_FAULT_REASON_TEXT = new QName("http://www.w3.org/2003/05/soap-envelope", "Text");
/* 81 */   public static final QName QNAME_UPGRADE = new QName("http://www.w3.org/2003/05/soap-envelope", "Upgrade");
/* 82 */   public static final QName QNAME_UPGRADE_SUPPORTED_ENVELOPE = new QName("http://www.w3.org/2003/05/soap-envelope", "SupportedEnvelope");
/*    */ 
/*    */ 
/*    */   
/* 86 */   public static final QName FAULT_CODE_MUST_UNDERSTAND = new QName("http://www.w3.org/2003/05/soap-envelope", "MustUnderstand");
/* 87 */   public static final QName FAULT_CODE_MISUNDERSTOOD = new QName("http://www.w3.org/2003/05/soap-envelope", "Misunderstood");
/* 88 */   public static final QName FAULT_CODE_VERSION_MISMATCH = new QName("http://www.w3.org/2003/05/soap-envelope", "VersionMismatch");
/* 89 */   public static final QName FAULT_CODE_DATA_ENCODING_UNKNOWN = new QName("http://www.w3.org/2003/05/soap-envelope", "DataEncodingUnknown");
/* 90 */   public static final QName FAULT_CODE_PROCEDURE_NOT_PRESENT = new QName("http://www.w3.org/2003/05/soap-envelope", "ProcedureNotPresent");
/* 91 */   public static final QName FAULT_CODE_BAD_ARGUMENTS = new QName("http://www.w3.org/2003/05/soap-envelope", "BadArguments");
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\soap\SOAP12Constants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */