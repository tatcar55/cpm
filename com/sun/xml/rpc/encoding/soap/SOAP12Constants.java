/*    */ package com.sun.xml.rpc.encoding.soap;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.document.soap.SOAP12Constants;
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
/*    */ public class SOAP12Constants
/*    */   implements SOAP12Constants
/*    */ {
/*    */   public static final String URI_ENVELOPE = "http://www.w3.org/2002/06/soap-envelope";
/*    */   public static final String URI_ENCODING = "http://www.w3.org/2002/06/soap-encoding";
/*    */   public static final String URI_HTTP = "http://www.w3.org/2002/06/soap/bindings/HTTP/";
/*    */   public static final String URI_SOAP_RPC = "http://www.w3.org/2002/06/soap-rpc";
/* 41 */   public static final QName QNAME_ENCODING_ARRAY = QNAME_TYPE_ARRAY;
/* 42 */   public static final QName QNAME_ENCODING_ARRAYTYPE = QNAME_ATTR_ARRAY_TYPE;
/* 43 */   public static final QName QNAME_ENCODING_ARRAYSIZE = QNAME_ATTR_ARRAY_SIZE;
/* 44 */   public static final QName QNAME_ENCODING_ITEMTYPE = QNAME_ATTR_ITEM_TYPE;
/* 45 */   public static final QName QNAME_ENCODING_BASE64 = QNAME_TYPE_BASE64;
/*    */   
/* 47 */   public static final QName QNAME_SOAP_RPC = new QName("http://www.w3.org/2002/06/soap-rpc", "rpc");
/* 48 */   public static final QName QNAME_SOAP_RESULT = new QName("http://www.w3.org/2002/06/soap-rpc", "result");
/* 49 */   public static final QName QNAME_ENVELOPE_ENCODINGSTYLE = new QName("http://www.w3.org/2002/06/soap-envelope", "encodingStyle");
/* 50 */   public static final QName QNAME_SOAP_FAULT = new QName("http://www.w3.org/2002/06/soap-envelope", "Fault");
/* 51 */   public static final QName QNAME_MUSTUNDERSTAND = new QName("http://www.w3.org/2002/06/soap-envelope", "mustUnderstand");
/*    */   
/* 53 */   public static final QName FAULT_CODE_CLIENT = new QName("http://www.w3.org/2002/06/soap-envelope", "Sender");
/* 54 */   public static final QName FAULT_CODE_MUST_UNDERSTAND = new QName("http://www.w3.org/2002/06/soap-envelope", "MustUnderstand");
/* 55 */   public static final QName FAULT_CODE_MISUNDERSTOOD = new QName("http://www.w3.org/2002/06/soap-envelope", "Misunderstood");
/* 56 */   public static final QName FAULT_CODE_SERVER = new QName("http://www.w3.org/2002/06/soap-envelope", "Receiver");
/* 57 */   public static final QName FAULT_CODE_VERSION_MISMATCH = new QName("http://www.w3.org/2002/06/soap-envelope", "VersionMismatch");
/* 58 */   public static final QName FAULT_CODE_DATA_ENCODING_UNKNOWN = new QName("http://www.w3.org/2002/06/soap-envelope", "DataEncodingUnknown");
/* 59 */   public static final QName FAULT_CODE_PROCEDURE_NOT_PRESENT = new QName("http://www.w3.org/2002/06/soap-envelope", "ProcedureNotPresent");
/* 60 */   public static final QName FAULT_CODE_BAD_ARGUMENTS = new QName("http://www.w3.org/2002/06/soap-envelope", "BadArguments");
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\SOAP12Constants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */