/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.BuiltInTypes;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import java.net.URI;
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
/*     */ public class LiteralSimpleTypeCreator
/*     */   extends JavaSimpleTypeCreator
/*     */   implements RmiConstants
/*     */ {
/*  79 */   public LiteralSimpleType XSD_BOOLEAN_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.BOOLEAN, this.BOOLEAN_JAVATYPE);
/*     */   
/*  81 */   public LiteralSimpleType XSD_BOXED_BOOLEAN_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.BOOLEAN, this.BOXED_BOOLEAN_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public LiteralSimpleType XSD_BYTE_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.BYTE, this.BYTE_JAVATYPE);
/*     */   
/*  88 */   public LiteralSimpleType XSD_BYTE_ARRAY_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.BASE64_BINARY, this.BYTE_ARRAY_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public LiteralSimpleType XSD_BOXED_BYTE_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.BYTE, this.BOXED_BYTE_JAVATYPE, true);
/*     */   
/*  95 */   public LiteralSimpleType XSD_BOXED_BYTE_ARRAY_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.BASE64_BINARY, this.BOXED_BYTE_ARRAY_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public LiteralSimpleType XSD_DOUBLE_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.DOUBLE, this.DOUBLE_JAVATYPE);
/*     */   
/* 102 */   public LiteralSimpleType XSD_BOXED_DOUBLE_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.DOUBLE, this.BOXED_DOUBLE_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public LiteralSimpleType XSD_FLOAT_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.FLOAT, this.FLOAT_JAVATYPE);
/*     */   
/* 109 */   public LiteralSimpleType XSD_BOXED_FLOAT_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.FLOAT, this.BOXED_FLOAT_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public LiteralSimpleType XSD_INT_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.INT, this.INT_JAVATYPE);
/*     */   
/* 116 */   public LiteralSimpleType XSD_BOXED_INTEGER_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.INT, this.BOXED_INTEGER_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public LiteralSimpleType XSD_INTEGER_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.INTEGER, this.BIG_INTEGER_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public LiteralSimpleType XSD_LONG_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.LONG, this.LONG_JAVATYPE);
/*     */   
/* 128 */   public LiteralSimpleType XSD_BOXED_LONG_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.LONG, this.BOXED_LONG_JAVATYPE, true);
/*     */   
/* 130 */   public LiteralSimpleType XSD_SHORT_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.SHORT, this.SHORT_JAVATYPE);
/*     */   
/* 132 */   public LiteralSimpleType XSD_BOXED_SHORT_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.SHORT, this.BOXED_SHORT_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public LiteralSimpleType XSD_DECIMAL_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.DECIMAL, this.DECIMAL_JAVATYPE, true);
/*     */   
/* 139 */   public LiteralSimpleType XSD_DATE_TIME_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.DATE_TIME, this.DATE_JAVATYPE, true);
/*     */   
/* 141 */   public LiteralSimpleType XSD_DATE_TIME_CALENDAR_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.DATE_TIME, this.CALENDAR_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public LiteralSimpleType XSD_STRING_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.STRING, this.STRING_JAVATYPE, true);
/*     */   
/* 148 */   public LiteralSimpleType XSD_QNAME_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.QNAME, this.QNAME_JAVATYPE, true);
/*     */   
/* 150 */   public LiteralSimpleType XSD_VOID_LITERALTYPE = new LiteralSimpleType(null, this.VOID_JAVATYPE);
/* 151 */   public LiteralSimpleType XSD_ANYTYPE_LITERALTYPE = new LiteralSimpleType(SchemaConstants.QNAME_TYPE_URTYPE, this.OBJECT_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public LiteralSimpleType XSD_ANY_URI_LITERALTYPE = new LiteralSimpleType(BuiltInTypes.ANY_URI, this.URI_JAVATYPE, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeJavaToXmlTypeMap(Map<Class<?>, QName> typeMap) {
/* 162 */     Map<Object, Object> typeNameToDescription = new HashMap<Object, Object>();
/* 163 */     initializeTypeMap(typeNameToDescription);
/*     */     
/* 165 */     Iterator<String> eachClassName = typeNameToDescription.keySet().iterator();
/* 166 */     while (eachClassName.hasNext()) {
/*     */       
/* 168 */       String className = eachClassName.next();
/* 169 */       LiteralType typeDescription = (LiteralType)typeNameToDescription.get(className);
/*     */ 
/*     */       
/* 172 */       Class<?> javaType = classForName(className);
/* 173 */       if (javaType != null) {
/* 174 */         typeMap.put(javaType, typeDescription.getName());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Class classForName(String name) {
/* 180 */     return SOAPSimpleTypeCreatorBase.classForName(name);
/*     */   }
/*     */   
/*     */   public void initializeTypeMap(Map<String, LiteralSimpleType> typeMap) {
/* 184 */     typeMap.put("java.lang.Boolean", this.XSD_BOXED_BOOLEAN_LITERALTYPE);
/* 185 */     typeMap.put("boolean", this.XSD_BOOLEAN_LITERALTYPE);
/* 186 */     typeMap.put("java.lang.Byte", this.XSD_BOXED_BYTE_LITERALTYPE);
/*     */ 
/*     */ 
/*     */     
/* 190 */     typeMap.put("byte", this.XSD_BYTE_LITERALTYPE);
/* 191 */     typeMap.put("byte[]", this.XSD_BYTE_ARRAY_LITERALTYPE);
/* 192 */     typeMap.put("java.lang.Double", this.XSD_BOXED_DOUBLE_LITERALTYPE);
/* 193 */     typeMap.put("double", this.XSD_DOUBLE_LITERALTYPE);
/* 194 */     typeMap.put("java.lang.Float", this.XSD_BOXED_FLOAT_LITERALTYPE);
/* 195 */     typeMap.put("float", this.XSD_FLOAT_LITERALTYPE);
/* 196 */     typeMap.put("java.lang.Integer", this.XSD_BOXED_INTEGER_LITERALTYPE);
/* 197 */     typeMap.put("int", this.XSD_INT_LITERALTYPE);
/* 198 */     typeMap.put("java.lang.Long", this.XSD_BOXED_LONG_LITERALTYPE);
/* 199 */     typeMap.put("long", this.XSD_LONG_LITERALTYPE);
/* 200 */     typeMap.put("java.lang.Short", this.XSD_BOXED_SHORT_LITERALTYPE);
/* 201 */     typeMap.put("short", this.XSD_SHORT_LITERALTYPE);
/* 202 */     typeMap.put("java.lang.String", this.XSD_STRING_LITERALTYPE);
/* 203 */     typeMap.put("java.math.BigDecimal", this.XSD_DECIMAL_LITERALTYPE);
/* 204 */     typeMap.put("java.math.BigInteger", this.XSD_INTEGER_LITERALTYPE);
/* 205 */     typeMap.put("java.util.Date", this.XSD_DATE_TIME_LITERALTYPE);
/* 206 */     typeMap.put("java.util.Calendar", this.XSD_DATE_TIME_CALENDAR_LITERALTYPE);
/* 207 */     typeMap.put("javax.xml.namespace.QName", this.XSD_QNAME_LITERALTYPE);
/* 208 */     typeMap.put("void", this.XSD_VOID_LITERALTYPE);
/*     */ 
/*     */     
/* 211 */     if (VersionUtil.isJavaVersionGreaterThan1_3()) {
/* 212 */       typeMap.put(URI.class.getName(), this.XSD_ANY_URI_LITERALTYPE);
/*     */     } else {
/* 214 */       typeMap.put("java.net.URI", this.XSD_ANY_URI_LITERALTYPE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\LiteralSimpleTypeCreator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */