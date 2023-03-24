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
/*    */ public class SOAPConstants
/*    */ {
/*    */   public static final String URI_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
/*    */   public static final String URI_HTTP = "http://schemas.xmlsoap.org/soap/http";
/*    */   public static final String URI_ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";
/*    */   public static final String NS_WSDL_SOAP = "http://schemas.xmlsoap.org/wsdl/soap/";
/* 57 */   public static final QName QNAME_ENVELOPE_ENCODINGSTYLE = new QName("http://schemas.xmlsoap.org/soap/envelope/", "encodingStyle");
/*    */   
/* 59 */   public static final QName QNAME_SOAP_ENVELOPE = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Envelope");
/* 60 */   public static final QName QNAME_SOAP_HEADER = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Header");
/* 61 */   public static final QName QNAME_MUSTUNDERSTAND = new QName("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand");
/* 62 */   public static final QName QNAME_ROLE = new QName("http://schemas.xmlsoap.org/soap/envelope/", "actor");
/* 63 */   public static final QName QNAME_SOAP_BODY = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Body");
/* 64 */   public static final QName QNAME_SOAP_FAULT = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Fault");
/* 65 */   public static final QName QNAME_SOAP_FAULT_CODE = new QName("", "faultcode");
/* 66 */   public static final QName QNAME_SOAP_FAULT_STRING = new QName("", "faultstring");
/* 67 */   public static final QName QNAME_SOAP_FAULT_ACTOR = new QName("", "faultactor");
/* 68 */   public static final QName QNAME_SOAP_FAULT_DETAIL = new QName("", "detail");
/* 69 */   public static final QName FAULT_CODE_MUST_UNDERSTAND = new QName("http://schemas.xmlsoap.org/soap/envelope/", "MustUnderstand");
/*    */   
/* 71 */   public static final QName FAULT_CODE_VERSION_MISMATCH = new QName("http://schemas.xmlsoap.org/soap/envelope/", "VersionMismatch");
/* 72 */   public static final QName FAULT_CODE_DATA_ENCODING_UNKNOWN = new QName("http://schemas.xmlsoap.org/soap/envelope/", "DataEncodingUnknown");
/* 73 */   public static final QName FAULT_CODE_PROCEDURE_NOT_PRESENT = new QName("http://schemas.xmlsoap.org/soap/envelope/", "ProcedureNotPresent");
/* 74 */   public static final QName FAULT_CODE_BAD_ARGUMENTS = new QName("http://schemas.xmlsoap.org/soap/envelope/", "BadArguments");
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\soap\SOAPConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */