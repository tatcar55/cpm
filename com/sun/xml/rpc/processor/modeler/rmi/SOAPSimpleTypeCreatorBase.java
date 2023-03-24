/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.soap.SOAPWSDLConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.BuiltInTypes;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public abstract class SOAPSimpleTypeCreatorBase
/*     */   extends JavaSimpleTypeCreator
/*     */   implements RmiConstants
/*     */ {
/*     */   public SOAPSimpleType XSD_BOOLEAN_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_BOXED_BOOLEAN_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_BYTE_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_BYTE_ARRAY_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_BOXED_BYTE_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_BOXED_BYTE_ARRAY_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_DOUBLE_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_BOXED_DOUBLE_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_FLOAT_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_BOXED_FLOAT_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_INT_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_BOXED_INTEGER_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_INTEGER_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_LONG_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_BOXED_LONG_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_SHORT_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_BOXED_SHORT_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_DECIMAL_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_DATE_TIME_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_DATE_TIME_CALENDAR_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_STRING_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_QNAME_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_VOID_SOAPTYPE;
/*     */   public SOAPAnyType XSD_ANYTYPE_SOAPTYPE;
/*     */   public SOAPSimpleType XSD_ANY_URI_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_BOXED_BOOLEAN_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_BOXED_BYTE_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_BYTE_ARRAY_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_BOXED_BYTE_ARRAY_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_BOXED_DOUBLE_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_BOXED_FLOAT_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_BOXED_INTEGER_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_BOXED_LONG_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_BOXED_SHORT_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_DECIMAL_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_DATE_TIME_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_STRING_SOAPTYPE;
/*     */   public SOAPSimpleType SOAP_QNAME_SOAPTYPE;
/*     */   public SOAPSimpleType COLLECTION_SOAPTYPE;
/*     */   public SOAPSimpleType LIST_SOAPTYPE;
/*     */   public SOAPSimpleType SET_SOAPTYPE;
/*     */   public SOAPSimpleType VECTOR_SOAPTYPE;
/*     */   public SOAPSimpleType STACK_SOAPTYPE;
/*     */   public SOAPSimpleType LINKED_LIST_SOAPTYPE;
/*     */   public SOAPSimpleType ARRAY_LIST_SOAPTYPE;
/*     */   public SOAPSimpleType HASH_SET_SOAPTYPE;
/*     */   public SOAPSimpleType TREE_SET_SOAPTYPE;
/*     */   public SOAPSimpleType MAP_SOAPTYPE;
/*     */   public SOAPSimpleType HASH_MAP_SOAPTYPE;
/*     */   public SOAPSimpleType TREE_MAP_SOAPTYPE;
/*     */   public SOAPSimpleType HASHTABLE_SOAPTYPE;
/*     */   public SOAPSimpleType PROPERTIES_SOAPTYPE;
/*     */   public SOAPSimpleType JAX_RPC_MAP_ENTRY_SOAPTYPE;
/*     */   public SOAPSimpleType IMAGE_SOAPTYPE;
/*     */   public SOAPSimpleType MIME_MULTIPART_SOAPTYPE;
/*     */   public SOAPSimpleType SOURCE_SOAPTYPE;
/*     */   public SOAPSimpleType DATA_HANDLER_SOAPTYPE;
/*     */   protected boolean useStrictMode = false;
/*     */   
/*     */   public SOAPSimpleTypeCreatorBase() {
/* 122 */     initSOAPTypes(SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   public SOAPSimpleTypeCreatorBase(boolean useStrictMode) {
/* 126 */     this(useStrictMode, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPSimpleTypeCreatorBase(boolean useStrictMode, SOAPVersion version) {
/* 132 */     this.useStrictMode = useStrictMode;
/* 133 */     initSOAPTypes(version);
/*     */   }
/*     */   
/*     */   protected void initSOAPTypes(SOAPVersion version) {
/* 137 */     SOAPWSDLConstants soapConstants = SOAPConstantsFactory.getSOAPWSDLConstants(version);
/*     */ 
/*     */     
/* 140 */     this.XSD_BOOLEAN_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.BOOLEAN, this.BOOLEAN_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     this.XSD_BOXED_BOOLEAN_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.BOOLEAN, this.BOXED_BOOLEAN_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     this.XSD_BYTE_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.BYTE, this.BYTE_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     this.XSD_BYTE_ARRAY_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.BASE64_BINARY, this.BYTE_ARRAY_JAVATYPE, true, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     this.XSD_BOXED_BYTE_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.BYTE, this.BOXED_BYTE_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     this.XSD_BOXED_BYTE_ARRAY_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.BASE64_BINARY, this.BOXED_BYTE_ARRAY_JAVATYPE, true, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     this.XSD_DOUBLE_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.DOUBLE, this.DOUBLE_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     this.XSD_BOXED_DOUBLE_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.DOUBLE, this.BOXED_DOUBLE_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     this.XSD_FLOAT_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.FLOAT, this.FLOAT_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     this.XSD_BOXED_FLOAT_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.FLOAT, this.BOXED_FLOAT_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     this.XSD_INT_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.INT, this.INT_JAVATYPE, false, version);
/*     */     
/* 202 */     this.XSD_BOXED_INTEGER_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.INT, this.BOXED_INTEGER_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     this.XSD_INTEGER_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.INTEGER, this.BIG_INTEGER_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     this.XSD_LONG_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.LONG, this.LONG_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 220 */     this.XSD_BOXED_LONG_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.LONG, this.BOXED_LONG_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     this.XSD_SHORT_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.SHORT, this.SHORT_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     this.XSD_BOXED_SHORT_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.SHORT, this.BOXED_SHORT_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     this.XSD_DECIMAL_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.DECIMAL, this.DECIMAL_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     this.XSD_DATE_TIME_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.DATE_TIME, this.DATE_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     this.XSD_DATE_TIME_CALENDAR_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.DATE_TIME, this.CALENDAR_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 256 */     this.XSD_STRING_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.STRING, this.STRING_JAVATYPE, true, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     this.XSD_QNAME_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.QNAME, this.QNAME_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     this.XSD_VOID_SOAPTYPE = new SOAPSimpleType(null, this.VOID_JAVATYPE, false, version);
/*     */     
/* 270 */     this.XSD_ANYTYPE_SOAPTYPE = new SOAPAnyType(SchemaConstants.QNAME_TYPE_URTYPE, (JavaType)this.OBJECT_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     this.XSD_ANY_URI_SOAPTYPE = new SOAPSimpleType(BuiltInTypes.ANY_URI, this.URI_JAVATYPE, false, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 283 */     this.SOAP_BOXED_BOOLEAN_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeBoolean(), this.BOXED_BOOLEAN_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 288 */     this.SOAP_BOXED_BYTE_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeByte(), this.BOXED_BYTE_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 293 */     this.SOAP_BYTE_ARRAY_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeBase64(), this.BYTE_ARRAY_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     this.SOAP_BOXED_BYTE_ARRAY_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeBase64(), this.BOXED_BYTE_ARRAY_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     this.SOAP_BOXED_DOUBLE_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeDouble(), this.BOXED_DOUBLE_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     this.SOAP_BOXED_FLOAT_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeFloat(), this.BOXED_FLOAT_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     this.SOAP_BOXED_INTEGER_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeInt(), this.BOXED_INTEGER_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 318 */     this.SOAP_BOXED_LONG_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeLong(), this.BOXED_LONG_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 323 */     this.SOAP_BOXED_SHORT_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeShort(), this.BOXED_SHORT_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     this.SOAP_DECIMAL_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeDecimal(), this.DECIMAL_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     this.SOAP_DATE_TIME_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeDateTime(), this.DATE_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     this.SOAP_STRING_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeString(), this.STRING_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 343 */     this.SOAP_QNAME_SOAPTYPE = new SOAPSimpleType(soapConstants.getQNameTypeQName(), this.QNAME_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 350 */     this.COLLECTION_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_COLLECTION, this.COLLECTION_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 355 */     this.LIST_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_LIST, this.LIST_JAVATYPE, version);
/*     */     
/* 357 */     this.SET_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_SET, this.SET_JAVATYPE, version);
/*     */     
/* 359 */     this.VECTOR_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_VECTOR, this.VECTOR_JAVATYPE, version);
/*     */     
/* 361 */     this.STACK_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_STACK, this.STACK_JAVATYPE, version);
/*     */     
/* 363 */     this.LINKED_LIST_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_LINKED_LIST, this.LINKED_LIST_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     this.ARRAY_LIST_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_ARRAY_LIST, this.ARRAY_LIST_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 373 */     this.HASH_SET_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_HASH_SET, this.HASH_SET_JAVATYPE, version);
/*     */     
/* 375 */     this.TREE_SET_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_TREE_SET, this.TREE_SET_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */     
/* 379 */     this.MAP_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_MAP, this.MAP_JAVATYPE, version);
/*     */     
/* 381 */     this.HASH_MAP_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_HASH_MAP, this.HASH_MAP_JAVATYPE, version);
/*     */     
/* 383 */     this.TREE_MAP_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_TREE_MAP, this.TREE_MAP_JAVATYPE, version);
/*     */     
/* 385 */     this.HASHTABLE_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_HASHTABLE, this.HASHTABLE_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 390 */     this.PROPERTIES_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_PROPERTIES, this.PROPERTIES_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 396 */     this.JAX_RPC_MAP_ENTRY_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_JAX_RPC_MAP_ENTRY, this.JAX_RPC_MAP_ENTRY_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 403 */     this.IMAGE_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_IMAGE, this.IMAGE_JAVATYPE, version);
/*     */     
/* 405 */     this.MIME_MULTIPART_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_MIME_MULTIPART, this.MIME_MULTIPART_JAVATYPE, version);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 410 */     this.SOURCE_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_SOURCE, this.SOURCE_JAVATYPE, version);
/*     */     
/* 412 */     this.DATA_HANDLER_SOAPTYPE = new SOAPSimpleType(QNAME_TYPE_DATA_HANDLER, this.DATA_HANDLER_JAVATYPE, version);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void initializeTypeMap(Map paramMap);
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeJavaToXmlTypeMap(Map<Class<?>, QName> typeMap) {
/* 422 */     Map<Object, Object> typeNameToDescription = new HashMap<Object, Object>();
/* 423 */     initializeTypeMap(typeNameToDescription);
/*     */     
/* 425 */     Iterator<String> eachClassName = typeNameToDescription.keySet().iterator();
/* 426 */     while (eachClassName.hasNext()) {
/*     */       
/* 428 */       String className = eachClassName.next();
/* 429 */       SOAPType typeDescription = (SOAPType)typeNameToDescription.get(className);
/*     */ 
/*     */       
/* 432 */       Class<?> javaType = classForName(className);
/* 433 */       if (javaType != null) {
/* 434 */         typeMap.put(javaType, typeDescription.getName());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Class classForName(String name) {
/* 440 */     Class<?> javaType = null;
/*     */     try {
/* 442 */       javaType = Class.forName(name);
/* 443 */     } catch (Exception e) {}
/*     */     
/* 445 */     if (javaType == null) {
/* 446 */       if ("boolean".equals(name))
/* 447 */         return boolean.class; 
/* 448 */       if ("byte".equals(name))
/* 449 */         return byte.class; 
/* 450 */       if ("short".equals(name))
/* 451 */         return short.class; 
/* 452 */       if ("int".equals(name))
/* 453 */         return int.class; 
/* 454 */       if ("long".equals(name))
/* 455 */         return long.class; 
/* 456 */       if ("char".equals(name))
/* 457 */         return char.class; 
/* 458 */       if ("float".equals(name))
/* 459 */         return float.class; 
/* 460 */       if ("double".equals(name))
/* 461 */         return double.class; 
/*     */     } 
/* 463 */     return javaType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\SOAPSimpleTypeCreatorBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */