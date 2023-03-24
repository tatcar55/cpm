/*    */ package com.sun.xml.rpc.encoding.soap;
/*    */ 
/*    */ import com.sun.xml.rpc.spi.runtime.SOAPConstants;
/*    */ import com.sun.xml.rpc.wsdl.document.soap.SOAPConstants;
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
/*    */ public class SOAPConstants
/*    */   implements SOAPConstants, SOAPConstants
/*    */ {
/*    */   public static final String URI_HTTP = "http://schemas.xmlsoap.org/soap/http/";
/* 43 */   public static final QName QNAME_ENCODING_ARRAY = QNAME_TYPE_ARRAY;
/* 44 */   public static final QName QNAME_ENCODING_ARRAYTYPE = QNAME_ATTR_ARRAY_TYPE;
/* 45 */   public static final QName QNAME_ENCODING_BASE64 = QNAME_TYPE_BASE64;
/* 46 */   public static final QName QNAME_ENVELOPE_ENCODINGSTYLE = new QName("http://schemas.xmlsoap.org/soap/envelope/", "encodingStyle");
/*    */   
/* 48 */   public static final QName QNAME_SOAP_FAULT = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Fault");
/* 49 */   public static final QName FAULT_CODE_CLIENT = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Client");
/* 50 */   public static final QName FAULT_CODE_MUST_UNDERSTAND = new QName("http://schemas.xmlsoap.org/soap/envelope/", "MustUnderstand");
/*    */   
/* 52 */   public static final QName FAULT_CODE_VERSION_MISMATCH = new QName("http://schemas.xmlsoap.org/soap/envelope/", "VersionMismatch");
/* 53 */   public static final QName FAULT_CODE_DATA_ENCODING_UNKNOWN = new QName("http://schemas.xmlsoap.org/soap/envelope/", "DataEncodingUnknown");
/* 54 */   public static final QName FAULT_CODE_PROCEDURE_NOT_PRESENT = new QName("http://schemas.xmlsoap.org/soap/envelope/", "ProcedureNotPresent");
/* 55 */   public static final QName FAULT_CODE_BAD_ARGUMENTS = new QName("http://schemas.xmlsoap.org/soap/envelope/", "BadArguments");
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\SOAPConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */