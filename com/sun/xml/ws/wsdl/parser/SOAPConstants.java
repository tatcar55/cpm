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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface SOAPConstants
/*    */ {
/*    */   public static final String URI_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
/*    */   public static final String URI_ENVELOPE12 = "http://www.w3.org/2003/05/soap-envelope";
/*    */   public static final String NS_WSDL_SOAP = "http://schemas.xmlsoap.org/wsdl/soap/";
/*    */   public static final String NS_WSDL_SOAP12 = "http://schemas.xmlsoap.org/wsdl/soap12/";
/*    */   public static final String NS_SOAP_ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";
/*    */   public static final String URI_SOAP_TRANSPORT_HTTP = "http://schemas.xmlsoap.org/soap/http";
/* 67 */   public static final QName QNAME_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "address");
/*    */   
/* 69 */   public static final QName QNAME_SOAP12ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "address");
/*    */   
/* 71 */   public static final QName QNAME_BINDING = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "binding");
/*    */   
/* 73 */   public static final QName QNAME_BODY = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "body");
/* 74 */   public static final QName QNAME_SOAP12BODY = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "body");
/* 75 */   public static final QName QNAME_FAULT = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "fault");
/* 76 */   public static final QName QNAME_HEADER = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "header");
/* 77 */   public static final QName QNAME_SOAP12HEADER = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "header");
/* 78 */   public static final QName QNAME_HEADERFAULT = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "headerfault");
/*    */   
/* 80 */   public static final QName QNAME_OPERATION = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "operation");
/*    */   
/* 82 */   public static final QName QNAME_SOAP12OPERATION = new QName("http://schemas.xmlsoap.org/wsdl/soap12/", "operation");
/*    */   
/* 84 */   public static final QName QNAME_MUSTUNDERSTAND = new QName("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand");
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\SOAPConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */