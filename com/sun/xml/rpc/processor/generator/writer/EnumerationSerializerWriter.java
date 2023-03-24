/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorException;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import java.io.IOException;
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
/*     */ public class EnumerationSerializerWriter
/*     */   extends SerializerWriterBase
/*     */   implements GeneratorConstants
/*     */ {
/*     */   private String serializerMemberName;
/*     */   private SOAPType dataType;
/*     */   
/*     */   public EnumerationSerializerWriter(String basePackage, SOAPType type, Names names) {
/*  51 */     super((AbstractType)type, names);
/*  52 */     this.dataType = type;
/*  53 */     String serializerName = names.typeObjectSerializerClassName(basePackage, type);
/*     */     
/*  55 */     this.serializerMemberName = names.getClassMemberName(serializerName, (AbstractType)type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createSerializer(IndentingWriter p, StringBuffer typeName, String serName, boolean encodeTypes, boolean multiRefEncoding, String typeMapping) throws IOException, GeneratorException {
/*  66 */     SOAPType type = (SOAPType)this.type;
/*  67 */     String nillable = type.isNillable() ? "NULLABLE" : "NOT_NULLABLE";
/*  68 */     String referenceable = type.isReferenceable() ? "REFERENCEABLE" : "NOT_REFERENCEABLE";
/*     */ 
/*     */ 
/*     */     
/*  72 */     String multiRef = (multiRefEncoding && type.isReferenceable()) ? "SERIALIZE_AS_REF" : "DONT_SERIALIZE_AS_REF";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     String encodeType = encodeTypes ? "ENCODE_TYPE" : "DONT_ENCODE_TYPE";
/*     */     
/*  79 */     declareType(p, typeName, type.getName(), false, false);
/*  80 */     String encoder = type.getJavaType().getName() + "_Encoder";
/*     */     
/*  82 */     p.plnI(serializerName() + " " + serName + " = new " + "SimpleTypeSerializer" + "(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     p.pln(encodeType + ", " + nillable + ", " + getEncodingStyleString() + ", " + encoder + ".getInstance());");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     p.pO();
/* 101 */     if (type.isReferenceable()) {
/* 102 */       p.pln(serName + " = new " + "ReferenceableSerializerImpl" + "(" + multiRef + ", " + serName + ", " + getSOAPVersionString() + ");");
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
/*     */   public void declareSerializer(IndentingWriter p, boolean isStatic, boolean isFinal) throws IOException, GeneratorException {
/* 121 */     String modifier = getPrivateModifier(isStatic, isFinal);
/* 122 */     p.pln(modifier + serializerName() + " " + serializerMemberName() + ";");
/*     */   }
/*     */   
/*     */   public String serializerMemberName() {
/* 126 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   public String deserializerMemberName() {
/* 130 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
/* 134 */     return "private " + getModifier(isStatic, isFinal);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\EnumerationSerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */