/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
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
/*     */ public class LiteralSequenceSerializerWriter
/*     */   extends LiteralSerializerWriterBase
/*     */   implements GeneratorConstants
/*     */ {
/*     */   protected String serializerName;
/*     */   protected String serializerMemberName;
/*     */   protected LiteralType dataType;
/*     */   
/*     */   public LiteralSequenceSerializerWriter(String basePackage, LiteralType type, Names names) {
/*  52 */     super((AbstractType)type, names);
/*  53 */     this.serializerName = names.typeObjectSerializerClassName(basePackage, type);
/*     */ 
/*     */ 
/*     */     
/*  57 */     this.serializerMemberName = names.getClassMemberName(this.serializerName);
/*  58 */     this.dataType = type;
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
/*  69 */     LiteralType type = (LiteralType)this.type;
/*  70 */     boolean isExtendable = (((LiteralStructuredType)type).getParentType() != null);
/*     */     
/*  72 */     String encodeType = (encodeTypes && isExtendable) ? "ENCODE_TYPE" : "DONT_ENCODE_TYPE";
/*     */ 
/*     */     
/*  75 */     declareType(p, typeName, type.getName(), false, false);
/*  76 */     p.plnI("CombinedSerializer " + serName + " = new " + serializerName() + "(" + typeName + ", \"\", " + encodeType + ");");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     p.pO();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void declareSerializer(IndentingWriter p, boolean isStatic, boolean isFinal) throws IOException {
/*  95 */     String modifier = getPrivateModifier(isStatic, isFinal);
/*  96 */     p.pln(modifier + "CombinedSerializer" + " " + serializerMemberName() + ";");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String serializerName() {
/* 105 */     return this.serializerName;
/*     */   }
/*     */   
/*     */   public String serializerMemberName() {
/* 109 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   public String deserializerName() {
/* 113 */     return this.serializerName;
/*     */   }
/*     */   
/*     */   public String deserializerMemberName() {
/* 117 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
/* 121 */     return "private " + getModifier(isStatic, isFinal);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\LiteralSequenceSerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */