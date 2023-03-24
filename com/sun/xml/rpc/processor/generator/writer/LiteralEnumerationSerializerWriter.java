/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorException;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
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
/*     */ public class LiteralEnumerationSerializerWriter
/*     */   extends LiteralSerializerWriterBase
/*     */   implements GeneratorConstants
/*     */ {
/*     */   private String serializerMemberName;
/*     */   private LiteralType dataType;
/*     */   
/*     */   public LiteralEnumerationSerializerWriter(String basePackage, LiteralType type, Names names) {
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
/*  66 */     LiteralType type = (LiteralType)this.type;
/*  67 */     declareType(p, typeName, type.getName(), false, false);
/*  68 */     String encoder = type.getJavaType().getName() + "_Encoder";
/*     */     
/*  70 */     p.plnI(serializerName() + " " + serName + " = new " + "LiteralSimpleTypeSerializer" + "(" + typeName + ", \"\",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     p.pln(encoder + ".getInstance());");
/*  80 */     p.pO();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void declareSerializer(IndentingWriter p, boolean isStatic, boolean isFinal) throws IOException, GeneratorException {
/*  88 */     String modifier = getPrivateModifier(isStatic, isFinal);
/*  89 */     p.pln(modifier + serializerName() + " " + serializerMemberName() + ";");
/*     */   }
/*     */   
/*     */   public String serializerMemberName() {
/*  93 */     return getPrefix((AbstractType)this.dataType) + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   public String deserializerMemberName() {
/*  97 */     return getPrefix((AbstractType)this.dataType) + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
/* 101 */     return "private " + getModifier(isStatic, isFinal);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\LiteralEnumerationSerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */