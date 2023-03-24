/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.generator.SimpleToBoxedUtil;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public class ArraySerializerWriter
/*     */   extends SerializerWriterBase
/*     */   implements GeneratorConstants
/*     */ {
/*     */   private String serializerMemberName;
/*     */   private AbstractType baseElemType;
/*     */   private SerializerWriterFactory writerFactory;
/*     */   private SOAPType dataType;
/*     */   private String basePackage;
/*     */   
/*     */   public ArraySerializerWriter(String basePackage, SOAPType type, Names names) {
/*  58 */     super((AbstractType)type, names);
/*  59 */     this.dataType = type;
/*  60 */     this.basePackage = basePackage;
/*     */     
/*  62 */     String serializerName = names.typeObjectArraySerializerClassName(basePackage, type);
/*     */ 
/*     */ 
/*     */     
/*  66 */     this.serializerMemberName = names.getClassMemberName(serializerName, (AbstractType)type) + ((SOAPArrayType)type).getRank();
/*     */ 
/*     */     
/*  69 */     this.baseElemType = getBaseElementType();
/*  70 */     this.writerFactory = new SerializerWriterFactoryImpl(names);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createSerializer(IndentingWriter p, StringBuffer typeName, String serName, boolean encodeTypes, boolean multiRefEncoding, String typeMapping) throws IOException {
/*  81 */     SOAPArrayType type = (SOAPArrayType)this.type;
/*  82 */     String nillable = type.isNillable() ? "NULLABLE" : "NOT_NULLABLE";
/*  83 */     String referenceable = type.isReferenceable() ? "REFERENCEABLE" : "NOT_REFERENCEABLE";
/*     */ 
/*     */ 
/*     */     
/*  87 */     String multiRef = (multiRefEncoding && type.isReferenceable()) ? "SERIALIZE_AS_REF" : "DONT_SERIALIZE_AS_REF";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     String encodeType = encodeTypes ? "ENCODE_TYPE" : "DONT_ENCODE_TYPE";
/*     */ 
/*     */     
/*  95 */     declareType(p, typeName, type.getName(), false, false);
/*  96 */     StringBuffer elemName = new StringBuffer("elemName");
/*  97 */     if (type.getElementName() != null) {
/*  98 */       declareType(p, elemName, type.getElementName(), false, false);
/*     */     } else {
/* 100 */       elemName = new StringBuffer("null");
/*     */     } 
/* 102 */     StringBuffer elemType = new StringBuffer("elemType");
/* 103 */     declareType(p, elemType, this.baseElemType.getName(), false, false);
/* 104 */     if (isSimpleType(this.baseElemType.getJavaType().getName()) && !((SOAPType)this.baseElemType).isReferenceable()) {
/*     */       
/* 106 */       SerializerWriter writer = this.writerFactory.createWriter(this.basePackage, this.baseElemType);
/*     */       
/* 108 */       StringBuffer serNameElemType = new StringBuffer(serName + elemType);
/* 109 */       writer.createSerializer(p, serNameElemType, serName + "elemSerializer", encodeTypes, multiRefEncoding, typeMapping);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       p.plnI(serializerName() + " " + serName + " = new SimpleTypeArraySerializer(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       p.pln(encodeType + ", " + nillable + ", " + getEncodingStyleString() + " , ");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 130 */       p.pln(elemName + ", " + elemType + ", " + this.baseElemType.getJavaType().getName() + ".class, " + type.getRank() + ", " + type.getSize() + ", (SimpleTypeSerializer)" + serName + "elemSerializer, " + getSOAPVersionString() + ");");
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
/* 145 */       p.pO();
/*     */     } else {
/* 147 */       p.plnI(serializerName() + " " + serName + " = new ObjectArraySerializer(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       p.pln(encodeType + ", " + nillable + ", " + getEncodingStyleString() + " , ");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 161 */       p.pln(elemName + ", " + elemType + ", " + this.baseElemType.getJavaType().getName() + ".class, " + type.getRank() + ", " + type.getSize() + ", " + getSOAPVersionString() + ");");
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
/* 174 */       p.pO();
/*     */     } 
/* 176 */     if (type.isReferenceable()) {
/* 177 */       p.pln(serName + " = new " + "ReferenceableSerializerImpl" + "(" + multiRef + ", " + serName + ", " + getSOAPVersionString() + ");");
/*     */     }
/*     */   }
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
/*     */   public void declareSerializer(IndentingWriter p, boolean isStatic, boolean isFinal) throws IOException {
/* 196 */     String modifier = getPrivateModifier(isStatic, isFinal);
/* 197 */     p.pln(modifier + serializerName() + " " + serializerMemberName() + ";");
/*     */   }
/*     */   
/*     */   public String serializerMemberName() {
/* 201 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   public String deserializerMemberName() {
/* 205 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
/* 209 */     return "private " + getModifier(isStatic, isFinal);
/*     */   }
/*     */   
/*     */   public AbstractType getBaseElementType() {
/* 213 */     SOAPType elemType = ((SOAPArrayType)this.type).getElementType();
/* 214 */     while (elemType instanceof SOAPArrayType) {
/* 215 */       elemType = ((SOAPArrayType)elemType).getElementType();
/*     */     }
/* 217 */     return (AbstractType)elemType;
/*     */   }
/*     */   
/*     */   private boolean isSimpleType(String javaName) {
/* 221 */     return (SimpleToBoxedUtil.isPrimitive(javaName) || boxedSet.contains(javaName));
/*     */   }
/*     */ 
/*     */   
/* 225 */   private static Set boxedSet = null;
/*     */   
/*     */   static {
/* 228 */     boxedSet = new HashSet();
/* 229 */     boxedSet.add("java.lang.Boolean");
/* 230 */     boxedSet.add("java.lang.Byte");
/* 231 */     boxedSet.add("java.lang.Double");
/* 232 */     boxedSet.add("java.lang.Float");
/* 233 */     boxedSet.add("java.lang.Int");
/* 234 */     boxedSet.add("java.lang.Long");
/* 235 */     boxedSet.add("java.lang.Short");
/* 236 */     boxedSet.add("java.lang.String");
/* 237 */     boxedSet.add("javax.xml.namespace.QName");
/* 238 */     boxedSet.add("java.lang.BigDecimal");
/* 239 */     boxedSet.add("java.lang.BigInteger");
/* 240 */     boxedSet.add("java.net.URI");
/* 241 */     boxedSet.add("java.util.Calendar");
/* 242 */     boxedSet.add("java.util.Date");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\ArraySerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */