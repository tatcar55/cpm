/*     */ package com.sun.xml.rpc.processor.modeler;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import java.util.HashMap;
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
/*     */ public class JavaSimpleTypeCreator
/*     */   implements ModelerConstants
/*     */ {
/*     */   public JavaSimpleType BOOLEAN_JAVATYPE;
/*     */   public JavaSimpleType BOXED_BOOLEAN_JAVATYPE;
/*     */   public JavaSimpleType BYTE_JAVATYPE;
/*     */   public JavaSimpleType BYTE_ARRAY_JAVATYPE;
/*     */   public JavaSimpleType BOXED_BYTE_JAVATYPE;
/*     */   public JavaSimpleType BOXED_BYTE_ARRAY_JAVATYPE;
/*     */   public JavaSimpleType DOUBLE_JAVATYPE;
/*     */   public JavaSimpleType BOXED_DOUBLE_JAVATYPE;
/*     */   public JavaSimpleType FLOAT_JAVATYPE;
/*     */   public JavaSimpleType BOXED_FLOAT_JAVATYPE;
/*     */   public JavaSimpleType INT_JAVATYPE;
/*     */   public JavaSimpleType BOXED_INTEGER_JAVATYPE;
/*     */   public JavaSimpleType LONG_JAVATYPE;
/*     */   public JavaSimpleType BOXED_LONG_JAVATYPE;
/*     */   public JavaSimpleType SHORT_JAVATYPE;
/*     */   public JavaSimpleType BOXED_SHORT_JAVATYPE;
/*     */   public JavaSimpleType DECIMAL_JAVATYPE;
/*     */   public JavaSimpleType BIG_INTEGER_JAVATYPE;
/*     */   public JavaSimpleType CALENDAR_JAVATYPE;
/*     */   public JavaSimpleType DATE_JAVATYPE;
/*     */   public JavaSimpleType STRING_JAVATYPE;
/*     */   public JavaSimpleType STRING_ARRAY_JAVATYPE;
/*     */   public JavaSimpleType QNAME_JAVATYPE;
/*     */   public JavaSimpleType VOID_JAVATYPE;
/*     */   public JavaSimpleType OBJECT_JAVATYPE;
/*     */   public JavaSimpleType SOAPELEMENT_JAVATYPE;
/*     */   public JavaSimpleType URI_JAVATYPE;
/*     */   public JavaSimpleType COLLECTION_JAVATYPE;
/*     */   public JavaSimpleType LIST_JAVATYPE;
/*     */   public JavaSimpleType SET_JAVATYPE;
/*     */   public JavaSimpleType VECTOR_JAVATYPE;
/*     */   public JavaSimpleType STACK_JAVATYPE;
/*     */   public JavaSimpleType LINKED_LIST_JAVATYPE;
/*     */   public JavaSimpleType ARRAY_LIST_JAVATYPE;
/*     */   public JavaSimpleType HASH_SET_JAVATYPE;
/*     */   public JavaSimpleType TREE_SET_JAVATYPE;
/*     */   public JavaSimpleType MAP_JAVATYPE;
/*     */   public JavaSimpleType HASH_MAP_JAVATYPE;
/*     */   public JavaSimpleType TREE_MAP_JAVATYPE;
/*     */   public JavaSimpleType HASHTABLE_JAVATYPE;
/*     */   public JavaSimpleType PROPERTIES_JAVATYPE;
/*     */   public JavaSimpleType JAX_RPC_MAP_ENTRY_JAVATYPE;
/*     */   public JavaSimpleType IMAGE_JAVATYPE;
/*     */   public JavaSimpleType MIME_MULTIPART_JAVATYPE;
/*     */   public JavaSimpleType SOURCE_JAVATYPE;
/*     */   public JavaSimpleType DATA_HANDLER_JAVATYPE;
/*  98 */   private Map javaTypes = new HashMap<Object, Object>();
/*     */   
/*     */   public JavaSimpleTypeCreator() {
/* 101 */     this.BOOLEAN_JAVATYPE = new JavaSimpleType("boolean", "false");
/* 102 */     this.javaTypes.put("boolean", this.BOOLEAN_JAVATYPE);
/* 103 */     this.BOXED_BOOLEAN_JAVATYPE = new JavaSimpleType("java.lang.Boolean", "null");
/*     */     
/* 105 */     this.javaTypes.put("java.lang.Boolean", this.BOXED_BOOLEAN_JAVATYPE);
/* 106 */     this.BYTE_JAVATYPE = new JavaSimpleType("byte", "(byte)0");
/* 107 */     this.javaTypes.put("byte", this.BYTE_JAVATYPE);
/* 108 */     this.BYTE_ARRAY_JAVATYPE = new JavaSimpleType("byte[]", "null");
/*     */     
/* 110 */     this.javaTypes.put("byte[]", this.BYTE_ARRAY_JAVATYPE);
/* 111 */     this.BOXED_BYTE_JAVATYPE = new JavaSimpleType("java.lang.Byte", "null");
/*     */     
/* 113 */     this.javaTypes.put("java.lang.Byte", this.BOXED_BYTE_JAVATYPE);
/* 114 */     this.BOXED_BYTE_ARRAY_JAVATYPE = new JavaSimpleType("java.lang.Byte[]", "null");
/*     */     
/* 116 */     this.javaTypes.put("java.lang.Byte[]", this.BOXED_BYTE_ARRAY_JAVATYPE);
/* 117 */     this.DOUBLE_JAVATYPE = new JavaSimpleType("double", "0");
/* 118 */     this.javaTypes.put("double", this.DOUBLE_JAVATYPE);
/* 119 */     this.BOXED_DOUBLE_JAVATYPE = new JavaSimpleType("java.lang.Double", "null");
/*     */     
/* 121 */     this.javaTypes.put("java.lang.Double", this.BOXED_DOUBLE_JAVATYPE);
/* 122 */     this.FLOAT_JAVATYPE = new JavaSimpleType("float", "0");
/* 123 */     this.javaTypes.put("float", this.FLOAT_JAVATYPE);
/* 124 */     this.BOXED_FLOAT_JAVATYPE = new JavaSimpleType("java.lang.Float", "null");
/*     */     
/* 126 */     this.javaTypes.put("java.lang.Float", this.BOXED_FLOAT_JAVATYPE);
/* 127 */     this.INT_JAVATYPE = new JavaSimpleType("int", "0");
/* 128 */     this.javaTypes.put("int", this.INT_JAVATYPE);
/* 129 */     this.BOXED_INTEGER_JAVATYPE = new JavaSimpleType("java.lang.Integer", "null");
/*     */     
/* 131 */     this.javaTypes.put("java.lang.Integer", this.BOXED_INTEGER_JAVATYPE);
/* 132 */     this.LONG_JAVATYPE = new JavaSimpleType("long", "0");
/* 133 */     this.javaTypes.put("long", this.LONG_JAVATYPE);
/* 134 */     this.BOXED_LONG_JAVATYPE = new JavaSimpleType("java.lang.Long", "null");
/*     */     
/* 136 */     this.javaTypes.put("java.lang.Long", this.BOXED_LONG_JAVATYPE);
/* 137 */     this.SHORT_JAVATYPE = new JavaSimpleType("short", "(short)0");
/*     */     
/* 139 */     this.javaTypes.put("short", this.SHORT_JAVATYPE);
/* 140 */     this.BOXED_SHORT_JAVATYPE = new JavaSimpleType("java.lang.Short", "null");
/*     */     
/* 142 */     this.javaTypes.put("java.lang.Short", this.BOXED_SHORT_JAVATYPE);
/* 143 */     this.DECIMAL_JAVATYPE = new JavaSimpleType("java.math.BigDecimal", "null");
/* 144 */     this.javaTypes.put("java.math.BigDecimal", this.DECIMAL_JAVATYPE);
/* 145 */     this.BIG_INTEGER_JAVATYPE = new JavaSimpleType("java.math.BigInteger", "null");
/*     */     
/* 147 */     this.javaTypes.put("java.math.BigInteger", this.BIG_INTEGER_JAVATYPE);
/* 148 */     this.CALENDAR_JAVATYPE = new JavaSimpleType("java.util.Calendar", "null");
/* 149 */     this.javaTypes.put("java.util.Calendar", this.CALENDAR_JAVATYPE);
/* 150 */     this.DATE_JAVATYPE = new JavaSimpleType("java.util.Date", "null");
/* 151 */     this.javaTypes.put("java.util.Date", this.DATE_JAVATYPE);
/* 152 */     this.STRING_JAVATYPE = new JavaSimpleType("java.lang.String", "null");
/* 153 */     this.javaTypes.put("java.lang.String", this.STRING_JAVATYPE);
/* 154 */     this.STRING_ARRAY_JAVATYPE = new JavaSimpleType("java.lang.String[]", "null");
/*     */     
/* 156 */     this.javaTypes.put("java.lang.String[]", this.STRING_ARRAY_JAVATYPE);
/* 157 */     this.QNAME_JAVATYPE = new JavaSimpleType("javax.xml.namespace.QName", "null");
/* 158 */     this.javaTypes.put("javax.xml.namespace.QName", this.QNAME_JAVATYPE);
/*     */ 
/*     */     
/* 161 */     this.VOID_JAVATYPE = new JavaSimpleType("void", null);
/* 162 */     this.javaTypes.put("void", this.VOID_JAVATYPE);
/* 163 */     this.OBJECT_JAVATYPE = new JavaSimpleType("java.lang.Object", null);
/* 164 */     this.javaTypes.put("java.lang.Object", this.OBJECT_JAVATYPE);
/* 165 */     this.SOAPELEMENT_JAVATYPE = new JavaSimpleType("javax.xml.soap.SOAPElement", null);
/* 166 */     this.javaTypes.put("javax.xml.soap.SOAPElement", this.SOAPELEMENT_JAVATYPE);
/* 167 */     if (VersionUtil.isJavaVersionGreaterThan1_3()) {
/* 168 */       this.URI_JAVATYPE = new JavaSimpleType("java.net.URI", null);
/* 169 */       this.javaTypes.put("java.net.URI", this.URI_JAVATYPE);
/*     */     } else {
/* 171 */       this.URI_JAVATYPE = new JavaSimpleType("java.lang.String", null);
/* 172 */       this.javaTypes.put("java.lang.String", this.URI_JAVATYPE);
/*     */     } 
/*     */ 
/*     */     
/* 176 */     this.COLLECTION_JAVATYPE = new JavaSimpleType("java.util.Collection", null);
/* 177 */     this.javaTypes.put("java.util.Collection", this.COLLECTION_JAVATYPE);
/* 178 */     this.LIST_JAVATYPE = new JavaSimpleType("java.util.List", null);
/* 179 */     this.javaTypes.put("java.util.List", this.LIST_JAVATYPE);
/* 180 */     this.SET_JAVATYPE = new JavaSimpleType("java.util.Set", null);
/* 181 */     this.javaTypes.put("java.util.Set", this.SET_JAVATYPE);
/* 182 */     this.VECTOR_JAVATYPE = new JavaSimpleType("java.util.Vector", null);
/* 183 */     this.javaTypes.put("java.util.Vector", this.VECTOR_JAVATYPE);
/* 184 */     this.STACK_JAVATYPE = new JavaSimpleType("java.util.Stack", null);
/* 185 */     this.javaTypes.put("java.util.Stack", this.STACK_JAVATYPE);
/* 186 */     this.LINKED_LIST_JAVATYPE = new JavaSimpleType("java.util.LinkedList", null);
/* 187 */     this.javaTypes.put("java.util.LinkedList", this.LINKED_LIST_JAVATYPE);
/* 188 */     this.ARRAY_LIST_JAVATYPE = new JavaSimpleType("java.util.ArrayList", null);
/* 189 */     this.javaTypes.put("java.util.ArrayList", this.ARRAY_LIST_JAVATYPE);
/* 190 */     this.HASH_SET_JAVATYPE = new JavaSimpleType("java.util.HashSet", null);
/* 191 */     this.javaTypes.put("java.util.HashSet", this.HASH_SET_JAVATYPE);
/* 192 */     this.TREE_SET_JAVATYPE = new JavaSimpleType("java.util.TreeSet", null);
/* 193 */     this.javaTypes.put("java.util.TreeSet", this.TREE_SET_JAVATYPE);
/*     */ 
/*     */     
/* 196 */     this.MAP_JAVATYPE = new JavaSimpleType("java.util.Map", null);
/* 197 */     this.javaTypes.put("java.util.Map", this.MAP_JAVATYPE);
/* 198 */     this.HASH_MAP_JAVATYPE = new JavaSimpleType("java.util.HashMap", null);
/* 199 */     this.javaTypes.put("java.util.HashMap", this.HASH_MAP_JAVATYPE);
/* 200 */     this.TREE_MAP_JAVATYPE = new JavaSimpleType("java.util.TreeMap", null);
/* 201 */     this.javaTypes.put("java.util.TreeMap", this.TREE_MAP_JAVATYPE);
/* 202 */     this.HASHTABLE_JAVATYPE = new JavaSimpleType("java.util.Hashtable", null);
/* 203 */     this.javaTypes.put("java.util.Hashtable", this.HASHTABLE_JAVATYPE);
/* 204 */     this.PROPERTIES_JAVATYPE = new JavaSimpleType("java.util.Properties", null);
/* 205 */     this.javaTypes.put("java.util.Properties", this.PROPERTIES_JAVATYPE);
/* 206 */     this.JAX_RPC_MAP_ENTRY_JAVATYPE = new JavaSimpleType("com.sun.xml.rpc.encoding.soap.JAXRpcMapEntry", null);
/*     */     
/* 208 */     this.javaTypes.put("com.sun.xml.rpc.encoding.soap.JAXRpcMapEntry", this.JAX_RPC_MAP_ENTRY_JAVATYPE);
/*     */ 
/*     */     
/* 211 */     this.IMAGE_JAVATYPE = new JavaSimpleType("java.awt.Image", null);
/* 212 */     this.javaTypes.put("java.awt.Image", this.IMAGE_JAVATYPE);
/* 213 */     this.MIME_MULTIPART_JAVATYPE = new JavaSimpleType("javax.mail.internet.MimeMultipart", null);
/* 214 */     this.javaTypes.put("javax.mail.internet.MimeMultipart", this.MIME_MULTIPART_JAVATYPE);
/* 215 */     this.SOURCE_JAVATYPE = new JavaSimpleType("javax.xml.transform.Source", null);
/* 216 */     this.javaTypes.put("javax.xml.transform.Source", this.SOURCE_JAVATYPE);
/* 217 */     this.DATA_HANDLER_JAVATYPE = new JavaSimpleType("javax.activation.DataHandler", null);
/* 218 */     this.javaTypes.put("javax.activation.DataHandler", this.DATA_HANDLER_JAVATYPE);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaSimpleType getJavaSimpleType(String classname) {
/* 223 */     return (JavaSimpleType)this.javaTypes.get(classname);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\JavaSimpleTypeCreator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */