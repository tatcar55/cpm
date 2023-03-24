/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.TypeMappingInfo;
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorException;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaCustomType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.io.IOException;
/*     */ import javax.naming.OperationNotSupportedException;
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
/*     */ public class CustomSerializerWriter
/*     */   extends SerializerWriterBase
/*     */   implements GeneratorConstants
/*     */ {
/*     */   private String serializerName;
/*     */   private String serializerMemberName;
/*     */   private String deserializerName;
/*     */   private String deserializerMemberName;
/*     */   private SOAPType dataType;
/*     */   
/*     */   public CustomSerializerWriter(SOAPType type, Names names) {
/*  61 */     super((AbstractType)type, names);
/*  62 */     this.dataType = type;
/*  63 */     this.serializerName = names.getTypeQName(type.getName()) + "_Serializer";
/*  64 */     this.serializerMemberName = names.getClassMemberName(this.serializerName);
/*  65 */     this.deserializerName = names.getTypeQName(type.getName()) + "_Deserializer";
/*  66 */     this.deserializerMemberName = names.getClassMemberName(this.deserializerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createSerializer(IndentingWriter p, StringBuffer typeName, String varName, boolean encodeTypes, boolean multiRefEncoding, String typeMapping) throws IOException {
/*  77 */     throw new GeneratorException("generator.nestedGeneratorError", new LocalizableExceptionAdapter(new OperationNotSupportedException()));
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
/*     */   public void registerSerializer(IndentingWriter p, boolean encodeTypes, boolean multiRefEncoding, String typeMapping) throws IOException {
/*  89 */     TypeMappingInfo mappingInfo = ((JavaCustomType)this.type.getJavaType()).getTypeMappingInfo();
/*     */     
/*  91 */     if ((this.soapVer.equals(SOAPVersion.SOAP_11) && !mappingInfo.getEncodingStyle().equals("http://schemas.xmlsoap.org/soap/encoding/")) || (this.soapVer.equals(SOAPVersion.SOAP_12) && !mappingInfo.getEncodingStyle().equals("http://www.w3.org/2002/06/soap-encoding")))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       throw new GeneratorException("generator.unsupported.encoding.encountered", mappingInfo.getEncodingStyle().toString());
/*     */     }
/*     */ 
/*     */     
/* 101 */     String serFac = mappingInfo.getSerializerFactoryName();
/* 102 */     String deserFac = mappingInfo.getDeserializerFactoryName();
/* 103 */     StringBuffer typeName = new StringBuffer("type");
/* 104 */     declareType(p, typeName, this.type.getName(), false, false);
/* 105 */     p.pln(typeMapping + ".register(" + this.type.getJavaType().getName() + ".class, " + typeName.toString() + ", " + "new " + serFac + "(), " + "new " + deserFac + "());");
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
/*     */ 
/*     */   
/*     */   public void declareSerializer(IndentingWriter p, boolean isStatic, boolean isFinal) throws IOException {
/* 125 */     String modifier = getPrivateModifier(isStatic, isFinal);
/* 126 */     p.pln("private JAXRPCSerializer " + serializerMemberName() + ";");
/* 127 */     p.pln("private JAXRPCDeserializer " + deserializerMemberName() + ";");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeSerializer(IndentingWriter p, String typeName, String registry) throws IOException {
/* 135 */     p.pln(serializerMemberName() + " = (JAXRPCSerializer)registry.getSerializer(" + getEncodingStyleString() + ", " + this.type.getJavaType().getName() + ".class, " + typeName + ");");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     p.pln(deserializerMemberName() + " = (JAXRPCDeserializer)registry.getDeserializer(" + getEncodingStyleString() + ", " + this.type.getJavaType().getName() + ".class, " + typeName + ");");
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
/*     */   public String serializerName() {
/* 156 */     return this.serializerName;
/*     */   }
/*     */   
/*     */   public String serializerMemberName() {
/* 160 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   public String deserializerName() {
/* 164 */     return this.deserializerName;
/*     */   }
/*     */   
/*     */   public String deserializerMemberName() {
/* 168 */     return getPrefix((AbstractType)this.dataType) + "_" + this.deserializerMemberName;
/*     */   }
/*     */   
/*     */   protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
/* 172 */     return "private " + getModifier(isStatic, isFinal);
/*     */   }
/*     */   
/*     */   public AbstractType getElementType() {
/* 176 */     SOAPType elemType = ((SOAPArrayType)this.type).getElementType();
/* 177 */     while (elemType instanceof SOAPArrayType) {
/* 178 */       elemType = ((SOAPArrayType)elemType).getElementType();
/*     */     }
/* 180 */     return (AbstractType)elemType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\CustomSerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */