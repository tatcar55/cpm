/*    */ package com.sun.xml.rpc.processor.generator.writer;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*    */ import com.sun.xml.rpc.processor.generator.Names;
/*    */ import com.sun.xml.rpc.processor.model.AbstractType;
/*    */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*    */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*    */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LiteralFragmentSerializerWriter
/*    */   extends LiteralSerializerWriterBase
/*    */   implements GeneratorConstants
/*    */ {
/*    */   private String serializerMemberName;
/*    */   private LiteralType dataType;
/*    */   
/*    */   public LiteralFragmentSerializerWriter(LiteralFragmentType type, Names names) {
/* 50 */     super((AbstractType)type, names);
/* 51 */     this.dataType = (LiteralType)type;
/* 52 */     String serializerName = "LiteralFragmentSerializer";
/* 53 */     this.serializerMemberName = names.getLiteralFragmentTypeSerializerMemberName(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void createSerializer(IndentingWriter p, StringBuffer typeName, String serName, boolean encodeTypes, boolean multiRefEncoding, String typeMapping) throws IOException {
/* 65 */     LiteralFragmentType type = (LiteralFragmentType)this.type;
/* 66 */     String nillable = type.isNillable() ? "NULLABLE" : "NOT_NULLABLE";
/* 67 */     declareType(p, typeName, type.getName(), false, false);
/* 68 */     p.pln(serializerName() + " " + serName + " = new " + "LiteralFragmentSerializer" + "(" + typeName + ", " + nillable + ", \"\");");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void declareSerializer(IndentingWriter p, boolean isStatic, boolean isFinal) throws IOException {
/* 86 */     String modifier = getPrivateModifier(isStatic, isFinal);
/* 87 */     p.pln(modifier + serializerName() + " " + serializerMemberName() + ";");
/*    */   }
/*    */   
/*    */   public String serializerMemberName() {
/* 91 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*    */   }
/*    */   
/*    */   public String deserializerMemberName() {
/* 95 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*    */   }
/*    */   
/*    */   protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
/* 99 */     return "private " + getModifier(isStatic, isFinal);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\LiteralFragmentSerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */