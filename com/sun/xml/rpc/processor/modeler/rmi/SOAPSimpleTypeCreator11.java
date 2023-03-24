/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import java.util.Map;
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
/*     */ public class SOAPSimpleTypeCreator11
/*     */   extends SOAPSimpleTypeCreatorBase
/*     */ {
/*     */   public SOAPSimpleTypeCreator11() {}
/*     */   
/*     */   public SOAPSimpleTypeCreator11(boolean useStrictMode) {
/*  58 */     super(useStrictMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPSimpleTypeCreator11(boolean useStrictMode, SOAPVersion version) {
/*  67 */     super(useStrictMode, version);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeTypeMap(Map<String, SOAPSimpleType> typeMap) {
/*  75 */     typeMap.put("java.lang.Boolean", this.SOAP_BOXED_BOOLEAN_SOAPTYPE);
/*  76 */     typeMap.put("boolean", this.XSD_BOOLEAN_SOAPTYPE);
/*  77 */     typeMap.put("java.lang.Byte", this.SOAP_BOXED_BYTE_SOAPTYPE);
/*     */ 
/*     */ 
/*     */     
/*  81 */     typeMap.put("byte", this.XSD_BYTE_SOAPTYPE);
/*  82 */     typeMap.put("byte[]", this.XSD_BYTE_ARRAY_SOAPTYPE);
/*  83 */     typeMap.put("java.lang.Double", this.SOAP_BOXED_DOUBLE_SOAPTYPE);
/*  84 */     typeMap.put("double", this.XSD_DOUBLE_SOAPTYPE);
/*  85 */     typeMap.put("java.lang.Float", this.SOAP_BOXED_FLOAT_SOAPTYPE);
/*  86 */     typeMap.put("float", this.XSD_FLOAT_SOAPTYPE);
/*  87 */     typeMap.put("java.lang.Integer", this.SOAP_BOXED_INTEGER_SOAPTYPE);
/*  88 */     typeMap.put("int", this.XSD_INT_SOAPTYPE);
/*  89 */     typeMap.put("java.lang.Long", this.SOAP_BOXED_LONG_SOAPTYPE);
/*  90 */     typeMap.put("long", this.XSD_LONG_SOAPTYPE);
/*  91 */     typeMap.put("java.lang.Short", this.SOAP_BOXED_SHORT_SOAPTYPE);
/*  92 */     typeMap.put("short", this.XSD_SHORT_SOAPTYPE);
/*  93 */     typeMap.put("java.lang.String", this.XSD_STRING_SOAPTYPE);
/*  94 */     typeMap.put("java.math.BigDecimal", this.XSD_DECIMAL_SOAPTYPE);
/*  95 */     typeMap.put("java.math.BigInteger", this.XSD_INTEGER_SOAPTYPE);
/*  96 */     typeMap.put("java.util.Date", this.XSD_DATE_TIME_SOAPTYPE);
/*  97 */     typeMap.put("java.util.Calendar", this.XSD_DATE_TIME_CALENDAR_SOAPTYPE);
/*  98 */     typeMap.put("javax.xml.namespace.QName", this.XSD_QNAME_SOAPTYPE);
/*  99 */     typeMap.put("void", this.XSD_VOID_SOAPTYPE);
/*     */     
/* 101 */     if (VersionUtil.isJavaVersionGreaterThan1_3()) {
/* 102 */       typeMap.put("java.net.URI", this.XSD_ANY_URI_SOAPTYPE);
/*     */     } else {
/* 104 */       typeMap.put("java.lang.String", this.XSD_ANY_URI_SOAPTYPE);
/*     */     } 
/* 106 */     if (!this.useStrictMode) {
/* 107 */       typeMap.put("java.lang.Object", this.XSD_ANYTYPE_SOAPTYPE);
/*     */ 
/*     */       
/* 110 */       typeMap.put("java.util.Collection", this.COLLECTION_SOAPTYPE);
/* 111 */       typeMap.put("java.util.List", this.LIST_SOAPTYPE);
/* 112 */       typeMap.put("java.util.Set", this.SET_SOAPTYPE);
/* 113 */       typeMap.put("java.util.Vector", this.VECTOR_SOAPTYPE);
/* 114 */       typeMap.put("java.util.Stack", this.STACK_SOAPTYPE);
/* 115 */       typeMap.put("java.util.LinkedList", this.LINKED_LIST_SOAPTYPE);
/* 116 */       typeMap.put("java.util.ArrayList", this.ARRAY_LIST_SOAPTYPE);
/* 117 */       typeMap.put("java.util.HashSet", this.HASH_SET_SOAPTYPE);
/* 118 */       typeMap.put("java.util.TreeSet", this.TREE_SET_SOAPTYPE);
/*     */ 
/*     */       
/* 121 */       typeMap.put("java.util.Map", this.MAP_SOAPTYPE);
/* 122 */       typeMap.put("java.util.HashMap", this.HASH_MAP_SOAPTYPE);
/* 123 */       typeMap.put("java.util.TreeMap", this.TREE_MAP_SOAPTYPE);
/* 124 */       typeMap.put("java.util.Hashtable", this.HASHTABLE_SOAPTYPE);
/* 125 */       typeMap.put("java.util.Properties", this.PROPERTIES_SOAPTYPE);
/*     */     } 
/*     */ 
/*     */     
/* 129 */     typeMap.put("java.awt.Image", this.IMAGE_SOAPTYPE);
/* 130 */     typeMap.put("javax.mail.internet.MimeMultipart", this.MIME_MULTIPART_SOAPTYPE);
/* 131 */     typeMap.put("javax.xml.transform.Source", this.SOURCE_SOAPTYPE);
/* 132 */     typeMap.put("javax.activation.DataHandler", this.DATA_HANDLER_SOAPTYPE);
/* 133 */     typeMap.put("com.sun.xml.rpc.encoding.soap.JAXRpcMapEntry", this.JAX_RPC_MAP_ENTRY_SOAPTYPE);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\SOAPSimpleTypeCreator11.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */