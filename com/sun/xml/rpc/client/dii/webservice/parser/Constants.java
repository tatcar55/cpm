/*    */ package com.sun.xml.rpc.client.dii.webservice.parser;
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
/*    */ public interface Constants
/*    */ {
/*    */   public static final String NS_NAME = "http://java.sun.com/xml/ns/jax-rpc/ri/client";
/* 34 */   public static final QName QNAME_CLIENT = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/client", "webServicesClient");
/*    */   
/* 36 */   public static final QName QNAME_SERVICE = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/client", "service");
/*    */   
/* 38 */   public static final QName QNAME_PROPERTY = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/client", "property");
/*    */   public static final String ATTR_WSDL_LOCATION = "wsdlLocation";
/*    */   public static final String ATTR_MODEL = "model";
/*    */   public static final String ATTR_NAME = "name";
/*    */   public static final String ATTR_VALUE = "value";
/*    */   public static final String ATTRVALUE_VERSION_1_0 = "1.0";
/*    */   public static final String ATTRVALUE_CLIENT = "client";
/*    */   public static final String ATTRVALUE_SERVER = "server";
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\webservice\parser\Constants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */