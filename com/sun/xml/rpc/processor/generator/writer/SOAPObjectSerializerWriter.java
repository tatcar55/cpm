/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPObjectSerializerWriter
/*     */   extends SerializerWriterBase
/*     */   implements GeneratorConstants
/*     */ {
/*     */   protected String serializerName;
/*     */   protected String serializerMemberName;
/*     */   private SOAPType dataType;
/*     */   
/*     */   public SOAPObjectSerializerWriter(String basePackage, SOAPType type, Names names) {
/*  55 */     super((AbstractType)type, names);
/*  56 */     this.dataType = type;
/*  57 */     this.serializerName = names.typeObjectSerializerClassName(basePackage, type);
/*     */     
/*  59 */     this.serializerMemberName = names.getClassMemberName(this.serializerName);
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
/*  70 */     SOAPType type = (SOAPType)this.type;
/*  71 */     String nillable = type.isNillable() ? "NULLABLE" : "NOT_NULLABLE";
/*  72 */     String multiRef = (multiRefEncoding && type.isReferenceable()) ? "SERIALIZE_AS_REF" : "DONT_SERIALIZE_AS_REF";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     String encodeType = encodeTypes ? "ENCODE_TYPE" : "DONT_ENCODE_TYPE";
/*     */     
/*  79 */     if (type instanceof com.sun.xml.rpc.processor.model.soap.RPCRequestOrderedStructureType || type instanceof com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType || type instanceof com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType || type.getJavaType() instanceof com.sun.xml.rpc.processor.model.java.JavaException) {
/*     */ 
/*     */ 
/*     */       
/*  83 */       if (!(type.getJavaType() instanceof com.sun.xml.rpc.processor.model.java.JavaException))
/*  84 */         encodeType = "DONT_ENCODE_TYPE"; 
/*  85 */       multiRef = "DONT_SERIALIZE_AS_REF";
/*     */     } 
/*     */     
/*  88 */     declareType(p, typeName, type.getName(), false, false);
/*  89 */     p.plnI("CombinedSerializer " + serName + " = new " + this.serializerName + "(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     p.pln(encodeType + ", " + nillable + ", " + getEncodingStyleString() + ");");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     p.pO();
/* 106 */     if (type.isReferenceable()) {
/* 107 */       p.pln(serName + " = new " + "ReferenceableSerializerImpl" + "(" + multiRef + ", " + serName + ", " + getSOAPVersionString() + ");");
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
/* 126 */     String modifier = getPrivateModifier(isStatic, isFinal);
/* 127 */     p.pln(modifier + "CombinedSerializer" + " " + serializerMemberName() + ";");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String serializerName() {
/* 136 */     return this.serializerName;
/*     */   }
/*     */   
/*     */   public String serializerMemberName() {
/* 140 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   public String deserializerName() {
/* 144 */     return this.serializerName;
/*     */   }
/*     */   
/*     */   public String deserializerMemberName() {
/* 148 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
/* 152 */     return "private " + getModifier(isStatic, isFinal);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\SOAPObjectSerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */