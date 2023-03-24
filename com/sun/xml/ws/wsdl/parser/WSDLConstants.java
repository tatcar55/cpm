/*    */ package com.sun.xml.ws.wsdl.parser;
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
/*    */ public interface WSDLConstants
/*    */ {
/*    */   public static final String PREFIX_NS_WSDL = "wsdl";
/*    */   public static final String NS_XMLNS = "http://www.w3.org/2001/XMLSchema";
/*    */   public static final String NS_WSDL = "http://schemas.xmlsoap.org/wsdl/";
/*    */   public static final String NS_SOAP11_HTTP_BINDING = "http://schemas.xmlsoap.org/soap/http";
/* 58 */   public static final QName QNAME_SCHEMA = new QName("http://www.w3.org/2001/XMLSchema", "schema");
/*    */ 
/*    */   
/* 61 */   public static final QName QNAME_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/", "binding");
/* 62 */   public static final QName QNAME_DEFINITIONS = new QName("http://schemas.xmlsoap.org/wsdl/", "definitions");
/* 63 */   public static final QName QNAME_DOCUMENTATION = new QName("http://schemas.xmlsoap.org/wsdl/", "documentation");
/* 64 */   public static final QName NS_SOAP_BINDING_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "address");
/* 65 */   public static final QName NS_SOAP_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "binding");
/* 66 */   public static final QName NS_SOAP12_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "binding");
/* 67 */   public static final QName NS_SOAP12_BINDING_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "address");
/*    */ 
/*    */   
/* 70 */   public static final QName QNAME_IMPORT = new QName("http://schemas.xmlsoap.org/wsdl/", "import");
/*    */ 
/*    */   
/* 73 */   public static final QName QNAME_MESSAGE = new QName("http://schemas.xmlsoap.org/wsdl/", "message");
/* 74 */   public static final QName QNAME_PART = new QName("http://schemas.xmlsoap.org/wsdl/", "part");
/* 75 */   public static final QName QNAME_OPERATION = new QName("http://schemas.xmlsoap.org/wsdl/", "operation");
/* 76 */   public static final QName QNAME_INPUT = new QName("http://schemas.xmlsoap.org/wsdl/", "input");
/* 77 */   public static final QName QNAME_OUTPUT = new QName("http://schemas.xmlsoap.org/wsdl/", "output");
/*    */ 
/*    */ 
/*    */   
/* 81 */   public static final QName QNAME_PORT = new QName("http://schemas.xmlsoap.org/wsdl/", "port");
/* 82 */   public static final QName QNAME_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/", "address");
/* 83 */   public static final QName QNAME_PORT_TYPE = new QName("http://schemas.xmlsoap.org/wsdl/", "portType");
/* 84 */   public static final QName QNAME_FAULT = new QName("http://schemas.xmlsoap.org/wsdl/", "fault");
/* 85 */   public static final QName QNAME_SERVICE = new QName("http://schemas.xmlsoap.org/wsdl/", "service");
/* 86 */   public static final QName QNAME_TYPES = new QName("http://schemas.xmlsoap.org/wsdl/", "types");
/*    */   public static final String ATTR_TRANSPORT = "transport";
/*    */   public static final String ATTR_LOCATION = "location";
/*    */   public static final String ATTR_NAME = "name";
/*    */   public static final String ATTR_TNS = "targetNamespace";
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\WSDLConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */