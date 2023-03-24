/*    */ package com.sun.xml.rpc.processor.config.parser;
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
/*    */ public interface Constants
/*    */ {
/*    */   public static final String NS_NAME = "http://java.sun.com/xml/ns/jax-rpc/ri/config";
/* 39 */   public static final QName QNAME_CONFIGURATION = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "configuration");
/*    */   
/* 41 */   public static final QName QNAME_INTERFACE = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "interface");
/* 42 */   public static final QName QNAME_MODELFILE = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "modelfile");
/* 43 */   public static final QName QNAME_SERVICE = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "service");
/* 44 */   public static final QName QNAME_WSDL = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "wsdl");
/* 45 */   public static final QName QNAME_J2EE_MAPPING_FILE = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "j2eeMappingFile");
/*    */   
/* 47 */   public static final QName QNAME_NO_METADATA = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "noMetadata");
/*    */   
/* 49 */   public static final QName QNAME_TYPE_MAPPING_REGISTRY = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "typeMappingRegistry");
/*    */   
/* 51 */   public static final QName QNAME_TYPE_MAPPING = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "typeMapping");
/*    */   
/* 53 */   public static final QName QNAME_ENTRY = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "entry");
/* 54 */   public static final QName QNAME_HANDLER_CHAINS = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "handlerChains");
/*    */   
/* 56 */   public static final QName QNAME_CHAIN = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "chain");
/* 57 */   public static final QName QNAME_HANDLER = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "handler");
/* 58 */   public static final QName QNAME_PROPERTY = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "property");
/* 59 */   public static final QName QNAME_NAMESPACE_MAPPING_REGISTRY = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "namespaceMappingRegistry");
/*    */   
/* 61 */   public static final QName QNAME_NAMESPACE_MAPPING = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "namespaceMapping");
/*    */   
/* 63 */   public static final QName QNAME_IMPORT = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "import");
/* 64 */   public static final QName QNAME_SCHEMA = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "schema");
/* 65 */   public static final QName QNAME_ADDITIONAL_TYPES = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "additionalTypes");
/*    */   
/* 67 */   public static final QName QNAME_CLASS = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/config", "class");
/*    */   public static final String ATTR_NAME = "name";
/*    */   public static final String ATTR_VALUE = "value";
/*    */   public static final String ATTR_CLASS_NAME = "className";
/*    */   public static final String ATTR_DESERIALIZER_FACTORY = "deserializerFactory";
/*    */   public static final String ATTR_ENCODING = "encoding";
/*    */   public static final String ATTR_ENCODING_STYLE = "encodingStyle";
/*    */   public static final String ATTR_JAVA_TYPE = "javaType";
/*    */   public static final String ATTR_LOCATION = "location";
/*    */   public static final String ATTR_PACKAGE_NAME = "packageName";
/*    */   public static final String ATTR_SCHEMA_TYPE = "schemaType";
/*    */   public static final String ATTR_SERIALIZER_FACTORY = "serializerFactory";
/*    */   public static final String ATTR_SERVANT_NAME = "servantName";
/*    */   public static final String ATTR_SOAP_ACTION = "soapAction";
/*    */   public static final String ATTR_SOAP_ACTION_BASE = "soapActionBase";
/*    */   public static final String ATTR_TARGET_NAMESPACE = "targetNamespace";
/*    */   public static final String ATTR_TYPE_NAMESPACE = "typeNamespace";
/*    */   public static final String ATTR_ROLES = "roles";
/*    */   public static final String ATTR_HEADERS = "headers";
/*    */   public static final String ATTR_RUN_AT = "runAt";
/*    */   public static final String ATTR_NAMESPACE = "namespace";
/*    */   public static final String ATTR_VERSION = "version";
/*    */   public static final String ATTR_INTERFACE_NAME = "interfaceName";
/*    */   public static final String ATTR_SERVICE_INTERFACE_NAME = "serviceInterfaceName";
/*    */   public static final String ATTR_SERVICE_NAME = "serviceName";
/*    */   public static final String ATTR_PORT_NAME = "portName";
/*    */   public static final String ATTR_SOAP_VERSION = "soapVersion";
/*    */   public static final String ATTR_WSDL_LOCATION = "wsdlLocation";
/*    */   public static final String ATTRVALUE_VERSION_1_0 = "1.0";
/*    */   public static final String ATTRVALUE_CLIENT = "client";
/*    */   public static final String ATTRVALUE_SERVER = "server";
/*    */   public static final String ATTRVALUE_SOAP_1_1 = "1.1";
/*    */   public static final String ATTRVALUE_SOAP_1_2 = "1.2";
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\Constants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */